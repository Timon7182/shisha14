package Buttons;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.util.ArrayList;
import java.util.List;


public class KeyboardMarkup {

    private static KeyboardMarkup instance;


    public synchronized static KeyboardMarkup getInstance(){
        if(instance ==null){
            instance=new KeyboardMarkup();
        }
        return instance;
    }

    public KeyboardMarkup() {
    }

    public ReplyKeyboardMarkup firstPage(){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add("\uD83D\uDCA8" + " Каталог");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDCAC О нас");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDCDE "+"Связаться с нами");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }



    public ReplyKeyboardMarkup listOfItemsWithTypes(){

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add("\uD83D\uDCA8 Легкую");
        row.add("\uD83D\uDCA8 Среднюю");
        row.add("\uD83D\uDCA8 Крепкую");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDE96 Заказать");
        row.add("\uD83D\uDCCB Главное меню");
        row.add("Пустой аппарат");

        keyboard.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDED2 Корзина");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup ordering(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add("Назад");
        row.add("Отменить заказ");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDED2 Корзина");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }

    public ReplyKeyboardMarkup orderProcessingFinal(){
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow row = new KeyboardRow();
        row.add("Назад");
        row.add("Отменить заказ");
        row.add("Оформить заказ");
        keyboard.add(row);
        row=new KeyboardRow();
        row.add("Изменить номер");
        row.add("Изменить адрес");
        keyboard.add(row);
        row = new KeyboardRow();
        row.add("\uD83D\uDED2 Корзина");
        keyboard.add(row);
        keyboardMarkup.setKeyboard(keyboard);
        return keyboardMarkup;
    }







}
