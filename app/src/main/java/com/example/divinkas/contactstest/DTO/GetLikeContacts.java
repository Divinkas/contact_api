package com.example.divinkas.contactstest.DTO;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.divinkas.contactstest.Data.ContactItem;

import java.util.ArrayList;
import java.util.List;

public class GetLikeContacts {
    private Context context;
    private LocalDB_Like_contacts dbHelper;
    private SQLiteDatabase db;
    private List<ContactItem> list;

    public GetLikeContacts(Context context){
        this.context = context;
        dbHelper = new LocalDB_Like_contacts(context);
        db = dbHelper.getWritableDatabase();
    }

    public List<ContactItem> getList() {
        list = new ArrayList<>();


        Cursor cursor = db.query(LocalDB_Like_contacts.TABLE_CONTACTS_LIKE, null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                ContactItem contactItem = new ContactItem();
                contactItem.setId(cursor.getString(cursor.getColumnIndex(LocalDB_Like_contacts.CONTACT_IDENTIFY)));
                contactItem.setLast_name(cursor.getString(cursor.getColumnIndex(LocalDB_Like_contacts.CONTACT_LAST_NAME)));
                contactItem.setLink_pdf(cursor.getString(cursor.getColumnIndex(LocalDB_Like_contacts.CONTACT_LINK_PDF)));
                contactItem.setMisce_rob(cursor.getString(cursor.getColumnIndex(LocalDB_Like_contacts.CONTACT_MISCE_ROB)));
                contactItem.setComment(cursor.getString(cursor.getColumnIndex(LocalDB_Like_contacts.CONTACT_COMMENT)));
                contactItem.setFirst_name(cursor.getString(cursor.getColumnIndex(LocalDB_Like_contacts.CONTACT_FIRST_NAME)));
                list.add(contactItem);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
