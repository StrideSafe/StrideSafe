//Activity that allows the user to select contacts to store as emergency contacts in case an abduction is detected.
package com.example.joyce.stridesafe;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class ContactsActivity extends AppCompatActivity {

    private static final String TAG = ContactsActivity.class.getSimpleName();
    private static final int REQUEST_CODE_PICK_CONTACTS = 1;
    private Uri uriContact;
    private String contactID;     // contacts unique ID


    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
    }

    public void onClickSelectContact(View btnSelectContact) {

        // using native contacts selection
        // Intent.ACTION_PICK = Pick an item from the data, returning what was selected.
        startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI), REQUEST_CODE_PICK_CONTACTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
            Log.d(TAG, "Response: " + data.toString());
            uriContact = data.getData();

            retrieveContactName();
            retrieveContactNumber();
        }
    }

    private void retrieveContactNumber() {

        String contactNumber = null;

        // getting contacts ID
        Cursor cursorID = getContentResolver().query(uriContact,
                new String[]{ContactsContract.Contacts._ID},
                null, null, null);

        if (cursorID.moveToFirst()) {

            contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
        }

        cursorID.close();

        Log.d(TAG, "Contact ID: " + contactID);

        // Using the contact ID, we will get the contact's phone number
        Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                        ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

                new String[]{contactID},
                null);

        if (cursorPhone.moveToFirst()) {
            contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
        }

        cursorPhone.close();

        Log.d(TAG, "Contact Phone Number: " + contactNumber);

        TextView contactNum1 = findViewById(R.id.contactNum1);
        String num1 = contactNum1.getText().toString();

        TextView contactNum2 = findViewById(R.id.contactNum2);
        String num2 = contactNum2.getText().toString();

        TextView contactNum3 = findViewById(R.id.contactNum3);
        String num3 = contactNum3.getText().toString();

        if(num1.matches(""))
            contactNum1.setText("Contact Number: " + contactNumber);
        else if(num2.matches(""))
            contactNum2.setText("Contact Number: " + contactNumber);
        else if(num3.matches(""))
            contactNum3.setText("Contact Number: " + contactNumber);

        Intent intent = new Intent(this, HelpActivity.class);
        intent.putExtra("firstNum", num1);
        intent.putExtra("secNum", num2);
        intent.putExtra("answer", num3);

    }

    private void retrieveContactName() {

        String contactName = null;

        // querying contact data store
        Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

        if (cursor.moveToFirst()) {

            // DISPLAY_NAME = The display name for the contact.
            // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

            contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        }

        cursor.close();

        Log.d(TAG, "Contact Name: " + contactName);

        TextView contactName1 = findViewById(R.id.contactName1);
        String name1 = contactName1.getText().toString();

        TextView contactName2 = findViewById(R.id.contactName2);
        String name2 = contactName2.getText().toString();

        TextView contactName3 = findViewById(R.id.contactName3);
        String name3 = contactName3.getText().toString();

        if(name1.matches(""))
            contactName1.setText("Contact Name: " + contactName);
        else if(name2.matches(""))
            contactName2.setText("Contact Name: " + contactName);
        else if(name3.matches(""))
            contactName3.setText("Contact Name: " + contactName);
    }

 }
