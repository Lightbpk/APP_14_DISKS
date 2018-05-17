package bpk.light.app_14_disks;

import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.Log;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import static android.content.Context.MODE_PRIVATE;

public class ReadConf {
    String carBase[][] = new String[100][7];
    String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
    File conf = new File(cardiskPath+"/conf.txt");
    FileOutputStream FOS;
    public ReadConf(){
        Log.d("LightLog", "ReadConf created");
        try {
            BufferedReader br = new BufferedReader(new FileReader(conf));
            String str = "";
            Log.d("LightLog", "Start read conf");
            int n =0;
            while ((str = br.readLine()) != null) {
                Log.d("LightLog", str);
                String strs[]= str.split(",");
                for(int i =0; i <strs.length; i++){
                    carBase[n][i]= strs[i];
                    Log.d("LightLog", ""+carBase[n][i]);
                }
                n++;
            }
            Log.d("LightLog", "MASSIVE GETED");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("LightLog", "File not Found");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LightLog", "IO ERR");
        }
        //return carBase;
    }
    public String[][] getConfMas(){
        return carBase;
    }
    public String getName(int id){
        return carBase[id][0];
    }
    public String getImg(int id){
        return carBase[id][1];
    }
    public String getW1X(int id){
        return carBase[id][2];
    }
    public String getW1Y(int id){
        return carBase[id][3];
    }
    public String getW2X(int id){
        return carBase[id][4];
    }
    public String getW2Y(int id){
        return carBase[id][5];
    }

    public void setW1X(int id, int w1x){
        carBase[id][2]= ""+w1x;
    }
    public void setW1Y(int id,int w1y){
        carBase[id][3]= ""+w1y;
    }
    public void setW2X(int id,int w2x){
        carBase[id][4]= ""+w2x;
    }
    public void setW2Y(int id,int w2y){
        carBase[id][5]= ""+w2y;
    }
    public void WriteConf(){
        try {
            FileWriter writer = new FileWriter(conf,false);
            for(int i=0; i<carBase.length ;i++) {
                Log.d("LightLog"," i "+i);
                for(int j =0 ; j<carBase[i].length;j++){
                    Log.d("LightLog"," j "+j);
                    if(carBase[i][j]!= null) {
                        writer.write(carBase[i][j]);
                        if (j < (carBase[i].length) - 1) writer.append(',');
                    }
                }
                writer.append('\n');
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
