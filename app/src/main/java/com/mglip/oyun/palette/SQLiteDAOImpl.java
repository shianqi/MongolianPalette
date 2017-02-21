package com.mglip.oyun.palette;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2016/12/4
 */
public class SQLiteDAOImpl {
    private DBOpenHandler dbOpenHandler;

    public SQLiteDAOImpl(Context context) {
        this.dbOpenHandler = new DBOpenHandler(context, "dbtest.db", null, 1);
    }

    public void save(Word word) {// 插入记录
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();// 取得数据库操作
        db.execSQL("insert into t_cache (str,word,wordIndex,phoneId) values(?,?,?,?)", new Object[] { word.getStr(), word.getWord(), word.getWordIndex(), word.getPhoneId()});
        db.close();// 记得关闭数据库操作
    }

    public void delete(String id) {// 删除纪录
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.execSQL("delete from t_cache where word=?", new Object[] { id });
        db.close();
    }

    public List<Word> findAll() {// 查询所有记录
        List<Word> lists = new ArrayList<Word>();
        Word word = null;
        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
        // Cursor cursor=db.rawQuery("select * from t_users limit ?,?", new
        // String[]{offset.toString(),maxLength.toString()});
        // //这里支持类型MYSQL的limit分页操作

        Cursor cursor = db.rawQuery("select * from t_cache ", null);
        while (cursor.moveToNext()) {
            word = new Word();
            word.setWordIndex(cursor.getInt(cursor.getColumnIndex("wordIndex")));
            word.setStr(cursor.getString(cursor.getColumnIndex("str")));
            word.setWord(cursor.getString(cursor.getColumnIndex("word")));
            word.setPhoneId(cursor.getString(cursor.getColumnIndex("phoneId")));
            lists.add(word);
        }
        db.close();
        return lists;
    }
//
//    public long getCount() {//统计所有记录数
//        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
//        Cursor cursor = db.rawQuery("select count(*) from t_cache ", null);
//        cursor.moveToFirst();
//        db.close();
//        return cursor.getLong(0);
//    }

}
