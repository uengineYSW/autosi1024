package testmodel.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import testmodel.InventoryApplication;

@Entity
@Table(name = "Inventory_table")
@Data
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
        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publishAfterCommit();
    }

    public static void updateInventory(OrderPlaced orderPlaced) {
        // Implement the business logic to handle OrderPlaced events

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
