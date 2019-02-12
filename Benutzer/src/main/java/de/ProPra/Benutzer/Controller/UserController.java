package de.ProPra.Benutzer.Controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import de.ProPra.Benutzer.model.User;
import de.ProPra.Benutzer.service.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class UserController {

    @Autowired
    UserRepository userRepository;

    @GetMapping(path = "/")
    public String showLogin(Model model) {
        return "start";
    }

    @PostMapping(path = "/")
    public String showView(Model model, @RequestParam String username, @RequestParam(value = "action") String button) {
       if(button.equals("Signup")){
           return "redirect:/signup";
       }
       else if(button.equals("Login")){
           System.out.println(username);
           return signIn(username);

       }
       return "";
    }

    public String signIn(String username){
       /* int i=0;
        boolean exists = false;
        Iterable<User> users= userRepository.findAll();

        for(User u : users ){
            System.out.println(u.getUsername()+"");
            if(u.getUsername().equals(username)) {
                exists=true;
                break;
            }
            i++;
        }
            if(exists) {
                long userId = getUserId(username);
                System.out.println(getUserId(username));
                return "dummyhome";
            }
            else{*/
                return "redirect:/start";
    //        }

    }

    @GetMapping(path = "/signup")
    public String showSignUp(){
            return "signUp";
    }

    @PostMapping(path="/signup")
    public String signUp(Model model, @RequestParam String username, @RequestParam String name, @RequestParam String email,@RequestParam(value = "action") String button){
        boolean isAdmin=false;
        User user = new User(username,name,email,isAdmin);
        user =userRepository.save(user);
        //System.out.println(user.getId());

        return "dummyhome";
    }



    public long getUserId(String username){
        return 3;
    }
}
