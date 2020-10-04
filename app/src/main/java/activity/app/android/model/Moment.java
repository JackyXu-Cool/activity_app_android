package activity.app.android.model;

public class Moment {
    private int likes;
    private Person creator;
    private String content;

    public Moment(Person creator, String content) {
        this.likes = 0;
        this.creator = creator;
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
