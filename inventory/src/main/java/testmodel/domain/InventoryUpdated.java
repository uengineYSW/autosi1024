package testmodel.domain;

import testmodel.domain.*;
import testmodel.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
@NoArgConstructor
public class InventoryUpdated extends AbstractEvent {

    private Long productId;
    private Integer stock;

    public InventoryUpdated(Inventory aggregate){
        super(aggregate);
    }
    public InventoryUpdated(){
        super();
    }
}
