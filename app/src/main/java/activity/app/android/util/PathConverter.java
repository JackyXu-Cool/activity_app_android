package activity.app.android.util;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class PathConverter {
    public static String convertMediaUriToPath(Context context, Uri uri) {
        String [] proj={MediaStore.Images.Media.DATA};
        Cursor cursor = context.getContentResolver().query(uri, proj,  null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }
}
