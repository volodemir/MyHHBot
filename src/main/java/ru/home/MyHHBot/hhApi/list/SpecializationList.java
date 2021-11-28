package ru.home.MyHHBot.hhApi.list;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.MyHHBot.hhApi.HH;
import ru.home.MyHHBot.hhApi.Specialization;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Getter
public class SpecializationList {
    private Specialization[] specializationArr;
    private HH[] specializationSubArr;

    public SpecializationList(Specialization[] specializationArr) {
        this.specializationArr = specializationArr;
    }

    @SneakyThrows
    public InlineKeyboardMarkup generateSpecializationList(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hh.ru/specializations"))
                .build();

        List<List<InlineKeyboardButton>> specButtons = new ArrayList<>();
        InlineKeyboardMarkup specMenu = new InlineKeyboardMarkup();

        HttpResponse<String> response = client.send(requestHttp, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper(); //преобразование из строки в JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String body = response.body();
        specializationArr = mapper.readValue(body, Specialization[].class);
        for (Specialization s : specializationArr) {
            List<InlineKeyboardButton> specRow = new ArrayList<>();
            InlineKeyboardButton setSpecButton = new InlineKeyboardButton();
            setSpecButton.setText(s.getName());
            setSpecButton.setCallbackData(s.getId());
            specRow.add(setSpecButton);
            specButtons.add(specRow);
        }
        response.body();
        specMenu.setKeyboard(specButtons);


        return specMenu;
    }
    @SneakyThrows
    public InlineKeyboardMarkup generateSpecializationSubList(String defSpecialization){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hh.ru/specializations"))
                .build();

        List<List<InlineKeyboardButton>> specButtons = new ArrayList<>();
        InlineKeyboardMarkup specMenu = new InlineKeyboardMarkup();

        HttpResponse<String> response = client.send(requestHttp, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper(); //преобразование из строки в JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String body = response.body();
        specializationSubArr = mapper.readValue(body, HH[].class);
        for (HH s : specializationSubArr) {
            s.getSpecializations().forEach(specialization -> {
                if(defSpecialization.equals(specialization.getId().substring(0, specialization.getId().indexOf(".")))){
                    List<InlineKeyboardButton> specRow = new ArrayList<>();
                    InlineKeyboardButton setSpecButton = new InlineKeyboardButton();
                    setSpecButton.setText(specialization.getName());
                    setSpecButton.setCallbackData(specialization.getId());
                    specRow.add(setSpecButton);
                    specButtons.add(specRow);
                }
            });
        }
        response.body();
        specMenu.setKeyboard(specButtons);

        return specMenu;
    }
}
