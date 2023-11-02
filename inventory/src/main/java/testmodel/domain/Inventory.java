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

    //... rest of the code ...

    public static void updateInventory(OrderPlaced orderPlaced) {
        // Implement the business logic here

        // Find the inventory by product ID
        Inventory inventory = repository()
            .findById(orderPlaced.getProductId())
            .orElse(null);
        if (inventory != null) {
            // Decrease the stock
            inventory.setStock(inventory.getStock() - orderPlaced.getQty());

            // Save the updated inventory
            repository().save(inventory);

            // Publish the inventory updated event
            InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
            inventoryUpdated.publishAfterCommit();
        }
    }

    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // Implement the business logic here
    }
    //... rest of the code ...
}
