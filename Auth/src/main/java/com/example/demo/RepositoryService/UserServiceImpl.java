package com.example.demo.RepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.UnimplService.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository; // Assuming you have a UserRepository
    
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
    @Override
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email);
        if (user != null) {

        	    // Hash the password
        	    String hashedPassword = hashPassword(user.getPassword());
        	    user.setPassword(hashedPassword);
        	    userRepository.save(user);
        	    // Save the updated user
        	    
        }
        // Handle the case where the user is not found (optional)
    }

	

    // Implement other methods if needed...
}
