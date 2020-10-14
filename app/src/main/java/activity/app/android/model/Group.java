package activity.app.android.model;

import java.util.Date;

import cn.bmob.v3.BmobObject;

public class Group extends BmobObject {
    private String groupName;
    private String groupIntroduction;
    private String coverURL;  // Cover photo for this group
    private Date createdDate;

    //TODO: learn how to connect different data object in Bmob
//    private List<Person> groupMembers;
//    private List<Activity> activityList;
//    private List<Moment> momentList;

    public Group() {}

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
}
