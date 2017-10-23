package ru.mera.sergeynazin.dao.impl;

import ru.mera.sergeynazin.dao.NoteDAO;
import ru.mera.sergeynazin.model.old.Note;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public abstract class NoteDAOImpl implements NoteDAO {

    @Resource(name = "sessionFactory")
    private SessionFactory sessionFactory;

    private Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void add(Note note) {
        getCurrentSession().save(note);
    }

    @Override
    public void update(Note note) {
        Note noteToUpdate = getNote(note.getNoteId());
        noteToUpdate.setNoteTitle(note.getNoteTitle());
        noteToUpdate.setNoteBody(note.getNoteBody());
        noteToUpdate.setCategoryId(note.getCategoryId());
        getCurrentSession().update(noteToUpdate);
    }

    @Override
    public Note getNote(Long id) {
        Note note = (Note) getCurrentSession().get(Note.class, id);
        return note;
    }

    @Override
    public void delete(Long id) {
        Note getNote = getNote(id);
        if (getNote != null) {
            getCurrentSession().delete(getNote);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<Note> getNotes() {
        return getCurrentSession().createQuery("from Note").list();
    }
}
