package ru.mera.sergeynazin.dao;

import ru.mera.sergeynazin.model.old.Note;

import java.util.List;

public interface NoteDAO {

    void add(Note note);

    void update(Note note);

    Note getNote(Long id);

    void delete(Long id);

    List<Note> getNotes();

}
