package js.technology.session.utils.indicators;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.wang.avi.Indicator;

import java.util.ArrayList;

public class BallPulseSyncIndicator extends Indicator {
    float[] translateYFloats = new float[4];

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float circleSpacing = 4;
        float radius = (getWidth() - circleSpacing * 2) / 6;
        float x = getWidth() / 2 - (radius * 2 + circleSpacing);
        for (int i = 0; i < 4; i++) {
            canvas.save();
            float translateX = x + (radius * 2) * i + circleSpacing * i;
            canvas.translate(translateX, translateYFloats[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    @Override
    public ArrayList<ValueAnimator> onCreateAnimators() {
        ArrayList<ValueAnimator> animators = new ArrayList<>();
        float circleSpacing = 4;
        float radius = (getWidth() - circleSpacing * 2) / 6;
        int[] delays = new int[]{70, 140, 210, 280};
        for (int i = 0; i < 4; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(getHeight() / 2, getHeight() / 2 - radius * 2, getHeight() / 2);
            scaleAnim.setDuration(600);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            addUpdateListener(scaleAnim, animation -> {
                translateYFloats[index] = (float) animation.getAnimatedValue();
                postInvalidate();
            });
            animators.add(scaleAnim);
        }
        return animators;
    }
}
