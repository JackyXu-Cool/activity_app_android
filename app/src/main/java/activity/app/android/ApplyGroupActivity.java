package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ViewFlipper;


public class ApplyGroupActivity extends AppCompatActivity{

    Button displayDocs;
    Button displayHighlights;
    Button displayMoments;
    ViewFlipper slidingContentSelector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_apply_page);

        displayDocs = findViewById(R.id.activityDocsButton);
        displayHighlights = findViewById(R.id.activityHighlightsButton);
        displayMoments = findViewById(R.id.activityMomentsButton);
        slidingContentSelector = findViewById(R.id.slidingDisplaySelector);

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
