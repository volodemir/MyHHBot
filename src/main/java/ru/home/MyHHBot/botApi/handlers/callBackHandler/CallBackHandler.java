package ru.home.MyHHBot.botApi.handlers.callBackHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import ru.home.MyHHBot.botApi.entity.BotState;

public interface CallBackHandler {
    SendMessage handleCallBack (CallbackQuery callbackQuery);
    BotState getHandlerName();
}
