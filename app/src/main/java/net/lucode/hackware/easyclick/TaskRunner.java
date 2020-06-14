package net.lucode.hackware.easyclick;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;

/**
 * 在主线程中执行runnable
 * Created by fbchen2 on 2016/4/7.
 */
public class TaskRunner {
    private static final String TAG = "TaskRunner";
    private Handler mUIHandler;
    private Handler mBackHandler;

    private TaskRunner() {
        mUIHandler = new Handler(Looper.getMainLooper());
    }

    private static class SingletonHolder {
        static TaskRunner sRunner = new TaskRunner();
    }

    public static synchronized Handler getUIHandler() {
        return SingletonHolder.sRunner.mUIHandler;
    }

    public static synchronized Handler getBackHandler() {
        Handler backHandler = SingletonHolder.sRunner.mBackHandler;
        if (null == backHandler) {
            HandlerThread handlerThread = new HandlerThread(TAG);
            handlerThread.start();
            backHandler = new Handler(handlerThread.getLooper());
            SingletonHolder.sRunner.mBackHandler = backHandler;
        }

        return backHandler;
    }
}
