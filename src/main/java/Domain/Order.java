package Domain;

import com.sun.xml.bind.v2.model.core.ID;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Orders")
public class Order {

    @Id
    @Column(name = "Id", unique = true, updatable = true, nullable = false)
    private Long id;


    @Column(name = "CustomerName",unique = false,nullable = false)
    private String CustomerName;


    @ElementCollection(fetch= FetchType.EAGER)
    @Column(name = "orders")
    private List<Long> orders;

    @Column(name = "address",unique = false,nullable = true)
    private String address;

    @Column(name = "contactNumber",unique = false,nullable = true)
    private String contactNumber;

    @Column(name = "waitingForAddress",unique = false,nullable = true)
    private Boolean waitingForAddress;

    @Column(name = "waitingForNumber",unique = false,nullable = true)
    private Boolean waitingForNumber;

    @Column(name = "newOrder",unique = false,nullable = true)
    private Boolean newOrder;


    public Order(Long id,String customerName, String address, String contactNumber) {
        CustomerName = customerName;
        this.address = address;
        this.contactNumber = contactNumber;
        this.orders=new ArrayList<>();
        this.waitingForAddress=false;
        waitingForNumber=false;
        this.newOrder=false;
    }

    public Order() {

    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public List<Long> getOrders() {
        return orders;
    }

    public void setOrders(List<Long> orders) {
        this.orders = orders;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getWaitingForAddress() {
        return waitingForAddress;
    }

    public void setWaitingForAddress(Boolean waitingForAddress) {
        this.waitingForAddress = waitingForAddress;
    }

    public Boolean getWaitingForNumber() {
        return waitingForNumber;
    }

    public void setWaitingForNumber(Boolean waitingForNumber) {
        this.waitingForNumber = waitingForNumber;
    }

    public Boolean getNewOrder() {
        return newOrder;
    }

    public void setNewOrder(Boolean newOrder) {
        this.newOrder = newOrder;
    }
}
