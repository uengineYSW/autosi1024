package testmodel.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import testmodel.OrderApplication;
import testmodel.domain.OrderRefused;

@Entity
@Table(name = "Order_table")
@Data
//<<< DDD / Aggregate Root
public class Order {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)

    private Long id;

    private String productId;

    private Integer qty;

    @PostPersist
    public void onPostPersist() {
        OrderRefused orderRefused = new OrderRefused(this);
        orderRefused.publishAfterCommit();
        // Get request from Order
        //testmodel.external.Order order =
        //    Application.applicationContext.getBean(testmodel.external.OrderService.class)
        //    .getOrder(/** mapping value needed */);

    }

    public static OrderRepository repository() {
        OrderRepository orderRepository = OrderApplication.applicationContext.getBean(
            OrderRepository.class
        );
        return orderRepository;
    }

    //<<< Clean Arch / Port Method
    public void order() {
        //implement business logic here:

        OrderPlaced orderPlaced = new OrderPlaced(this);
        orderPlaced.publishAfterCommit();

        //Following code causes dependency to external APIs
        // it is NOT A GOOD PRACTICE. instead, Event-Policy mapping is recommended.

        testmodel.external.Inventory inventory = new testmodel.external.Inventory();
        // mappings goes here
        OrderApplication.applicationContext
            .getBean(testmodel.external.InventoryService.class)
            .decreaseStock(inventory);
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
