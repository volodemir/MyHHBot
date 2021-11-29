package ru.home.MyHHBot.botApi.handlers.inputMessageHandler;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.entity.BotState;

public interface InputMessageHandler {
    SendMessage handle (Message message);
    BotState getHandlerName();
}
