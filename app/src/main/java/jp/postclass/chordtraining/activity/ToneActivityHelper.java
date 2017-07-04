package jp.postclass.chordtraining.activity;


import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.UtCommon;

public class ToneActivityHelper extends Qa1ActivityHelper {

    private int soundPoolElements[];
    private SoundPool soundPool;
    private Map<Integer, String> noTonenameMap = new HashMap<>();

    private String keymode;
    private boolean includeHigh;
    private boolean includeMiddle;
    private boolean includeLow;

    public ToneActivityHelper(Qa1Activity activity) {
        super(activity);
    }

    @Override
    protected void init() {
        this.keymode = preferences.getString(Constants.PREF_TONE_KEYMODE, Constants.KEYMODE_MAJOR);
        this.includeHigh = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_HIGH, true);
        this.includeMiddle = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_MIDDLE, true);
        this.includeLow = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_LOW, true);

        this.highestScorePrefKeySuffix = "_" + this.keymode + this.includeHigh + this.includeMiddle + this.includeLow + this.countDownSecond;
        this.highestScore = preferences.getInt(Constants.DATA_TONE_HIGHESTSCORE + this.highestScorePrefKeySuffix, 0);
        this.highestScoreDate = preferences.getString(Constants.DATA_TONE_HIGHESTSCORE_DATE + this.highestScorePrefKeySuffix, "");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.tone_title, this.keymode));

        AudioAttributes soundPoolAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        int tmpToneNo = 0;

        if (Constants.KEYMODE_MAJOR.equals(this.keymode)) {
            if (this.includeHigh) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Cs);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Fs);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_B);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_E);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_A);
            }
            if (this.includeMiddle) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_D);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_G);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_C);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_F);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Bb);
            }
            if (this.includeLow) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Eb);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Ab);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Db);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Gb);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Cb);
            }
        }

        if (Constants.KEYMODE_MINOR.equals(this.keymode)) {
            if (this.includeHigh) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Asm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Dsm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Gsm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Csm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Fsm);
            }
            if (this.includeMiddle) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Bm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Em);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Am);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Dm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Gm);
            }
            if (this.includeLow) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Cm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Fm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Bbm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Ebm);
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Abm);
            }
        }

        this.maxIdx = tmpToneNo;
        this.soundPoolElements = new int[maxIdx];

        this.soundPool = new SoundPool.Builder()
                .setAudioAttributes(soundPoolAttributes)
                .setMaxStreams(2)
                .build();

        for (int i = minIdx; i < maxIdx; i++) {
            String toneName = this.noTonenameMap.get(i);
            this.soundPoolElements[i] =
                    UtCommon.loadSound(
                            soundPool,
                            getResources().getAssets(),
                            "tone/" + this.keymode +"/tone_" + toneName + ".mid");
        }

        stopCountdown();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.soundPool.release();
        this.soundPool = null;
    }



    @Override
    public void onClickStart(View view) {

        if (this.started) {
            this.soundPool.play(this.soundPoolElements[this.collectNo], 1, 1, 0, 0, 1);

        } else {
            this.started = true;
            this.currentScore = 0;
            nextQuestion();
            ((Button) view).setText(R.string.qa1_listen_collect);

            this.countDownNow = this.countDownSecond;

            TimerTask countDownTimerTask = new TimerTask() {
                @Override
                public void run() {
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            countDownNow--;
                            textViewCountDown.setText(String.valueOf(countDownNow));

                            if (countDownNow <= 0) {
                                stopCountdown();

                                if (highestScore < currentScore) {
                                    highestScore = currentScore;
                                    highestScoreDate = UtCommon.getCurrentDateString(Constants.DATE_FORMAT_YYYYslMMslDD);
                                    SharedPreferences.Editor preferences = ToneActivityHelper.super.preferences.edit();
                                    preferences.putInt(Constants.DATA_TONE_HIGHESTSCORE + highestScorePrefKeySuffix, highestScore);
                                    preferences.putString(Constants.DATA_TONE_HIGHESTSCORE_DATE + highestScorePrefKeySuffix, highestScoreDate);
                                    preferences.commit();
                                }

                                popupResult();
                            }
                        }
                    });
                }
            };

            this.countDownTimer = new Timer(true);
            this.countDownTimer.schedule(countDownTimerTask, 1000, 1000);
            textViewCountDown.setText(String.valueOf(countDownNow));
        }
    }

    @Override
    void onClickSound1(View view) {
        this.soundPool.play(this.soundPoolElements[this.answerNo1], 1, 1, 0, 0, 1);
    }

    @Override
    void onClickSound2(View view) {
        this.soundPool.play(this.soundPoolElements[this.answerNo2], 1, 1, 0, 0, 1);
    }

    @Override
    void onClickSound3(View view) {
        this.soundPool.play(this.soundPoolElements[this.answerNo3], 1, 1, 0, 0, 1);
    }

    public void nextQuestion() {
        this.collectNo = getRandomNo(this.collectNo);
        String toneString = this.noTonenameMap.get(this.collectNo);

        webView.loadUrl("file:///android_asset/tone/" + this.keymode + "/tone_" + toneString + "_svg001.svg");

        this.soundPool.play(this.soundPoolElements[this.collectNo], 1, 1, 0, 0, 1);

        List<Integer> answerList = new ArrayList<>();
        answerList.add(this.collectNo);
        answerList.add(getRandomNo(this.collectNo));
        answerList.add(getRandomNo(this.collectNo, answerList.get(1)));
        Collections.shuffle(answerList);

        this.answerNo1 = answerList.get(0);
        this.answerNo2 = answerList.get(1);
        this.answerNo3 = answerList.get(2);

        btnAnswer1.setText(getResources().getIdentifier("key_name_" + this.noTonenameMap.get(this.answerNo1), "string", getPackageName()));
        btnAnswer2.setText(getResources().getIdentifier("key_name_" + this.noTonenameMap.get(this.answerNo2), "string", getPackageName()));
        btnAnswer3.setText(getResources().getIdentifier("key_name_" + this.noTonenameMap.get(this.answerNo3), "string", getPackageName()));

        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnSound1.setEnabled(true);
        btnSound2.setEnabled(true);
        btnSound3.setEnabled(true);
    }

}
