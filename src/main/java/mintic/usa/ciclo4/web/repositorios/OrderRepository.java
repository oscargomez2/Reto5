/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mintic.usa.ciclo4.web.repositorios;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import mintic.usa.ciclo4.web.modelos.Order;
import mintic.usa.ciclo4.web.repositorios.crud.OrderCrudRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Nea
 */
@Repository
public class OrderRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    
    @Autowired
    private OrderCrudRepository crud;
    
    public List<Order> getAll(){
        return (List<Order>) crud.findAll();
    }
    
    public List<Order> getByZone(String zona){
        return crud.findByZone(zona);
    }
    
    /*public List<Order> getByStatus(String status){
        return crud.findByStatus(status);
    }*/
    
    public Optional<Order> getOrder(int idOrder){
        return crud.findById(idOrder);
    }
    
    public Order save(Order order){
        return crud.save(order);
    }
    
    public void delete (Integer id){
        crud.deleteById(id);
    }
    
    
    //Métodos del reto 4
    //Reto 4: Ordenes de un asesor
    public List<Order> ordersSalesManByID(Integer id) {
        Query query = new Query();
        
        Criteria criterio = Criteria.where("salesMan.id").is(id);
        query.addCriteria(criterio);
        
        List<Order> orders = mongoTemplate.find(query, Order.class);
        
        return orders;
        
    }
    
    
    
    //Reto 4: Ordenes de un asesor x Estado
    public List<Order> ordersSalesManByState(String state, Integer id) {
        Query query = new Query();
        Criteria criterio = Criteria.where("salesMan.id").is(id)
                            .and("status").is(state);
        
        query.addCriteria(criterio);
        
        List<Order> orders = mongoTemplate.find(query,Order.class);
        
        return orders;
    }
    
    //Reto 4: Ordenes de un asesor x Fecha
    public List<Order> ordersSalesManByDate(String dateStr, Integer id) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Query query = new Query();
        
        Criteria dateCriteria = Criteria.where("registerDay")
                        .gte(LocalDate.parse(dateStr, dtf).minusDays(1).atStartOfDay())
                        .lt(LocalDate.parse(dateStr, dtf).plusDays(1).atStartOfDay())
                        .and("salesMan.id").is(id);
        
        query.addCriteria(dateCriteria);
        
        List<Order> orders = mongoTemplate.find(query,Order.class);
        
        return orders;       
    }
    
    
}
