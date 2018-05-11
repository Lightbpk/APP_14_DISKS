package bpk.light.app_14_disks;

import android.os.Environment;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadConf {
    String carBase[][] = new String[100][7];
    String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
    File conf = new File(cardiskPath+"/conf.txt");
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

}
