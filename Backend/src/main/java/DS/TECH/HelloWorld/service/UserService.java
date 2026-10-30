package DS.TECH.HelloWorld.service;


import DS.TECH.HelloWorld.entity.User;
import DS.TECH.HelloWorld.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User createUser(User user){
        return userRepository.save(user);

    }

    public User getUser(Long id){
        return userRepository.findById(id).orElseThrow(() ->new RuntimeException("User is not found"));
    }

}
