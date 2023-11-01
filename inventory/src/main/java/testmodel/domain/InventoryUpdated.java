package testmodel.domain;

import java.util.*;
import lombok.*;
import testmodel.domain.*;
import testmodel.infra.AbstractEvent;

@Data
@ToString
@NoArgsConstructor
public class InventoryUpdated extends AbstractEvent {

    private Long productId;
    private Integer stock;

    public InventoryUpdated() {
        super();
    }

    public InventoryUpdated(Inventory aggregate) {
        super(aggregate);
    }
}
