import Buttons.InlineKeyboard;
import Buttons.KeyboardMarkup;
import Commands.StartCommand;
import Commands.StopCommand;
import Domain.Item;
import Domain.Order;
import Storage.Busket;
import Storage.Items;
import org.checkerframework.checker.units.qual.K;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendContact;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageCaption;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ForceReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.updateshandlers.SentCallback;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


public final class Bot extends TelegramLongPollingCommandBot {
    private final String BOT_NAME;
    private final String BOT_TOKEN;

    InlineKeyboard markup = new InlineKeyboard();
    KeyboardMarkup keyboardMarkup = KeyboardMarkup.getInstance();

    Busket busket=Busket.getInstance();
    Items items= Items.getInstance();

    Order order=null;


    public Bot(String botName, String botToken) {
        super();
        this.BOT_NAME = botName;
        this.BOT_TOKEN = botToken;


        register(new StartCommand("start", "Старт"));
        register(new StopCommand("stop","Стоп"));
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }


    @Override
    public void processNonCommandUpdate(Update update) {
        NonCommands nonCommands=NonCommands.getInstance();
        if(update.hasMessage() && update.getMessage().hasText()){
            String msg = update.getMessage().getText();


            if(msg.equals("\uD83D\uDCA8 Легкую")){
                HashMap sweets=items.sweetLight;
                HashMap text=items.sweetNames;
                try{


                    for(int i=1;i<=sweets.size();i++){
                        String url = (String) sweets.get(String.valueOf(i));
                        String caption =text.get(i).toString();
                        SendPhoto sendPhoto=markup.getCounts(update.getMessage().getChatId().toString(), url,caption);

                        execute(sendPhoto);

                    }

                }catch (Exception e){

                    System.out.println(e);
                }
            }
            else if(msg.equals("\uD83D\uDE96 Заказать")){
                try {
                    execute(busket.order(update,update.getMessage().getChatId().toString(),busket.getById(update.getMessage().getFrom().getId())));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if(msg.equals("\uD83D\uDCA8 Среднюю")){
                HashMap sweets=items.mediumLinks;
                HashMap text=items.mediumNames;
                try{


                    for(int i=1;i<=sweets.size();i++){
                        String url = (String) sweets.get(String.valueOf(i));
                        String caption =text.get(i).toString();
                        SendPhoto sendPhoto=markup.getCounts(update.getMessage().getChatId().toString(), url,caption);

                        execute(sendPhoto);

                    }

                }catch (Exception e){

                    System.out.println(e);
                }

            }
            else if(msg.equals("\uD83D\uDCA8 Крепкую")){
                HashMap sweets=items.hardLinks;
                HashMap text=items.hardNames;

                try{


                    for(int i=1;i<=sweets.size();i++){
                        String url = (String) sweets.get(String.valueOf(i));
                        String caption =text.get(i).toString();
                        SendPhoto sendPhoto=markup.getCounts(update.getMessage().getChatId().toString(), url,caption);

                        execute(sendPhoto);

                    }

                }catch (Exception e){

                    System.out.println(e);
                }

            }

            else if(msg.equals("Пустой аппарат")){

                try{



                        String url ="https://cs10.pikabu.ru/post_img/big/2018/12/10/10/1544463358162979440.jpg";
                        String caption ="Пустой аппарат" + " \n" + "\nЦена за одну: 2000KZT";
                        SendPhoto sendPhoto=markup.getCounts(update.getMessage().getChatId().toString(), url,caption);

                        execute(sendPhoto);


                }catch (Exception e){

                    System.out.println(e);
                }

            }
            else if(msg.equals("\uD83D\uDCDE "+"Связаться с нами")){

                SendContact yesbolatContact = new SendContact();
                SendContact zhasik = new SendContact();
                zhasik.setFirstName("Жасик");
                zhasik.setPhoneNumber("+77789747700 ");
                zhasik.setChatId(update.getMessage().getChatId().toString());

                yesbolatContact.setFirstName("Есболат");
                yesbolatContact.setPhoneNumber("+77783368725");
                yesbolatContact.setChatId(update.getMessage().getChatId().toString());

                try {
                    execute(yesbolatContact);
                    execute(zhasik);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

            }
            else if(msg.equals("Оформить заказ")){
                SendMessage sendMessage = new SendMessage();
                SendMessage sendMessageToUser = new SendMessage();

                sendMessageToUser.setChatId(update.getMessage().getChatId().toString());
                Order o= null;
                try {
                    o = busket.getById(update.getMessage().getFrom().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                sendMessage.setChatId("-1001488368989");
                String customer=o.getCustomerName() + " \n Адрес: " +  o.getAddress() + ". \n Номер: " + o.getContactNumber() + " \n username: "+ update.getMessage().getFrom().getUserName()+" \n";

                String lastReuslt="";
                int finalPrice=0;
                int delivery=500;
                int add =0;
                int count=0;
                boolean existHardTobacco=false;
                for(int i=0;i<o.getOrders().size();i++){
                    Long itemIds= o.getOrders().get(i);
                    Item item= null;
                    try {
                        item = busket.getByIdItem(itemIds);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(item!=null){
                        lastReuslt=lastReuslt+item.getName() + " " +item.getCount() +" \n";
                        if(item.getName().equals("Musthave") || item.getName().equals("Darkside") || item.getName().equals("Blackburn")){
                            delivery=0;
                            existHardTobacco=true;
                        }
                        count+=item.getCount();
                    }else {
                        lastReuslt="";
                        break;
                    }

                }
                if(existHardTobacco==true){
                    add=1500;
                }
                finalPrice=add+count*1000+2000;

                List<Long> newList= new LinkedList<>();
                o.setOrders(newList);
                try {
                    busket.saveOrupdate(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                sendMessageToUser.setText("Спасибо за заказ, ближайшие минуты свяжемся с вами \uD83D\uDC9C \n"+"\nИмя: "+o.getCustomerName() +" \n"+
                        "Номер: "+o.getContactNumber() +" \n"+
                        "Адрес: "+o.getAddress() +" \n"+
                        "Ваш Заказ: "+"\n"+ lastReuslt+"\n "+ "\n Цена:" + finalPrice  +"KZT  +"+" Доставка: "+ delivery+"KZT");
                sendMessageToUser.setReplyMarkup(keyboardMarkup.firstPage());
                sendMessage.setText("Заказ: " + customer + lastReuslt +"\n "+ "\n Цена:" + finalPrice + "KZT + " +" Доставка: "+ delivery+"KZT");
                try {
                    execute(sendMessage);
                    execute(sendMessageToUser);

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }

                try{
                    execute(nonCommands.updatecommands(msg,update.getMessage().getChatId().toString(),update));
                }catch (TelegramApiException | FileNotFoundException e){

                } catch (Exception e) {
                    e.printStackTrace();
                }
        } else if (update.hasCallbackQuery()) {
            String call_data = update.getCallbackQuery().getData();
            String[] call_divided=update.getCallbackQuery().getData().split(" ");
            String call_data_1=call_divided[0];
            String call_data_2="";
            if(call_divided.length>1){
                 call_data_2=call_divided[1];

            }

            Integer message_id = update.getCallbackQuery().getMessage().getMessageId();
            Long chat_id = update.getCallbackQuery().getMessage().getChatId();


            if (call_data.equals("one")||call_data.equals("two")||call_data.equals("three")||call_data.equals("four")) {

                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();

                InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton();
                inlineKeyboardButton1.setText("\uD83D\uDC4C Добавлено в корзину");
                inlineKeyboardButton1.setCallbackData("added");
                InlineKeyboardButton inlineKeyboardButton2 = new InlineKeyboardButton();
                inlineKeyboardButton2.setText("Убрать из корзины");
                inlineKeyboardButton2.setCallbackData("cancel");

                List<InlineKeyboardButton> keyboardButtonsRow1 = new ArrayList<InlineKeyboardButton>();
                List<InlineKeyboardButton> keyboardButtonsRow2 = new ArrayList<InlineKeyboardButton>();

                keyboardButtonsRow1.add(inlineKeyboardButton1);
                keyboardButtonsRow1.add(inlineKeyboardButton2);

                InlineKeyboardButton inlineKeyboardButton3 = new InlineKeyboardButton();
                inlineKeyboardButton3.setText("\uD83D\uDE96 Заказать");
                inlineKeyboardButton3.setCallbackData("order");

                keyboardButtonsRow2.add(inlineKeyboardButton3);

                List<List<InlineKeyboardButton>> rowList = new ArrayList<>();
                rowList.add(keyboardButtonsRow1);
                rowList.add(keyboardButtonsRow2);

                inlineKeyboardMarkup.setKeyboard(rowList);
                EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
                editMessageReplyMarkup.setChatId(chat_id.toString());
                editMessageReplyMarkup.setMessageId(message_id);
                editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);

                EditMessageCaption messageCaption = new EditMessageCaption();
                messageCaption.setChatId(chat_id.toString());

                try {
                     order= busket.getById(update.getCallbackQuery().getFrom().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                messageCaption.setCaption(update.getCallbackQuery().getMessage().getCaption().split(" ")[0] + " x " + busket.getNumberFromString(call_data));
                messageCaption.setMessageId(message_id);
                try {
                    busket.getData(update,call_data);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    execute(messageCaption);
                    execute(editMessageReplyMarkup);

                } catch (Exception e) {
                    System.out.println(e);
                }
            }else if(call_data.equals("cancel")){

                InlineKeyboardMarkup inlineKeyboardMarkup = markup.count();
                EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
                editMessageReplyMarkup.setChatId(chat_id.toString());
                editMessageReplyMarkup.setMessageId(message_id);
                editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);

                EditMessageCaption messageCaption = new EditMessageCaption();
                messageCaption.setChatId(chat_id.toString());
                String text=update.getCallbackQuery().getMessage().getCaption();
                String hookah =text.split(" ")[0];
                String caption =hookah+ " \n" + "\n Выберите количество забивок: ";
                messageCaption.setReplyMarkup(markup.count());
                messageCaption.setCaption(caption);
                messageCaption.setMessageId(message_id);

                try {
                    busket.deleteOrderFromInline(update);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    execute(editMessageReplyMarkup);
                    execute(messageCaption);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else if(call_data_1.equals("removeOrder")){

                Order o=null;
                try {
                    busket.deleteOrder(update,Integer.parseInt(call_data_2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                     o= busket.getById(update.getCallbackQuery().getFrom().getId());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                InlineKeyboardMarkup inlineKeyboardMarkup = null;
                try {
                    inlineKeyboardMarkup = markup.keyboardFromOrders(o);
                } catch (Exception e) {
                    e.printStackTrace();
                }


                String lastReuslt="";
                int finalPrice=0;
                int delivery=500;
                int add =0;
                int count=0;
                boolean existHardTobacco=false;
                for(int i=0;i<o.getOrders().size();i++){
                    Long itemIds= o.getOrders().get(i);
                    Item item= null;
                    try {
                        item = busket.getByIdItem(itemIds);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if(item!=null){
                        lastReuslt=lastReuslt+item.getName() +" " +item.getCount() +" \n";
                        if(item.getName().equals("Musthave") || item.getName().equals("Darkside") || item.getName().equals("Blackburn")){
                            delivery=0;
                            existHardTobacco=true;
                        }
                        count+=item.getCount();
                    }else {
                        lastReuslt="";
                        break;
                    }

                }
                if(existHardTobacco==true){
                    add=1500;
                }
                finalPrice=add+count*1000+2000;

                EditMessageText editMessageText=new EditMessageText();
                editMessageText.setChatId(chat_id.toString());
                editMessageText.setMessageId(message_id);
                editMessageText.setReplyMarkup(inlineKeyboardMarkup);
                editMessageText.setText("Ваш заказ: \n "+"\n" + lastReuslt + "\n" + "\n Цена: " + finalPrice +"KZT +"+" Доставка: "+ delivery +"KZT");
                try {
                    execute(editMessageText);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }

            else if(call_data.equals("removeAll")){

                try {
                    busket.deleteAllOrders(update);
                } catch (Exception e) {
                    e.printStackTrace();
                }



                String lastResult="Корзина очищена \n ";
                EditMessageText editMessageText=new EditMessageText();
                editMessageText.setChatId(chat_id.toString());
                editMessageText.setMessageId(message_id);
                editMessageText.setText(lastResult);
                try {
                    execute(editMessageText);
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
            else if(call_data.equals("order")){
                try {

                    execute(busket.order(update,update.getCallbackQuery().getMessage().getChatId().toString(),busket.getById(update.getCallbackQuery().getFrom().getId())));

                } catch (TelegramApiException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }

    }



}