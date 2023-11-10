package testmodel.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Date;
import lombok.Data;

@Entity
@Table(name="FindInventory_table")
@Data
public class FindInventory {

        @Id
        //@GeneratedValue(strategy=GenerationType.AUTO)
        private Long id;


}