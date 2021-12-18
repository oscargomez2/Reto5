
package mintic.usa.ciclo4.web.servicios;

import java.util.List;
import java.util.Optional;
import mintic.usa.ciclo4.web.modelos.Order;
import mintic.usa.ciclo4.web.repositorios.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Nea
 */
@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    
    public List<Order> getAll(){
        return (List<Order>) repository.getAll();
    }
    
    public List<Order> getByZone(String zona){
        return repository.getByZone(zona);
    }
    
    public Order getOrder(int idOrder){
        return repository.getOrder(idOrder).orElse(new Order());
        
    }
    
    public Order save(Order order){
        if(order.getId()==null){
            return repository.save(order);
        }else{
            Optional<Order> buscar= repository.getOrder(order.getId());
            if(buscar.isPresent()){
                return order;
            }else{
                return repository.save(order);
            }
        }
    }

    
   
    
    
    public Order update(Order order){
        if(order.getId()!=null){
            Optional<Order> buscar= repository.getOrder(order.getId());
            if(buscar.isPresent()){
                if(order.getRegisterDay()!=null){
                    buscar.get().setRegisterDay(order.getRegisterDay());
                }
                if(order.getStatus()!=null){
                    buscar.get().setStatus(order.getStatus());
                }               
                return repository.save(buscar.get());
            } else {
                return order;
            }
        }
        return order;
    }
    
    public boolean delete(Integer id){
        Boolean aBoolean = repository.getOrder(id).map(order -> {
            repository.delete(id);
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
    
    //Métodos del reto 4
    //Reto 4: Ordenes de un asesor
    public List<Order> ordersSalesManByID(Integer id){
        return repository.ordersSalesManByID(id);
    }
    
    
    public List<Order> ordersSalesManByState(String status, Integer id){
        return repository.ordersSalesManByState(status, id);
    }
    
    
    //Reto 4: Ordenes de un asesor x fecha
    public List<Order> ordersSalesManByDate(String dateStr, Integer id) {
        return repository.ordersSalesManByDate(dateStr,id);
    }
    
    
}
