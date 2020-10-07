package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.bumptech.glide.Glide;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.module.AppGlideModule;

import java.net.URL;

import activity.app.android.databinding.GroupApplyPageBinding;
import activity.app.android.model.Group;

public class ApplyGroupActivity extends AppCompatActivity{

    Button displayDocs;
    Button displayHighlights;
    Button displayMoments;
    ViewFlipper slidingContentSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GroupApplyPageBinding mbinding = DataBindingUtil.setContentView(this, R.layout.group_apply_page);

        Group group = new Group(
                "Jacky's soccer club",
                "One of the best soccer team in FIFA20 FUT.",
                "https://www.adorama.com/alc/wp-content/uploads/2017/11/shutterstock_114802408-1024x683.jpg");

        mbinding.setGroup(group);

        displayDocs = findViewById(R.id.activityDocsButton);
        displayHighlights = findViewById(R.id.activityHighlightsButton);
        displayMoments = findViewById(R.id.activityMomentsButton);
        slidingContentSelector = findViewById(R.id.slidingDisplaySelector);

        ImageView avatar = findViewById(R.id.avatar);

        Glide.with(ApplyGroupActivity.this).load(group.getCoverURL()).into(avatar);

        sliding_buttongroups_setup();
        slidingContentSelector.setDisplayedChild(1);
    }

    // Basic set up for the button groups on the top of the sliding panel
    private void sliding_buttongroups_setup() {
        String[] buttonNames = getResources().getStringArray(R.array.activity_info_buttons);
        displayDocs.setText(buttonNames[0]);
        displayHighlights.setText(buttonNames[1]);
        displayHighlights.setSelected(true);
        displayMoments.setText(buttonNames[2]);
    }

    // Event listener for button that change to documentation page
    public void changeToDocs (View view) {
        if (!displayDocs.isSelected()) {
            slidingContentSelector.setDisplayedChild(0);
            displayDocs.setSelected(true);
            displayHighlights.setSelected(false);
            displayMoments.setSelected(false);
        }
    }

    // Event listener for button that change to highlight page
    public void changeToHighlight (View view) {
        if (!displayHighlights.isSelected()) {
            slidingContentSelector.setDisplayedChild(1);
            displayDocs.setSelected(false);
            displayHighlights.setSelected(true);
            displayMoments.setSelected(false);
        }
    }

    // Event listener for button that change to moment page
    public void changeToMoment (View view) {
        if (!displayMoments.isSelected()) {
            slidingContentSelector.setDisplayedChild(2);
            displayDocs.setSelected(false);
            displayHighlights.setSelected(false);
            displayMoments.setSelected(true);
        }
    }
}
