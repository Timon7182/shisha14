package Storage;

import Buttons.InlineKeyboard;

import java.util.HashMap;

public class Items {
    
    public HashMap sweetLight;
    public HashMap sweetNames;
    public HashMap mediumLinks;
    public HashMap mediumNames;
    public HashMap hardLinks;
    public HashMap hardNames;
    public HashMap pricesHookah;
    public HashMap pricesEach;

    private static Items instance;


    public synchronized static Items getInstance(){
        if(instance ==null){
            instance=new Items();
        }
        return instance;
    }
    public Items() {
        this.sweetLight = new HashMap();
        this.sweetNames=new HashMap();

        this.mediumNames=new HashMap();
        this.mediumLinks=new HashMap();

        this.hardLinks=new HashMap();
        this.hardNames=new HashMap();

        this.pricesHookah=new HashMap();
        this.pricesEach = new HashMap();


    }

    public void fillData(){

        sweetLight.put("1","https://i.pinimg.com/564x/85/f8/c7/85f8c7bc079a65b935721b416878a83e.jpg");
        sweetLight.put("2","https://i.pinimg.com/564x/7e/e1/61/7ee16162e098f19700cd2457d3a7e87c.jpg");
        sweetNames.put(1,"Serbetli \n Выберите количество забивок:");
        sweetNames.put(2,"Jibiar \nВыберите количество забивок:");

        mediumLinks.put("1","https://i.pinimg.com/564x/ee/02/5f/ee025fa3c1d30c05ff5600d9134a52c4.jpg");
        mediumLinks.put("2","https://i.pinimg.com/564x/2b/b3/fc/2bb3fcb3c25671c22cf9bf6948e8e4a9.jpg");
        mediumLinks.put("3","https://i.pinimg.com/564x/ad/fc/44/adfc44cc6efc6dc468ac00b5daf9826d.jpg");
        mediumNames.put(1,"Adalya \nВыберите количество забивок:");
        mediumNames.put(2,"Al-Fakher \nВыберите количество забивок:");
        mediumNames.put(3,"Lirra \nВыберите количество забивок:");


        hardLinks.put("1","https://i.pinimg.com/564x/6c/67/06/6c6706bcfed29745a67e07047906937c.jpg");
        hardLinks.put("2","https://i.pinimg.com/564x/84/fc/af/84fcaffe05d94109370b94b4323ac9de.jpg");
        hardLinks.put("3","https://i.pinimg.com/564x/04/96/4f/04964f12d5e4dc5b060f1f108cfedf0c.jpg");
        hardNames.put(1,"Musthave \nВыберите количество забивок:");
        hardNames.put(2,"Darkside \nВыберите количество забивок:");
        hardNames.put(3,"Blackburn \nВыберите количество забивок:");

        pricesHookah.put("Serbetli",1000);
        pricesHookah.put("Jibiar",1000);
        pricesHookah.put("Adalya",1000);
        pricesHookah.put("Al-Fakher",1000);
        pricesHookah.put("Lirra",1000);

        pricesEach.put("Serbetli",1000);
        pricesEach.put("Jibiar",1000);
        pricesEach.put("Adalya",1000);
        pricesEach.put("Al-Fakher",1000);
        pricesEach.put("Lirra",1000);

        pricesHookah.put("Musthave",1000);
        pricesHookah.put("Darkside",1000);
        pricesHookah.put("Blackburn",1000);
        pricesHookah.put("Пустой",2000);

        pricesEach.put("Musthave",1000);
        pricesEach.put("Darkside",1000);
        pricesEach.put("Blackburn",1000);
        pricesEach.put("Пустой",2000);


    }

    public HashMap getSweetLight() {
        return sweetLight;
    }

    public void setSweetLight(HashMap sweetLight) {
        this.sweetLight = sweetLight;
    }

    public HashMap getSweetNames() {
        return sweetNames;
    }

    public void setSweetNames(HashMap sweetNames) {
        this.sweetNames = sweetNames;
    }

    public HashMap getMediumLinks() {
        return mediumLinks;
    }

    public void setMediumLinks(HashMap mediumLinks) {
        this.mediumLinks = mediumLinks;
    }

    public HashMap getMediumNames() {
        return mediumNames;
    }

    public void setMediumNames(HashMap mediumNames) {
        this.mediumNames = mediumNames;
    }

    public HashMap getHardLinks() {
        return hardLinks;
    }

    public void setHardLinks(HashMap hardLinks) {
        this.hardLinks = hardLinks;
    }

    public HashMap getHardNames() {
        return hardNames;
    }

    public void setHardNames(HashMap hardNames) {
        this.hardNames = hardNames;
    }

    public HashMap getPricesHookah() {
        return pricesHookah;
    }

    public void setPricesHookah(HashMap pricesHookah) {
        this.pricesHookah = pricesHookah;
    }

    public HashMap getPricesEach() {
        return pricesEach;
    }

    public void setPricesEach(HashMap pricesEach) {
        this.pricesEach = pricesEach;
    }


}

