package testmodel.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Data
public class DecreaseStockCommand {

        private Long productId;
        private Integer qty;


}
