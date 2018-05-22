package bpk.light.app_14_disks;

import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ReadDiskConf {
    String disksBase[][] = new String[100][10];
    String cardiskPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)+"/cardisk";
    File conf = new File(cardiskPath+"/confDisk.txt");
    public ReadDiskConf(){
        Log.d("LightLog", "ReadConfDisk created");
        try {
            BufferedReader br = new BufferedReader(new FileReader(conf));
            String str = "";
            Log.d("LightLog", "Start read conf disk");
            int n =0;
            while ((str = br.readLine()) != null) {
                Log.d("LightLog", str);
                String strs[]= str.split(" ");
                for(int i =0; i <strs.length; i++){
                    disksBase[n][i]= strs[i];
                    Log.d("LightLog", ""+disksBase[n][i]);
                }
                n++;
            }
            Log.d("LightLog", "MASSIVE Disks GETED");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d("LightLog", "Disks File not Found");

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("LightLog", "IO ERR");
        }
        //return carBase;
    }
    public String[] getSizes(int diskID){
        String[] sizes = new String[disksBase[diskID].length-1];
        for (int i=0;i<sizes.length;i++){
            sizes[i]= disksBase[diskID][i+1];
        }
        return sizes;
    }
    public String getDiskName(int diskID){
        return disksBase[diskID][0].substring(0,disksBase[diskID][0].length()-1);
    }
    public int getLen(){
        return disksBase.length;
    }
}
