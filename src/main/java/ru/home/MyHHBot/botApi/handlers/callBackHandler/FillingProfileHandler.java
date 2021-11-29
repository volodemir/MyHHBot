package ru.home.MyHHBot.botApi.handlers.callBackHandler;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import ru.home.MyHHBot.botApi.handlers.inputMessageHandler.AnswerOnFillingProfile;
import ru.home.MyHHBot.botApi.entity.keyboard.MinSalaryMenu;
import ru.home.MyHHBot.botApi.entity.keyboard.OptionsMenu;
import ru.home.MyHHBot.botApi.entity.keyboard.YesOrNoMenu;
import ru.home.MyHHBot.botApi.entity.BotState;
import ru.home.MyHHBot.botApi.handlers.inputMessageHandler.FindJobHandler;
import ru.home.MyHHBot.botApi.handlers.inputMessageHandler.GetVacanciesHandler;
import ru.home.MyHHBot.botApi.userData.UserProfileData;
import ru.home.MyHHBot.botApi.userData.cache.UserDataCache;
import ru.home.MyHHBot.hhApi.*;
import ru.home.MyHHBot.hhApi.list.CityList;
import ru.home.MyHHBot.hhApi.list.CountryList;
import ru.home.MyHHBot.hhApi.list.RegionList;
import ru.home.MyHHBot.hhApi.list.SpecializationList;

@Slf4j
@Component
public class FillingProfileHandler implements CallBackHandler{
    private UserDataCache userDataCache;
    private YesOrNoMenu yesOrNoMenu;
    private CountryList countryList;
    private RegionList regionList;
    private CityList cityList;
    private Country[] countryArr;
    private HH[] regionArr;
    private SpecializationList specList;
    private Specialization[] specArr;
    private HH[] specSubArr;
    private MinSalaryMenu salaryMenu;
    private OptionsMenu optionMenu;
    private AnswerOnFillingProfile answerOnFillingProfile;
    private FindJobHandler findJobHandler;
    private @Getter long userId;
    private GetVacanciesHandler getVacanciesHandler;

    public FillingProfileHandler(UserDataCache userDataCache, YesOrNoMenu yesOrNoMenu, CountryList countryList,
                                 RegionList regionList, CityList cityList, SpecializationList specList,
                                 MinSalaryMenu salaryMenu, OptionsMenu optionMenu,
                                 AnswerOnFillingProfile answerOnFillingProfile, FindJobHandler findJobHandler,
                                 GetVacanciesHandler getVacanciesHandler)
    {
        this.userDataCache = userDataCache;
        this.yesOrNoMenu = yesOrNoMenu;
        this.countryList = countryList;
        this.regionList = regionList;
        this.cityList = cityList;
        this.specList = specList;
        this.salaryMenu = salaryMenu;
        this.optionMenu = optionMenu;
        this.answerOnFillingProfile = answerOnFillingProfile;
        this.findJobHandler = findJobHandler;
        this.getVacanciesHandler = getVacanciesHandler;
    }

    @Override
    public SendMessage handleCallBack(CallbackQuery callbackQuery) {
        return processCallBackQuery(callbackQuery);
    }

    @Override
    public BotState getHandlerName() {
        return BotState.FILLING_PROFILE;
    }

    @SneakyThrows
    private SendMessage processCallBackQuery(CallbackQuery callbackQuery) {
      Message message = callbackQuery.getMessage();
        String data = callbackQuery.getData();
        userId = message.getFrom().getId();
        long chatId = message.getChat().getId();
        String countryName;
        SendMessage replyToUser = null;

        UserProfileData profileData = userDataCache.getUserProfileData(userId);
        BotState botState = userDataCache.getUsersCurrentBotState(userId);
        InlineKeyboardMarkup oMenu = null;

        if (profileData.getCountryName() == null) {
            oMenu = optionMenu.generateOptionMenuWithCountry();
        }
        if (profileData.getCountryName() != null) {
            oMenu = optionMenu.generateOptionMenuWithRegion();
        }
        if (profileData.getRegionName() != null) {
            oMenu = optionMenu.generateOptionMenuWithCity();
        }

        if (data.equals("Выберите свою страну из списка")) {
            profileData.setCountryId(null);
            profileData.setCountryName(null);
            replyToUser = new SendMessage(chatId, data);

            InlineKeyboardMarkup countryMarkup = countryList.generateCountryList();
            replyToUser.setReplyMarkup(countryMarkup);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_COUNTRY);
        }

        if (botState.equals(BotState.ASK_COUNTRY)) {
            countryArr = countryList.getCountryArr();
            for (Country c : countryArr) {
                String countryId = c.getId();
                countryName = c.getName();
                if (data.equals(countryId)) {
                    profileData.setCountryId(countryId);
                    profileData.setCountryName(countryName);
                    profileData.setGeneralRegionId(countryId);
                    replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
                    replyToUser.setReplyMarkup(optionMenu.generateOptionMenuWithRegion());
                }
            }
        }

        if (data.equals("Выберите свой регион из списка")) {
            profileData.setRegionId(null);
            profileData.setRegionName(null);
            replyToUser = new SendMessage(chatId, data);
            String destCountryId = profileData.getCountryId();
            InlineKeyboardMarkup regionMarkup = regionList.generateRegionList(destCountryId);
            replyToUser.setReplyMarkup(regionMarkup);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_REGION);
        }
        if (botState.equals(BotState.ASK_REGION)) {
            regionArr = regionList.getRegionArr();
            for (HH r : regionArr) {
                r.getAreas().forEach(reg -> {
                    String regName = reg.getName();
                    if (data.equals(reg.getId())) {
                        profileData.setRegionId(reg.getId());
                        profileData.setRegionName(regName);
                        profileData.setGeneralRegionId(reg.getId());
                    }
                });
                replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
                replyToUser.setReplyMarkup(optionMenu.generateOptionMenuWithCity());
            }
        }

        if (data.equals("Выберите свой город из списка")) {
            profileData.setCityId(null);
            profileData.setCityName(null);
            replyToUser = new SendMessage(chatId, data);
            String destRegionId = profileData.getRegionId();
            System.out.println("des region" + destRegionId);
            InlineKeyboardMarkup cityMarkup = cityList.generateCityList(destRegionId);
            replyToUser.setReplyMarkup(cityMarkup);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_CITY);
        }
        if (botState.equals(BotState.ASK_CITY)) {
            regionArr = regionList.getRegionArr();
            for (HH r : regionArr) {
                r.getAreas().forEach(reg -> reg.getAreas().forEach(city -> {
                    String cityName = city.getName();
                    if (data.equals(city.getId())) {
                        profileData.setCityId(city.getId());
                        profileData.setCityName(cityName);
                        profileData.setGeneralRegionId(city.getId());

                    }
                }));
                replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
                replyToUser.setReplyMarkup(oMenu);
            }
        }
        if (data.equals("Выберите категорию из списка")) {
            replyToUser = new SendMessage(chatId, data);
            InlineKeyboardMarkup specMarkup = specList.generateSpecializationList();
            replyToUser.setReplyMarkup(specMarkup);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_SPECIALIZATION);
        }
        if (botState.equals(BotState.ASK_SPECIALIZATION)) {
            profileData.setSpecializationId(null);
            profileData.setSpecializationName(null);
            specArr = specList.getSpecializationArr();
            String specId = null;
            for (Specialization s : specArr) {
                if (data.equals(s.getId())) {
                    specId = s.getId();
                    profileData.setSpecializationId(specId);
                    profileData.setSpecializationName(s.getName());
                }
            }
            replyToUser = new SendMessage(chatId, "Специализация " + profileData.getSpecializationName() + " задана"
                    + "\n"
                    + "Укажите подкатегорию:");
            replyToUser.setReplyMarkup(specList.generateSpecializationSubList(specId));
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_SPECIALIZATION_SUBLIST);
        }

        if (botState.equals(BotState.ASK_SPECIALIZATION_SUBLIST)) {
            profileData.setSpecializationId(null);
            specSubArr = specList.getSpecializationSubArr();
            for (HH s : specSubArr) {
                s.getSpecializations().forEach(specialization -> {
                    if (data.equals(specialization.getId())) {
                        profileData.setSpecializationId(specialization.getId());
                        profileData.setSpecializationName(specialization.getName());
                    }
                });
            }
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }

        if (data.equals("Отображать только с указанием з/п?")) {
            profileData.setDisplayWages(false);
            replyToUser = new SendMessage(chatId, data);
            replyToUser.setReplyMarkup(yesOrNoMenu.generateYesOrNoMenu());
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_DISPLAY_WAGES);
        }
        if (data.equals("Да") && botState.equals(BotState.ASK_DISPLAY_WAGES)) {
            profileData.setDisplayWagesString(null);
            profileData.setDisplayWages(true);
            profileData.setDisplayWagesString("Да");
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
            System.out.println(botState);
        }
        if (data.equals("Нет") && botState.equals(BotState.ASK_DISPLAY_WAGES)) {
            profileData.setDisplayWagesString(null);
            profileData.setDisplayWages(false);
            profileData.setDisplayWagesString("Нет");
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }

        if (data.equals("Выберите минимальную з/п")) {
            profileData.setMinSalary(0);
            replyToUser = new SendMessage(chatId, data);
            InlineKeyboardMarkup salaryMarkup = salaryMenu.generateSalaryMenu();
            replyToUser.setReplyMarkup(salaryMarkup);
            userDataCache.setUsersCurrentBotState(userId, BotState.ASK_MIN_SALARY);
        }
        if (data.equals("от 30 000")) {
            profileData.setMinSalary(0);
            profileData.setMinSalary(30000);
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }
        if (data.equals("от 50 000")) {
            profileData.setMinSalary(0);
            profileData.setMinSalary(50000);
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }
        if (data.equals("от 70 000")) {
            profileData.setMinSalary(0);
            profileData.setMinSalary(70000);
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }
        if (data.equals("от 90 000")) {
            profileData.setMinSalary(0);
            profileData.setMinSalary(90000);
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }
        if (data.equals("от 110 000")) {
            profileData.setMinSalary(0);
            profileData.setMinSalary(110000);
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }
        if (data.equals("от 130 000")) {
            profileData.setMinSalary(0);
            profileData.setMinSalary(130000);
            replyToUser = answerOnFillingProfile.showCurrentOptions(userId, chatId);
            replyToUser.setReplyMarkup(oMenu);
        }

        userDataCache.saveUserProfileData(userId, profileData);
        answerOnFillingProfile.setUserId(userId);
        getVacanciesHandler.setUserId(userId);

        return replyToUser;
    }
}
