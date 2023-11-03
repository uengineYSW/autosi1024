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

    public void decreaseStock(DecreaseStockCommand decreaseStockCommand) {
        // implement business logic here:
        this.stock -= decreaseStockCommand.getQty();

        InventoryUpdated inventoryUpdated = new InventoryUpdated(this);
        inventoryUpdated.publishAfterCommit();
    }

    public static void updateInventory(OrderPlaced orderPlaced) {
        // implement business logic here:
        Inventory inventory = repository()
            .findById(orderPlaced.getProductId())
            .orElseGet(() -> new Inventory());
        inventory.setStock(inventory.getStock() - orderPlaced.getQty());
        repository().save(inventory);

        InventoryUpdated inventoryUpdated = new InventoryUpdated(inventory);
        inventoryUpdated.publishAfterCommit();
    }
}
