package ru.home.MyHHBot.botApi.handlers.greeting;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.InputMessageHandler;
import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.botApi.handlers.fillingProfile.UserProfileData;
import ru.home.MyHHBot.cache.UserDataCache;
import ru.home.MyHHBot.hhApi.VacanciesList;

@Slf4j
@Component
public class GetVacanciesHandler implements InputMessageHandler {

    private UserDataCache userDataCache;
    private VacanciesList vacancy;

    public GetVacanciesHandler(UserDataCache userDataCache) {
        this.userDataCache = userDataCache;

    }

    @Override
    public SendMessage handle(Message message) {
        return processUsersInput(message);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.GET_VACANCIES;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long userId = inputMsg.getFrom().getId();
        vacancy = new VacanciesList(userDataCache);

       /* UserProfileData profileData = userDataCache.getUserProfileData(userId);
        userDataCache.getStringProfileData(profileData);*/

        SendMessage replyToUser = vacancy.getVacanciesList(inputMsg);

        return replyToUser;
    }
}
