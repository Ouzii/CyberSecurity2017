package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Note;
import sec.project.repository.AccountRepository;
import sec.project.repository.NoteRepository;

@Controller
public class NoteController {
    
    @Autowired
    private NoteRepository noteRepository;
    @Autowired
    private AccountRepository accountRepository;
    
    @GetMapping("/notes/{id}")
    public String notes(Model model, @PathVariable Long id) {
        Account a = accountRepository.getOne(id);
        model.addAttribute("notes", noteRepository.findByOwner(a));
        return "notes";
    }
    
    @GetMapping("/notes/{id}/addNote")
    public String addForm(@PathVariable Long id) {
        return "newNote";
    }
    
    @PostMapping("/notes/{id}/addNote")
    public String add(@PathVariable Long id, @RequestParam String text) {
        Note n = new Note();
        n.setText(text);
        n.setOwner(accountRepository.getOne(id));
        noteRepository.save(n);
        return "redirect:/notes/"+id;
    }
}
