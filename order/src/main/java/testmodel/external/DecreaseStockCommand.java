package testmodel.external;

import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

@Data
public class DecreaseStockCommand {

    private Long productId;
    private Integer qty;
}
