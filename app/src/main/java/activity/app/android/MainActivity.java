package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;


public class MainActivity extends AppCompatActivity {

    MapView mMapView = null;
    AMap aMap;

    SlidingUpPanelLayout panel;
    LinearLayout slidingButtons;
    LinearLayout mainPageButtons;
    LinearLayout activityGallery;
    Button displayRecrutingInfo;
    Button displayActivityInfo;
    Button displayHighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingButtons = findViewById(R.id.slidingButtons);
        mainPageButtons = (LinearLayout)(findViewById(R.id.main_page_buttons));
        activityGallery = findViewById(R.id.activityGallery);
        panel = findViewById(R.id.sliding_main_page);

        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map);
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView.onCreate(savedInstanceState);
        if (aMap == null) {
            aMap = mMapView.getMap();
        }

        // Instantiate the buttons on sliding panel
        displayRecrutingInfo = (Button)(slidingButtons.getChildAt(0));
        displayActivityInfo = (Button)(slidingButtons.getChildAt(1));
        displayHighlights = (Button)(slidingButtons.getChildAt(2));
        sliding_buttongroups_setup();

        panel.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {
                if (newState.name().equals("EXPANDED") ||
                        newState.name().equals("DRAGGING") && previousState.name().equals("COLLAPSED")) {
                    mainPageButtons.setVisibility(View.INVISIBLE);
                } else {
                    mainPageButtons.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        ImageView line = findViewById(R.id.sliding_line);
        panel.setPanelHeight(5 + line.getHeight() + 10 + activityGallery.getHeight() + 5 + slidingButtons.getHeight() + 20
                + mainPageButtons.getHeight() + 30);
    }

    // Basic set up for the button groups on the top of the sliding panel
    private void sliding_buttongroups_setup() {
        String[] buttonNames = getResources().getStringArray(R.array.main_page_buttons);
        displayRecrutingInfo.setText(buttonNames[0]);
        displayActivityInfo.setText(buttonNames[1]);
        displayHighlights.setText(buttonNames[2]);
    }

    /**
     * Handle the event that start another activity
     * @param view start button
     */
    public void changeToApplyPage(View view) {
        Intent intent = new Intent(this, ApplyGroupActivity.class);
        startActivity(intent);
    }
}