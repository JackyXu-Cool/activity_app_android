package activity.app.android.model;

import java.util.Date;

public class Activity {
    private String imageURL;
    private Date activtiyDate;

    public Activity(String imageURL, Date activtiyDate) {
        this.imageURL = imageURL;
        this.activtiyDate = activtiyDate;
    }
}
