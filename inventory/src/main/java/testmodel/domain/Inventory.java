package testmodel.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import testmodel.InventoryApplication;

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
    public void onPostPersist() {}

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = InventoryApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    //<<< Clean Arch / Port Method
    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // Decrease the stock by the quantity specified in the command
        stock -= decreaseStockCommand.getQty();
        // Check if the stock is below 0 and set it to 0
        if (stock < 0) {
            stock = 0;
        }
        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void updateInventory(OrderPlaced orderPlaced) {
        repository()
            .findById(orderPlaced.getId())
            .ifPresent(inventory -> {
                // Update the stock based on the order quantity
                inventory.setStock(inventory.getStock() - orderPlaced.getQty());
                // Check if the stock is below 0 and set it to 0
                if (inventory.getStock() < 0) {
                    inventory.setStock(0);
                }
                repository().save(inventory);
                InventoryUpdated inventoryUpdated = new InventoryUpdated(
                    inventory
                );
                inventoryUpdated.publishAfterCommit();
            });
    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
