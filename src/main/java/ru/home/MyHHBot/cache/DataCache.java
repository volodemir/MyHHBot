package ru.home.MyHHBot.cache;


import ru.home.MyHHBot.botApi.handlers.BotState;
import ru.home.MyHHBot.botApi.handlers.fillingProfile.UserProfileData;

public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);

    UserProfileData getUserProfileData(long userId);

    void saveUserProfileData(long userId, UserProfileData userProfileData);
}
