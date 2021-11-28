package ru.home.MyHHBot.hhApi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.MyHHBot.botApi.userData.UserProfileData;
import ru.home.MyHHBot.botApi.userData.cache.UserDataCache;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Data
public class VacanciesList {
    private UserDataCache userDataCache;
    private String message;
    private HH vacancy;
    private String jobId;

    public VacanciesList(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    @SneakyThrows
    public SendMessage getVacanciesList(Message inputMsg, long userId) {
        message = inputMsg.getText().replaceAll("\\s+","");

        UserProfileData profileData = userDataCache.getUserProfileData(userId);

        SendMessage replyToUser = new SendMessage();
        replyToUser.setChatId(inputMsg.getChatId());

        StringBuilder sb = new StringBuilder();
        StringBuilder url = sb.append("https://api.hh.ru/vacancies?text=").append(message).append("&only_with_salary=").append(profileData.isDisplayWages());
        if (profileData.getGeneralRegionId() != null) {sb.append("&area=").append(profileData.getGeneralRegionId());}
        if (profileData.getSpecializationId() != null) {sb.append("&specialization=").append(profileData.getSpecializationId());}
        if (profileData.getMinSalary() != 0) {sb.append("&salary").append(profileData.getMinSalary());}
        System.out.println(url.toString());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create(url.toString()))
                .build();

        List<List<InlineKeyboardButton>> vacancyButtons = new ArrayList<>();
        InlineKeyboardMarkup vacancyMenu = new InlineKeyboardMarkup();

            HttpResponse<String> response = client.send(requestHttp, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String body = response.body();
            vacancy = mapper.readValue(body, HH.class);

            StringBuilder vacancySb = new StringBuilder();
            StringBuilder jobIdSB = new StringBuilder();
                vacancy.getItems().forEach(job -> {
                    jobIdSB.append(job.getId());
                    vacancySb.append("Вакансия: ")
                            .append(job.name)
                            .append("\nСсылка: http://hh.ru/vacancy/")
                            .append(job.id)
                            .append(" " + job.employer.getName() + "\n");
                    jobId = jobIdSB.toString();
                    List<InlineKeyboardButton> vacancyRow = new ArrayList<>();
                    InlineKeyboardButton setVacancyButton = new InlineKeyboardButton();
                    setVacancyButton.setText(job.getName() + " :: " + job.employer.getName());
                    setVacancyButton.setCallbackData(job.getId());
                    vacancyRow.add(setVacancyButton);
                    vacancyButtons.add(vacancyRow);
                    });
            replyToUser.setText(vacancySb.toString());
            vacancyMenu.setKeyboard(vacancyButtons);

        return replyToUser;
}}
