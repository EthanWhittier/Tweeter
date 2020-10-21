package edu.byu.cs.tweeter.model.domain;


import java.time.LocalDateTime;
import java.util.List;

public class Status {


    private String message;
    private List<String> urls;
    private LocalDateTime createdDate;
    private List<String> mentions;
    private User author;


    public Status(String message, List<String> urls, LocalDateTime createdDate, List<String> mentions, User author) {
        this.message = message;
        this.urls = urls;
        this.createdDate = createdDate;
        this.mentions = mentions;
        this.author = author;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public List<String> getMentions() {
        return mentions;
    }

    public void setMentions(List<String> mentions) {
        this.mentions = mentions;
    }

    public User getAuthor() {return author;}

    public void setAuthor(User author) {this.author = author;}

    public String formatDate() {
        String month = createdDate.getMonth().toString().toLowerCase();
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        return month + " " + createdDate.getDayOfMonth() + " " + createdDate.getYear() +
                " " + createdDate.getHour() + ":" + createdDate.getMinute();
     }
}


