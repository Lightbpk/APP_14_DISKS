package bpk.light.app_14_disks;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class NineActivity extends Activity {
    Rect rectBitmap, rectDisp;
    String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
    String[] sizes;
    Button btn_save_img;
    String diskname;
    ArrayAdapter<String> adapter;
    ReadConf readConf = new ReadConf();
    RelativeLayout rlayDisks;
    ListView listDisk;
    ConstraintLayout layConst;
    TextView textModelDisk;
    Bitmap carBG, x;
    DrawView dw;
    EditText editSearchDisk;
    Matrix m;
    SeekBar seekR;
    Bitmap diskBitmapScale, diskBitmap;
    int r;
    int id,diskID = 0;
    int w1x=50, w1y=50, w2x=200, w2y=200;
    int wrapContent = LinearLayout.LayoutParams.WRAP_CONTENT;
    XClass x1,x2, leftDisk, rightDisk;
    Paint mPaint = new Paint();
    ReadDiskConf diskConf;
    final ArrayList<String> diskSearch = new ArrayList<>();

    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_THRESHOLD_VELOCITY = 200;
    GestureDetector gdt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nine);
        gdt = new GestureDetector(new GestureListener());
        Intent intent = getIntent();
        diskConf = new ReadDiskConf();
        sizes = diskConf.getSizes(diskID);
        id = intent.getIntExtra("id",0);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        rectDisp = new Rect(0,0,size.x,size.y);
        rlayDisks = findViewById(R.id.rlayDisk);
        layConst = findViewById(R.id.layConst);
        listDisk = findViewById(R.id.ListSearchDisk);
        btn_save_img = findViewById(R.id.btn_saveWithDisk);
        editSearchDisk = findViewById(R.id.editSearchDisk);
        textModelDisk = findViewById(R.id.textModelDisk);
        adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, diskSearch);
        listDisk.setAdapter(adapter);
        seekR = findViewById(R.id.seekR);
        seekR.setMax(sizes.length);
        r = Integer.parseInt(sizes[seekR.getProgress()]);
        RelativeLayout.LayoutParams lParams = new RelativeLayout.LayoutParams(
                wrapContent, wrapContent);
        rlayDisks.addView(dw = new DrawView(this),lParams);
        diskname = diskConf.getDiskName(0);
        diskBitmap = BitmapFactory.decodeFile(cardiskPath+"/"+diskname+".png");
        m = new Matrix();
        m.postScale( (float) (r*0.0625),(float)(r*0.0625));
        editSearchDisk.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                for(int i =0 ;i<diskConf.getLen(); i++) {
                    Log.d(getString(R.string.LL),"i ="+i);
                    try {
                        if (((editSearchDisk.getText()).toString()).equalsIgnoreCase(diskConf.getDiskName(i))) {
                            if(!(diskSearch.contains(editSearchDisk.getText().toString()))) {
                                diskSearch.add(0, editSearchDisk.getText().toString());
                                diskID = i;
                                Log.d(getString(R.string.LL), "add");
                                break;
                            }
                        }else{
                            diskSearch.clear();
                            Log.d(getString(R.string.LL), "cleared");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                adapter.notifyDataSetChanged();
            }
        });
        listDisk.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView = (TextView) view;
                String carName = textView.getText().toString();
                listDisk(diskID);
            }
        });
        dw.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gdt.onTouchEvent(event);
                return true;
            }
        });
        seekR.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                try {
                    r = Integer.parseInt(sizes[seekR.getProgress()]);
                    Log.d(getString(R.string.LL),"sizes index "+seekR.getProgress());
                    Log.d(getString(R.string.LL),"r "+r);
                    m = new Matrix();
                    m.postScale( (float) (r*0.0625),(float)(r*0.0625));
                    diskBitmapScale = Bitmap.createBitmap(diskBitmap,0,0, diskBitmap.getWidth(), diskBitmap.getHeight(),m,true);
                    leftDisk.setBitmap(diskBitmapScale);
                    rightDisk.setBitmap(diskBitmapScale);
                    dw.invalidate();
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }
    private class GestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            if(e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.d(getString(R.string.LL),"To the Left");
                if(diskID < diskConf.getLen()){
                    diskID++;
                }
                try {
                    listDisk(diskID);
                }catch (Exception e){
                    Log.d(getString(R.string.LL),"Disk ID "+diskID);
                }
                return false; // справа налево
            }  else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                Log.d(getString(R.string.LL),"To the Right");
                if(diskID>0){
                    diskID--;
                }
                try {
                    listDisk(diskID);
                }catch (Exception e){
                    Log.d(getString(R.string.LL),"Disk ID "+diskID);
                }
                return false; // слева направо
            }
            else Log.d(getString(R.string.LL),"Err swipe");
            Log.d(getString(R.string.LL),"shnopt");
            return false;
        }
    }
    public void listDisk(int diskID){
        diskname = diskConf.getDiskName(diskID);
        diskBitmap = BitmapFactory.decodeFile(cardiskPath+"/"+diskname+".png");
        sizes = diskConf.getSizes(diskID);
        m = new Matrix();
        r = Integer.parseInt(sizes[0]);
        seekR.setProgress(0);
        diskBitmapScale = Bitmap.createBitmap(diskBitmap,0,0, diskBitmap.getWidth(), diskBitmap.getHeight(),m,true);
        leftDisk.setBitmap(diskBitmapScale);
        rightDisk.setBitmap(diskBitmapScale);
        textModelDisk.setText(diskname);
        seekR.setMax(sizes.length);
        dw.invalidate();
    }
    class DrawView extends View  {
        public DrawView(Context context) {
            super(context);
            setDrawingCacheEnabled(true);
            //x = BitmapFactory.decodeResource(getResources(),R.drawable.x);
            if(id == 77){
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = false;
                options.inPreferredConfig = Bitmap.Config.RGB_565;
                options.inDither = true;
                carBG = BitmapFactory.decodeFile(cardiskPath+"/CarPhoto.jpg", options);
            }else {
                carBG = BitmapFactory.decodeFile(cardiskPath + "/" + readConf.getImg(id));
            }
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

      /*  @Override
        public boolean onTouchEvent(MotionEvent event){
            return gdt.onTouchEvent(event);
        }*/
        protected void onDraw(Canvas canvas){
            mPaint.setStyle(Paint.Style.FILL);
            canvas.drawBitmap(carBG,rectBitmap,rectDisp,mPaint);
            //x1.draw(canvas);
            //x2.draw(canvas);
            leftDisk.draw(canvas);
            rightDisk.draw(canvas);
            btn_save_img.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        getDrawingCache().compress(Bitmap.CompressFormat.JPEG, 100, new FileOutputStream(new File("/mnt/sdcard/pictures/CarWithDisk.jpg")));
                    } catch (Exception e) {
                        Log.e(getString(R.string.LL), e.toString());
                    }
                }
            });

        }

    }
}
