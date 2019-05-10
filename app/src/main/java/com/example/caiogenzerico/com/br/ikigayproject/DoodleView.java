package com.example.caiogenzerico.com.br.ikigayproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class DoodleView extends View {
    private static final int TOUCH_TOLERANCE = 10;
    private static final int AUTO_VALUE = 13;

    private Bitmap bitmap;
    private Canvas canvasBitmap;
    private Paint paintScreen;
    private Paint paintLine;
    private Map<Integer, Path> pathMap = new HashMap<>();
    private Map<Integer, Point> previousPointMap = new HashMap<>();
    private int alternative;
    private int circleX;
    private int circleY;
    private static final int RADIUS = 300;
    private int erasetask =0;

    private Region reg1;
    private Region reg2;
    private Region reg3;
    private Region reg4;
    private boolean region1;
    private boolean region2;
    private boolean region3;
    private boolean region4;
    private int RADIUS2;

    public DoodleView(Context context, AttributeSet set) {
        super(context, set);
        paintScreen = new Paint();
        paintLine = new Paint();
        paintLine.setAntiAlias(true);
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);
        paintLine.setStrokeCap(Paint.Cap.ROUND);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvasBitmap = new Canvas(bitmap);
        bitmap.eraseColor(Color.WHITE);
        canvasBitmap.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        if(erasetask == 0) {
            circleLove(canvasBitmap, paintLine);
            circleDoWell(canvasBitmap, paintLine);
            circlePayFor(canvasBitmap, paintLine);
            circleWorldNeed(canvasBitmap, paintLine);
            erasetask = erasetask +1;
        }else{
            region1= false;
            region2 = false;
            region3 = false;
            region4 = false;
            RADIUS2 = (int) (getWidth()* 0.3);
            erasetask = erasetask +1;

        }

    }

    public void clear() {
        pathMap.clear();
        previousPointMap.clear();
        bitmap.eraseColor(Color.WHITE);
        invalidate();
    }

    public void setDrawingColor(int color) {
        this.paintLine.setColor(color);
    }

    public int getDrawingColor() {
        return this.paintLine.getColor();
    }

    public void setLineWidth(int width) {
        this.paintLine.setStrokeWidth(width);
    }

    public int getLineWidth() {
        return (int) paintLine.getStrokeWidth();
    }


    public void erase() {
        clear();
        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        alternative =0;
    }



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(bitmap, 0, 0, paintScreen);
            // Random random = new Random();
            //int a = 255;//   random.nextInt(256);
            //int r = 255;//   random.nextInt(256);
            //int g = 255;//   random.nextInt(256);
            //int b = 255;//   random.nextInt(256);
            // paintLine.setColor(Color.argb(a, r, g, b));
        for (Integer key : pathMap.keySet()) {
            canvas.drawPath(pathMap.get(key), paintLine);
        }

    }

    private void circleLove(Canvas canvasBitmap, Paint paintLine) {
        randomColor(paintLine);
        paintLine.setStyle(Paint.Style.FILL);
        circleX = getWidth() / 2;
        circleY = getHeight() / 4;
        canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
    }

    private void circleDoWell(Canvas canvasBitmap, Paint paintLine) {
        randomColor(paintLine);
        paintLine.setStyle(Paint.Style.FILL);
        circleX = getWidth() -300;
        circleY = getHeight()/2;
        canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
    }

    private void circlePayFor(Canvas canvasBitmap, Paint paintLine) {

        randomColor(paintLine);
        paintLine.setStyle(Paint.Style.FILL);
        circleX = getWidth() / 2;
        circleY = getHeight() -475;
        canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
    }

    private void circleWorldNeed(Canvas canvasBitmap, Paint paintLine) {

        randomColor(paintLine);
        paintLine.setStyle(Paint.Style.FILL);
        circleX = getWidth() -775;
        circleY = getHeight() / 2;
        canvasBitmap.drawCircle(circleX, circleY, RADIUS, paintLine);
    }
    private void randomColor(Paint PaintLine){
        Random random = new Random();
        int a = random.nextInt(256);
        int r = random.nextInt(255);
        int g = random.nextInt(255);
        int b = random.nextInt(255);

        paintLine.setColor(Color.argb(a, r, g, b));
    }



    private boolean itsCircleLove(MotionEvent event){
        circleX = getWidth() / 2;
        circleY = getHeight() / 4;
        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
            return true;
        return false;
    }
    private boolean itsCircleDowell(MotionEvent event){
        circleX = getWidth() -300;
        circleY = getHeight()/2;
        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
            return true;
        return false;
    }
    private boolean itsCirclePayfor(MotionEvent event){
        circleX = getWidth() / 2;
        circleY = getHeight() -475;
        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
            return true;
        return false;
    }
    private boolean itsCircleWorldNeed(MotionEvent event){
        circleX = getWidth() -775;
        circleY = getHeight() / 2;
        if(Math.sqrt(Math.pow(event.getX() - circleX, 2) + Math.pow(event.getY() - circleY, 2)) <= RADIUS)
            return true;
        return false;
    }

    private boolean checkPoint(MotionEvent event) {
        if(erasetask <= 1) {
            if (itsCircleLove(event) && itsCircleDowell(event)) {
                alternative = 6;
                return true;
            } else if (itsCircleLove(event) && itsCircleWorldNeed(event)) {
                alternative = 4;
                return true;
            } else if (itsCirclePayfor(event) && itsCircleWorldNeed(event)) {
                alternative = 10;
                return true;
            } else if (itsCirclePayfor(event) && itsCircleDowell(event)) {
                alternative = 12;
                return true;
            } else if (itsCirclePayfor(event)) {
                alternative = 7;
                return true;
            } else if (itsCircleDowell(event)) {
                alternative = 5;
                return true;
            } else if (itsCircleWorldNeed(event)) {
                alternative = 3;
                return true;
            } else if (itsCircleLove(event)) {
                alternative = 1;
                return true;
            }

        }else if (erasetask >= 1){
        return false;
        }
        return false;
    }

    private void WhatIs(int altern){
        if(altern == 12 ){
            Toast.makeText(getContext(),"intersecão profissão",Toast.LENGTH_SHORT).show();
        }
        else if(altern == 10 ){
            Toast.makeText(getContext(),"intersecão talento",Toast.LENGTH_SHORT).show();
        }
        else if(altern == 6 ){
            Toast.makeText(getContext(),"intersecão paixão",Toast.LENGTH_SHORT).show();
        }
        else if(altern == 4 ){
            Toast.makeText(getContext(),"intersecão missao",Toast.LENGTH_SHORT).show();
        }
        else if(altern == 7 ){
            Toast.makeText(getContext()," pago para fazer",Toast.LENGTH_SHORT).show();
        }
        else if(altern == 5 ){
            Toast.makeText(getContext(),"Bom em fazer",Toast.LENGTH_SHORT).show();
        }
        else if(altern == 3 ){
            Toast.makeText(getContext(),"Bom para o mundo",Toast.LENGTH_SHORT).show();
        }
        else if(altern == 1 ){
            Toast.makeText(getContext(),"Ama fazer",Toast.LENGTH_SHORT).show();
        }
        else {
            erase();
        }
    }
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        int actionIndex = event.getActionIndex();

        if(checkPoint(event)){
                WhatIs(alternative);
        }else if(erasetask >=1){
         if( action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_POINTER_DOWN){

            touchStarted(
                    event.getX(actionIndex),

                    event.getY(actionIndex),
                    event.getPointerId(actionIndex)
            );


        }
        if(action == MotionEvent.ACTION_UP ||
                action == MotionEvent.ACTION_POINTER_UP) {

            touchEnded (event.getPointerId(actionIndex));

        } else {

            touchMoved(event);

        }

        invalidate();

    } else {


    }

 return true;

}

    public boolean onTouch(MotionEvent event) {
        int action = event.getActionMasked();
        int actionIndex = event.getActionIndex();

        if (action == MotionEvent.ACTION_DOWN ||
                action == MotionEvent.ACTION_DOWN) {
                touchStarted (
                event.getX(actionIndex),
                event.getY(actionIndex),
                event.getPointerId(actionIndex));
        } else if (action == MotionEvent.ACTION_UP
                || action == MotionEvent.ACTION_POINTER_UP) {
                touchEnded (event.getPointerId(actionIndex));
        } else {
            touchMoved (event);
        }
        invalidate();
        return true;
    }

    private void touchStarted (float x, float y, int lineID){
    Path path;
    Point point;
    if (pathMap.containsKey(lineID)){
    path = pathMap.get(lineID);
    //limpa todas as linhas desse path/
    //não quer dizer que o que está na tela será apagado
    path.reset();
    point = previousPointMap.get(lineID);
    }else{
    path = new Path ();
    pathMap.put(lineID, path);
    point = new Point ();
    previousPointMap.put (lineID, point);
    }
    path.moveTo(x,  y);
    point.x = (int)x;
    point.y = (int)y;
    }
    private void touchMoved (MotionEvent event){
    for (int i = 0; i < event.getPointerCount(); i++){
    int pointerID = event.getPointerId(i);
    int pointerIndex = event.findPointerIndex(pointerID);
    if (pathMap.containsKey(pointerID)){
    float newX = event.getX(pointerIndex);
    float newY = event.getY(pointerIndex);
    Path path = pathMap.get(pointerID);
    Point point = previousPointMap.get(pointerID);
    float deltaX = Math.abs(newX - point.x);
    float deltaY = Math.abs(newY - point.y);
    if (deltaX >= TOUCH_TOLERANCE || deltaY >= TOUCH_TOLERANCE){
    path.quadTo(point.x,
    point.y,
    (newX + point.x) / 2,
    (newY + point.y) / 2);
    point.x = (int) newX;
    point.y = (int) newY;
    }
    }
    }
    }
    private void touchEnded (int lineID) {
        paintLine.setStyle(Paint.Style.FILL);
        paintLine.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));
        if (region3) {
            Path path = pathMap.get(lineID);
            canvasBitmap.drawPath(path, paintLine);
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            reg4 = new Region();
            reg4.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            region4 = true;
            checkIntersection();
            path.reset();
        } else if (region2) {
            Path path = pathMap.get(lineID);
            canvasBitmap.drawPath(path, paintLine);
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            reg3 = new Region();
            reg3.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            region3 = true;
            path.reset();
        } else if (region1) {
            Path path = pathMap.get(lineID);
            canvasBitmap.drawPath(path, paintLine);
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            reg2 = new Region();
            reg2.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            region2 = true;
            path.reset();
        } else {
            Path path = pathMap.get(lineID);
            canvasBitmap.drawPath(path, paintLine);
            RectF rectF = new RectF();
            path.computeBounds(rectF, true);
            reg1 = new Region();
            reg1.setPath(path, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
            region1 = true;
            path.reset();
        }
        paintLine.setStyle(Paint.Style.STROKE);
    }
    private void checkIntersection() {
        if ((!reg1.quickReject(reg2) && reg1.op(reg2, Region.Op.INTERSECT))
                && (!reg1.quickReject(reg3) && reg1.op(reg3, Region.Op.INTERSECT))
                && (!reg1.quickReject(reg4) && reg1.op(reg4, Region.Op.INTERSECT))
                && (!reg1.quickReject(reg4) && reg1.op(reg4, Region.Op.INTERSECT))
                && (!reg2.quickReject(reg3) && reg2.op(reg3, Region.Op.INTERSECT))
                && (!reg2.quickReject(reg4) && reg2.op(reg4, Region.Op.INTERSECT))
                && (!reg3.quickReject(reg4) && reg3.op(reg4, Region.Op.INTERSECT))
        ){
            Toast.makeText(getContext(),
                    "get Intersection!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(),
                    R.string.No_intersect,
                    Toast.LENGTH_SHORT).show();
            erase();
        }
    }
}
