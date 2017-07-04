package jp.postclass.chordtraining.common;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ActivityHelper {

    protected AppCompatActivity activity;

    public ActivityHelper(AppCompatActivity activity) {
        this.activity = activity;
    }

    @Nullable
    public View findViewById(@IdRes int id) {
        return this.activity.findViewById(id);
    }

    @NonNull
    public final String getString(@StringRes int resId) {
        return this.activity.getString(resId);
    }

    @NonNull
    public final String getString(@StringRes int resId, Object... formatArgs) {
        return this.activity.getString(resId, formatArgs);
    }

    public Context getApplicationContext() {
        return this.activity.getApplicationContext();
    }

    public Resources getResources() {
        return this.activity.getResources();
    }

    public Resources.Theme getTheme() {
        return this.activity.getTheme();
    }

    @Nullable
    public ActionBar getSupportActionBar() {
        return this.activity.getSupportActionBar();
    }

    public Object getSystemService(@NonNull String name) {
        return this.activity.getSystemService(name);
    }

    public String getPackageName() {
        return this.activity.getPackageName();
    }
}
