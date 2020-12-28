package activity.app.android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;


import activity.app.android.model.User;
import activity.app.android.util.AESCrypt;
import activity.app.android.util.BmobObjectOperation;
import activity.app.android.util.PathConverter;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UploadFileListener;


public class RegisterActivity extends AppCompatActivity {

    // Fetch private bmob key from cpp file
    static {
        System.loadLibrary("keys");
    }
    public native String getNativeKey2();
    String key2 = new String(Base64.decode(getNativeKey2(), Base64.DEFAULT));

    int SELECT_PHOTO = 1;
    Uri uri;
    ImageView preview;

    EditText usernameTxt;
    EditText passwordTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Set up Bmob
        Bmob.initialize(this, key2);

        // Define components
        preview = findViewById(R.id.user_avatar_preview);
        usernameTxt = findViewById(R.id.usernameTxt_register);
        passwordTxt = findViewById(R.id.passwordTxt_register);
    }

    // TODO: 文件域名注册 & error handling
    public void registerOperation(View view) throws URISyntaxException {
        if (usernameTxt.getText().toString().trim().equals("") || passwordTxt.getText().toString().trim().equals("") || uri == null) {
            Toast.makeText(this, "Enter the required field", Toast.LENGTH_SHORT).show();
            return;
        }
        String username = usernameTxt.getText().toString();
        String hashedPassword = "";
        try {
            hashedPassword = AESCrypt.encrypt(passwordTxt.getText().toString());
        } catch (Exception e) {
            Toast.makeText(this, "Please enter the password in correct format", Toast.LENGTH_SHORT).show();
        }
        // Store new user to the database
        User newUser = new User();
        newUser.setUsername(username);
        newUser.setPassword(hashedPassword);
        String path = PathConverter.convertMediaUriToPath(this, uri);
        File pic = new File(path);
        BmobFile a1 = new BmobFile(pic);
        a1.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                    if(e==null){
                        System.out.println("上传文件成功:");
                        BmobObjectOperation.signUp(newUser);
                    }else{
                        System.out.println("上传文件失败：" + e.getMessage());
                    }
            }
        });

        // Automatically open user profile page
        Intent intent = new Intent(this, UserProfileActivity.class);
        startActivity(intent);
    }

    public void switchToLogin(View view) {
        Intent intent = new Intent(this, WelcomActivity.class);
        startActivity(intent);
    }

    public void selectFromGallery(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        // startActivityForResult() defines that when this activity exits, onActivityResult() will be called
        startActivityForResult(intent, SELECT_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_PHOTO && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                preview.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}