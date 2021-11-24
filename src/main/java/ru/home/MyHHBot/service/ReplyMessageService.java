package ru.home.MyHHBot.service;

/*Формирует готовые сообщения в чат*/

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

@Service
public class ReplyMessageService {
    private LocaleMessageService localeMessageService;

    public ReplyMessageService(LocaleMessageService messageService){
        this.localeMessageService = messageService;
    }
    public SendMessage getReplyMessage(long chatId, String replyMessage){
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessage (long chatId, String replyMessage, Object... args){
        return new SendMessage(chatId, localeMessageService.getMessage(replyMessage));
    }

    public SendMessage getReplyMessageStr (long chatId, String replyMessage, Object... args){
        return new SendMessage(chatId, replyMessage);
    }
}
