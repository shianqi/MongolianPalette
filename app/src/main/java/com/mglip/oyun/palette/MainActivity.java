package com.mglip.oyun.palette;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TypefaceSpan;
import android.util.Log;
import android.view.*;
import android.widget.TextView;
import cn.bmob.v3.Bmob;

import java.io.*;


public class MainActivity extends AppCompatActivity {
    private MyView myView;
    private TextView textView;
    private SpannableString msp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //初始化Bmob
        Bmob.initialize(this, "8f30f830ee035896610ddd472c56eb8e");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                myView.clear();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.save();
            }
        });
        myView= (MyView) findViewById(R.id.surfaceView);
        textView = (TextView) this.findViewById(R.id.textView);
        init();
    }


    private void init(){
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text);
        int size = 0;
        try {
            inputStreamReader = new InputStreamReader(inputStream, "unicode");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line = "";
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
                size++;
                sb.append("\n");
                if(size==2){
                    Log.i("textSize",""+size+" "+line);
                    msp = new SpannableString(line);
                    msp.setSpan(new TypefaceSpan("raw/ldmongbt.ttf"), 0, line.length()-1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    textView.setText("");
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("textSize",""+size+" "+line);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
