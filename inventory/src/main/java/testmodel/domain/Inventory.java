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

    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // implement business logic to decrease stock based on the decreaseStockCommand
    }

    public static void updateInventory(OrderPlaced orderPlaced) {
        repository()
            .findById(orderPlaced.getId())
            .ifPresent(inventory -> {
                inventory.setStock(inventory.getStock() - orderPlaced.getQty());
                repository().save(inventory);

                InventoryUpdated inventoryUpdated = new InventoryUpdated(
                    inventory
                );
                inventoryUpdated.publishAfterCommit();
            });
    }
}
