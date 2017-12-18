package sec.project.controller;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sec.project.domain.Account;
import sec.project.domain.Note;
import sec.project.repository.AccountRepository;
import sec.project.repository.NoteRepository;

@Controller
public class DefaultController {
    
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private NoteRepository noteRepository;
    
    @GetMapping("/")
    public String index() {
        if (accountRepository.findAll().isEmpty()) {
            Account admin = new Account();
            admin.setAdmin(true);
            admin.setPassword("admin");
            admin.setUsername("admin");
            List<Note> notes = new ArrayList<>();
            Note n = new Note();
            n.setText("This is a note");
            notes.add(n);
            admin.setNotes(notes);
            accountRepository.save(admin);
        }
        return "index";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/emptyDatabase")
    public String db(HttpSession session) {
        for (Note note : noteRepository.findAll()) {
            note.setOwner(null);
        }
        for (Account account : accountRepository.findAll()) {
            account.setNotes(null);
        }
        accountRepository.deleteAll();
        noteRepository.deleteAll();
        session.setAttribute("user", null);
        return "redirect:/";
    }
}