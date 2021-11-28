package ru.home.MyHHBot.botApi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.entity.BotState;
import ru.home.MyHHBot.botApi.handlers.inputMessageHandler.InputMessageHandler;
import ru.home.MyHHBot.botApi.handlers.callBackHandler.CallBackHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class BotStateContext {
    private Map<BotState, InputMessageHandler> messageHandlers = new HashMap<>();
    private Map<BotState, CallBackHandler> callBackHandlers = new HashMap<>();

    public BotStateContext(List<InputMessageHandler> messageHandlers, List<CallBackHandler> callBackHandlers){
        messageHandlers.forEach(handler -> this.messageHandlers.put(handler.getHandlerName(), handler));
        callBackHandlers.forEach(handler -> this.callBackHandlers.put(handler.getHandlerName(), handler));
    }
    public SendMessage processInputMessage (BotState currentState, Message message){
        InputMessageHandler currentMessageHandler = findMessageHandler(currentState);
        return currentMessageHandler.handle(message);
    }
    public SendMessage processCallBackQuery (BotState currentState, CallbackQuery callBackQuery){
        CallBackHandler currentCallBackHandler = callBackHandlers.get(currentState);
        return currentCallBackHandler.handleCallBack(callBackQuery);
    }

    private InputMessageHandler findMessageHandler (BotState currentState){
        return messageHandlers.get(currentState);
    }
    private CallBackHandler findCallBackQueryHandler (BotState currentState){
        return callBackHandlers.get(currentState);
    }

}
