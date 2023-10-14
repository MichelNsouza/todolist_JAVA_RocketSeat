package br.com.michelsouza.todolist.user;

import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;


@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    private IUserRepository userRepository;

    @PostMapping("/")
    public ResponseEntity creat(@RequestBody UserModel userModel){
       var user = this.userRepository.findByUsername(userModel.getUsername());

       if(user != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuario ja existe");
       }

       var passwordHash = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray());

       userModel.setPassword(passwordHash);

        var userCreated = this.userRepository.save(userModel);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
