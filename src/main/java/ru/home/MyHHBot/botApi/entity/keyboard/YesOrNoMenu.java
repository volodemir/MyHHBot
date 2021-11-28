package ru.home.MyHHBot.botApi.entity.keyboard;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class YesOrNoMenu {
    private List<InlineKeyboardButton> yesRow;
    private List<InlineKeyboardButton> noRow;
    private List<List<InlineKeyboardButton>> buttonsMenu;
    private InlineKeyboardMarkup yesOrNoMenu;

    public YesOrNoMenu(){}

    public InlineKeyboardMarkup generateYesOrNoMenu() {
        yesRow = new ArrayList<>();
        noRow = new ArrayList<>();
        buttonsMenu = new ArrayList<>();
        InlineKeyboardButton yesButton = new InlineKeyboardButton();
        yesButton.setText("Да");
        yesButton.setCallbackData("Да");
        yesRow.add(yesButton);

        InlineKeyboardButton noButton = new InlineKeyboardButton();
        noButton.setText("Нет");
        noButton.setCallbackData("Нет");
        yesRow.add(noButton);

        buttonsMenu.add(yesRow);
        buttonsMenu.add(noRow);

        yesOrNoMenu = new InlineKeyboardMarkup();
        yesOrNoMenu.setKeyboard(buttonsMenu);

        return yesOrNoMenu;
    }
}
