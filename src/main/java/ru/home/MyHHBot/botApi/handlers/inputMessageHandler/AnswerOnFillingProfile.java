package ru.home.MyHHBot.botApi.handlers.inputMessageHandler;

import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.entity.BotState;
import ru.home.MyHHBot.botApi.userData.UserProfileData;
import ru.home.MyHHBot.botApi.userData.cache.UserDataCache;

@Slf4j
@Component
@Data
public class AnswerOnFillingProfile implements InputMessageHandler {

    private UserDataCache userDataCache;

    private long userId;

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

    public long setUserId (long usrId){
        userId = usrId;
        return userId;
    }
    @SneakyThrows
    private SendMessage processUsersInput(Message message) {
        long chatId = message.getChatId();
        System.out.println("aof" + userId);
        System.out.println("aof chatid " + chatId);

        userDataCache.getUserProfileData(userId);
        System.out.println(userDataCache.getUserProfileData(userId));
        SendMessage replyMarkup = showCurrentOptions(userId, chatId);

        return replyMarkup;
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
