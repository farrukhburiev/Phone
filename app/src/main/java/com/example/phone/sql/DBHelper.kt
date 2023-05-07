package com.example.phone.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.phone.model.Contact

class DBHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "ContactDatabase"

        private const val TABLE_CONTACTS = "ContactTable"
        private const val ID = "id"
        private const val NAME = "name"
        private const val PHOME_NUMBER = "phone_number"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val contacts =
            ("CREATE TABLE" + TABLE_CONTACTS + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + NAME + " TEXT, " + PHOME_NUMBER + " TEXT" + ")")

        db?.execSQL(contacts)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_CONTACTS")
        onCreate(db)
    }

    fun addContact(contact: Contact):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(NAME,contact.name)
        contentValues.put(ID,contact.id)
        contentValues.put(PHOME_NUMBER,contact.number)

        val succes = db.insert(TABLE_CONTACTS,null,contentValues)
        db.close()
        return succes
    }

    fun contacts():ArrayList    <Contact>{
        val selectQuery = "SELECT * FROM $TABLE_CONTACTS"
        val contactList:ArrayList<Contact> = ArrayList()
        val db = this.readableDatabase

        var cursor:Cursor

        cursor = db.rawQuery(selectQuery,null)

        var id :Int
        var name:String
        var number:String

        if (cursor.moveToFirst()){
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                number = cursor.getString(cursor.getColumnIndex("phone_number"))

                val item = Contact(id = id,name = name,number = number)
                contactList.add(item)
            }
                while (cursor.moveToNext())
        }
        return contactList
    }
}