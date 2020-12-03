package tweeter.server.dto;

public class PostStatusDTO {


    private String status;
    private String author;
    private long created;

    public PostStatusDTO(String status, String author, long created) {
        this.status = status;
        this.author = author;
        this.created = created;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
