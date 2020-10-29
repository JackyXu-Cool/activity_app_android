package activity.app.android.model;


import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;

public class User extends BmobUser {
    private String nickName; // account name
    private String avatarURL;
    private List<String> affliatedGroup; //String stores the group id

    public String getNickname() { return nickName; }

    public User setNickName(String nickName) {
        this.nickName = nickName;
        return this;
    }

    public String getAvatarURL() {
        return avatarURL;
    }

    public User setAvatarURL(String avatarURL) {
        this.avatarURL = avatarURL;
        return this;
    }

    public List<String> getAffliatedGroup() {
        return affliatedGroup;
    }

    public User setAffliatedGroup(List<String> affliatedGroup) {
        this.affliatedGroup = new ArrayList<>(affliatedGroup);
        return this;
    }
}
