package DS.TECH.HelloWorld.service;

import DS.TECH.HelloWorld.repository.TodoRepository;
import DS.TECH.HelloWorld.entity.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService
{
    @Autowired
    private TodoRepository todoRepository;

    public Todo createTodo(Todo todo){
        return todoRepository.save(todo);

    }

    public Todo getTodo(Long id){
        return todoRepository.findById(id).orElseThrow(() ->new RuntimeException("Todo is not found"));
    }


    public List<Todo> getttodoss(){
        return todoRepository.findAll();
    }

    public Todo updatetodo(Todo todo){
        return todoRepository.save(todo);
    }

    public void deletetodo(Long id){
        todoRepository.delete(getTodo(id));
    }

    public Page<Todo> getalltodopage(int page,int size){
        Pageable pageable =PageRequest.of(page, size);
        return todoRepository.findAll(pageable);
        }





}
