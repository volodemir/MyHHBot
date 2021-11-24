package ru.home.MyHHBot.hhApi;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Getter
public class CityList {
    private HH[] regionArr;


    public CityList(HH[] regionArr) {
        this.regionArr = regionArr;
    }

    @SneakyThrows
    public InlineKeyboardMarkup generateCityList(String destRegionId){
        System.out.println("ищу города");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hh.ru/areas"))
                .build();

        List<List<InlineKeyboardButton>> cityButtons = new ArrayList<>();
        InlineKeyboardMarkup cityMenu = new InlineKeyboardMarkup();

        HttpResponse<String> response = client.send(requestHttp, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper(); //преобразование из строки в JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String body = response.body();
        regionArr = mapper.readValue(body, HH[].class);
        for(HH r : regionArr) {
            r.getAreas().forEach(reg -> {
                reg.getAreas().forEach(city -> {
                    System.out.println(city.getParent_id()
                    );
                if (city.getParent_id().equals(destRegionId)) {
                    System.out.println(destRegionId);
                    System.out.println(city.getParent_id());
                    System.out.println("Выполняюсь city");
                    List<InlineKeyboardButton> cityRow = new ArrayList<>();
                    InlineKeyboardButton setCityButton = new InlineKeyboardButton();
                    setCityButton.setText(city.getName());
                    setCityButton.setCallbackData(city.getId());
                    cityRow.add(setCityButton);
                    cityButtons.add(cityRow);
                    System.out.println("regGeetName " + city.getName());
                }});});
                cityMenu.setKeyboard(cityButtons);
        }
        return cityMenu;
    }
}
