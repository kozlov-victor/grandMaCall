package ua.victor.grandmacall.provider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

public class PhoneBookProvider {

    public @Nullable String getContactNameByPhoneNumber(Context context, String phoneNumber) {
        if (phoneNumber==null) return "";
        if (phoneNumber.equals("")) return "";

        if (ContextCompat.checkSelfPermission(context,
                Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            return phoneNumber;
        }

        try {
            ContentResolver cr = context.getContentResolver();
            Uri uri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI, Uri.encode(phoneNumber));
            Cursor cursor = cr.query(uri, new String[]{ContactsContract.PhoneLookup.DISPLAY_NAME}, null, null, null);
            if (cursor == null) {
                return null;
            }
            String contactName = null;
            if(cursor.moveToFirst()) {
                contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.PhoneLookup.DISPLAY_NAME));
            }
            if(!cursor.isClosed()) {
                cursor.close();
            }
            return contactName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


}
