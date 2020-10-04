package activity.app.android.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Group {
    private String groupName;
    private String groupIntroduction;
    private String coverURL;  // Cover photo for this group
    private Date createdDate;
    private List<Person> groupMembers;
    private List<Activity> activityList;
    private List<Moment> momentList;

    public Group (String groupName, String groupIntroduction, String coverURL, Date createdDate) {
        this.groupName = groupName;
        this.groupIntroduction = groupIntroduction;
        this.coverURL = coverURL;
        this.createdDate = createdDate;
        groupMembers = new ArrayList<>();
        activityList = new ArrayList<>();
        momentList = new ArrayList<>();
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

    public List<Person> getGroupMembers() {
        return groupMembers;
    }

    public void setGroupMembers(List<Person> groupMembers) {
        this.groupMembers = groupMembers;
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public void setActivityList(List<Activity> activityList) {
        this.activityList = activityList;
    }

    public List<Moment> getMomentList() {
        return momentList;
    }

    public void setMomentList(List<Moment> momentListList) {
        this.momentList = momentList;
    }

    public int getGroupSize() {
        return groupMembers.size();
    }

    // TODO: Extra group methods necessary?
}
