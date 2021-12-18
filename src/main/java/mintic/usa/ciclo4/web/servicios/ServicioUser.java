
package mintic.usa.ciclo4.web.servicios;

import java.util.List;
import java.util.Optional;
import mintic.usa.ciclo4.web.modelos.User;
import mintic.usa.ciclo4.web.repositorios.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author equipo 5
 */
@Service
public class ServicioUser {
    /**
     * instancia de la clase UserRepository
     */
    @Autowired
    private UserRepository repositorioUser;
    
    /**
     * obtener todos los usuarios
     * @return 
     */
    public List<User> getAll(){
        return (List<User>) repositorioUser.getAll();
    }
    
    /**
     * obtener un usuario
     * @param idUser
     * @return 
     */
    public User getUser(int idUser){
        return repositorioUser.getUser(idUser).orElse(new User());
        
    }
    
    /**
     * guardar un usuario
     * @param user
     * @return 
     */
    public User save(User user){
        if(user.getEmail()==null || user.getPassword()==null || user.getName()==null 
                || user.getIdentification()==null || user.getType()==null){
            return user;
        }else{
            List<User> existe= repositorioUser.getUsersByNameOrEmail(user.getName(), user.getEmail());
            if(existe.isEmpty()){
                if(user.getId()==null){
                    return repositorioUser.save(user);
                }else{
                    Optional<User> existe1 = repositorioUser.getUser(user.getId());
                    if(existe1.isEmpty()){
                        return repositorioUser.save(user);
                    }else{
                        return user;
                    }
                }
                
            }else{
                return user;
            }
        }
        
    }
    
    /**
     * consulatar un usuario por email
     * @param email
     * @return 
     */
    public boolean existe(String email){
        return repositorioUser.existe(email).isPresent();
    }
   
    /**
     * consulatar un usuario por email y password
     * @param email
     * @param password
     * @return 
     */
    public User existeUser(String email, String password){
        Optional<User> userExiste= repositorioUser.existeUser(email, password);
        if(userExiste.isPresent()){
            return userExiste.get();
        }else{
            return new User();
        }
        
    }
    
    /**
     * actualizar un usuario
     * @param user
     * @return 
     */
    public User update(User user){
        if(user.getId()!=null){
            Optional<User> buscar= repositorioUser.getUser(user.getId());
            if(buscar.isPresent()){
                if(user.getIdentification()!=null){
                    buscar.get().setIdentification(user.getIdentification());
                }
                if(user.getName()!=null){
                    buscar.get().setName(user.getName());
                }
                if(user.getBirthtDay()!=null){
                    buscar.get().setBirthtDay(user.getBirthtDay());
                }
                if(user.getMonthBirthtDay()!=null){
                    buscar.get().setMonthBirthtDay(user.getMonthBirthtDay());
                }
                if(user.getAddress()!=null){
                    buscar.get().setAddress(user.getAddress());
                }
                if(user.getCellPhone()!=null){
                    buscar.get().setCellPhone(user.getCellPhone());
                }
                if(user.getEmail()!=null){
                    buscar.get().setEmail(user.getEmail());
                }
                if(user.getPassword()!=null){
                    buscar.get().setPassword(user.getPassword());
                }
                if(user.getZone()!=null){
                    buscar.get().setZone(user.getZone());
                }
                if(user.getType()!=null){
                    buscar.get().setType(user.getType());
                }
                return repositorioUser.save(buscar.get());
            } else {
                return user;
            }
        }
        return user;
    }
    
    /**
     * eliminar un usuario
     * @param idUser
     * @return 
     */
    public boolean delete(Integer idUser){
        Boolean aBoolean = repositorioUser.getUser(idUser).map(user -> {
            repositorioUser.delete(idUser);
            return true;
        }).orElse(false);
        return aBoolean;
    }
    
    public List<User> getUsersByMonthBirthtDay(String monthBirthtDay) {
        return repositorioUser.getUsersByMonthBirthtDay(monthBirthtDay);
    }
}
