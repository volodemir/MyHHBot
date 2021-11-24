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
public class RegionList {
private HH[] regionArr;

    public RegionList(HH[] regionArr) {
        this.regionArr = regionArr;
    }

    @SneakyThrows
    public InlineKeyboardMarkup generateRegionList(String destCountryId){
        System.out.println("ищу");
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hh.ru/areas"))
                .build();

        List<List<InlineKeyboardButton>> regionButtons = new ArrayList<>();
        InlineKeyboardMarkup regionMenu = new InlineKeyboardMarkup();

        HttpResponse<String> response = client.send(requestHttp, HttpResponse.BodyHandlers.ofString());
        ObjectMapper mapper = new ObjectMapper(); //преобразование из строки в JSON
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String body = response.body();
        regionArr = mapper.readValue(body, HH[].class);
        for(HH r : regionArr) {
            r.getAreas().forEach(reg -> {
                        if (reg.getParent_id().equals(destCountryId)) {
                            System.out.println(destCountryId);
                            System.out.println(reg.getParent_id());
                            System.out.println("Выполняюсь");
                            List<InlineKeyboardButton> regionRow = new ArrayList<>();
                            InlineKeyboardButton setRegionButton = new InlineKeyboardButton();
                            setRegionButton.setText(reg.getName());
                            setRegionButton.setCallbackData(reg.getId());
                            regionRow.add(setRegionButton);
                            regionButtons.add(regionRow);
                            System.out.println("regGeetName " + reg.getName());
                        }
                regionMenu.setKeyboard(regionButtons);
                    });
        }
        return regionMenu;
    }
}
