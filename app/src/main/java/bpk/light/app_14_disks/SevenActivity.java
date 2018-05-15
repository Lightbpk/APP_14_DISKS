package bpk.light.app_14_disks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

public class SevenActivity extends Activity {
    int id;
    int w1x=50, w1y=50, w2x=200, w2y=200;
    //String carBase[][] = new String[100][7];
    Bitmap carBG, x;
    String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
    Paint mPaint = new Paint();
    Rect rectBitmap, rectDisp;
    XClass x1,x2;
    ReadConf readConf;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        readConf = new ReadConf();
        //carBase = readConf.getConfMas();
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        rectDisp = new Rect(0,0,size.x,size.y);
        setContentView(new DrawView(this));
    }
    class DrawView extends View {
        public DrawView(Context context) {
            super(context);
            //x = BitmapFactory.decodeResource(getResources(),R.drawable.x);
            carBG  = BitmapFactory.decodeFile(cardiskPath + "/" + readConf.getImg(id));
            Log.d("LightLog"," carpath "+cardiskPath);
            Log.d("LightLog"," img  "+readConf.getImg(id));
            rectBitmap = new Rect(0,0,carBG.getWidth(),carBG.getHeight());
            w1x = Integer.parseInt(readConf.getW1X(id));
            w1y = Integer.parseInt(readConf.getW1Y(id));
            w2x = Integer.parseInt(readConf.getW2X(id));
            w2y = Integer.parseInt(readConf.getW2Y(id));
            x1 = new XClass(BitmapFactory.decodeResource(getResources(), R.drawable.x), w1x, w1y);
            x2 = new XClass(BitmapFactory.decodeResource(getResources(), R.drawable.x), w2x, w2y);
        }
        @Override
        public boolean onTouchEvent(MotionEvent event){
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                x1.handleActionDown((int) event.getX(), (int) event.getY());


                if (event.getY() > getHeight() - 50) {
                    //thread.setRunning(false);
                    ((Activity) getContext()).finish();
                } else {
                    Log.d(getString(R.string.LL), "Coords: x=" + event.getX() + ",y=" + event.getY());
                }
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {

                if (x1.isTouched()) {
                    x1.setX((int) event.getX());
                    x1.setY((int) event.getY());
                    readConf.setW1X(id,(int)event.getX());
                    readConf.setW1Y(id,(int)event.getY());
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {

                if (x1.isTouched()) {
                    invalidate();
                    x1.setTouched(false);
                }
            }
            //------------------------------double two---------------------
            if (event.getAction() == MotionEvent.ACTION_DOWN) {

                x2.handleActionDown((int) event.getX(), (int) event.getY());


                if (event.getY() > getHeight() - 50) {
                    //thread.setRunning(false);
                    ((Activity) getContext()).finish();
                } else {
                    Log.d(getString(R.string.LL), "Coords: x=" + event.getX() + ",y=" + event.getY());
                }
            }
            if (event.getAction() == MotionEvent.ACTION_MOVE) {

                if (x2.isTouched()) {

                    x2.setX((int) event.getX());
                    x2.setY((int) event.getY());
                    readConf.setW2X(id,(int)event.getX());
                    readConf.setW2Y(id,(int)event.getY());
                }
            }
            if (event.getAction() == MotionEvent.ACTION_UP) {

                if (x2.isTouched()) {
                    invalidate();
                    x2.setTouched(false);
                }
            }
            return true;
        }
        protected void onDraw(Canvas canvas){
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawBitmap(carBG,rectBitmap,rectDisp,mPaint);
            x1.draw(canvas);
            x2.draw(canvas);
        }
    }
}
