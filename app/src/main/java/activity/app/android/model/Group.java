package activity.app.android.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// TODO: create realm model
public class Group {
    private String groupName;
    private String groupIntroduction;
    private String coverURL;  // Cover photo for this group
    private Date createdDate;

    private List<String> groupMembers; // String stores the user id
    private List<String> activityList; // String stores the activity id

    public Group() {}

    // TODO: CoverURL should be replaced by file (image file...??)
    public Group (String groupName, String groupIntroduction, String coverURL) {
        this.groupName = groupName;
        this.groupIntroduction = groupIntroduction;
        this.coverURL = coverURL;
        this.createdDate = new Date();
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

    public List<String> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<String> groupMembers) {
        this.groupMembers = new ArrayList<>(groupMembers);
    }

    public List<String> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<String> activityList) {
        this.activityList = new ArrayList<>(activityList);
    }
}
