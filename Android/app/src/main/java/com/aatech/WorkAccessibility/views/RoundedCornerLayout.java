// This layout will display its children with rounded corners
// It works with Glide image library placeholders and animations
// It assumes your background is a solid color. If you need the corners to be truly transparent,
// this solution will not work for you.
// https://gist.github.com/grennis/da9f86870c45f3b8ae00912edb72e868

package com.aatech.WorkAccessibility.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.aatech.WorkAccessibility.R;

public class RoundedCornerLayout extends RelativeLayout {
    private Bitmap maskBitmap;
    private Paint paint;
    private float cornerRadius;
    private int outerdBg;

    public RoundedCornerLayout(Context context) {
        super(context);
        init(context, null, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public RoundedCornerLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);


        TypedArray a = (attrs==null)
                ? context.obtainStyledAttributes(R.styleable.RoundedCornerLayout)
                : context.obtainStyledAttributes(attrs, R.styleable.RoundedCornerLayout);

        outerdBg = a.getColor(R.styleable.RoundedCornerLayout_outerBg, 0xFF000000);
        cornerRadius = a.getDimensionPixelSize(R.styleable.RoundedCornerLayout_cornerRadius, 0); // 24 * getResources().getDisplayMetrics().density

        a.recycle();
    }

    public void reInit() {
        maskBitmap = null;
        this.invalidate();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        if(cornerRadius==0) {
            cornerRadius = canvas.getHeight() / 2;
        }

        if (maskBitmap == null) {
            maskBitmap = createMask(canvas.getWidth(), canvas.getHeight());
        }

        canvas.drawBitmap(maskBitmap, 0f, 0f, paint);
    }

    private Bitmap createMask(int width, int height) {
        Bitmap mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(mask);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(outerdBg);
        canvas.drawRect(0, 0, width, height, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawRoundRect(new RectF(0, 0, width, height), cornerRadius, cornerRadius, paint);

        return mask;
    }
}
