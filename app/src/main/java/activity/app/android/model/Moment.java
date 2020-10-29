package activity.app.android.model;

public class Moment {
    private int likes;
    private String creatorID;
    private String content;

    public Moment(String creatorID, String content) {
        this.likes = 0;
        this.creatorID = creatorID;
        this.content = content;
    }

    public void incrementLikes() {
        this.likes += 1;
    }

    public String getContent() {
        return content;
    }

    public void updateContent(String content) {
        this.content = content;
    }
}
