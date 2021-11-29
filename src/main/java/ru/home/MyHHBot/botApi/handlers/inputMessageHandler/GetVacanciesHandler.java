package ru.home.MyHHBot.botApi.handlers.inputMessageHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.entity.BotState;
import ru.home.MyHHBot.botApi.userData.cache.UserDataCache;
import ru.home.MyHHBot.hhApi.list.VacanciesList;

@Slf4j
@Component
public class GetVacanciesHandler implements InputMessageHandler {

    private VacanciesList vacanciesList;
    private long userId;
    private UserDataCache userDataCache;

    public GetVacanciesHandler(VacanciesList vacanciesList, UserDataCache userDataCache) {
        this.vacanciesList = vacanciesList;
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

    public long setUserId (long usrId){
        userId = usrId;
        return userId;
    }

    private SendMessage processUsersInput(Message inputMsg) {
        long chatId = inputMsg.getFrom().getId();
        SendMessage replyToUser = new SendMessage(chatId, "Выберите интересующую вакансию из списка");
        //replyToUser = vacanciesList.getVacanciesList(inputMsg, userId);
        replyToUser.setReplyMarkup(vacanciesList.getVacanciesList(inputMsg, userId));

        return replyToUser;
    }
}
