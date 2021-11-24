package ru.home.MyHHBot.botApi.handlers.greeting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.home.MyHHBot.botApi.InputMessageHandler;
import ru.home.MyHHBot.botApi.entity.OptionsMenu;
import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.botApi.handlers.fillingProfile.UserProfileData;
import ru.home.MyHHBot.cache.UserDataCache;
import ru.home.MyHHBot.service.ReplyMessageService;

@Slf4j
@Component
public class OptionsMenuHandler implements InputMessageHandler {
    private UserDataCache userDataCache;
    private ReplyMessageService messageService;
    private OptionsMenu optionMenuWithCountry;


    public OptionsMenuHandler(UserDataCache userDataCache, ReplyMessageService messageService, OptionsMenu optionMenu) {
        this.userDataCache = userDataCache;
        this.messageService = messageService;
        this.optionMenuWithCountry = optionMenu;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.ASK_OPTIONS;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long userId = inputMsg.getFrom().getId();
        long chatId = inputMsg.getChatId();
        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);
        SendMessage replyToUser;
        replyToUser = messageService.getReplyMessageStr(chatId, "Задайте настройки для поиска");

            InlineKeyboardMarkup oMenu = optionMenuWithCountry.generateOptionMenuWithCountry();
            replyToUser.setReplyMarkup(oMenu);

        userDataCache.setUsersCurrentBotState(userId, BotState.FILLING_PROFILE);

        return replyToUser;

    }
}
