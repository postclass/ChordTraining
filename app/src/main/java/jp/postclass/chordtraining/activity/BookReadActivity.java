package jp.postclass.chordtraining.activity;

import android.annotation.SuppressLint;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Globals;
import jp.postclass.chordtraining.common.UtCommon;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class BookReadActivity extends AppCompatActivity {

    private WebView mContentView;

    private int soundPoolElements[];
    private SoundPool soundPool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_read);

        mContentView = (WebView) findViewById(R.id.bookReadWebView);
        mContentView.getSettings().setJavaScriptEnabled(true);
        mContentView.getSettings().setBuiltInZoomControls(true);
        mContentView.getSettings().setDisplayZoomControls(false);

        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        mContentView.loadUrl(Globals.bookReadUrl);


        AudioAttributes soundPoolAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        this.soundPoolElements = new int[1];

        this.soundPool = new SoundPool.Builder()
                .setAudioAttributes(soundPoolAttributes)
                .setMaxStreams(1)
                .build();

        this.soundPoolElements[0] =
                UtCommon.loadSound(
                        soundPool,
                        getResources().getAssets(),
                        Globals.bookSoundUrl);


        mContentView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                soundPool.play(soundPoolElements[0], 1, 1, 0, 0, 1);
            }
        });

    }
}
