package DS.TECH.HelloWorld.repository;

import DS.TECH.HelloWorld.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;


//CRUD
public interface TodoRepository extends JpaRepository<Todo, Long>{

}