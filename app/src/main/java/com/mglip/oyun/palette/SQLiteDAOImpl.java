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
        db.execSQL("insert into t_cache (str,word) values(?,?)", new Object[] { word.getStr(), word.getWord()});
        db.close();// 记得关闭数据库操作
    }

    public void delete(Integer id) {// 删除纪录
        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
        db.execSQL("delete from t_cache where id=?", new Object[] { id.toString() });
        db.close();
    }
//
//    public void update(TUsers tusers) {// 修改纪录
//        SQLiteDatabase db = dbOpenHandler.getWritableDatabase();
//        db.execSQL("update t_cache set username=?,pass=? where" + " id=?", new Object[] { tusers.getUsername(), tusers.getPass(), tusers.getId() });
//        db.close();
//    }
//
//    public TUsers find(Integer id) {// 根据ID查找纪录
//        TUsers tusers = null;
//        SQLiteDatabase db = dbOpenHandler.getReadableDatabase();
//        // 用游标Cursor接收从数据库检索到的数据
//        Cursor cursor = db.rawQuery("select * from t_cache where id=?", new String[] { id.toString() });
//        if (cursor.moveToFirst()) {// 依次取出数据
//            tusers = new TUsers();
//            tusers.setId(cursor.getInt(cursor.getColumnIndex("id")));
//            tusers.setUsername(cursor.getString(cursor.getColumnIndex("username")));
//            tusers.setPass(cursor.getString(cursor.getColumnIndex("pass")));
//
//        }
//        db.close();
//        return tusers;
//    }
//
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
            word.setId(cursor.getInt(cursor.getColumnIndex("id")));
            word.setStr(cursor.getString(cursor.getColumnIndex("str")));
            word.setWord(cursor.getString(cursor.getColumnIndex("word")));
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
