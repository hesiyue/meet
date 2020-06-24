package com.example.im.data.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.im.app.IMApplication
import org.jetbrains.anko.db.*

class DatabaseHelper(
    ctx: Context = IMApplication.instance
) : ManagedSQLiteOpenHelper(ctx, NAME, null, VERSION) {


    companion object {
        val NAME = "im.db"
        val VERSION = 1
    }


    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(Tables.NAME,true,
        Tables.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
        Tables.CONTACT to TEXT)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(Tables.NAME,true)
        onCreate(db)

    }
}