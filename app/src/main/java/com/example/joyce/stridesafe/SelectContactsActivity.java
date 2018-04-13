//Activity that allows the user to select contacts to store as emergency contacts in case an abduction is detected.
package com.example.joyce.stridesafe;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.view.Menu;
import android.widget.AdapterView;
import android.widget.Toast;

//public class SelectContactsActivity extends ListActivity {

    //@Override
    //protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_select_contacts);

    //Accesses the contact's name and phone number.
        //Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,null,null, null);
        //while (phones.moveToNext())
        //{
           // String name=phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            //String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            //Toast.makeText(getApplicationContext(),name, Toast.LENGTH_LONG).show();
        //}
       //phones.close();
    //}

// Accesses the selected item ID in the contact list.
    //@Override
    //public long getSelectedItemId() {
       //return super.getSelectedItemId();
    //}

//Gets the position of the contact in the contact list.
    //@Override
    //public int getSelectedItemPosition() {
        //return super.getSelectedItemPosition();
    //}

//}
