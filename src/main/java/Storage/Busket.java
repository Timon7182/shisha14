package Storage;

import Buttons.KeyboardMarkup;
import Db.HibernateUtil;
import Domain.Item;
import Domain.Order;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.hibernate.resource.transaction.spi.TransactionStatus;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Busket {




    private static Busket instance;

    KeyboardMarkup keyboardMarkup= KeyboardMarkup.getInstance();

    Items items= Items.getInstance();


    public synchronized static Busket getInstance(){
        if(instance ==null){
            instance=new Busket();
        }
        return instance;
    }



    public Busket() {


    }




    @FunctionalInterface
    interface SqlAction<R> {
        R process(Session session, Transaction tr) throws Exception;
    }

    @FunctionalInterface
    interface CriteriaSetting<R, T>  {
        void create(CriteriaBuilder cb, Root<T> root, CriteriaQuery<R> query);
    }

    protected <R>  R doIt(SqlAction<R> action) throws Exception {
        try(Session session = HibernateUtil.getSessionFactory().getCurrentSession()) {
            Transaction tr = session.beginTransaction();
            try {
                return action.process(session, tr);
            }
            catch (Exception e) {
                tr.rollback();
                throw e;
            }
            finally {
                if (tr.getStatus().equals(TransactionStatus.ACTIVE))
                    tr.commit();
            }
        }
    }


    protected <R> Query<R> createCriteria(Session session, Class<R> resultClass, CriteriaSetting<R, Order> criteriaSetting) {
        CriteriaBuilder cb = session.getCriteriaBuilder();
        CriteriaQuery<R> cq = cb.createQuery(resultClass);
        Root<Order> root = cq.from(Order.class);
        criteriaSetting.create(cb, root, cq);
        return session.createQuery(cq);
    }



    public void saveOrupdate(Order entity) throws Exception{
        if(entity==null){
            return;
        }
        doIt((((session, tr) -> {
            session.saveOrUpdate(entity);
            return null;
        })));

    }
    public void saveOrupdateItem(Item entity) throws Exception{
        if(entity==null){
            return;
        }
        doIt((((session, tr) -> {
            session.saveOrUpdate(entity);
            return null;
        })));

    }




    public void getData(Update update,String count) throws Exception {

        Long id =update.getCallbackQuery().getFrom().getId();
        Order order =this.getById(id);
        String caption=update.getCallbackQuery().getMessage().getCaption();
        String[] splittedCaption = caption.split(" ");
        Item item=new Item((0),splittedCaption[0],this.getNumberFromString(count));
        saveOrupdateItem(item);
        if(order!=null){
            order.getOrders().add((item.getId()));
            saveOrupdate(order);

//523227863
        }else {
            Order order1=new Order();
            order1.setAddress(null);
            order1.setContactNumber(null);
            order1.setWaitingForAddress(false);
            order1.setWaitingForNumber(false);
            order1.setNewOrder(false);
            order1.setCustomerName(update.getCallbackQuery().getFrom().getFirstName());
            order1.setId(id);
            List<Long> items=new ArrayList<>();
            items.add(item.getId());
            order1.setOrders(items);
            saveOrupdate(order1);
        }

    }
    public List<Order> getAll() throws Exception{

        return doIt(((session, tr) -> {
            Query<Order> query = createCriteria(session,Order.class, (cb, r, cq) -> {
                cq.select(r);
            });
            return query.getResultList();}));
    }

    public Order getById(Long id) throws Exception {
        if (id == null)
            return null;

        return doIt((session, tr) -> session.find(Order.class, id));
    }

    public Item getByIdItem(Long id) throws Exception {
        if (id == null)
            return null;

        return doIt((session, tr) -> session.find(Item.class, id));
    }

    public void deleteOrderFromInline(Update update) throws Exception {
        Long id =update.getCallbackQuery().getFrom().getId();
        String text =update.getCallbackQuery().getMessage().getCaption().split(" ")[0];
        int count =Integer.parseInt(update.getCallbackQuery().getMessage().getCaption().split(" ")[2]);

        Order order = getById(id);
        for(int i=0;i<order.getOrders().size();i++){
            Item item=getByIdItem(order.getOrders().get(i));

            String name = text;

            boolean yes=item.getName().equals(name)&&item.getCount()==count;
            if(yes){
                order.getOrders().remove(i);
                long itemid =item.getId();
                deleteItems(itemid);
            }
        }
        saveOrupdate(order);
    }

    public void deleteOrder(Update update,int number) throws Exception {
        Long id =update.getCallbackQuery().getFrom().getId();
        String text =update.getCallbackQuery().getMessage().getReplyMarkup().getKeyboard().get(number).get(0).getText();
        Order order = getById(id);
        for(int i=0;i<order.getOrders().size();i++){
            Item item=getByIdItem(order.getOrders().get(i));
            String[] splitText = text.split(" ");
            String name = splitText[0];
            int count = Integer.parseInt(splitText[1]);
            boolean yes=item.getName().equals(name)&&item.getCount()==count;
            if(yes){
                order.getOrders().remove(i);
                long itemid =item.getId();
                deleteItems(itemid);
            }
        }
        saveOrupdate(order);
    }
    public void deleteAllOrders(Update update) throws Exception {
        Long id =update.getCallbackQuery().getFrom().getId();
        Order order = getById(id);
        List<Long> newEmptyList = new ArrayList<>();
        if(order!=null){
            for(int i=0;i<order.getOrders().size();i++){

                deleteItems(order.getOrders().get(i));

            }
            order.setOrders(newEmptyList);
            saveOrupdate(order);
        }

    }

    public void deleteItems(Long id) throws Exception {
        if (id == null){
            return;}

        Session session = HibernateUtil.getSessionFactory().openSession();
        try {
            String s="Items";
            String hql = "DELETE FROM "+s+" stud WHERE stud.id=:id";
            Query query = session.createQuery(hql);
            query.setParameter("id", id);
            long rowCount = query.executeUpdate();


        } catch (HibernateException e) {
            e.printStackTrace();
        } finally {
            session.close();
        }


    }


    public int getNumberFromString(String count){
        int result=0;
        switch (count){
            case "one": result=1;
            break;
            case "two": result=2;
                break;
            case "three": result=3;
                break;
            case "four": result=4;
                break;
        }
        return result;
    }

     public SendMessage order(Update update,String id,Order order) throws Exception {

        if(order.getAddress()!=null){
            order.setAddress(null);
            order.setContactNumber(null);
        }

        SendMessage sendMessage =new SendMessage();
        sendMessage.setChatId(id);

        if(order!=null && order.getOrders().size()>0){
            sendMessage.setText("Напишите пожалуйста ваш номер:");
            order.setWaitingForNumber(true);
            order.setNewOrder(true);
            saveOrupdate(order);
        }else {
            sendMessage.setText("У вас пустая корзина");
        }

        sendMessage.setReplyMarkup(keyboardMarkup.ordering());
        return sendMessage;
     }


     public int calculatePrice(String name,String count,Order order){
         HashMap pricesHookah = items.pricesHookah;
         HashMap pricesEach = items.pricesEach;
         int mainPrice=0;
        if(order!=null){
            if(order.getOrders().size()>0){
                mainPrice +=1000;
            }else {
                mainPrice +=Integer.parseInt(pricesHookah.get(name).toString())+2000;
            }

        }else {
            mainPrice +=Integer.parseInt(pricesHookah.get(name).toString())+2000;
        }

        int countInt = getNumberFromString(count);
        int eachPrice=Integer.parseInt(pricesEach.get(name).toString());

         int result = mainPrice+(eachPrice*(countInt-1));
        return result;

     }

     public int getPriceHookah(String name){
         HashMap pricesHookah = items.pricesHookah;
         return Integer.parseInt(pricesHookah.get(name).toString());
     }
    public int getPriceEach(String name){
        HashMap pricesEach = items.pricesEach;
        return Integer.parseInt(pricesEach.get(name).toString());

    }
}
