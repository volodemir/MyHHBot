package ru.home.MyHHBot.botApi.handlers;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Message;
import ru.home.MyHHBot.botApi.InputMessageHandler;

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
        CallBackHandler currentCallBackHandler = findCallBackQueryHandler(currentState);
        return currentCallBackHandler.handleCallBack(callBackQuery);
    }

    private InputMessageHandler findMessageHandler (BotState currentState){
        if (isFillingProfileState(currentState)){
            return messageHandlers.get(BotState.FILLING_PROFILE);
        }
        return messageHandlers.get(currentState);
    }
    private CallBackHandler findCallBackQueryHandler (BotState currentState){
        if (isFillingProfileState(currentState)){
            return callBackHandlers.get(BotState.FILLING_PROFILE);
        }
        return callBackHandlers.get(currentState);
    }

    private boolean isFillingProfileState(BotState currentState){
        switch (currentState){
            /*case ASK_POSITION:
            case ASK_OPTIONS:*/
            case ASK_DISPLAY_WAGES:
            case FILLING_PROFILE:
            case PROFILE_FILED:
                return true;
            default:
                return false;
        }
    }
}
