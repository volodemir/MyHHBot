package ru.home.MyHHBot.botApi.userData;

/*Данные анкеты пользователя*/

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

@Data
@FieldDefaults (level = AccessLevel.PRIVATE)
public class UserProfileData {
    String countryId;
    String countryName;

    String regionId;
    String regionName;

    String cityId;
    String cityName;

    String generalRegionId;

    String specializationId;
    String specializationName;

    boolean displayWages;
    String displayWagesString;

    String vacancyId;

    int minSalary;
}
