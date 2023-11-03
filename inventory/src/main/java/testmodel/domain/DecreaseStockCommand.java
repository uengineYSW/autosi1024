package testmodel.domain;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class DecreaseStockCommand {

    private Long productId;
    private Integer qty;

    public DecreaseStockCommand() {}

    public DecreaseStockCommand(Long productId, Integer qty) {
        this.productId = productId;
        this.qty = qty;
    }
}
