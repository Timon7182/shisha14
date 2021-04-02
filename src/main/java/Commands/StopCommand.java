package Commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

public class StopCommand extends ServiceCommand {


    public StopCommand(String identifier, String description) {
        super(identifier, description);
    }

    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] arguments) {
        String userName = (user.getUserName() != null) ? user.getUserName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());



            SendMessage answer = new SendMessage();
            answer.setChatId(chat.getId().toString());
            answer.setText("Good bye " + userName + "\n" + "Hope to see you soon!");

            try {
                absSender.execute(answer);
            } catch (TelegramApiException e) {

            }

    }
}
