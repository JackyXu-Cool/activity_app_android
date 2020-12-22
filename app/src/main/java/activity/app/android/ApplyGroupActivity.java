package activity.app.android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.NoSuchElementException;

import activity.app.android.databinding.ActivityApplyGroupBinding;
import activity.app.android.model.Group;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class ApplyGroupActivity extends AppCompatActivity{

    // Fetch private bmob key from cpp file
    static {
        System.loadLibrary("keys");
    }
    public native String getNativeKey1();
    String key1 = new String(Base64.decode(getNativeKey1(), Base64.DEFAULT));

    // Declare useful elemnt from the page
    Button displayDocs;
    Button displayHighlights;
    Button displayMoments;
    ImageView avatar;
    ViewFlipper slidingContentSelector;
    LinearLayout progressView;
    LinearLayout groupContent;
    LinearLayout slidingButtons;
    ActivityApplyGroupBinding mbinding;
    SlidingUpPanelLayout panel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set up Bmob
        Bmob.initialize(this, key1);

        // Apply data binding
        mbinding = DataBindingUtil.setContentView(this, R.layout.activity_apply_group);

        // Define view elements
        groupContent = findViewById(R.id.group);
        progressView = findViewById(R.id.progressbar_view);
        slidingButtons = findViewById(R.id.slidingButtons);
        panel = findViewById(R.id.slidingpanel_group_whole);

        // Set up toolbars
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.returnbutton);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Instantiate the buttons on sliding panel
        displayDocs = (Button)(slidingButtons.getChildAt(0));
        displayHighlights = (Button)(slidingButtons.getChildAt(1));
        displayMoments = (Button)(slidingButtons.getChildAt(2));
        slidingContentSelector = findViewById(R.id.slidingDisplaySelector);
        avatar = findViewById(R.id.avatar);

        displayDocs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!displayDocs.isSelected()) {
                    slidingContentSelector.setDisplayedChild(0);
                    displayDocs.setSelected(true);
                    displayHighlights.setSelected(false);
                    displayMoments.setSelected(false);
                }
            }
        });

        displayHighlights.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if (!displayHighlights.isSelected()) {
                    slidingContentSelector.setDisplayedChild(1);
                    displayDocs.setSelected(false);
                    displayHighlights.setSelected(true);
                    displayMoments.setSelected(false);
                }
            }
        });

        displayMoments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!displayMoments.isSelected()) {
                    slidingContentSelector.setDisplayedChild(2);
                    displayDocs.setSelected(false);
                    displayHighlights.setSelected(false);
                    displayMoments.setSelected(true);
                }
            }
        });

        // Set up sliding button groups functionality
        sliding_buttongroups_setup();
        slidingContentSelector.setDisplayedChild(1);

        new Task().execute();
    }

    // Fetch data from Bmob database and then apply those data to views on this page
    class Task extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            progressView.setVisibility(View.VISIBLE);
            groupContent.setVisibility(View.GONE);
            avatar.setVisibility(View.GONE);

            try {
                BmobQuery<Group> bmobQuery = new BmobQuery<Group>();
                bmobQuery.getObject(getIntent().getStringExtra("Group_ID"), new QueryListener<Group>() {
                    @Override
                    public void done(Group group,BmobException e) {
                        if(e==null){
                            mbinding.setGroup(group);

                            // Set Image view, group introduction, and created date
                            setGroupCover(group.getCoverURL());
                            setGroupIntro(group.getGroupIntroduction());
                            setGroupCreatedDate(group.getCreatedDate());

                        }else{
                            throw new NoSuchElementException("Cannot find group");
                        }
                    }
                });

            } catch(Exception e) {
                finish(); // Return to the previous page is group not found
            }
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            progressView.setVisibility(View.GONE);
            groupContent.setVisibility(View.VISIBLE);
            avatar.setVisibility(View.VISIBLE);
            super.onPostExecute(result);
        }

        @Override
        protected Boolean doInBackground(String... params) {
            while (avatar.getDrawable() == null) {
                try {
                    panel.setTouchEnabled(false);
                    Thread.sleep(1000);
                    panel.setTouchEnabled(true);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        // Private method that set the created date of this group
        private void setGroupCreatedDate(Date date) {
            DateFormat df = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
            TextView txtCreatedDate = findViewById(R.id.groupDateText);
            txtCreatedDate.setText("成立时间：" + df.format(date));
        }

        // Private method that set up group's cover
        private void setGroupCover(String url){
            Glide.with(ApplyGroupActivity.this).load(url).
                    apply(new RequestOptions().override(600, 400)).centerCrop().into(avatar);
        }

        // Private method that set up group's introduction
        private void setGroupIntro(String intro){
            TextView txtGroupIntro = findViewById(R.id.group_intro_content);
            txtGroupIntro.setText(intro);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    // Handle event for "cancel" button: return to the previous page
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Basic set up for the button groups on the top of the sliding panel
    private void sliding_buttongroups_setup() {
        String[] buttonNames = getResources().getStringArray(R.array.group_info_buttons);
        displayDocs.setText(buttonNames[0]);
        displayHighlights.setText(buttonNames[1]);
        displayHighlights.setSelected(true);
        displayMoments.setText(buttonNames[2]);
    }
}