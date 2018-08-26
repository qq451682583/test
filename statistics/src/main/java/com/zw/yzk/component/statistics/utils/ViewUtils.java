package com.zw.yzk.component.statistics.utils;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DecorContentParent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

public class ViewUtils {

    public static final int VIEW_TAG = 0xff000001;
    public static final int FRAGMENT_TAG = 0xff000002;
    public static final String CHILD = "Child";

    /**
     * 获取View路径
     *
     * @param target view
     * @return View路径
     */
    public static String getViewPath(View target) {
        StringBuilder builder = new StringBuilder();
        View view = target;
        while (true) {
            if (view == null) {
                break;
            }
            int id = view.getId();
            if (id == android.R.id.content) {
                //添加Activity名称
                builder.insert(0, ".").insert(builder.indexOf("."), getActivityName(target));
                break;
            }
            //添加View层级
            Object tag = view.getTag(VIEW_TAG);
            if (tag == null) {
                builder.insert(0, ".").insert(builder.indexOf("."), view.getClass().getSimpleName());
            } else {
                builder.insert(0, "].").insert(0, (int) tag).insert(0, "[")
                        .insert(builder.indexOf("["), view.getClass().getSimpleName());
            }
            //判断是否是RecyclerView、ViewPager、Spinner等可复用的View
            int index = getAdapterViewIndex(view);
            if (index != -1) {
                builder.insert(0, "].").insert(0, index).insert(0, "[")
                        .insert(builder.indexOf("["), CHILD);
            }
            //如果View在Fragment中则添加Fragment
            Object fragment = view.getTag(FRAGMENT_TAG);
            if (fragment != null) {
                builder.insert(0, ".").insert(builder.indexOf("."), fragment);
            }
            if (view.getParent() instanceof View) {
                view = (View) view.getParent();
            } else {
                break;
            }
        }
        //去掉最后一个点
        return builder.deleteCharAt(builder.length() - 1).toString();
    }

    /**
     * 判断View是否是RecyclerView、ViewPager、Spinner等可复用的View
     *
     * @param view view
     */
    private static int getAdapterViewIndex(View view) {
        if (view.getParent() instanceof View) {
            View parent = (View) view.getParent();
            int index;
            if (parent instanceof Spinner) {
                index = ((Spinner) parent).getPositionForView(view);
            } else if (parent instanceof RecyclerView) {
                index = ((RecyclerView) parent).getChildAdapterPosition(view);
            } else if (parent instanceof ViewPager) {
                index = ((ViewPager) parent).getCurrentItem();
            } else {
                index = -1;
            }
            return index;
        } else {
            return -1;
        }
    }

    /**
     * 给ViewTree的每个元素添加tag,tag为该元素在View中的索引
     *
     * @param view view
     */
    public static void setTagToViewTree(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vp = (ViewGroup) view;
            for (int i = 0; i < vp.getChildCount(); i++) {
                View child = vp.getChildAt(i);
                setTagToView(child, i);
                //再次 调用本身（递归）
                setTagToViewTree(child);
            }
        }
    }

    /**
     * geiView设置Tag
     *
     * @param view view
     * @param tag  tag
     */
    public static void setTagToView(View view, Object tag) {
        view.setTag(VIEW_TAG, tag);
    }

    /**
     * 根据View获取Activity名称
     *
     * @param view view
     * @return Activity名称
     */
    public static String getActivityName(View view) {
        Context context = view.getContext();
        if (context instanceof Activity) {
            return ((Activity) context).getClass().getSimpleName();
        } else if (context instanceof ContextWrapper) {
            Activity activity = getActivityFromContextWrapper((ContextWrapper) context);
            if (activity != null) {
                //如果组件通过Application的context创建的那么获取不到activity
                return activity.getClass().getSimpleName();
            }
        }
        return "";
    }

    /**
     * 根据ContextWrapper获取Activity
     *
     * @param wrapper ContextWrapper
     * @return 被Wrapper装饰的Activity
     */
    private static Activity getActivityFromContextWrapper(ContextWrapper wrapper) {
        Context context = wrapper;
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity) context;
            }
            context = ((ContextWrapper) context).getBaseContext();
        }
        return null;
    }
}
