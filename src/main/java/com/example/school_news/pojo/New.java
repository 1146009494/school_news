package com.example.school_news.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "new")
@JsonIgnoreProperties({ "handler","hibernateLazyInitializer" })
public class New {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @ManyToOne
    @JoinColumn(name = "uwrite_id")
    private User user_write;

    @ManyToOne
    @JoinColumn(name = "uread_id")
    private User user_read;

    @ManyToOne
    @JoinColumn(name = "ntid")
    private NewType note_type;

    private Date refer_time;
    private Date auditor_time;

    private String note;
    private String note_title;
    private String image_url;
    private String video_url;

    private String state;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser_write() {
        return user_write;
    }

    public void setUser_write(User user_write) {
        this.user_write = user_write;
    }

    public User getUser_read() {
        return user_read;
    }

    public void setUser_read(User user_read) {
        this.user_read = user_read;
    }

    public NewType getNote_type() {
        return note_type;
    }

    public void setNote_type(NewType note_type) {
        this.note_type = note_type;
    }

    public Date getRefer_time() {
        return refer_time;
    }

    public void setRefer_time(Date refer_time) {
        this.refer_time = refer_time;
    }

    public Date getAuditor_time() {
        return auditor_time;
    }

    public void setAuditor_time(Date auditor_time) {
        this.auditor_time = auditor_time;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNote_title() {
        return note_title;
    }

    public void setNote_title(String note_title) {
        this.note_title = note_title;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getVideo_url() {
        return video_url;
    }

    public void setVideo_url(String video_url) {
        this.video_url = video_url;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
