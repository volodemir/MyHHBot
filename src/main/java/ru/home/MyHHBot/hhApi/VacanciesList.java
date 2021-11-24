package ru.home.MyHHBot.hhApi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.home.MyHHBot.botApi.handlers.fillingProfile.UserProfileData;
import ru.home.MyHHBot.cache.UserDataCache;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.URI;
import java.net.http.HttpResponse;
import java.util.concurrent.atomic.AtomicReference;

public class VacanciesList {
    private UserDataCache userDataCache;

    public VacanciesList(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    @SneakyThrows
    public SendMessage getVacanciesList(Message inputMsg) {
        long userId = inputMsg.getFrom().getId();
        String message = inputMsg.getText().replaceAll("\\s+","");
        System.out.println("vacancylist " + userId);
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage replyToUser;
        System.out.println(profileData.getGeneralRegionId() + profileData.isDisplayWages() + profileData.getMinSalary());
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestHttp = HttpRequest.newBuilder()

                .uri(URI.create("https://api.hh.ru/vacancies?text=" + message
                        + "&area=" + profileData.getGeneralRegionId() + "&only_with_salary=" + profileData.isDisplayWages()
                        + "&salary" + profileData.getMinSalary()))
                .build();
        SendMessage sm = new SendMessage();
        try {
            HttpResponse<String> response = client.send(requestHttp, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper(); //преобразование из строки в JSON
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String body = response.body();
            HH hh = mapper.readValue(body, HH.class);
            hh.getItems().forEach(job -> {
                sm.setText("Вакансия: " + job.name + "\nСсылка: http://hh.ru/vacancy/" + job.id);
                sm.setChatId(inputMsg.getChatId());
            });
            response.body();
        }catch (IOException | InterruptedException el) {
            System.out.println(el.getMessage());
        }
        replyToUser = sm;
        return replyToUser;
    }
}
