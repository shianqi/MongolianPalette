package com.mglip.oyun.palette;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2016/12/4
 */
public class DBOpenHandler extends SQLiteOpenHelper{
    public DBOpenHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 覆写onCreate方法，当数据库创建时就用SQL命令创建一个表
        // 创建一个t_users表，id主键，自动增长，字符类型的username和pass;
        db.execSQL("create table t_cache(id integer primary key autoincrement,str varchar(200),word varchar(10000) )");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
