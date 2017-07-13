package jp.postclass.chordtraining.activity;

import android.annotation.SuppressLint;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.TextView;

import java.io.IOException;

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;
import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Globals;
import jp.postclass.chordtraining.common.UtCommon;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class BookReadActivity extends AppCompatActivity {

    private WebView mContentView;
    private View mControlsView;
    private boolean mVisible;
    private final Handler mHideHandler = new Handler();
    private static final int UI_ANIMATION_DELAY = 300;

    private int soundPoolElements[];
    private SoundPool soundPool;
    private MediaPlayer mediaPlayer;
    private boolean started = false;
    private ImageButton pauseSoundButton;
    private TextView speedText;
    private float speed = 1.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mContentView = (WebView) findViewById(R.id.bookReadWebView);
        mControlsView = findViewById(R.id.bookReadFullscreenContentControls);
        pauseSoundButton = (ImageButton) findViewById(R.id.bookReadPauseSoundButton);
        speedText = (TextView) findViewById(R.id.bookReadSppedText);

        try (AssetFileDescriptor fd = getAssets().openFd(Globals.bookSoundUrl)) {
            this.mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(), fd.getLength());
            mediaPlayer.prepare();
        } catch (IOException e) {
            throw new ApplicationRuntimeException(e);
        }


//        mContentView.getSettings().setJavaScriptEnabled(true);
        mContentView.getSettings().setBuiltInZoomControls(true);
        mContentView.getSettings().setDisplayZoomControls(false);

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mContentView.loadUrl(Globals.bookReadUrl);
        mContentView.setOnTouchListener(new OnToucheReferenceListener());

        mContentView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                mediaPlayer.start();
                started = true;
            }
        });

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        hide();
    }

    public void onClickBookReadRestartSound(View view) {
        mediaPlayer.seekTo(0);
        mediaPlayer.start();
        pauseSoundButton.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
        started = true;
    }

    public void onClickBookReadPauseSound(View view) {

        if (started) {
            mediaPlayer.pause();
            pauseSoundButton.setImageResource(R.drawable.ic_play_circle_outline_white_24dp);
            started = false;
        } else {
            mediaPlayer.start();
            pauseSoundButton.setImageResource(R.drawable.ic_pause_circle_outline_white_24dp);
            started = true;
        }
    }

    public void onClickBookReadSpeedUpSound(View view) {
        this.speed += 0.1;
        PlaybackParams params = new PlaybackParams();
        params.setSpeed(this.speed);
        mediaPlayer.setPlaybackParams(params);
        speedText.setText(UtCommon.decimalFormat(this.speed, "#0.00"));
    }

    public void onClickBookReadSpeedDownSound(View view) {
        this.speed -= 0.1;
        PlaybackParams params = new PlaybackParams();
        params.setSpeed(this.speed);
        mediaPlayer.setPlaybackParams(params);
        speedText.setText(UtCommon.decimalFormat(this.speed, "#0.00"));
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.stop();
        mediaPlayer.release();
        super.onDestroy();
    }



    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
//            ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.show();
//            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

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

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
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
