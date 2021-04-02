import Buttons.InlineKeyboard;
import Buttons.KeyboardMarkup;
import Domain.Item;
import Domain.Order;
import Storage.Busket;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class NonCommands {



    private static NonCommands instance;

    Busket busket= Busket.getInstance();
    KeyboardMarkup markup = KeyboardMarkup.getInstance();

    InlineKeyboard inlineKeyboardMarkup = new InlineKeyboard();
    public synchronized static NonCommands getInstance(){
        if(instance ==null){
            instance=new NonCommands();
        }
        return instance;
    }

    public NonCommands() {
    }

    public SendMessage updatecommands(String message, String id, Update update) throws Exception {
        SendMessage sendMessage=new SendMessage();
        Order o=busket.getById(update.getMessage().getFrom().getId());
        sendMessage.setChatId(id);

        if(message.equals("\uD83D\uDCA8" + " Каталог")){

            sendMessage.setText("Какую крепкость желаете?");
            sendMessage.setReplyMarkup(markup.listOfItemsWithTypes());

        }else if(message.equals("\uD83D\uDCCB Главное меню")){
            sendMessage.setText("Главное меню");
            sendMessage.setReplyMarkup(markup.firstPage());
        }
        else if(message.equals("\uD83D\uDCCB Главное меню")){
            sendMessage.setText("Главное меню");
            sendMessage.setReplyMarkup(markup.firstPage());
        }else if(message.equals("\uD83D\uDCAC О нас")){
            sendMessage.setText("\n Hookah Vibes Pavlodar " +
                    "\n "+"\n"+
                    "•Доставка 24/7 \n" +
                    "•Стерильно и безопасно\n" +
                    "•Акции каждую неделю\n" +
                    "•Кальянные карты только у нас"+
                    "\n"+"\n"+
                    " Instagram: https://instagram.com/hookah_vibes14?igshid=1qreij8pr08vv" +"\n"+
                    " Telegram: https://t.me/hookah_vibes14");
        }




        else if(message.equals("Назад")){
            if(o!=null){
                o.setWaitingForAddress(false);
                o.setWaitingForNumber(false);
            }

            sendMessage.setText("Какую крепкость желаете?");
            sendMessage.setReplyMarkup(markup.listOfItemsWithTypes());
        }
        else if(message.equals("В главное меню")){
            sendMessage.setText("Главное меню");
            sendMessage.setReplyMarkup(markup.firstPage());
        }
        else if(message.equals("Изменить адрес")){
            if(o.getAddress()!=""){
                sendMessage.setText("Введите новый адрес");
                o.setWaitingForAddress(true);
                busket.saveOrupdate(o);

            }else {
                sendMessage.setText("У вас пустой адрес");
            }
            sendMessage.setReplyMarkup(markup.orderProcessingFinal());

        }
        else if(message.equals("Изменить номер")){

            if(o.getAddress()!=""){
                sendMessage.setText("Введите новый номер");
                o.setWaitingForNumber(true);
                busket.saveOrupdate(o);

            }else {
                sendMessage.setText("У вас пустой номер");
            }
            sendMessage.setReplyMarkup(markup.orderProcessingFinal());

        }


        else if (message.equals("\uD83D\uDED2 Корзина")) {

            Busket busket = Busket.getInstance();

            if(o==null || o.getOrders().size()==0){
                sendMessage.setText("У вас пустая корзина");
            }else{
                String lastResult="";
                int finalPrice=0;
                int delivery=500;
                int add =0;
                int count=0;
                boolean existHardTobacco=false;
                for(int i=0;i<o.getOrders().size();i++){
                    Long itemIds= o.getOrders().get(i);
                    Item item= busket.getByIdItem(itemIds);
                    if(item!=null){
                        lastResult=lastResult+item.getName()  +item.getCount() +" \n";
                        if(item.getName().equals("Musthave") || item.getName().equals("Darkside") || item.getName().equals("Blackburn")){
                            delivery=0;
                            existHardTobacco=true;
                        }
                        count+=item.getCount();

                    }else {
                        lastResult="";
                        break;
                    }

                }
                if(existHardTobacco==true){
                    add=1500;
                }
                finalPrice=add+count*1000 +2000;
                sendMessage.setReplyMarkup(inlineKeyboardMarkup.keyboardFromOrders(o));
                if(lastResult.equals("")){
                    sendMessage.setText("У вас пустая корзина");

                }else{
                    sendMessage.setText("Ваш заказ:\n "+"\n" + lastResult + "\n" + "\n Цена: " + finalPrice +"KZT +"+" Доставка: "+ delivery +"KZT");

                }
            }

        }
        else if(message.equals("Отменить заказ")){
            if(o!=null){
                List<Long> newEmptyList= new ArrayList<>();
                o.setOrders(newEmptyList);
                o.setWaitingForAddress(false);
                o.setWaitingForNumber(false);
                busket.saveOrupdate(o);
                sendMessage.setText("Заказ отменен и корзина пуста");
            }else {
                sendMessage.setText("У вас корзина пустая");
            }

            sendMessage.setReplyMarkup(markup.listOfItemsWithTypes());
        }else if(o!=null){
            if(o.getWaitingForNumber()==true && o.getContactNumber()!=null && o.getNewOrder()==false && !message.equals("\uD83D\uDE96 Заказать")){
                String response="";
                if(o==null || o.getOrders().size()==0){
                    sendMessage.setText("У вас пустая корзина");
                }else {
                    o.setWaitingForNumber(false);
                    String number = update.getMessage().getText();
                    o.setContactNumber(number);
                    busket.saveOrupdate(o);
                    sendMessage.setText("Номер изменен - " + o.getContactNumber());



                }
            }
            else if (o.getWaitingForAddress()==true && o.getAddress()!=null  && o.getNewOrder()==false && !message.equals("\uD83D\uDE96 Заказать")) {

                String response="";
                if(o==null || o.getOrders().size()==0){
                    sendMessage.setText("У вас пустая корзина");
                }else {
                    o.setWaitingForAddress(false);
                    String address = update.getMessage().getText();
                    o.setAddress(address);
                    busket.saveOrupdate(o);
                    sendMessage.setText("Адрес изменен - " + o.getAddress());

                }

            }


            else if (o.getWaitingForNumber()==true && !message.equals("\uD83D\uDE96 Заказать")) {


                String response="";
                if(o==null || o.getOrders().size()==0){
                    sendMessage.setText("У вас пустая корзина");
                }else {
                    o.setNewOrder(false);
                    o.setWaitingForNumber(false);
                    o.setWaitingForAddress(true);
                    String number = update.getMessage().getText();
                    o.setContactNumber(number);
                    busket.saveOrupdate(o);
                    sendMessage.setText("Напишите адрес доставки: (улица,подьезд,кв)");
                }

            }
            else if (o.getWaitingForAddress()==true && !message.equals("\uD83D\uDE96 Заказать")) {


                String response="";
                if(o==null || o.getOrders().size()==0){
                    sendMessage.setText("У вас пустая корзина");
                }else {
                    o.setWaitingForAddress(false);
                    o.setAddress(update.getMessage().getText());
                    busket.saveOrupdate(o);
                    sendMessage.setText("Ваш номер:" + o.getContactNumber() + " и адрес: " +o.getAddress());
                    sendMessage.setReplyMarkup(markup.orderProcessingFinal());
                }

            }
        }








        return sendMessage;

    }
}
