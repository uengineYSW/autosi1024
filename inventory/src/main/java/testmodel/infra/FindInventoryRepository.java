package testmodel.infra;

import testmodel.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.util.List;

@RepositoryRestResource(collectionResourceRel="findInventories", path="findInventories")
public interface FindInventoryRepository extends PagingAndSortingRepository<FindInventory, Long> {

    

    
}
