package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.bson.Document;

import io.realm.mongodb.App;
import io.realm.mongodb.mongo.MongoClient;
import io.realm.mongodb.mongo.MongoCollection;
import io.realm.mongodb.mongo.MongoDatabase;

public class SelectSchoolActivity extends AppCompatActivity {

    String[] schoolArray = {"Boston University","Georgia Institute of Technology",
    "Northwestern University", "University of Wisconsin", "Standford University", "University of Washington",
            "Syracuse University"};
    ListView listView;
    App app;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = ((MyApplication) this.getApplication()).app;
        setContentView(R.layout.activity_select_school);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.single_item, R.id.listViewItem, schoolArray);
        listView = (ListView) findViewById(R.id.schoolListView);
        listView.setAdapter(adapter);

        // Item select listener for the list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            public void onItemClick(AdapterView<?> adpterView, View view, int position, long id) {
                for (int i = 0; i < listView.getChildCount(); i++) {
                    if(position == i ){
                        listView.getChildAt(i).setBackgroundColor(Color.rgb(117, 115, 115));
                    }else{
                        listView.getChildAt(i).setBackgroundColor(Color.rgb(127, 180, 252));
                    }
                }
            }
        });
    }

    /**
     * Handle the event that start another activity
     * @param view "Get started" textview
     */
    public void changeToGroupListPage(View view) {
        for (int i = 0; i < listView.getChildCount(); i++) {
            if (listView.isItemChecked(i)) {
                listView.setItemChecked(i, false);
                Intent intent = new Intent(this, GroupListActivity.class);
                String name = (String) (listView.getItemAtPosition(i));

                MongoClient mongoClient = app.currentUser().getMongoClient("mongodb-atlas");
                MongoDatabase mongoDatabase = mongoClient.getDatabase("clubM");
                MongoCollection<Document> mongoCollection = mongoDatabase.getCollection("users");
                mongoCollection.updateOne(new Document("user-id-field", app.currentUser().getId()), new Document("$set", new Document("school", name))).getAsync(task -> {
                    if (task.isSuccess()) {
                        long count = task.get().getModifiedCount();
                        if (count == 1) {
                            Log.v("Success", "successfully updated a document.");
                        } else {
                            Log.v("Error", "did not update a document.");
                        }
                        finish();
                    } else {
                        Log.e("ERROR", task.getError().getErrorMessage());
                    }
                });

                intent.putExtra("school_name", name);
                startActivity(intent);
                finish();
                return;
            }
        }
        Toast.makeText(this, "Select your school", Toast.LENGTH_LONG).show();
    }
}