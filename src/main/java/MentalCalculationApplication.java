import Storage.Items;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

public class MentalCalculationApplication {
    private static final Map<String, String> getenv = System.getenv();

    public static void main(String[] args) {
        Items items= Items.getInstance();
        items.fillData();
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("hookah_vibes14bot", "1772932504:AAHgJ7CJetGhFo6eQSI9wGLVJbW3-hhn63M"));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}