package testmodel.domain;

import testmodel.domain.*;
import testmodel.infra.AbstractEvent;
import java.util.*;
import lombok.*;

@Data
@ToString
@NoArgConstructor
public class OrderRefused extends AbstractEvent {

    private Long id;

    public OrderRefused(Order aggregate){
        super(aggregate);
    }
    public OrderRefused(){
        super();
    }
}
