package Domain;

import javax.persistence.*;

@Entity(name = "Items")
public class Item {
    @Id
    @Column(name = "id", unique = true, updatable = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "price",unique = false,nullable = true)
    private int price;

    @Column(name = "name",unique = false,nullable = true)
    private String name;

    @Column(name = "count",unique = false,nullable = true)
    private int count;

    public Item(int price, String name, int count) {
        this.price = price;
        this.name = name;
        this.count = count;
    }

    public Item() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
