package com.example.demo.Controller;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import com.example.demo.RepositoryService.OtpService;
import com.example.demo.RepositoryService.UserServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class OtpController {
	@Autowired
	UserRepository userRepository;
    @Autowired
    private OtpService otpService;
    @Autowired
    UserServiceImpl usl;

   
    @PostMapping("/generate")
    public String generateAndSendOtp(@RequestParam String email, Model model) {
        if (userRepository.existsByEmail(email)) {
            // Email exists, generate and send OTP
            String generatedOtp = otpService.generateAndSendOtp(email);
            model.addAttribute("generatedOtp", generatedOtp);
            return "validate-otp"; // Thymeleaf template name for the validation page
            
        } else {
            // Email doesn't exist, redirect to an error page
            return "errorPage"; // Thymeleaf template name for the error page
        }
    }
    
    @PostMapping("/validate-otp")
    public String validateOtp(@RequestParam String email, @RequestParam String enteredOtp) {
        boolean isValid = otpService.validateOtp(email, enteredOtp);

        if (isValid) {
        	return "reset-password";

        } else {
            return "index";
        }
    }
    
    @PostMapping("/reset-password")
    public String resetPassword(
            @RequestParam String email,
            @RequestParam String newPassword,
            @RequestParam String confirmPassword,
            Model model
    ) {
        // Validate that the new password and confirm password match
        if (!newPassword.equals(confirmPassword)) {
            model.addAttribute("errorMessage", "New password and confirm password do not match.");
            return "redirect:/reset-password";
        }

        // Find the user by email
        User user = userRepository.findByEmail(email);

        if (user == null) {
            model.addAttribute("errorMessage", "User not found.");
            return "reset-password";
        } else {
            // Update the user's password with the hashed version
            String hashedPassword = hashPassword(newPassword);
            user.setPassword(hashedPassword);

            // Save the updated user information
            userRepository.save(user);

            // Redirect to the login page
            return "redirect:/login";
        }
    }
    
        // Check if the user exists
//        if (user == null) {
//            model.addAttribute("errorMessage", "User not found.");
//            return "reset-password";
//        }
//        else {
//        
//        	String hashedPassword = hashPassword(newPassword);
//            user.setPassword(hashedPassword);
////      usl.updatePassword(email, newPassword);
//            userRepository.save(user);
//        // Update the user's password
////        String hashedPassword = hashPassword(newPassword);
////        user.setPassword(hashedPassword);
//
//        // Save the updated user in the database
////        userRepository.save(user);
//           
//        // Redirect to a success page
//        return "login";
//        }
//    }
//    
    @GetMapping("/generate")
    public String generateOtpForm() {
        return "generate"; // This will return generate-otp.html Thymeleaf template
    }
   
    @GetMapping("/reset-password")
    public String reset() { 
        return "reset-password";
    }
    
    @GetMapping("/validate-otp")
    public String generateOtpp() {
        return "validate-otp"; // This will return generate-otp.html Thymeleaf template
    }

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }
}


