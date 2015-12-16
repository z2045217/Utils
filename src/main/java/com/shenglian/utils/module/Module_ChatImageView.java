package com.shenglian.utils.module;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.shenglian.utils.R;

/**
 * Created by admin on 2015/11/17.
 */
public class Module_ChatImageView extends ImageView {

    int direction;          //1 = direction right      0 = direction left
    float pixels;
    float arrow_down;

    //region Module_ChatImageView
    public Module_ChatImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        ResetView(context.obtainStyledAttributes(attrs, R.styleable.Module_ChatImageView));
    }
    //endregion

    //region
    public void ResetView(TypedArray Parm) {
        direction = Parm.getInteger(R.styleable.Module_ChatImageView_chat_direction, -1);
        pixels = Parm.getDimension(R.styleable.Module_ChatImageView_chat_pixels,-1);
        arrow_down = Parm.getDimension(R.styleable.Module_ChatImageView_chat_arrow_down,-1);
    }
    //endregion

    //region clipit
    public Bitmap clipit(Bitmap bitmapimg,int direct,float pixels) {

        Bitmap output = Bitmap.createBitmap(bitmapimg.getWidth(),
                bitmapimg.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmapimg.getWidth(),
                bitmapimg.getHeight());

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        float roundPx = pixels;
        Path path = new Path();

        if(direct == 0) {
            Rect rectC = new Rect(0, 0, bitmapimg.getWidth()-15, bitmapimg.getHeight());
            RectF rectF = new RectF(rectC);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            path.moveTo(bitmapimg.getWidth() - 15, arrow_down);
            path.lineTo(bitmapimg.getWidth(), arrow_down+10);
            path.lineTo(bitmapimg.getWidth()-15, arrow_down+20);
            path.lineTo(bitmapimg.getWidth() - 15, arrow_down);
        }
        if(direct == 1) {
            Rect rectC = new Rect(15, 0, bitmapimg.getWidth(), bitmapimg.getHeight());
            RectF rectF = new RectF(rectC);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

            path.moveTo(15, arrow_down);
            path.lineTo(0, arrow_down+10);
            path.lineTo(15, arrow_down+20);
            path.lineTo(15, arrow_down);
        }

        canvas.drawPath(path,paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmapimg, rect, rect, paint);
        return output;
    }
    //endregion

    //region setImageBitmap Override
    @Override
    public void setImageBitmap(Bitmap bm) {
        if(direction!=1||direction!=0||pixels<0||bm==null)
            super.setImageBitmap(bm);
        super.setImageBitmap(clipit(bm,direction,pixels));
    }
    //endregion
}
