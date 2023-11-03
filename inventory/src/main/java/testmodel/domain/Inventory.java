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

    public Inventory() {}

    public void onPostPersist() {}

    public static InventoryRepository repository() {
        InventoryRepository inventoryRepository = InventoryApplication.applicationContext.getBean(
            InventoryRepository.class
        );
        return inventoryRepository;
    }

    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // Implement decrease stock logic
        if (this.stock >= decreaseStockCommand.getQty()) {
            this.stock -= decreaseStockCommand.getQty();

            InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
            inventoryUpdated.publishAfterCommit();
        } else {
            throw new RuntimeException("Not enough stock to decrease");
        }
    }

    public static void updateInventory(OrderPlaced orderPlaced) {
        // Implement update inventory logic
        Long productId = Long.parseLong(
            orderPlaced.getProductId().substring(1)
        );

        repository()
            .findById(productId)
            .ifPresent(inventory -> {
                if (inventory.getStock() >= orderPlaced.getQty()) {
                    inventory.setStock(
                        inventory.getStock() - orderPlaced.getQty()
                    );
                    repository().save(inventory);

                    InventoryUpdated inventoryUpdated = new InventoryUpdated(
                        inventory
                    );
                    inventoryUpdated.publishAfterCommit();
                } else {
                    throw new RuntimeException("Not enough stock to update");
                }
            });
    }
}
//>>> DDD / Aggregate Root
