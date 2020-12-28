package activity.app.android.util;

import activity.app.android.model.User;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class BmobObjectOperation {
    public static void saveToDatabase(BmobObject object) {
        object.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e==null) {
                    System.out.println("数据存储成功");
                } else {
                    System.out.println("数据存储失败" + e.getMessage());
                }
            }
        });
    }
    public static void signUp(User user){
        user.signUp(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    System.out.println("数据存储成功");
                } else {
                    System.out.println("数据存储失败" + e.getMessage());
                }
            }
        });
    }
}
