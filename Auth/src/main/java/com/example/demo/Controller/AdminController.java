package com.example.demo.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.Entity.Admin;
import com.example.demo.Repository.AdminRepository;

@Controller
public class AdminController {

    @Autowired
    private AdminRepository adminRepository;

    @GetMapping("/admin_login")
    public String showLoginForm1() {
        return "admin_login";
    }

//    @GetMapping("/signup")
//    
//    public String showSignupForm(Model model) { 
//        model.addAttribute("user", new User());
//        return "signup";
//    }

//    @PostMapping("/signup")
//    public String signup(@ModelAttribute User user) {
//        userRepository.save(user);
//        return "redirect:/login";
//
//    }

    @PostMapping("/admin_login")
    public String login(@RequestParam String adminname, @RequestParam String adminpassword, Model model) {
        Admin admin = adminRepository.findByAdminname(adminname);
//        System.out.println(user);
        if (admin != null && admin.getAdminpassword().equals(adminpassword)) {
            // TODO: Implement successful login logic
            // For simplicity, redirect to a welcome page.
//            System.out.println("inside if "+user);

//            return "redirect:/welcome";
            return "redirect:/index1";
        } else {
            model.addAttribute("error", "Invalid username or password");
            return "admin_login";
        }
    }

    @GetMapping("/index1")
    public String welcome1() {
        return "index";
    }
    
    
    
//    @RequestMapping(value = "/getme", method = RequestMethod.GET)
//    public String getme() {
//    	return "login";
//    }
    @GetMapping("/getadm")
    public String getme1() {
    	return "login";
    }
    
    
    @GetMapping("/getmeadm")
    public String getmee1() {
        return "redirect:/index";
    }
    

//        @Autowired
//        private UserRepository userRepository1;
//
//        @PostMapping("/signup")
//        public ResponseEntity<String> signup11(@RequestBody User user) {
//            try {
//                userRepository.save(user);
//                return ResponseEntity.ok("User registered successfully!");
//            } catch (DataIntegrityViolationException e) {
//                // Handle constraint violation (duplicate entry)
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body("Email address is already in use. Please use another email address.");
//            }
//        }
//
//        // Other methods...
//   
    }


