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
        //implement business logic here:

        this.stock -= decreaseStockCommand.getQty();

        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void updateInventory(OrderPlaced orderPlaced) {
        //implement business logic here:

        /** Example 1:  new item 
        Inventory inventory = new Inventory();
        repository().save(inventory);

        InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
        inventoryUpdated.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        repository().findById(orderPlaced.get???()).ifPresent(inventory->{
            
            inventory // do something
            repository().save(inventory);

            InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
            inventoryUpdated.publishAfterCommit();

         });
        */

        if (orderPlaced.getProductId().equals("p1")) {
            Inventory inventory = repository().findById(1L).orElse(null);
            if (inventory != null) {
                inventory.setStock(inventory.getStock() - orderPlaced.getQty());
                repository().save(inventory);
            }
        } else if (orderPlaced.getProductId().equals("p2")) {
            Inventory inventory = repository().findById(2L).orElse(null);
            if (inventory != null) {
                inventory.setStock(inventory.getStock() - orderPlaced.getQty());
                repository().save(inventory);
            }
        } else if (orderPlaced.getProductId().equals("p3")) {
            Inventory inventory = repository().findById(3L).orElse(null);
            if (inventory != null) {
                inventory.setStock(inventory.getStock() - orderPlaced.getQty());
                repository().save(inventory);
            }
        }
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
