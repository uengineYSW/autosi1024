package testmodel.external;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@FeignClient(name = "inventory", url = "${api.url.inventory}")
public interface InventoryService {
    @RequestMapping(method= RequestMethod.PUT, path="/inventories/{id}/update")
    public void decreaseStock(@PathVariable("id") Long productId, @RequestBody DecreaseStockCommand decreaseStockCommand );
}
