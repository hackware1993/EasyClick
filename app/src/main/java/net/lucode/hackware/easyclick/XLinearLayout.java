package net.lucode.hackware.easyclick;

import android.content.Context;
import android.graphics.PointF;
import android.support.annotation.Keep;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

/**
 * fbchen2 2019.01.01
 */
public class XLinearLayout extends LinearLayout {
    private View mEnhanceTouchChild;

    public XLinearLayout(Context context) {
        super(context);
    }

    public XLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public XLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            mEnhanceTouchChild = null;
        }

        boolean handled;
        if (mEnhanceTouchChild != null) {
            // TODO fbchen2 映射位置
            float xOffset = ev.getX() - mEnhanceTouchChild.getLeft();
            float yOffset = ev.getY() - mEnhanceTouchChild.getTop();
            ev.offsetLocation(-xOffset, -yOffset);
            handled = super.dispatchTouchEvent(ev);
            ev.offsetLocation(xOffset, yOffset);
        } else {
            handled = super.dispatchTouchEvent(ev);
        }

        if (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_CANCEL) {
            mEnhanceTouchChild = null;
        }

        return handled;
    }

    @Keep
    protected boolean isTransformedTouchPointInView(float x, float y, View child, PointF outLocalPoint) {
        x += getScrollX() - child.getLeft();
        y += getScrollY() - child.getTop();

        boolean isInView;
        ClickPadding clickPadding = (ClickPadding) child.getTag(R.id.click_padding);
        if (clickPadding != null) {
            isInView = pointInView(child, x, y, clickPadding.getLeftClickPadding(),
                    clickPadding.getTopClickPadding(), clickPadding.getRightClickPadding(),
                    clickPadding.getBottomClickPadding());
            if (isInView) {
                mEnhanceTouchChild = child;
            }
        } else {
            isInView = pointInView(child, x, y, 0, 0, 0, 0);
        }

        return isInView;
    }

    private static boolean pointInView(View view, float localX, float localY, float leftPadding,
                                       float topPadding, float rightPadding, float bottomPadding) {
        return localX >= -leftPadding && localY >= -topPadding && localX < ((view.getRight() - view.getLeft()) + rightPadding) &&
                localY < ((view.getBottom() - view.getTop()) + bottomPadding);

    }
}
