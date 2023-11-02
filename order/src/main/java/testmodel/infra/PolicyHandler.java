package testmodel.infra;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import testmodel.domain.*;
import testmodel.domain.*;

@Service
public class PolicyHandler {

    @Autowired
    InventoryRepository inventoryRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='OrderPlaced'"
    )
    public void wheneverOrderPlaced_UpdateInventory(
        @Payload OrderPlaced orderPlaced
    ) {
        OrderPlaced event = orderPlaced;
        System.out.println(
            "\n\n##### listener UpdateInventory : " + orderPlaced + "\n\n"
        );

        // Sample Logic //
        Inventory inventory = inventoryRepository
            .findById(event.getProductId())
            .orElse(null);
        if (inventory != null) {
            DecreaseStockCommand decreaseStockCommand = new DecreaseStockCommand();
            decreaseStockCommand.setProductId(event.getProductId());
            decreaseStockCommand.setQty(event.getQty());
            inventory.decreaseStock(decreaseStockCommand);
            inventoryRepository.save(inventory);
        }
    }
}
