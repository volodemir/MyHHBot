package ru.home.MyHHBot.botApi.entity;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.MyHHBot.botApi.InputMessageHandler;
import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.botApi.handlers.fillingProfile.FillingProfileHandler;
import ru.home.MyHHBot.botApi.handlers.fillingProfile.UserProfileData;
import ru.home.MyHHBot.cache.UserDataCache;

@Slf4j
@Component
@Data
public class AnswerOnFillingProfile implements InputMessageHandler {

    private UserDataCache userDataCache;

    public AnswerOnFillingProfile(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;
    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }


    @Override
    public BotState getHandlerName() {
        return BotState.CURRENT_OPTIONS;
    }

    private SendMessage processUsersInput(Message message) {
        String userId = message.getFrom().getUserName();
        long chatId = message.getChatId();
        System.out.println("aof" + userId);
        System.out.println("aof chatid " + chatId);

        /*userDataCache.getUserProfileData(userId);
        SendMessage replyMarkup = showCurrentOptions(userId,chatId);*/

        return null;
    }

    public SendMessage showCurrentOptions(long userId, long chatId){

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        SendMessage sm = new SendMessage(chatId, "Страна/Регион/Город - "
                + profileData.getCountryName() + "/" + profileData.getRegionName() + "/"
                + profileData.getCityName() + "\n"
                + "Категория - " + profileData.getSpecializationName() + "\n"
                + "Отображать только вакансии с указанием з/п - " + profileData.getDisplayWagesString() + "\n"
                + "Заработная плата от - " + profileData.getMinSalary());

        return sm;
    }


}
