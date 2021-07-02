package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import org.bson.Document;

import io.realm.mongodb.App;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    String[] schoolArray = {"Boston University","Georgia Institute of Technology",
            "Northwestern University", "University of Wisconsin", "Standford University", "University of Washington",
            "Syracuse University"};

    private String selectedSchool;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        app = ((MyApplication) this.getApplication()).app;

        // Define the school dropdown list
        Spinner spin = (Spinner) findViewById(R.id.schoolListSpinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_txt, schoolArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
        spin.setOnItemSelectedListener(this);

        String currSchool = app.currentUser().getCustomData().getString("school");
        if (currSchool != "" && currSchool.length() > 0) {
            for (int i = 0; i < schoolArray.length; i++) {
                if (currSchool.equals(schoolArray[i])) {
                    spin.setSelection(i);
                }
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
        selectedSchool = schoolArray[position];
        Log.v("school", selectedSchool);
    }

    // Nothing will be done
    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void saveChanges(View view) {
        MongoClient mongoClient = app.currentUser().getMongoClient("mongodb-atlas");
        MongoDatabase mongoDatabase = mongoClient.getDatabase("clubM");
        MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
        mongoCollection.updateOne(new Document("user-id-field", app.currentUser().getId()), new Document("$set", new Document("school", selectedSchool))).getAsync(task -> {
            if (task.isSuccess()) {
                long count = task.get().getModifiedCount();
                if (count == 1) {
                    Log.v("Success", "successfully updated a document.");
                } else {
                    Log.v("ERROR", "did not update a document.");
                }
                finish();
            } else {
                Log.e("ERROR", "Fail to update information");
            }
        });
    }

    public void backToUserProfile(View view) {
        finish();
    }
}