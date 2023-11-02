package testmodel.infra;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.naming.NameParser;
import javax.naming.NameParser;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import testmodel.config.kafka.KafkaProcessor;
import testmodel.domain.*;

@Service
@Transactional
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
        Long productId = Long.valueOf(event.getProductId().substring(1));
        DecreaseStockCommand decreaseStockCommand = new DecreaseStockCommand();
        decreaseStockCommand.setProductId(productId);
        decreaseStockCommand.setQty(event.getQty());
        Inventory inventory = inventoryRepository
            .findById(productId)
            .orElse(null);
        if (inventory != null) {
            inventory.decreaseStock(decreaseStockCommand);
            inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException(
                "Inventory not found with productId: " + productId
            );
        }
    }
}
