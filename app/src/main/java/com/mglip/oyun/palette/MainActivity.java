package com.mglip.oyun.palette;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;
import java.io.*;


public class MainActivity extends AppCompatActivity {
    private MyView myView;
    private TextView textView;
    private int textSize = 0;
    private String nowString = "";
    private int nowIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.clear();
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myView.save(nowString,nowIndex);
                loadText();
            }
        });
        Typeface type =Typeface.createFromAsset(this.getAssets(),"font/LD-MONG-BT.ttf");

        myView= (MyView) findViewById(R.id.surfaceView);
        textView = (TextView) this.findViewById(R.id.textView);
        textView.setTypeface(type);
        init();
    }

    /**
     * 初始化文本长度
     */
    private void textSizeInit(){
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text);
        try {
            inputStreamReader = new InputStreamReader(inputStream, "unicode");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        try {
            while ((reader.readLine()) != null) {
                textSize++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadText(){
        InputStreamReader inputStreamReader = null;
        InputStream inputStream = getResources().openRawResource(R.raw.text);
        try {
            inputStreamReader = new InputStreamReader(inputStream, "unicode");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(inputStreamReader);
        StringBuffer sb = new StringBuffer("");
        String line = "";
        try {
            int size = (int)(Math.random()*textSize);
            nowIndex = size;
            while ((line = reader.readLine()) != null) {
                size--;
                if(size==1){
                    sb.append(line);
                    sb.append("\n");
                    nowString = line;
                    textView.setText(sb);
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void init(){
        textSizeInit();
        loadText();
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