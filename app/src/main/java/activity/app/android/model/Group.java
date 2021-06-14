package activity.app.android.model;

import org.bson.types.ObjectId;

import java.util.Date;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

// TODO: create realm schema on Atlas
// TODO: remove function for groupmemebers and activies
public class Group extends RealmObject{
    @PrimaryKey
    private String groupName;
    private String groupIntroduction;
    private String coverURL;  // Cover photo for this group
    private Date createdDate;

    private RealmList<ObjectId> groupMembers;
    private RealmList<Activity> activities;

    public Group() {}

    public Group (String groupName, String groupIntroduction, String coverURL) {
        this.groupName = groupName;
        this.groupIntroduction = groupIntroduction;
        this.coverURL = coverURL;
        this.createdDate = new Date();
        groupMembers = new RealmList<>();
        activities = new RealmList<>();
    }

    public Group(Group anotherGroup) {
        this.groupName = anotherGroup.getGroupName();
        this.groupIntroduction = anotherGroup.getGroupIntroduction();
        this.coverURL = anotherGroup.getCoverURL();
        this.createdDate = new Date(anotherGroup.getCreatedDate().getTime());
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupIntroduction() {
        return groupIntroduction;
    }

    public void setGroupIntroduction(String groupIntroduction) {
        this.groupIntroduction = groupIntroduction;
    }

    public String getCoverURL() {
        return coverURL;
    }

    public void setCoverURL(String coverURL) {
        this.coverURL = coverURL;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public RealmList<ObjectId> getGroupMembers() {
        return groupMembers;
    }

    public void addMembers(ObjectId id) {
        groupMembers.add(id);
    }

    public RealmList<Activity> getActivities() {
        return activities;
    }

    public void addActivity(Activity a) {
        activities.add(a);
    }
}
