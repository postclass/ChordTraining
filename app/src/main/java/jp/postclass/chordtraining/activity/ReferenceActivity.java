package jp.postclass.chordtraining.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import jp.postclass.chordtraining.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ReferenceActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private WebView mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };

    private OnToucheReferenceListener onToucheReferenceListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_reference);

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = (WebView) findViewById(R.id.viewerWebView);
        mContentView.getSettings().setJavaScriptEnabled(true);
        mContentView.getSettings().setBuiltInZoomControls(true);
        mContentView.getSettings().setDisplayZoomControls(false);
//        mContentView.getSettings().setAllowContentAccess(true);
//        mContentView.getSettings().setAllowFileAccess(true);
//        mContentView.getSettings().setAllowFileAccessFromFileURLs(true);
//        mContentView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        onToucheReferenceListener = new OnToucheReferenceListener();

        onClickReferenceDegreeAbsolute(null);

        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.reference_key_button).setOnTouchListener(mDelayHideTouchListener);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void onClickReferenceKey(View view) {
        mContentView.loadUrl("file:///android_asset/abcjs.html");
//        mContentView.loadUrl("file:///android_asset/reference/reference_key.html");
        mContentView.setOnTouchListener(onToucheReferenceListener);
    }

    public void onClickReferenceDegreeAbsolute(View view) {
        mContentView.loadUrl("file:///android_asset/reference/reference_degree_absolute.html");
        mContentView.setOnTouchListener(onToucheReferenceListener);
    }


    public class OnToucheReferenceListener implements View.OnTouchListener {

        public final static int FINGER_DRAGGING = 2;
        public final static int FINGER_UNDEFINED = 3;

        private int fingerState = FINGER_UNDEFINED;

        private Float beforeX;
        private Float beforeY;


        @Override
        public boolean onTouch(View v, MotionEvent event) {

            int action = event.getAction() & MotionEvent.ACTION_MASK;

            if (action == MotionEvent.ACTION_MOVE) {

                // 最初はmoveしていないことにする
                if (beforeX == null || beforeY == null) {
                    beforeX = event.getX();
                    beforeY = event.getY();
                    fingerState = FINGER_UNDEFINED;
                    return false;
                }

                // 移動していないときはmoveしていないことにする
                if (beforeX == event.getX() && beforeY == event.getY()) {
                    fingerState = FINGER_UNDEFINED;
                    return false;
                }
            }

//            AlertDialog.Builder dlg = new AlertDialog.Builder(ReferenceActivity.this);
//            dlg.setMessage("" + action);
//            dlg.create().show();

            switch (action) {

                case MotionEvent.ACTION_DOWN:
                    fingerState = FINGER_UNDEFINED;
                    beforeX = null;
                    beforeY = null;
                    break;

                case MotionEvent.ACTION_UP:
                    if(fingerState != FINGER_DRAGGING) {
                        toggle();
                    }

                    fingerState = FINGER_UNDEFINED;
                    beforeX = null;
                    beforeY = null;
                    break;

                case MotionEvent.ACTION_MOVE:
                    fingerState = FINGER_DRAGGING;
                    beforeX = event.getX();
                    beforeY = event.getY();
                    break;

                default:
                    fingerState = FINGER_UNDEFINED;

            }
            return false;
        }
    }
}
