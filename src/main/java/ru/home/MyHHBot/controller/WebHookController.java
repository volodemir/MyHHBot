package ru.home.MyHHBot.controller;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.home.MyHHBot.MyWizardTelegramBot;

@RestController
public class WebHookController {
    private final MyWizardTelegramBot telegramBot;

    public WebHookController(MyWizardTelegramBot telegramBot) {
        this.telegramBot = telegramBot;
    }
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public BotApiMethod<?> onUpdateRecieved(@RequestBody Update update){
        return telegramBot.onWebhookUpdateReceived(update);
    }
}
