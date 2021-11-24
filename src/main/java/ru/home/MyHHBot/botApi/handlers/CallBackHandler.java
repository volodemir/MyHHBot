package ru.home.MyHHBot.botApi.handlers;

/*Обработчик пунктов меню*/

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;

public interface CallBackHandler {
    SendMessage handleCallBack (CallbackQuery callbackQuery);
    BotState getHandlerName();
}
