package testmodel.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.*;
import lombok.Data;
import testmodel.InventoryApplication;
import testmodel.domain.*;
import testmodel.infra.AbstractEvent;

@Entity
@Table(name = "Inventory_table")
@Data
//<<< DDD / Aggregate Root
public class Inventory {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long productId;

    private Integer stock;

    @PostPersist
    public void onPostPersist() {
        // logic to handle PostPersist event
    }

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = InventoryApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // implement business logic here
        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publishAfterCommit();
    }

    public static void updateInventory(OrderPlaced orderPlaced) {
        //implement business logic here

        repository()
            .findById(orderPlaced.getProductId())
            .ifPresent(inventory -> {
                // do something

                inventory.setStock(inventory.getStock() - orderPlaced.getQty());
                repository().save(inventory);

                InventoryUpdated inventoryUpdated = new InventoryUpdated(
                    inventory
                );
                inventoryUpdated.publishAfterCommit();
            });
    }
}
