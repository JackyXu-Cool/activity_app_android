package activity.app.android.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Group {
    private String groupName;
    private String groupIntroduction;
    private String coverURL;  // Cover photo for this group
    private Date createdDate;
    private List<Person> groupMembers;
    private List<Activity> activityList;
    private List<Moment> momentList;

    public Group (String groupName, String groupIntroduction, String coverURL) {
        this.groupName = groupName;
        this.groupIntroduction = groupIntroduction;
        this.coverURL = coverURL;
        this.createdDate = new Date();
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

    public String getCreatedDate() {
        DateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        return "成立时间：" + df.format(createdDate);
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

    public void addToMomentList(Moment moment) {
        this.momentList.add(moment);
    }

    public int getGroupSize() {
        return groupMembers.size();
    }

    public String getGroupSizeString() {
        return "小组人数： " + getGroupSize() + "人";
    }

    // TODO: Extra group methods necessary?
}
