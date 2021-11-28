package ru.home.MyHHBot.botApi.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.MyHHBot.botApi.entity.BotState;
import ru.home.MyHHBot.botApi.userData.cache.UserDataCache;
import ru.home.MyHHBot.hhApi.VacanciesList;

@Component
//Используем lombok для логирования
@Slf4j
public class TelegramFacade {
    private BotStateContext botStateContext;
    private UserDataCache userDataCache;
    private long userId;
    private VacanciesList vacanciesList;

    public TelegramFacade(BotStateContext botStateContext, UserDataCache userDataCache, VacanciesList vacanciesList) {
        this.botStateContext = botStateContext;
        this.userDataCache = userDataCache;
        this.vacanciesList = vacanciesList;
    }

    public SendMessage handleUpdate (Update update){
        SendMessage replyMessage = null;
        Message message = update.getMessage();
        if (message != null && message.hasText()){
            userId = message.getFrom().getId();
            log.info("New message from user: {}, userId {}, chatId: {}, with text: {}",
                    message.getFrom().getUserName(), userId, message.getChatId(), message.getText());
            replyMessage = handleInputMessage(message);
        }

        CallbackQuery callbackQuery = update.getCallbackQuery();
        if (update.hasCallbackQuery()){
            replyMessage = handleCallBackQuery(callbackQuery);
        }
        return replyMessage;
    }

    private SendMessage handleInputMessage(Message message){
        String inputMsg = message.getText();
        int userId = message.getFrom().getId();
        long chatId = message.getChat().getId();
        System.out.println("facade user id " + userId);
        System.out.println("facade chat id " + chatId);
        BotState botState;
        SendMessage replyMessage;


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
                break;
            default:
                botState = userDataCache.getUsersCurrentBotState(userId);
                break;
        }

        userDataCache.setUsersCurrentBotState (userId, botState);
        System.out.println("botst " + botState);
        replyMessage = botStateContext.processInputMessage(botState, message);
        return replyMessage;
    }
    private SendMessage handleCallBackQuery (CallbackQuery callbackQuery){
        String data = callbackQuery.getData();
        BotState botState = null;
        System.out.println("jobID " + vacanciesList.getJobId());
       /* try {
        if (vacanciesList.getJobId().contains(data)) {
    botState = BotState.ASK_JOB_DESCRIPTION;
}}
        catch (NullPointerException nullPointerException){
            botState = BotState.FILLING_PROFILE;
        }*/
            botState = BotState.FILLING_PROFILE;
        SendMessage replyMessage;

        userDataCache.setUsersCurrentBotState (userId, botState);
        replyMessage = botStateContext.processCallBackQuery(botState, callbackQuery);

        return replyMessage;
    }
}
