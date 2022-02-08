package com.example.Demo.models;

import javax.persistence.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String author;
    private String text;

    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

    public Message(String author, String text, Date date) {
        this.text = text;
        this.author = author;
        this.creationDate = date;
    }

    public Message() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getCreationDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.MM.yyyy HH:mm");
        return formatter.format(this.creationDate);
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
