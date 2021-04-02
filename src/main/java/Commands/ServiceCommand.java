package Commands;

import Buttons.KeyboardMarkup;
import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;


abstract class ServiceCommand extends BotCommand {

    ServiceCommand(String identifier, String description) {
        super(identifier, description);
    }

    KeyboardMarkup keyboardMarkup = KeyboardMarkup.getInstance();
    void sentPhoto(AbsSender absSender, Long chatId, String commandName, String userName, String text){
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setCaption("Здравсвуйте "+userName+ ", вот наш прайс лист \uD83D\uDE04");
        sendPhoto.setPhoto(new InputFile("https://www.instagram.com/s/aGlnaGxpZ2h0OjE3ODU0MzMwMjUwNDUzNTg2?igshid=1evqoogpqmznl&story_media_id=2489366547897040734"));
        sendPhoto.setReplyMarkup(keyboardMarkup.firstPage());
        try {
            absSender.execute(sendPhoto);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя commandName и userName
        }
    }
    void sendAnswer(AbsSender absSender, Long chatId, String commandName, String userName, String text) {
        SendMessage message = new SendMessage();
        KeyboardMarkup keyboardMarkup = KeyboardMarkup.getInstance();

        message.enableMarkdown(true);
        message.setChatId(chatId.toString());
        message.setText(text);
        message.setReplyMarkup(keyboardMarkup.firstPage());
        try {
            absSender.execute(message);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя commandName и userName
        }
    }
}