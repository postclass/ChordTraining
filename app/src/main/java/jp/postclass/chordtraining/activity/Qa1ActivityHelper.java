package jp.postclass.chordtraining.activity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.ActivityHelper;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.UtCommon;

public abstract class Qa1ActivityHelper extends ActivityHelper {

    protected SharedPreferences preferences;

    protected Button btnStart;
    protected Button btnAnswer1;
    protected Button btnAnswer2;
    protected Button btnAnswer3;
    protected ImageButton btnSound1;
    protected ImageButton btnSound2;
    protected ImageButton btnSound3;
    protected WebView webView;
    protected TextView textViewCountDown;

    protected ImageView incorrectImageView;
    protected ImageView correctImageView;
    protected WindowManager.LayoutParams correctImageViewLayoutParams;

    protected boolean started = false;

    protected Timer countDownTimer;
    protected int countDownNow;
    protected int countDownSecond;

    protected int currentScore;
    protected int highestScore;
    protected String highestScoreDate;
    protected String highestScorePrefKeySuffix;

    protected int collectNo = -1;
    protected int answerNo1 = -1;
    protected int answerNo2 = -1;
    protected int answerNo3 = -1;

    protected int minIdx = 0;
    protected int maxIdx = 0;

    public Qa1ActivityHelper(Qa1Activity activity) {
        super(activity);

        this.preferences = PreferenceManager.getDefaultSharedPreferences(this.activity);


        this.btnStart = (Button) findViewById(R.id.qa1ButtonStart);
        this.btnAnswer1 = (Button) findViewById(R.id.qa1ButtonAns1);
        this.btnAnswer2 = (Button) findViewById(R.id.qa1ButtonAns2);
        this.btnAnswer3 = (Button) findViewById(R.id.qa1ButtonAns3);
        this.btnSound1 = (ImageButton) findViewById(R.id.qa1ImageButtonSound1);
        this.btnSound2 = (ImageButton) findViewById(R.id.qa1ImageButtonSound2);
        this.btnSound3 = (ImageButton) findViewById(R.id.qa1ImageButtonSound3);
        this.webView = (WebView) findViewById(R.id.qa1WebView);
        this.textViewCountDown = (TextView) findViewById(R.id.qa1TextViewCountDown);

        webView.setInitialScale(500);

        this.correctImageView = new ImageView(getApplicationContext());
        this.correctImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_correct, getTheme()));
        this.correctImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        this.incorrectImageView = new ImageView(getApplicationContext());
        this.incorrectImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_incorrect, getTheme()));
        this.incorrectImageView.setScaleType(ImageView.ScaleType.FIT_CENTER);

        this.correctImageViewLayoutParams = new WindowManager.LayoutParams();
        this.correctImageViewLayoutParams.gravity = Gravity.TOP | Gravity.LEFT;
        this.correctImageViewLayoutParams.x       = 0;
        this.correctImageViewLayoutParams.y       = 0;
        this.correctImageViewLayoutParams.width   = WindowManager.LayoutParams.MATCH_PARENT;
        this.correctImageViewLayoutParams.height  = WindowManager.LayoutParams.MATCH_PARENT;
        this.correctImageViewLayoutParams.flags   = WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN;
        this.correctImageViewLayoutParams.format  = PixelFormat.TRANSPARENT;
    }

    public void onResume() {
        this.countDownSecond = Integer.parseInt(preferences.getString(Constants.PREF_COMMON_COUNTDOWN_SECOND, "30"));
        init();
    }

    public void onPause() {
        if (this.countDownTimer != null) {
            this.countDownTimer.cancel();
            this.countDownTimer.purge();
            this.countDownTimer = null;
        }
    }

//    public void onDestroy() {
//    }

    protected void stopCountdown() {

        this.started = false;

        if (this.countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer.purge();
            countDownTimer = null;
        }

        this.btnStart.setText(getString(R.string.qa1_start_button_default));
        this.btnAnswer1.setEnabled(false);
        this.btnAnswer2.setEnabled(false);
        this.btnAnswer3.setEnabled(false);
        this.btnSound1.setEnabled(false);
        this.btnSound2.setEnabled(false);
        this.btnSound3.setEnabled(false);
        this.webView.loadUrl("about:blank");
        this.textViewCountDown.setText(String.valueOf(this.countDownSecond));
    }

    protected void popupResult() {
        AlertDialog.Builder dlg = new AlertDialog.Builder(this.activity);
        dlg.setTitle(R.string.qa1_result_title);
        dlg.setMessage(getString(R.string.qa1_result_message, this.currentScore, this.highestScore, this.highestScoreDate));
        dlg.setPositiveButton(
                R.string.common_positive_button,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) { }
                }
        );
        dlg.setCancelable(false);
        dlg.create().show();
    }

    protected void showCorrectMark() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(correctImageView, correctImageViewLayoutParams);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                        windowManager.removeView(correctImageView);
                    }
                });
            }
        };

        new Timer(true).schedule(timerTask, 500);
    }

    protected void showIncorrectMark() {
        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        windowManager.addView(incorrectImageView, correctImageViewLayoutParams);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
                        windowManager.removeView(incorrectImageView);
                    }
                });
            }
        };

        new Timer(true).schedule(timerTask, 500);
    }

    protected int getRandomNo() {
        return UtCommon.getRandomInt(minIdx, maxIdx);
    }

    protected int getRandomNo(int... exceptNumbers) {

        int maxLoop = 100;
        int currentLoop = 0;

        outerloop:while (true) {

            int randomNumber = getRandomNo();
            if (currentLoop++ > maxLoop) {
                return randomNumber;
            }

            for (int i : exceptNumbers) {
                if (randomNumber == i) {
                    continue outerloop;
                }
            }

            return randomNumber;
        }
    }


    protected void judge(View view, int answerNo) {

        if (this.collectNo == answerNo) {
            this.currentScore += 100;
            showCorrectMark();
            nextQuestion();

        } else {
            this.currentScore -= 50;
            showIncorrectMark();
        }
    }

    public void onClickAnswer1(View view) {
        judge(view, this.answerNo1);
    }

    public void onClickAnswer2(View view) {
        judge(view, this.answerNo2);
    }

    public void onClickAnswer3(View view) {
        judge(view, this.answerNo3);
    }


    abstract void init();

    abstract void onClickStart(View view);

    abstract void onClickSound1(View view);

    abstract void onClickSound2(View view);

    abstract void onClickSound3(View view);

    abstract void nextQuestion();





}
