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

    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // Implement the business logic to decrease the stock based on the decreaseStockCommand
        // For example, you can subtract the 'qty' from the 'stock'
        stock -= decreaseStockCommand.getQty();

        // Check if the stock is less than or equal to 0
        if (stock <= 0) {
            // Handle out of stock scenario
        }

        // Save the updated inventory
        InventoryApplication.repository().save(this);

        // Publish InventoryUpdated event
        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publishAfterCommit();
    }

    public static void updateInventory(OrderPlaced orderPlaced) {
        // Implement the business logic to update the inventory based on the orderPlaced event
        // For example, you can find the inventory by productId and update the stock
        Inventory inventory = InventoryApplication
            .repository()
            .findById(orderPlaced.getProductId())
            .orElse(null);
        if (inventory != null) {
            // Update the stock based on the orderPlaced event
            inventory.setStock(orderPlaced.getQty());

            // Save the updated inventory
            InventoryApplication.repository().save(inventory);

            // Publish InventoryUpdated event
            InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
            inventoryUpdated.publishAfterCommit();
        }
    }
}
