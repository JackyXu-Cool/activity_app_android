package activity.app.android.model;

import io.realm.mongodb.sync.SyncConfiguration;

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

// TODO: check this out (Below is a working code that can successfully use realm sync).
//    SyncConfiguration config = new SyncConfiguration.Builder(app.currentUser(), "clubM_data")
//            .allowQueriesOnUiThread(true)
//            .allowWritesOnUiThread(true)
//            .build();
//        Realm.getInstanceAsync(config, new Realm.Callback() {
//@Override
//public void onSuccess(Realm realm) {
//        Log.v("EXAMPLE", "Successfully opened a realm.");
//        // Write to the realm. No special syntax required for synced realms.
//        realm.executeTransaction(r -> {
//        r.insert(new Group("FIFA Gaming Club", "Club for playing FIFA", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQNIQovuEBIXdJx--J0OWBnUkoRj0uj6KELYA&usqp=CAU", "Boston University"));
//        });
//        // Don't forget to close your realm!
//        realm.close();
//        }
//        });
//        SyncConfiguration config = new SyncConfiguration.Builder(
//                app.currentUser(),
//                "clubM_data")
//                .allowWritesOnUiThread(true)
//                .build();
//        Realm backgroundThreadRealm = Realm.getInstance(config);
//        backgroundThreadRealm.executeTransaction(t -> {
//            Group deleted = t.where(Group.class).equalTo("groupName", "FIFA Gaming Club").findFirst();
//            deleted.deleteFromRealm();
//        });
//        RealmResults<Group> groups = backgroundThreadRealm.where(Group.class).findAll();
//        Log.v("info", "" + groups.get(0).getGroupName());
//        Log.d("path", backgroundThreadRealm.getPath());