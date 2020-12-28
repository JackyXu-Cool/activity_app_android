package activity.app.android.model;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

public class User extends BmobUser {
    private List<String> affliatedGroup; // Stores a list of group id
    private List<String> attendedActivity; // Stores a list of activity id
    private BmobFile avatar;

    public List<String> getAffliatedGroup() {
        return affliatedGroup;
    }

    public User setAffliatedGroup(List<String> affliatedGroup) {
        this.affliatedGroup = new ArrayList<>(affliatedGroup);
        return this;
    }

    public List<String> getAttendedActivity() {
        return attendedActivity;
    }

    public User setAttendedActivity(List<String> attendedActivity) {
        this.attendedActivity = new ArrayList<>(attendedActivity);
        return this;
    }

    public BmobFile getAvatar() {
        return avatar;
    }

    public User setAvatar(BmobFile avatar) {
        this.avatar = avatar;
        return this;
    }
}
