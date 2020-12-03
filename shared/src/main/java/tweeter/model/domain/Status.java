package tweeter.model.domain;


import java.time.LocalDateTime;
import java.util.List;

public class Status {


    private String status;
    private List<String> urls;
    private long createdDate;
    private List<String> mentions;
    private User author;
    private String firstName;
    private String lastName;



    public Status(String status, List<String> urls, long createdDate, List<String> mentions, User author) {
        this.status = status;
        this.urls = urls;
        this.createdDate = createdDate;
        this.mentions = mentions;
        this.author = author;
    }

    public Status(String status, long createdDate) {
        this.status = status;
        this.createdDate = createdDate;
    }

    public Status(User author, String status, long createdDate) {
        this.status = status;
        this.author = author;
        this.createdDate = createdDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String message) {
        this.status = message;
    }

    public List<String> getUrls() {
        return urls;
    }

    public void setUrls(List<String> urls) {
        this.urls = urls;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /*
    public String formatDate() {
        String month = createdDate.getMonth().toString().toLowerCase();
        month = month.substring(0, 1).toUpperCase() + month.substring(1);
        return month + " " + createdDate.getDayOfMonth() + " " + createdDate.getYear() +
                " " + createdDate.getHour() + ":" + createdDate.getMinute();
    }
    */



}


