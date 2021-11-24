package ru.home.MyHHBot.botApi.handlers.fillingProfile;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.glassfish.grizzly.streams.Input;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.MyHHBot.botApi.InputMessageHandler;
import ru.home.MyHHBot.botApi.entity.OptionsMenu;
import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.cache.UserDataCache;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class MinSalaryHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private OptionsMenu optionMenu;

    public MinSalaryHandler(UserDataCache userDataCache, OptionsMenu optionMenu) {
        this.userDataCache = userDataCache;
        this.optionMenu = optionMenu;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }


    private SendMessage processUsersInput(Message message) {
        String usrMsg = message.getText();
        System.out.println(usrMsg);
        int userAnswer = Integer.parseInt(message.getText());
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        SendMessage replyToUser = null;

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);


            System.out.println("Im here");
            try{
                profileData.setMinSalary(userAnswer);
                replyToUser.setReplyMarkup(optionMenu.generateOptionMenuWithCountry());
            }
            catch (NumberFormatException e) {
                //JOptionPane.showConfirmDialog(null, "Please enter numbers only", "naughty", JOptionPane.CANCEL_OPTION);
                //replyToUser = new SendMessage(chatId, "Необходимо задать числовое значение");
            }

        return replyToUser;
        }
}
