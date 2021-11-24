package ru.home.MyHHBot.botApi.handlers.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.InputMessageHandler;
import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.botApi.handlers.fillingProfile.UserProfileData;
import ru.home.MyHHBot.cache.UserDataCache;
import ru.home.MyHHBot.service.ReplyMessageService;

@Slf4j
@Component
public class FindJobHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessageService messageService;

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

    private SendMessage processUsersInput(Message inputMsg) {
        long userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();

        /*UserProfileData profileData = userDataCache.getUserProfileData(userId);
        userDataCache.getStringProfileData(profileData);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);*/
        SendMessage replyToUser;

        replyToUser = messageService.getReplyMessage(chatId, "reply.askPosition");
        userDataCache.setUsersCurrentBotState(userId, BotState.GET_VACANCIES);
        return replyToUser;

    }
}
