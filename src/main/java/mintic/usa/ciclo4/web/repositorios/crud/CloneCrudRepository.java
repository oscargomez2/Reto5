
package mintic.usa.ciclo4.web.repositorios.crud;

import java.util.List;
import mintic.usa.ciclo4.web.modelos.Clone;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

/**
 *
 * @author Nea
 */
public interface CloneCrudRepository extends MongoRepository<Clone, Integer> {
    public List<Clone> findByPriceLessThanEqual(double precio);

    @Query("{'description':{'$regex':'?0','$options':'i'}}")
    List<Clone> findByDescriptionLike(String description);
}
