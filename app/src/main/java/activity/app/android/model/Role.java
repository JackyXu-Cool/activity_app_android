package activity.app.android.model;

public enum Role {
    CREATOR("Creator"),
    ADMINISTRATOR("Admin"),
    MEMBER("Member");

    private final String name;

    Role(String readableName) { this.name = readableName; }

    @Override
    public String toString() {
        return name;
    }
}
