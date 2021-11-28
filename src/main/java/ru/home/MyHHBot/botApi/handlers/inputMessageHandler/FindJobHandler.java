package ru.home.MyHHBot.botApi.handlers.inputMessageHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.entity.BotState;
import ru.home.MyHHBot.botApi.userData.cache.UserDataCache;
import ru.home.MyHHBot.service.ReplyMessageService;

@Slf4j
@Component
public class FindJobHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessageService messageService;
    private int userId;

    public FindJobHandler(UserDataCache userDataCache, ReplyMessageService messageService) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;

    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {return BotState.FIND_JOB;}

    public int setUserId (int usrId){
        userId = usrId;
        return userId;
    }

    private SendMessage processUsersInput(Message inputMsg) {

        long chatId = inputMsg.getChatId();
        System.out.println("findjob chatid " + chatId);

        /*UserProfileData profileData = userDataCache.getUserProfileData(userId);
        userDataCache.getStringProfileData(profileData);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);*/
        SendMessage replyToUser;

        replyToUser = messageService.getReplyMessage(chatId, "reply.askPosition");
        userDataCache.setUsersCurrentBotState(chatId, BotState.GET_VACANCIES);
        return replyToUser;

    }
}
