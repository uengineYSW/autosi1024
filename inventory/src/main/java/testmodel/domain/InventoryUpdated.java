package testmodel.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import testmodel.domain.AbstractEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public class InventoryUpdated extends AbstractEvent {

    private Long productId;
    private Integer stock;

    public InventoryUpdated(Inventory aggregate) {
        super(aggregate);
    }

    public InventoryUpdated() {
        super();
    }
}
