package net.lucode.hackware.easyclick;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

public final class EasyClick {

    private EasyClick() {
    }

    public static void install(Activity activity) {
        if (activity == null) {
            return;
        }

        LayoutInflater.Factory factory = activity.getLayoutInflater().getFactory();
        if (factory != null) {
            return;
        }

        LayoutInflaterCompat.setFactory2(activity.getLayoutInflater(), new LayoutInflater.Factory2() {
            @Override
            public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {

                if (name.equals("FrameLayout")) {
                    return new XFrameLayout(context, attrs);
                }

                if (name.equals("LinearLayout")) {
                    return new XLinearLayout(context, attrs);
                }

                // 代理创建全部 View
                View view = null;

                if (null == view) {
                    //代理创建View
                    view = createView(context, name, attrs);
                }

                if (view == null) {
                    return null;
                }

                TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EasyClick);
                int clickPadding = typedArray.getDimensionPixelOffset(R.styleable.EasyClick_clickPadding, 0);
                int leftClickPadding = typedArray.getDimensionPixelOffset(R.styleable.EasyClick_leftClickPadding, clickPadding);
                int topClickPadding = typedArray.getDimensionPixelOffset(R.styleable.EasyClick_topClickPadding, clickPadding);
                int rightClickPadding = typedArray.getDimensionPixelOffset(R.styleable.EasyClick_rightClickPadding, clickPadding);
                int bottomClickPadding = typedArray.getDimensionPixelOffset(R.styleable.EasyClick_bottomClickPadding, clickPadding);
                typedArray.recycle();
                if (leftClickPadding > 0 || topClickPadding > 0 || rightClickPadding > 0 || bottomClickPadding > 0) {
                    ClickPadding padding = new ClickPadding();
                    padding.setLeftClickPadding(leftClickPadding);
                    padding.setTopClickPadding(topClickPadding);
                    padding.setRightClickPadding(rightClickPadding);
                    padding.setBottomClickPadding(bottomClickPadding);
                    view.setTag(R.id.click_padding, padding);
                }

                return view;
            }

            @Override
            public View onCreateView(String name, Context context, AttributeSet attrs) {
                return null;
            }
        });
    }

    private static View createView(Context context, String name, AttributeSet attrs) {
        View view = null;
        try {
            LayoutInflater inflater = LayoutInflater.from(context);
            setupInflater(inflater, context);
            if (-1 == name.indexOf('.')) {
                if ("View".equals(name)
                        || "ViewStub".equals(name)
                        || "ViewGroup".equals(name)) {
                    view = inflater.createView(
                            name, "android.view.", attrs);
                }
                if (view == null) {
                    view = inflater.createView(
                            name, "android.widget.", attrs);
                }
                if (view == null) {
                    view = inflater.createView(
                            name, "android.webkit.", attrs);
                }
            } else {
                view = inflater.createView(name, null, attrs);
            }
        } catch (Exception ex) {
            view = null;
        }
        return view;
    }

    private static void setupInflater(LayoutInflater inflater, Context context) {
        //异常，处理context为空，一般不会发生
        Context inflaterContext = inflater.getContext();
        if (null == inflaterContext) {
            ReflectUtils.setFieldValueOpt(inflater, "mContext", context);
        }

        //设置mConstructorArgs的第一个参数context
        Object[] constructorArgs = ReflectUtils.getFieldValueOpt(inflater, "mConstructorArgs");
        if (null == constructorArgs || constructorArgs.length < 2) {
            //异常，一般不会发生
            constructorArgs = new Object[2];
            ReflectUtils.setFieldValueOpt(inflater, "mConstructorArgs", constructorArgs);
        }

        //如果mConstructorArgs的第一个参数为空，则设置为mContext
        if (null == constructorArgs[0]) {
            constructorArgs[0] = inflater.getContext();
        }
    }
}
