package ru.home.MyHHBot.botApi.handlers.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import ru.home.MyHHBot.botApi.InputMessageHandler;
//import ru.home.MyHHBot.botApi.entity.GeneralMenu;
import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.cache.UserDataCache;
import ru.home.MyHHBot.service.ReplyMessageService;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class GreetingHandler implements InputMessageHandler {

    private UserDataCache userDataCache;
    private ReplyMessageService messageService;

    public GreetingHandler(UserDataCache userDataCache, ReplyMessageService messageService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GREETING;
    }

    private SendMessage processUsersInput (Message inputMsg){
        long userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        List<KeyboardRow> gButtons = new ArrayList<>();
        KeyboardRow keyboardRowRep = new KeyboardRow();

            KeyboardButton keyboardButton1 = new KeyboardButton();
                    keyboardButton1.setText("Поиск"); //🔍
            keyboardRowRep.add(keyboardButton1);

        KeyboardButton keyboardButton2 = new KeyboardButton();
        keyboardButton2.setText("Настройки");
        keyboardRowRep.add(keyboardButton2);

        KeyboardButton keyboardButton3 = new KeyboardButton();
        keyboardButton3.setText("Показать текущие настройки");
        keyboardRowRep.add(keyboardButton3);

        gButtons.add(keyboardRowRep);

        ReplyKeyboardMarkup generalMenu = new ReplyKeyboardMarkup();
        generalMenu.setResizeKeyboard(true);
        generalMenu.setOneTimeKeyboard(true);
        generalMenu.setKeyboard(gButtons);


        SendMessage replyToUser = messageService.getReplyMessage(chatId, "reply.greeting");
        replyToUser.setReplyMarkup(generalMenu);

        BotState botState = userDataCache.getUsersCurrentBotState(userId);

        return replyToUser;
    }
}
