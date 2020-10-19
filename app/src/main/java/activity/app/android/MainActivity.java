package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.MapView;


public class MainActivity extends AppCompatActivity {

    MapView mMapView = null;
    AMap aMap;
    LinearLayout slidingButtons;
    Button displayRecrutingInfo;
    Button displayActivityInfo;
    Button displayHighlights;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        slidingButtons = findViewById(R.id.slidingButtons);

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
    }

    /**
     * Handle the event that start another activity
     * @param view start button
     */
    public void changeToApplyPage(View view) {
        Intent intent = new Intent(this, ApplyGroupActivity.class);
        startActivity(intent);
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

    // Basic set up for the button groups on the top of the sliding panel
    private void sliding_buttongroups_setup() {
        String[] buttonNames = getResources().getStringArray(R.array.main_page_buttons);
        displayRecrutingInfo.setText(buttonNames[0]);
        displayActivityInfo.setText(buttonNames[1]);
        displayHighlights.setText(buttonNames[2]);
    }
}