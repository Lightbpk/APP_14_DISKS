package bpk.light.app_14_disks;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


public class FiveActivity extends AppCompatActivity {
    TextView textView;
    ImageView imageView;
    //File conf;
    String carBase[][] = new String[100][7];
    String cardiskPath;
    ReadConf config;
    Button btn_selCar, btn_see;
    int id=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five);
        Intent intent = getIntent();
        btn_see = findViewById(R.id.btn_see);
        btn_selCar = findViewById(R.id.btn_selCar);
        textView = findViewById(R.id.textView);
        imageView = findViewById(R.id.imageView);
        String selCarName = intent.getStringExtra("carName");
        Log.d(getString(R.string.LL), "act5 "+selCarName);
        textView.setText(selCarName);
        config =new ReadConf();
        carBase = config.getConfMas();
        cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
        //--------------------select car----------------------------
        for(int i =0; i<99;i++){
            try {
                if (carBase[i][0].equals(selCarName)) {
                    textView.setText(carBase[i][0]);
                    Bitmap myBitmap = BitmapFactory.decodeFile(cardiskPath + "/" + config.getImg(i));
                    imageView.setImageBitmap(myBitmap);
                    id = i;
                    Log.d("LightLog", "Car Found!  i - " + i+" name "+carBase[i][0]+" path "+carBase[i][1]);
                }
                else{Log.d("LightLog", "NOT Found!  i - " + i+" name "+carBase[i][0]+" path "+carBase[i][1]);}
            }catch (Exception e){
                e.printStackTrace();
                //Log.d(getString(R.string.LL), "img sel err i "+i);
            }
        }
        btn_see.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiveActivity.this, SevenActivity.class);
                intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        btn_selCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FiveActivity.this, TwoActivity.class);
                //intent.putExtra("id",id);
                startActivity(intent);
            }
        });
        }

    }

