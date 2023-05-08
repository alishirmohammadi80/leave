package ir.rashasoft.leavemanagement.login;
import ir.rashasoft.leavemanagement.emailService.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    EmailService emailService;
    @Autowired
    private UserRepository repository;



    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping("/registerProcess")
    public String processRegister(User user) {
        //   emailService.send(user.getEmail(), "Account Information", "username:" + user.getUserName() + "---" + "password:" + user.getPassword());
        //Todo fix
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repository.save(user);
        return "login";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }



    @GetMapping("/users")
    public String listUsers(Model model) {
        List<User> listUsers = repository.findAll();
        model.addAttribute("listUsers", listUsers);
        return "list-user";
    }

    @GetMapping("/user/{id}")
    public String findCustomerById(@PathVariable("id") Long id, Model model) {
        User users = repository.getById(id);
        model.addAttribute("users", users);
        return "list-user";
    }

}



