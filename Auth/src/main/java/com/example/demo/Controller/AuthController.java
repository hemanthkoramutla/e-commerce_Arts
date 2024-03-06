package com.example.demo.Controller;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCrypt;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import com.example.demo.Entity.User;
//import com.example.demo.Repository.UserRepository;
//
//@Controller
//public class AuthController {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/login")
//    public String showLoginForm() {
//        return "login";
//    }
//
//    @GetMapping("/signup")
//    public String showSignupForm(Model model) { 
//        model.addAttribute("user", new User());
//        return "signup";
//    }
//    
//    // Hash the password before saving it to the database
//    private String hashPassword(String password) {
//        return BCrypt.hashpw(password, BCrypt.gensalt());
//    }
//
//    // Verify the password against its hashed version
//    private boolean verifyPassword(String password, String hashedPassword) {
//        return BCrypt.checkpw(password, hashedPassword);
//    }
//   @PostMapping("/signup")
//    public String registerUser(@ModelAttribute("user") User user) {
//        // You may want to add validation and error handling here
//    	
//    	String hashedPassword = hashPassword(user.getPassword());
//        user.setPassword(hashedPassword);
//    	userRepository.save(user);
//        return "redirect:/login";
//    	
//    }
//@PostMapping("/login")
//    public String loginUser(@ModelAttribute("user") User user) {
//        // You may want to add validation and error handling here
//    	User existingUser = userRepository.findByEmail(user.getEmail());
//        if (existingUser != null && verifyPassword(user.getPassword(), existingUser.getPassword())) {
//            // Successful login, redirect to home page
//            return "redirect:/home";
//        } else {
//            // Failed login, redirect back to login page
//            return "redirect:/login";
//        }
//    }
//
//
//
//    @GetMapping("/home")
//    public String welcome() {
//        return "home";
//    }
//    
//    @GetMapping("/getme")
//    public String getme() {
//    	return "login";
//    }
//    
//    
//    @GetMapping("/getmee")
//    public String getmee() {
//        return "redirect:/home";
//    }
//    
//    @GetMapping("/categories")
//    public String categories() {
//        return "categories";
//    }
//    @GetMapping("/start")
//    public String start() {
//        return "start";
//    }
//     
//}
//
//
//
////
//////SEssion?
//
//

//package com.example.demo.Controller;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
@Controller
public class AuthController {

    @Autowired
    private UserRepository userRepository;

   

    // Hash the password before saving it to the database
    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    // Verify the password against its hashed version
    private boolean verifyPassword(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        // You may want to add validation and error handling here

        // Check if the email is already registered
        if (userRepository.existsByEmail(user.getEmail())) {
            redirectAttributes.addFlashAttribute("errorMessage", "Email already registered. Please use a different email.");
            return "redirect:/signup";
        }

        // If the email is unique, proceed with registration
        String hashedPassword = hashPassword(user.getPassword());
        user.setPassword(hashedPassword);
        userRepository.save(user);
        return "redirect:/login";
    }

    

    @PostMapping("/login")
    public String loginUser(HttpServletRequest request, @ModelAttribute("user") User user, RedirectAttributes redirectAttributes) {
        // You may want to add validation and error handling here
    	User existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser != null && verifyPassword(user.getPassword(), existingUser.getPassword())) {
            // Successful login, redirect to home page
            HttpSession session = request.getSession();
            session.setAttribute("userId", existingUser.getId());
            return "redirect:/home";
        } else {
            // Failed login, redirect back to login page
            redirectAttributes.addFlashAttribute("errorMessage", "Invalid credentials. Please try again.");
            return "redirect:/login";
        }
    }
    


    @GetMapping("/home")
    public String welcome(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        return "home";
    }

    // Other methods omitted for brevity

    @GetMapping("/categories")
    public String categories(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        return "categories";
    }
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.invalidate();
        return "redirect:/login";
    }
   
    
//    @GetMapping("/categories")
//    public String showCategiries() {
//        return "categories";
//    }
    
    
  
//    @PostMapping("/reset-password")
//    public String resetPassword(
//            @RequestParam String email,
//            @RequestParam String newPassword,
//            @RequestParam String confirmPassword,
//            Model model
//    ) {
//        // Validate that the new password and confirm password match
//        if (!newPassword.equals(confirmPassword)) {
//            model.addAttribute("errorMessage", "New password and confirm password do not match.");
//            return "redirect/:reset-password";
//        }
//
//        // Retrieve the user from the database using the email
//        User user = userRepository.findByEmail(email);
//
//        // Check if the user exists
//        if (user == null) {
//            model.addAttribute("errorMessage", "User not found.");
//            return "reset-password";
//        }
//
//        // Update the user's password
//        String hashedPassword = hashPassword(newPassword);
//        user.setPassword(hashedPassword);
//
//        // Save the updated user in the database
//        userRepository.save(user);
//
//        // Redirect to a success page
//        return "reset-password-success";
//    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @GetMapping("/signup")
    public String showSignupForm(Model model) { 
        model.addAttribute("user", new User());
        return "signup";
    }
//    @GetMapping("/generate")
//    public String forget() { 
//        return "generate";
//    }
    
  
    @GetMapping("/validate")
    public String forgetv() { 
    	System.out.println("validate");
        return "validate-otp";
    }
 
    
    @GetMapping("/")
    public String showStart() {
        return "start";
    }
    
}