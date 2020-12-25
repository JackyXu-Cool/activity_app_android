package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;

import java.util.List;

public class RegisterActivity extends AppCompatActivity {

    ImageView preview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        preview = findViewById(R.id.user_avatar_preview);
    }

    // TODO: user registration
    public void registerOperation(View view) {

    }

    public void switchToLogin(View view) {
        Intent intent = new Intent(this, WelcomActivity.class);
        startActivity(intent);
    }

    // TODO: fix image picker.
    public void selectFromGallery(View view) {
        PictureSelector.create(this)
                .openGallery(PictureMimeType.ofImage())
                .forResult(PictureConfig.CHOOSE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PictureConfig.CHOOSE_REQUEST:
                    // 结果回调
                    List<LocalMedia> selectList = PictureSelector.obtainMultipleResult(data);
                    break;
                default:
                    break;
            }
        }
    }
}