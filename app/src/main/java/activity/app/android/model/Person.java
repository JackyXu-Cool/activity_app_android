package activity.app.android.model;

import cn.bmob.v3.BmobObject;

public class Person extends BmobObject {
    private String name; // account name
    private String realLastName;
    private String realFirstName;
    private String avatarURL;
    //private Map<Group, Role> affiliatedGroup;

    public String getName() { return name; }

    public void setName(String name) { this.name = name; }

    public String getRealLastName() {
        return realLastName;
    }

    public void setRealLastName(String realLastName) {
        this.realLastName = realLastName;
    }

    public String getRealFirstName() {
        return realFirstName;
    }

    public void setRealFirstName(String realFirstName) {
        this.realFirstName = realFirstName;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public void setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
    }
}
