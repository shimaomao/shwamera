package ru.mera.sergeynazin.service.impl;

import ru.mera.sergeynazin.dao.impl.NoteDAOImpl;
import ru.mera.sergeynazin.model.Note;
import ru.mera.sergeynazin.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public abstract class NoteServiceImpl implements NoteService {

    @Autowired
    NoteDAOImpl noteDAO;

    @Override
    public void add(Note note) {
        noteDAO.add(note);
    }

    @Override
    public void update(Note note) {
        noteDAO.update(note);
    }

    @Override
    public Note getCategory(Long id) {
        return noteDAO.getNote(id);
    }

    @Override
    public void delete(Long id) {
        noteDAO.delete(id);
    }

    @Override
    public List<Note> getNotes() {
        return noteDAO.getNotes();
    }
}
