package bpk.light.app_14_disks;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class XClass {
    private Bitmap bitmap;
    private int x;
    private int y;
    private boolean touched;
    Rect rect;
    public XClass(Bitmap bitmap, int x, int y){
        this.bitmap= bitmap;
        this.x= x;
        this.y= y;
    }
    public Bitmap getBitmap(){
        return bitmap;
    }
    public void setBitmap(Bitmap bitmap){
        this.bitmap= bitmap;
    }
    public int getX(){
        return x;
    }
    public void setX(int x){
        this.x= x;
    }
    public int getY(){
        return y;
    }
    public void setY(int y){
        this.y= y;
    }
    public int getX1(){
        return x - (bitmap.getWidth()/2);
    }
    public int getX2(){
        return x + (bitmap.getWidth()/2);
    }
    public int getY1(){
        return y - (bitmap.getHeight()/2);
    }
    public int getY2(){
        return y + (bitmap.getHeight()/2);
    }
    public Rect getRect(){
        return rect = new Rect(getX1(),getY1(),getX2(),getY2());
    }
    public boolean isTouched(){
        return touched;
    }
    public void setTouched(boolean touched){
        this.touched= touched;
    }
    public void draw(Canvas canvas){
        canvas.drawBitmap(bitmap, x-(bitmap.getWidth()/2), y-(bitmap.getHeight()/2),null);
    }
    public void handleActionDown(int eventX, int eventY) {
        if (eventX >= (x - bitmap.getWidth() / 2) && (eventX <= (x + bitmap.getWidth() / 2))) {
            if (eventY >= (y - bitmap.getHeight() / 2) && (y <= (y + bitmap.getHeight() / 2))) {

                setTouched(true);
            } else {
                setTouched(false);
            }
        } else {
            setTouched(false);
        }

    }
 /*   public void update(){
        if(!touched){
            x=(int)(x+speed.getXv());
            y=(int)(y+speed.getYv());
        }
    }*/
}
