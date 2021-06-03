package activity.app.android;

import android.app.Application;

import io.realm.Realm;
import io.realm.mongodb.App;
import io.realm.mongodb.AppConfiguration;

public class MyApplication extends Application {

    App app;

    @Override
    public void onCreate() {
        super.onCreate();
        Realm.init(this);
        app = new App(new AppConfiguration.Builder("activity_app-znbjb").build());
    }
}
