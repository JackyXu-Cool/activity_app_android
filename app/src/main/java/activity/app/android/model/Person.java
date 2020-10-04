package activity.app.android.model;

import java.util.HashMap;
import java.util.Map;

public class Person {
    private String accountName;
    private String realLastName;
    private String realFirstName;
    private String avatarURL;
    private Map<Group, Role> affiliatedGroup;

    public Person(String accountName, String realFirstName, String realLastName, String avatarURL) {
        this.accountName = accountName;
        this.realFirstName = realFirstName;
        this.realLastName = realLastName;
        this.avatarURL = avatarURL;
        affiliatedGroup = new HashMap<>();
    }
}
