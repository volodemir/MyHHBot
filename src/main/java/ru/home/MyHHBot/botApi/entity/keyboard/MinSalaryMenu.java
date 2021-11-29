package ru.home.MyHHBot.botApi.entity.keyboard;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MinSalaryMenu {
    private List<InlineKeyboardButton> salaryRow;
    private List<List<InlineKeyboardButton>> sButtonsMenu;
    private InlineKeyboardMarkup salaryMenu;

    public MinSalaryMenu(){}

    public InlineKeyboardMarkup generateSalaryMenu(){
        sButtonsMenu = new ArrayList<>();
        salaryMenu = new InlineKeyboardMarkup();

        for (int i=0; i<=5; i++){
            salaryRow = new ArrayList<>();
            InlineKeyboardButton setSalaryButton = new InlineKeyboardButton();
            if (i==0){
                setSalaryButton.setText("от 30 000");
                setSalaryButton.setCallbackData("от 30 000");
            }
            if (i==1){
                setSalaryButton.setText("от 50 000");
                setSalaryButton.setCallbackData("от 50 000");
            }
            if (i==2){
                setSalaryButton.setText("от 70 000");
                setSalaryButton.setCallbackData("от 70 000");
            }
            if (i==3){
                setSalaryButton.setText("от 90 000");
                setSalaryButton.setCallbackData("от 90 000");
            }
            if (i==4){
                setSalaryButton.setText("от 110 000");
                setSalaryButton.setCallbackData("от 110 000");
            }
            if (i==5){
                setSalaryButton.setText("от 130 000");
                setSalaryButton.setCallbackData("от 130 000");
            }
            salaryRow.add(setSalaryButton);
            sButtonsMenu.add(salaryRow);
        }
        salaryMenu.setKeyboard(sButtonsMenu);

        return salaryMenu;
    }
}
