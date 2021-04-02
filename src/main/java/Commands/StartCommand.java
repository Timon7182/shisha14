package Commands;

import Buttons.KeyboardMarkup;
import org.checkerframework.checker.units.qual.K;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;


public class StartCommand extends ServiceCommand {

    public StartCommand(String identifier, String description) {
        super(identifier, description);
    }


    @Override
    public void execute(AbsSender absSender, User user, Chat chat, String[] strings) {

        String userName = (user.getFirstName() != null) ? user.getFirstName() :
                String.format("%s %s", user.getLastName(), user.getFirstName());


        sentPhoto(absSender, chat.getId(), this.getCommandIdentifier(), userName,
                "Здравствуйте " + userName + "!" +
                        "\n Hi");
    }
}