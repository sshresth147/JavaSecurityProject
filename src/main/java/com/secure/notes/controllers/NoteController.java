package com.secure.notes.controllers;
import com.secure.notes.models.Note;
import com.secure.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // marks this as REST API
@RequestMapping("/api/notes") // base URL

public class NoteController {

    @Autowired
    private NoteService noteService;

    // ✅ Create Note
    @PostMapping
    public Note createNote(@RequestBody Note note,
                           @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        return noteService.createNoteForUser(username, note.getContent());
    }

    // ✅ Get all notes for a user
    @GetMapping
    public List<Note> getNotes(@AuthenticationPrincipal UserDetails userDetails) {


        String username= userDetails.getUsername();
        System.out.println("USER DETAILS OF :" + username);
        return noteService.getNOtesForUser(userDetails.getUsername());
    }

    // ✅ Update note
    @PutMapping("/{id}")
    public Note updateNote(@PathVariable Long id,
                           @RequestParam String content,
                           @AuthenticationPrincipal UserDetails userDetails) {

        return noteService.updateNoteForUser(id, content, userDetails.getUsername());
    }

    // ✅ Delete note
    @DeleteMapping("/{id}")
    public void deleteNote(@PathVariable Long id,
                           @AuthenticationPrincipal UserDetails userDetails) {

        noteService.deleteNoteForUser(id, userDetails.getUsername());
    }
}