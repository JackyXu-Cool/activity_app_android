package activity.app.android.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Activity extends BmobObject {
    private String name;
    private String imageURL;
    private String location;
    private Date activtiyDate;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getActivtiyDate() {
        return activtiyDate;
    }

    public void setActivtiyDate(Date activtiyDate) {
        this.activtiyDate = new Date(activtiyDate.getTime());
    }
}
