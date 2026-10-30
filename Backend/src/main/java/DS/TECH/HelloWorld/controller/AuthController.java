package DS.TECH.HelloWorld.controller;


import DS.TECH.HelloWorld.Util.JwtUtil;
import DS.TECH.HelloWorld.entity.User;
import DS.TECH.HelloWorld.repository.UserRepository;
import DS.TECH.HelloWorld.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @PostMapping("/Register")
    //here ResponseEntity is response (HTTP response Control +String Body) but the String only return the String Body
    public ResponseEntity<String> registeruser(@RequestBody Map<String,String> body){
        String fullName =body.get("fullName");
        String email=body.get("email");
        String password=body.get("password");
        password=passwordEncoder.encode(password);//here the password is encoded then not save the original password ,one way

        if (fullName == null || fullName.isBlank() ||
                fullName.trim().length() > 100 ||
                email == null || email.isBlank() ||
                password == null || password.length() < 8) {

            return ResponseEntity.badRequest()
                    .body("Enter a valid name, email and password (minimum 8 characters).");
        }

        if(userRepository.findByEmail(email).isPresent()){
            return new ResponseEntity<>("Email is already exits",HttpStatus.CONFLICT);
        }

        userService.createUser(User.builder().fullName(fullName).email(email).password(password).build());//here first create user object then set email and password then complete the creation
//        instead of writing above the line
//        User user = new User();
//        user.setEmail(email);
//        user.setPassword(password);
//        userService.createUser(user);

        return new ResponseEntity<>("Email is Successfully Registered",HttpStatus.CREATED);

    }

    @PostMapping("/Login")
    //ResponseEntity<?>	The response body type is not fixed to one specific type
    public ResponseEntity<?> loginuser(@RequestBody Map<String,String> body){  //Stores String keys and String values
        String email=body.get("email");
        String password=body.get("password");


        var userOptional =userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return new ResponseEntity<>("user not registered",HttpStatus.UNAUTHORIZED);
        }
        User user=userOptional.get();//Gets the User object from the Optional
        if (!passwordEncoder.matches(password,user.getPassword()))
        {
            return new ResponseEntity<>("Invalid User",HttpStatus.UNAUTHORIZED);

        }
        String token =jwtUtil.generateToken(email);
        return ResponseEntity.ok(Map.of("token",token));//creates a map containing the JWT under the key "token"


    }


    // Change the password of the User

    @PostMapping("/ChangePassword")
    public ResponseEntity<String> changePassword(
            @RequestBody Map<String, String> body,
            Authentication authentication) {

        // Step 1: Get logged-in user's email from JWT authentication
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Login required");
        }

        String email = authentication.getName();

        // Step 2: Get old and new passwords
        String oldPassword = body.get("oldPassword");
        String newPassword = body.get("newPassword");

        // Step 3: Validate input
        if (oldPassword == null || newPassword == null ||
                newPassword.length() < 8) {
            return ResponseEntity.badRequest()
                    .body("New password must contain at least 12 characters");
        }

        // Step 4: Find user in database
        var userOptional = userRepository.findByEmail(email);

        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("User not found");
        }

        User user = userOptional.get();

        // Step 5: Check old password
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Old password is incorrect");
        }

        // Step 6: Encode new password
        String encodedPassword = passwordEncoder.encode(newPassword);

        // Step 7: Set new password
        user.setPassword(encodedPassword);

        // Step 8: Update database
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }

}
