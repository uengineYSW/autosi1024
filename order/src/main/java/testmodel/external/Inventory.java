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
        // Implement business logic here
    }

    public static void updateInventory(OrderPlaced orderPlaced) {
        // Implement business logic here
        Inventory inventory = new Inventory();
        repository().save(inventory);
        InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
        inventoryUpdated.publishAfterCommit();
    }
}
