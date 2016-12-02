package com.mglip.oyun.palette;

import android.content.Context;
import android.content.Intent;
import android.graphics.*;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * @author shianqi@imudges.com
 *         Created by shianqi on 2016/12/1
 */
public class MyView extends SurfaceView implements SurfaceHolder.Callback,View.OnTouchListener {
    private Paint paint=new Paint();
    private Path path=new Path();
    private String linesToString(){
        String s = "";
        for(ArrayList list: lines){
            for(int i=0;i<list.size();i++){
                Point point = (Point) list.get(i);
                s+=point.x;
                s+=",";
                s+=point.y;
                if(i!=list.size()-1){
                    s+=";";
                }
            }
            s+="#";
        }
        return s;
    }
    class Point{
        int x;
        int y;
        Point(int x, int y){
            this.x = x;
            this.y = y;
        }
    }
    private ArrayList<ArrayList> lines = new ArrayList<>();
    private ArrayList<Point> list = new ArrayList<>();

    public MyView(Context context) {
        super(context);        //初始化
        initSurfaceView(context);
    }
    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initSurfaceView(context);
    }
    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initSurfaceView(context);
    }
    void initSurfaceView(Context context){
        getHolder().addCallback(this);
        paint.setStrokeWidth(15);
        paint.setColor(Color.rgb(255, 167, 0));        //画笔大小
        paint.setTextSize(20);
        paint.setStyle(Paint.Style.STROKE);        //设置监听
        setOnTouchListener(this);

    }
    //绘制方法
    public void draw(){
        //锁定画布
        Canvas canvas=getHolder().lockCanvas();
        canvas.drawColor(Color.WHITE);
        canvas.drawPath(path,paint);        //解锁画布
        getHolder().unlockCanvasAndPost(canvas);
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //在surfaceview初始化时调用
        draw();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()){            //按下事件，开始绘制
            case MotionEvent.ACTION_DOWN:                //获取按下点的x,y坐标
                path.moveTo(motionEvent.getX(),motionEvent.getY());                //绘制
                list = new ArrayList<>();
                Point point = new Point((int)(motionEvent.getX()+0.5),(int)(motionEvent.getY()+0.5));
                list.add(point);
                draw();
                break;
            case MotionEvent.ACTION_MOVE:                //移动使用lineto()
                path.lineTo(motionEvent.getX(),motionEvent.getY());
                draw();
                Point point2 = new Point((int)(motionEvent.getX()+0.5),(int)(motionEvent.getY()+0.5));
                list.add(point2);
                break;
            case MotionEvent.ACTION_UP:
                lines.add(list);
        }        //返回true，否则只能响应down事件
        return true;
    }

    //清理画布
    public  void clear(){        //路径重置
        path.reset();        //重新锁定，否则不能再次绘画
        Log.e("lines",""+lines.size());
        lines.clear();
        draw();
    }


    public void save(){
        Word word = new Word();
        File file;
        try {
            String fileName = "oyun/"+System.currentTimeMillis() + ".jpg";
            File dirFirstFolder = new File("/storage/emulated/0/oyun");
            //如果该文件夹不存在，则进行创建
            if(!dirFirstFolder.exists())
            {
                dirFirstFolder.mkdir();//创建文件夹
            }
            Bitmap bitmap = Bitmap.createBitmap(getWidth(),getHeight(),Bitmap.Config.ARGB_8888);
            //初始化画布为白色

            Canvas canvas = new Canvas(bitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bitmap, new Matrix(), paint);

            for(int i=0;i<lines.size();i++){
                Path path2 = new Path();
                for(int j=0;j<lines.get(i).size();j++){
                    Point point = (Point) lines.get(i).get(j);
                    if(j==0){
                        path2.moveTo(point.x,point.y);
                    }else{
                        path2.lineTo(point.x,point.y);
                    }
                }
                canvas.drawPath(path2,paint);
            }



            file = new File(Environment.getExternalStorageDirectory(), fileName);
            OutputStream stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.close();
            // 模拟一个广播，通知系统sdcard被挂载
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MEDIA_MOUNTED);
            intent.setData(Uri.fromFile(Environment.getExternalStorageDirectory()));
            Log.i("保存文件：","success!   "+Environment.getExternalStorageDirectory());
        } catch (Exception e) {
            Log.e("保存文件：","error");
            e.printStackTrace();
        }
        path.reset();        //重新锁定，否则不能再次绘画
        draw();
        word.setWord(linesToString());
        word.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Log.i("bmob:","success");
                }else{
                    Log.e("bmob:","error："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
        lines.clear();
    }
}




