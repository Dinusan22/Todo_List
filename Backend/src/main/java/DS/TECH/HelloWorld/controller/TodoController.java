package DS.TECH.HelloWorld.controller;

import DS.TECH.HelloWorld.service.TodoService;
import DS.TECH.HelloWorld.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
public class TodoController {
    @Autowired
    private TodoService Todoservice;

    //CREATE
    @PostMapping("/create")
    ResponseEntity<Todo> Todocreate(@RequestBody Todo todo) {
        return new ResponseEntity<>(Todoservice.createTodo(todo), HttpStatus.CREATED);

    }

    //GET By ID
    @GetMapping("/{id}")
    ResponseEntity<Todo> TodogetbyId(@PathVariable Long id){
        try{
            Todo getedtodo=Todoservice.getTodo(id);
            return new ResponseEntity<>(getedtodo, HttpStatus.OK);
        }
        catch(RuntimeException exception){
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);

        }

    }
    //GET ALL
    @GetMapping
    ResponseEntity<List<Todo>>  gettodos(){
        return new ResponseEntity <>(Todoservice.getttodoss(),HttpStatus.OK);
    }

    //UPDATE
    @PutMapping
    ResponseEntity<Todo> updatetodo(@RequestBody Todo todo) {
        return new ResponseEntity<>(Todoservice.updatetodo(todo), HttpStatus.OK);
    }
    //DELETE
    @DeleteMapping("/{id}")
    void deletetodo(@PathVariable Long id){
        Todoservice.deletetodo(id);
    }


    @GetMapping("/page")
    ResponseEntity<Page<Todo>> gettodopage(@RequestParam int page,@RequestParam int size){
        return new ResponseEntity<>(Todoservice.getalltodopage(page,size),HttpStatus.OK);
    }


}
