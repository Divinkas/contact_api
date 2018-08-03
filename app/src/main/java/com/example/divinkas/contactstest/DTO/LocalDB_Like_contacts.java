package com.example.divinkas.contactstest.DTO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDB_Like_contacts  extends SQLiteOpenHelper {
    public SQLiteDatabase database;

    private static final int VERSION = 7;
    private static final String DB_NAME = "contacts_like";

    public static final String TABLE_CONTACTS_LIKE = "contacts_like_table";

    public static final String CONTACT_IDENTIFY = "identify";
    public static final String CONTACT_FIRST_NAME = "f_name";
    public static final String CONTACT_LAST_NAME = "last_name";
    public static final String CONTACT_POSADA = "posada";
    public static final String CONTACT_LINK_PDF = "link_pos";
    public static final String CONTACT_MISCE_ROB = "misce";
    public static final String CONTACT_COMMENT = "comment";


    public LocalDB_Like_contacts(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createTables(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_CONTACTS_LIKE);
        onCreate(db);
    }

    private void createTables(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_CONTACTS_LIKE + " ("+ CONTACT_IDENTIFY + " text, "
                + CONTACT_FIRST_NAME + " text, " + CONTACT_LAST_NAME + " text, " + CONTACT_POSADA + " text, "
                + CONTACT_LINK_PDF + " text, " + CONTACT_MISCE_ROB + " text, " + CONTACT_COMMENT +" text)");
    }

    private boolean isNotice(SQLiteDatabase db, String tableName){
        return false;
    }

}