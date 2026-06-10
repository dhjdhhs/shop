package com.campusshop.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

/**
 * 公共工具类 — 提供全员可复用的方法
 */
public class CommonUtils {

    /**
     * 页面跳转
     */
    public static void goTo(Context context, Class<?> target) {
        context.startActivity(new Intent(context, target));
    }

    /**
     * 页面跳转并清空返回栈
     */
    public static void goToAndClear(Activity activity, Class<?> target) {
        Intent intent = new Intent(activity, target);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        activity.startActivity(intent);
        activity.finish();
    }

    /**
     * Toast 提示
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 播放本地提示音效（满足多媒体要求）
     * 可在「加入购物车」「登录成功」「提交订单」等场景调用
     */
    public static void playSound(Context context, int rawResId) {
        try {
            MediaPlayer mp = MediaPlayer.create(context, rawResId);
            if (mp != null) {
                mp.start();
                mp.setOnCompletionListener(MediaPlayer::release);
            }
        } catch (Exception e) {
            // 音效播放失败不影响主流程
        }
    }
}
