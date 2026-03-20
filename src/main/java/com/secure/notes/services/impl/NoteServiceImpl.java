package com.secure.notes.services.impl;

import com.secure.notes.models.Note;
import com.secure.notes.repositories.NoteRepository;
import com.secure.notes.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class NoteServiceImpl implements NoteService {

    @Autowired
    private NoteRepository noteRepository;


    @Override
    public Note createNoteForUser(String username, String content) {
        Note note = new Note(); //object creatiom
        note.setContent(content); //encapsulation via oop
        note.setOwnerUsername(username);

        return noteRepository.save(note);
    }



    @Override
    public Note updateNoteForUser(long noteId, String content, String username) {

        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        // Ownership check (VERY IMPORTANT in real apps)
        if (!note.getOwnerUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        note.setContent(content); // update state

        return noteRepository.save(note);
    }

    @Override
    public void deleteNoteForUser(Long noteId, String username) {
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new RuntimeException("Note not found"));

        if (!note.getOwnerUsername().equals(username)) {
            throw new RuntimeException("Unauthorized");
        }

        noteRepository.delete(note);
    }

    @Override
    public List<Note> getNOtesForUser(String username) {
        return noteRepository.findByOwnerUsername(username);
    }
}
