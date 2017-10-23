package ru.mera.sergeynazin.model.old;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table
public abstract class Note implements Serializable {

    private static final long serialVersionUID = -5527566248002296042L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "note_id", nullable = false, unique = true)
    private Long noteId;

    @Column(name = "note_title", length = 120)
    private String noteTitle;

    @Column(name = "note_body")
    private String noteBody;

    @Column(name = "category_id")
    private Long categoryId;


    public Long getNoteId() {
        return noteId;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteBody() {
        return noteBody;
    }

    public void setNoteBody(String noteBody) {
        this.noteBody = noteBody;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

}