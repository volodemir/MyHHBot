package ru.home.MyHHBot.botApi.entity.keyboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class OptionsMenu {
    private List<InlineKeyboardButton> countryRow;
    private List<InlineKeyboardButton> regionRow;
    private List<InlineKeyboardButton> categoryRow;
    private List<InlineKeyboardButton> displayWagesRow;
    private List<InlineKeyboardButton> minSalaryRow;
    private List<List<InlineKeyboardButton>> oButtonsMenu;
    private InlineKeyboardMarkup optionsMenu;

    public OptionsMenu() {}

    public InlineKeyboardMarkup generateOptionMenuWithCountry(){
        countryRow = new ArrayList<>();
        categoryRow = new ArrayList<>();
        displayWagesRow = new ArrayList<>();
        minSalaryRow = new ArrayList<>();
        oButtonsMenu = new ArrayList<>();

        InlineKeyboardButton setCountryButton = new InlineKeyboardButton();
        setCountryButton.setText("Задать свою страну");
        setCountryButton.setCallbackData("Выберите свою страну из списка");
        countryRow.add(setCountryButton);

        InlineKeyboardButton setCategoryButton = new InlineKeyboardButton();
        setCategoryButton.setText("Задать категорию вакансии");
        setCategoryButton.setCallbackData("Выберите категорию из списка");
        categoryRow.add(setCategoryButton);

        InlineKeyboardButton setDisplayWagesButton = new InlineKeyboardButton();
        setDisplayWagesButton.setText("Отображать вакансии с указанием з/п");
        setDisplayWagesButton.setCallbackData("Отображать только с указанием з/п?");
        displayWagesRow.add(setDisplayWagesButton);

        InlineKeyboardButton setMinSalaryButton = new InlineKeyboardButton();
        setMinSalaryButton.setText("Задать минимальную з/п");
        setMinSalaryButton.setCallbackData("Выберите минимальную желаемую з/п");
        minSalaryRow.add(setMinSalaryButton);

        oButtonsMenu.add(countryRow);
        oButtonsMenu.add(categoryRow);
        oButtonsMenu.add(displayWagesRow);
        oButtonsMenu.add(minSalaryRow);

        optionsMenu = new InlineKeyboardMarkup();
        optionsMenu.setKeyboard(oButtonsMenu);

        return optionsMenu;
    }
    public InlineKeyboardMarkup generateOptionMenuWithRegion(){
        regionRow = new ArrayList<>();
        categoryRow = new ArrayList<>();
        displayWagesRow = new ArrayList<>();
        minSalaryRow = new ArrayList<>();
        oButtonsMenu = new ArrayList<>();

        InlineKeyboardButton setRegionButton = new InlineKeyboardButton();
        setRegionButton.setText("Задать свой регион");
        setRegionButton.setCallbackData("Выберите свой регион из списка");
        regionRow.add(setRegionButton);

        InlineKeyboardButton setCategoryButton = new InlineKeyboardButton();
        setCategoryButton.setText("Задать категорию вакансии");
        setCategoryButton.setCallbackData("Выберите категорию из списка");
        categoryRow.add(setCategoryButton);

        InlineKeyboardButton setDisplayWagesButton = new InlineKeyboardButton();
        setDisplayWagesButton.setText("Отображать вакансии с указанием з/п");
        setDisplayWagesButton.setCallbackData("Отображать только с указанием з/п?");
        displayWagesRow.add(setDisplayWagesButton);

        InlineKeyboardButton setMinSalaryButton = new InlineKeyboardButton();
        setMinSalaryButton.setText("Задать минимальную з/п");
        setMinSalaryButton.setCallbackData("Напишите минимальную желаемую з/п");
        minSalaryRow.add(setMinSalaryButton);

        oButtonsMenu.add(regionRow);
        oButtonsMenu.add(categoryRow);
        oButtonsMenu.add(displayWagesRow);
        oButtonsMenu.add(minSalaryRow);

        optionsMenu = new InlineKeyboardMarkup();
        optionsMenu.setKeyboard(oButtonsMenu);

        return optionsMenu;
    }
    public InlineKeyboardMarkup generateOptionMenuWithCity(){
        regionRow = new ArrayList<>();
        categoryRow = new ArrayList<>();
        displayWagesRow = new ArrayList<>();
        minSalaryRow = new ArrayList<>();
        oButtonsMenu = new ArrayList<>();

        InlineKeyboardButton setRegionButton = new InlineKeyboardButton();
        setRegionButton.setText("Задать свой город");
        setRegionButton.setCallbackData("Выберите свой город из списка");
        regionRow.add(setRegionButton);

        InlineKeyboardButton setCategoryButton = new InlineKeyboardButton();
        setCategoryButton.setText("Задать категорию вакансии");
        setCategoryButton.setCallbackData("Выберите категорию из списка");
        categoryRow.add(setCategoryButton);

        InlineKeyboardButton setDisplayWagesButton = new InlineKeyboardButton();
        setDisplayWagesButton.setText("Отображать вакансии с указанием з/п");
        setDisplayWagesButton.setCallbackData("Отображать только с указанием з/п?");
        displayWagesRow.add(setDisplayWagesButton);

        InlineKeyboardButton setMinSalaryButton = new InlineKeyboardButton();
        setMinSalaryButton.setText("Задать минимальную з/п");
        setMinSalaryButton.setCallbackData("Напишите минимальную желаемую з/п");
        minSalaryRow.add(setMinSalaryButton);

        oButtonsMenu.add(regionRow);
        oButtonsMenu.add(categoryRow);
        oButtonsMenu.add(displayWagesRow);
        oButtonsMenu.add(minSalaryRow);

        optionsMenu = new InlineKeyboardMarkup();
        optionsMenu.setKeyboard(oButtonsMenu);

        return optionsMenu;
    }
}
