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
public class CountryList {
    private Country[] countryArr;

    public CountryList(Country[] countryArr) {
        this.countryArr = countryArr;
    }

    @SneakyThrows
    public InlineKeyboardMarkup generateCountryList(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestHttp = HttpRequest.newBuilder()
                .uri(URI.create("https://api.hh.ru/areas/countries"))
                .build();

        List<List<InlineKeyboardButton>> countryButtons = new ArrayList<>();
        InlineKeyboardMarkup countryMenu = new InlineKeyboardMarkup();

            HttpResponse<String> response = client.send(requestHttp, HttpResponse.BodyHandlers.ofString());
            ObjectMapper mapper = new ObjectMapper(); //преобразование из строки в JSON
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            String body = response.body();
            countryArr = mapper.readValue(body, Country[].class);
            //int count = 0;
            for (Country c : countryArr) {
                /*if (c!=null){
                    count++;
                }*/
                    //System.out.println(c.getId());
                    List<InlineKeyboardButton> countryRow = new ArrayList<>();
                    InlineKeyboardButton setCountryButton = new InlineKeyboardButton();
                    setCountryButton.setText(c.getName());
                    setCountryButton.setCallbackData(c.getId());
                    countryRow.add(setCountryButton);
                    countryButtons.add(countryRow);
            }
            //List<InlineKeyboardButton> regionRowNext;

            response.body();
            countryMenu.setKeyboard(countryButtons);


        return countryMenu;
    }
}
