package testmodel.domain;

import testmodel.domain.*;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import java.util.*;

//<<< PoEAA / Repository
@RepositoryRestResource(collectionResourceRel="inventories", path="inventories")
public interface InventoryRepository extends PagingAndSortingRepository<Inventory, Long>{
  @Query(value = "select inventory " +
        "from Inventory inventory " +
        "where (:name is null or inventory.name like %:name%) and true")
	List<Inventory> findByFindInventory(Long name, Pageable pageable);
}
//>>> PoEAA / Repository