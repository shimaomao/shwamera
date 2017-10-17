package ru.mera.sergeynazin.service;

import ru.mera.sergeynazin.model.Note;

import java.util.List;

public interface NoteService {

    void add(Note note);
    void update(Note note);
    Note getCategory(Long id);
    void delete(Long id);
    List<Note> getNotes();

}
