package net.lucode.hackware.easyclick;

import android.content.Context;
import android.os.Looper;
import android.widget.Toast;

/***
 * Toast相关
 *
 * author: qqliu
 * created at 2018/5/7
 */
public class ToastUtils {
    private ToastUtils() {
        throw new RuntimeException("should not initialize");
    }

    public static void toast(Context context, int toastId) {
        if (null == context) {
            return;
        }

        toast(context, context.getResources().getString(toastId));
    }

    /**
     * 显示Toast
     */
    public static void toast(Context context, String msg) {
        toast(context, msg, false);
    }

    private static Toast mStaticToastImpl = null;

    public static void toast(final Context context, final String msg, final boolean isLong) {
        if (null == context || null == msg) {
            return;
        }

        //需要在主线程显示toast
        if (Looper.myLooper() != Looper.getMainLooper()) {
            TaskRunner.getUIHandler().post(new Runnable() {
                @Override
                public void run() {
                    toast(context, msg, isLong);
                }
            });
            return;
        }



        if (null == mStaticToastImpl) {
            synchronized (ToastUtils.class) {
                if (null == mStaticToastImpl) {
                    mStaticToastImpl = Toast.makeText(
                            context.getApplicationContext(), "", Toast.LENGTH_SHORT);
                }
            }
        }

        mStaticToastImpl.setText(msg);
        mStaticToastImpl.setDuration(isLong ? Toast.LENGTH_LONG : Toast.LENGTH_SHORT);

        //当前在主线程
        mStaticToastImpl.show();
    }


}
