package com.hencoder.hencoderpracticedraw4.practice;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.hencoder.hencoderpracticedraw4.R;

public class Practice12CameraRotateFixedView extends View {
    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Bitmap bitmap;
    Point point1 = new Point(200, 200);
    Point point2 = new Point(600, 200);

    public Practice12CameraRotateFixedView(Context context) {
        super(context);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Practice12CameraRotateFixedView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.maps);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Camera camera = new Camera();
        canvas.save();
        camera.save();
        camera.rotateX(30);
        //把图形从原点移动回到原来的位置，canvas操作是倒序，这一步要放在上面
        canvas.translate(point1.x+bitmap.getWidth()/2,point1.y+bitmap.getHeight()/2);
        camera.applyToCanvas(canvas);
        //把中心点移到到原点，需要先将canvas移动中心点再旋转，但是canvas的操作顺序是倒叙的，所以translate要写在camera旋转的下面
        canvas.translate(-(point1.x+bitmap.getWidth()/2),-(point1.y+bitmap.getHeight()/2));
        camera.restore();
        canvas.drawBitmap(bitmap, point1.x, point1.y, paint);
        canvas.restore();
        //canvas.drawBitmap(bitmap, point2.x, point2.y, paint);

        /**
         * 这里难点就是在画图的执行顺序上，根据canvas倒序原则，程序从49行图形移到原点开始，然后就是47行的camera的映射（注意camera在映射以前已经旋转过了）
         * 然后旋转完成后，执行46行代码，将画图移动至初始位置
         */
        canvas.save();
        camera.save();
        camera.rotateY(30);
        canvas.translate(point2.x+bitmap.getWidth()/2,point2.y+bitmap.getHeight()/2);//图形移动至初始位置
        camera.applyToCanvas(canvas);
        canvas.translate(-(point2.x+bitmap.getWidth()/2),-(point2.y+bitmap.getHeight()/2));//图形移动至原点
        camera.restore();
        canvas.drawBitmap(bitmap, point2.x, point2.y, paint);
        canvas.restore();
    }
}