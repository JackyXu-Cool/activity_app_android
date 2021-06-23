package activity.app.android.model;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Activity extends RealmObject {
    @PrimaryKey
    private String _id;
    private String name; // activity name
    private String imageURL;
    private String location;
    private Date activtiyDate;
    @Required
    private RealmList<ObjectId> signUpUsers; // users id that sign up for this activity

    public Activity() {}

    public Activity(String name, String imageURL, String location, Date activtiyDate) {
        this._id = UUID.randomUUID().toString();
        this.name = name;
        this.imageURL = imageURL;
        this.location = location;
        this.activtiyDate = activtiyDate;
        this.signUpUsers = new RealmList<>();
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
        this.signUpUsers.add(id);
    }

    public RealmList<ObjectId> getAllUsers() {
        return signUpUsers;
    }

    public String removeUser(String id) {
        this.signUpUsers.remove(id);
        return id;
    }
}
