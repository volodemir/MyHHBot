package ru.home.MyHHBot.botApi.userData.cache;


import ru.home.MyHHBot.botApi.entity.BotState;
import ru.home.MyHHBot.botApi.userData.UserProfileData;

public interface DataCache {
    void setUsersCurrentBotState(long userId, BotState botState);

    BotState getUsersCurrentBotState(long userId);

    UserProfileData getUserProfileData(long userId);

    void saveUserProfileData(long userId, UserProfileData userProfileData);
}
