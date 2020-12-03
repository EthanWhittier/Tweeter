package tweeter.server.dto;

import java.util.List;

public class UpdateFeedDTO {


    private String status;
    private List<String> followers;
    private String author;
    private long created;


    public UpdateFeedDTO(String status, List<String> followers, String author, long created) {
        this.status = status;
        this.followers = followers;
        this.author = author;
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCreated() {
        return created;
    }

    public void setCreated(long created) {
        this.created = created;
    }
}
