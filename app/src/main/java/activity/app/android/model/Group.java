package activity.app.android.model;

import org.bson.types.ObjectId;

import java.util.Date;
import java.util.UUID;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class Group extends RealmObject{
    @PrimaryKey
    private String _id;
    private String groupName;
    private String groupIntroduction;
    private String coverURL;  // Cover photo for this group
    private Date createdDate;

    @Required
    private RealmList<ObjectId> groupMembers; // user id of those who is in this group
    @Required
    private RealmList<ObjectId> activities; // activity id belong to this group

    public Group() {}

    public Group (String groupName, String groupIntroduction, String coverURL) {
        this._id = UUID.randomUUID().toString();
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

    public String getId() {
        return _id;
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

    public ObjectId removeMembers(ObjectId id) {
        groupMembers.remove(id);
        return id;
    }

    public RealmList<ObjectId> getActivities() {
        return activities;
    }

    public void addActivity(ObjectId id) {
        activities.add(id);
    }

    public String removeActivity(String id) {
        activities.remove(id);
        return id;
    }
}
