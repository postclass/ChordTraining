package jp.postclass.chordtraining.activity;


import android.content.SharedPreferences;
import android.content.res.AssetFileDescriptor;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import java.io.FileDescriptor;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;
import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.UtCommon;
import jp.postclass.chordtraining.common.UtTemplate;

public class ScaleActivityHelper extends Qa1ActivityHelper {

    private int soundPoolElements[];
    private SoundPool soundPool;
    private Map<Integer, String> noScaleNameMap = new HashMap<>();


    public ScaleActivityHelper(Qa1Activity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        webView.setInitialScale(250);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.highestScorePrefKeySuffix = "_" + this.countDownSecond
                + preferences.getBoolean(Constants.PREF_SCALE_major, true)
                + preferences.getBoolean(Constants.PREF_SCALE_natural_minor, true)
                + preferences.getBoolean(Constants.PREF_SCALE_harmonic_minor, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor, true)
                + preferences.getBoolean(Constants.PREF_SCALE_major_2_dorian, true)
                + preferences.getBoolean(Constants.PREF_SCALE_major_3_phrygian, true)
                + preferences.getBoolean(Constants.PREF_SCALE_major_4_lydian, true)
                + preferences.getBoolean(Constants.PREF_SCALE_major_5_mixolydian, true)
                + preferences.getBoolean(Constants.PREF_SCALE_major_6_aeolian, true)
                + preferences.getBoolean(Constants.PREF_SCALE_major_7_locrian, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_2_dorian_b2, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_3_lydian_augmented, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_4_lydian_dominant, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_5_mixolydian_b6, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_6_locrian_n2, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_7_altered, true)
                + preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_acoustic, true)
                + preferences.getBoolean(Constants.PREF_SCALE_blues, true);

        this.highestScore = preferences.getInt(Constants.DATA_TONE_HIGHESTSCORE + this.highestScorePrefKeySuffix, 0);
        this.highestScoreDate = preferences.getString(Constants.DATA_TONE_HIGHESTSCORE_DATE + this.highestScorePrefKeySuffix, "");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getString(R.string.scale_title));

        AudioAttributes soundPoolAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        int tmpToneNo = 0;

        if (preferences.getBoolean(Constants.PREF_SCALE_major, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_major);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_natural_minor, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_natural_minor);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_harmonic_minor, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_harmonic_minor);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_major_2_dorian, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_major_2_dorian);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_major_3_phrygian, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_major_3_phrygian);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_major_4_lydian, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_major_4_lydian);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_major_5_mixolydian, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_major_5_mixolydian);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_major_6_aeolian, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_major_6_aeolian);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_major_7_locrian, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_major_7_locrian);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_2_dorian_b2, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor_2_dorian_b2);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_3_lydian_augmented, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor_3_lydian_augmented);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_4_lydian_dominant, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor_4_lydian_dominant);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_5_mixolydian_b6, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor_5_mixolydian_b6);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_6_locrian_n2, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor_6_locrian_n2);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_7_altered, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor_7_altered);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_melodic_minor_acoustic, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_melodic_minor_acoustic);
        }
        if (preferences.getBoolean(Constants.PREF_SCALE_blues, true)) {
            this.noScaleNameMap.put(tmpToneNo++, Constants.SCALE_blues);
        }

        this.maxIdx = tmpToneNo;
        this.soundPoolElements = new int[maxIdx];

        this.soundPool = new SoundPool.Builder()
                .setAudioAttributes(soundPoolAttributes)
                .setMaxStreams(1)
                .build();

        for (int i = minIdx; i < maxIdx; i++) {
            String scaleConstant = this.noScaleNameMap.get(i);
            this.soundPoolElements[i] =
                    UtCommon.loadSound(
                            soundPool,
                            getResources().getAssets(),
                            "scale/" + Constants.KEY_C +"/scale_" + Constants.KEY_C + "_" + scaleConstant + ".mid");
        }

        stopCountdown();
    }

    @Override
    public void onPause() {
        this.soundPool.release();
        this.soundPool = null;
        super.onPause();
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
                                    SharedPreferences.Editor preferences = ScaleActivityHelper.super.preferences.edit();
                                    preferences.putInt(Constants.DATA_SCALE_HIGHESTSCORE + highestScorePrefKeySuffix, highestScore);
                                    preferences.putString(Constants.DATA_SCALE_HIGHESTSCORE_DATE + highestScorePrefKeySuffix, highestScoreDate);
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
        String scaleConstant = this.noScaleNameMap.get(this.collectNo);

        try {
            UtCommon.loadSvg(getResources().getAssets(), webView, "scale/" + Constants.KEY_C +"/scale_" + Constants.KEY_C + "_" + scaleConstant + "_001.svg");
        } catch (IOException e) {
            throw new ApplicationRuntimeException(e);
        }

        this.soundPool.play(this.soundPoolElements[this.collectNo], 1, 1, 0, 0, 1);

        List<Integer> answerList = new ArrayList<>();
        answerList.add(this.collectNo);
        answerList.add(getRandomNo(this.collectNo));
        answerList.add(getRandomNo(this.collectNo, answerList.get(1)));
        Collections.shuffle(answerList);

        this.answerNo1 = answerList.get(0);
        this.answerNo2 = answerList.get(1);
        this.answerNo3 = answerList.get(2);

//        btnAnswer1.setText(getResources().getIdentifier("key_name_" + this.noScaleNameMap.get(this.answerNo1), "string", getPackageName()));
//        btnAnswer2.setText(getResources().getIdentifier("key_name_" + this.noScaleNameMap.get(this.answerNo2), "string", getPackageName()));
//        btnAnswer3.setText(getResources().getIdentifier("key_name_" + this.noScaleNameMap.get(this.answerNo3), "string", getPackageName()));
        btnAnswer1.setText(this.noScaleNameMap.get(this.answerNo1));
        btnAnswer2.setText(this.noScaleNameMap.get(this.answerNo2));
        btnAnswer3.setText(this.noScaleNameMap.get(this.answerNo3));


        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnSound1.setEnabled(true);
        btnSound2.setEnabled(true);
        btnSound3.setEnabled(true);
    }

}
