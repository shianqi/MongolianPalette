package com.mglip.oyun.palette;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import android.util.Log;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.mglip.oyun.palette", appContext.getPackageName());
    }

    @Test
    public void addWord(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        SQLiteDAOImpl p = new SQLiteDAOImpl(appContext);
        Word word = new Word();
        word.setStr("test");
        word.setWord("12312ej129j012jd0182hd0j0j8d2012d12d1231d212e12ds12e12sd");
        p.save(word);
    }

    @Test
    public void findAllWord(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        SQLiteDAOImpl p = new SQLiteDAOImpl(appContext);
        List<Word> list =p.findAll();

        for(Word word: list){
            Log.e("number",""+word.getId()+" "+word.getStr());
        }
    }

    @Test
    public void deleteWord(){
        Context appContext = InstrumentationRegistry.getTargetContext();
        SQLiteDAOImpl p = new SQLiteDAOImpl(appContext);
        p.delete(2);
    }
}
