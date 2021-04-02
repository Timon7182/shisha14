package Buttons;

import Domain.Item;
import Domain.Order;
import Storage.Busket;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

public class InlineKeyboard {




    public InlineKeyboard() {

    }
    Busket busket =Busket.getInstance();

    public InlineKeyboardMarkup count(){

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("1");
        inlineKeyboardButton1.setCallbackData("onecount");

        inlineKeyboardButton2.setText("2");
        inlineKeyboardButton2.setCallbackData("two");

        inlineKeyboardButton3.setText("3");
        inlineKeyboardButton3.setCallbackData("three");

        inlineKeyboardButton4.setText("4");
        inlineKeyboardButton4.setCallbackData("four");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow1.add(inlineKeyboardButton3);
        keyboardButtonsRow1.add(inlineKeyboardButton4);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);

        return inlineKeyboardMarkup;
    }
    public SendPhoto getCounts(String chatId,String url,String text)  {


        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
        InlineKeyboardButton inlineKeyboardButton4 = new InlineKeyboardButton();

        inlineKeyboardButton1.setText("1");
        inlineKeyboardButton1.setCallbackData("one");

        inlineKeyboardButton2.setText("2");
        inlineKeyboardButton2.setCallbackData("two");

        inlineKeyboardButton3.setText("3");
        inlineKeyboardButton3.setCallbackData("three");

        inlineKeyboardButton4.setText("4");
        inlineKeyboardButton4.setCallbackData("four");

        List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>();

        keyboardButtonsRow1.add(inlineKeyboardButton1);
        keyboardButtonsRow1.add(inlineKeyboardButton2);
        keyboardButtonsRow1.add(inlineKeyboardButton3);
        keyboardButtonsRow1.add(inlineKeyboardButton4);


        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();

        rowList.add(keyboardButtonsRow1);

        inlineKeyboardMarkup.setKeyboard(rowList);



        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        sendPhoto.setCaption(text);
        sendPhoto.setPhoto(new InputFile(url));

        sendPhoto.setReplyMarkup(inlineKeyboardMarkup);
        return sendPhoto;
    }

    public InlineKeyboardMarkup keyboardFromOrders(Order order) throws Exception {

        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
        if(order.getOrders().size()!=0){
            for(int i=0;i<order.getOrders().size();i++){

                InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton();
                Item item=busket.getByIdItem(order.getOrders().get(i));
                inlineKeyboardButton.setText(item.getName() + " " + item.getCount() + "  | " + " Убрать");
                inlineKeyboardButton.setCallbackData("removeOrder"+" "+ i);
                List<InlineKeyboardButton> keyboardButtonsRow = new ArrayList<InlineKeyboardButton>();
                keyboardButtonsRow.add(inlineKeyboardButton);
                rowList.add(keyboardButtonsRow);
            }
            inlineKeyboardButton1.setText("Очистить");
            inlineKeyboardButton1.setCallbackData("removeAll");
            List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>();
            keyboardButtonsRow1.add(inlineKeyboardButton1);
            rowList.add(keyboardButtonsRow1);

            inlineKeyboardMarkup.setKeyboard(rowList);
        }



        return inlineKeyboardMarkup;
    }





}
