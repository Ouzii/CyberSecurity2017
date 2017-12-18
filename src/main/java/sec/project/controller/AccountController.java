package sec.project.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class AccountController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    
    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        Account a = accountRepository.findByUsername(username);
        
        if (a != null) {
            if (a.getPassword().equals(password)) {
                session.setAttribute("user", a);
                return "redirect:/notes/"+a.getId();
            }
        }
        
        return "index";
    }
    
    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        Account a = accountRepository.findByUsername(username);
        Account newAccount = new Account();
        if (a == null) {
            newAccount.setUsername(username);
            newAccount.setPassword(password);
            newAccount.setNotes(new ArrayList<>());
            newAccount.setAdmin(false);
            accountRepository.save(newAccount);
        } else {
            return "redirect:/register";
        }
        
        return "redirect:/login";
    }
    
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "index";
    }
    
    @GetMapping("/{id}/changePassword")
    public String modifyPassword(Model model, @PathVariable Long id) {
        model.addAttribute("user", accountRepository.getOne(id));
        return "modifyPassword";
    }
    
    @PostMapping("/{id}/changePassword")
    public String changePassword(@PathVariable Long id, @RequestParam String password) {
        Account a = accountRepository.getOne(id);
        a.setPassword(password);
        accountRepository.save(a);
        return "redirect:/notes/"+id;
    }
}
