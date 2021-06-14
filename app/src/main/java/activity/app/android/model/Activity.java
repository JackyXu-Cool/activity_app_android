package activity.app.android.model;

import org.bson.types.ObjectId;

import java.util.Date;

import io.realm.RealmList;
import io.realm.RealmObject;

// TODO: create realm schema Atlas
// TODO: check if remove function is correct.
public class Activity extends RealmObject {
    private String name; // activity name
    private String imageURL;
    private String location;
    private Date activtiyDate;
    private RealmList<ObjectId> users; // users that sign up for this activity

    public Activity() {}

    public Activity(String name, String imageURL, String location, Date activtiyDate) {
        this.name = name;
        this.imageURL = imageURL;
        this.location = location;
        this.activtiyDate = activtiyDate;
        users = new RealmList<>();
    }

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

    public void addNewUser(ObjectId id) {
        users.add(id);
    }

    public RealmList<ObjectId> getAllUsers() {
        return users;
    }

    public ObjectId removeUser(ObjectId id) {
        users.remove(id);
        return id;
    }
}
