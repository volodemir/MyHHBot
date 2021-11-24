package ru.home.MyHHBot.botApi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import ru.home.MyHHBot.botApi.entity.AnswerOnFillingProfile;
import ru.home.MyHHBot.botApi.entity.OptionsMenu;
import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.cache.UserDataCache;

import java.util.ArrayList;
import java.util.List;

@Component
//Используем lombok для логирования
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private AnswerOnFillingProfile answerOnFillingProfile;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, AnswerOnFillingProfile answerOnFillingProfile) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.answerOnFillingProfile = answerOnFillingProfile;
    }

    public SendMessage handleUpdate (Update update){
        SendMessage replyMessage = null;

        Message message = update.getMessage();
        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (message != null && message.hasText()){
            log.info("New message from user: {}, userId: {} chatId: {}, with text: {}, with sticker: {}",
                    message.getFrom().getUserName(), message.getFrom().getId(), message.getChatId(), message.getText(), message.getSticker());
            replyMessage = handleInputMessage(message);
        }
        if (update.hasCallbackQuery()){
            replyMessage = handleCallBackQuery(callbackQuery);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message){
        String inputMsg = message.getText();
        long userId = message.getFrom().getId();
        long chatId = message.getChatId();
        System.out.println("facade " + userId);
        BotState botState;
        SendMessage replyMessage = new SendMessage();

        switch (inputMsg){
            case "/start":
                botState = BotState.GREETING;
                break;
            case "Поиск": //Поиск вакансии
                botState = BotState.FIND_JOB;
                break;
            case "Настройки": //Настройки
                botState = BotState.ASK_OPTIONS;
                break;
            case "Показать текущие настройки": //Настройки
                botState = BotState.CURRENT_OPTIONS;
                replyMessage = answerOnFillingProfile.showCurrentOptions(userId, chatId);
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                //botState = BotState.ASK_MIN_SALARY;
                System.out.println("botstate" + botState);
                break;
        }
        //if (inputMsg.)
        userDataCache.setUsersCurrentBotState (userId, botState);

        replyMessage = botStateContext.processInputMessage(botState, message);

        return replyMessage;
    }
    private SendMessage handleCallBackQuery (CallbackQuery callbackQuery){
        BotState botState;
        botState = BotState.FILLING_PROFILE;
        int userId = callbackQuery.getFrom().getId();
        SendMessage replyMessage;

        userDataCache.setUsersCurrentBotState (userId, botState);
        replyMessage = botStateContext.processCallBackQuery(botState, callbackQuery);

        return replyMessage;
    }
}
