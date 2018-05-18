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
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class NineActivity extends Activity {
    Rect rectBitmap, rectDisp;
    String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
    ReadConf readConf = new ReadConf();
    RelativeLayout rlayDisks;
    ConstraintLayout layConst;
    Bitmap carBG, x;
    int id;
    int w1x=50, w1y=50, w2x=200, w2y=200;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    XClass x1,x2, leftDisk, rightDisk;
    Paint mPaint = new Paint();
    ReadDiskConf diskConf;
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    final GestureDetector gdt = new GestureDetector(new GestureListener());
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nine);
        Intent intent = getIntent();
        id = intent.getIntExtra("id",0);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        rectDisp = new Rect(0,0,size.x,size.y);
        rlayDisks = findViewById(R.id.rlayDisk);
        layConst = findViewById(R.id.layConst);
        RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
                wrapContent, wrapContent);
        rlayDisks.addView(new DrawView(this),lParams);
        diskConf = new ReadDiskConf();

        layConst.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.d(getString(R.string.LL),"To the Left");
                Bitmap diskBitmap = BitmapFactory.decodeFile(cardiskPath+"/m1.png");
                leftDisk.setBitmap(diskBitmap);
                rightDisk.setBitmap(diskBitmap);
                return false; // справа налево
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.d(getString(R.string.LL),"To the Right");
                return false; // слева направо
            }
            else Log.d(getString(R.string.LL),"Err swipe");
            Log.d(getString(R.string.LL),"shnopt");
            return false;
        }
    }
    class DrawView extends View  {
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
                leftDisk = new XClass(BitmapFactory.decodeFile(cardiskPath + "/m0.png"), w1x, w1y);
                rightDisk = new XClass(BitmapFactory.decodeFile(cardiskPath + "/m0.png"), w2x, w2y);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event){
            /*if (event.getAction() == MotionEvent.ACTION_DOWN) {

                x1.handleActionDown((int) event.getX(), (int) event.getY());


                if (event.getY() > getHeight() - 50) {
                    //thread.setRunning(false);
                    //((Activity) getContext()).finish();
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
            }*/
            return true;
        }
        protected void onDraw(Canvas canvas){
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawBitmap(carBG,rectBitmap,rectDisp,mPaint);
            //x1.draw(canvas);
            //x2.draw(canvas);
            leftDisk.draw(canvas);
            rightDisk.draw(canvas);
        }

    }
}
