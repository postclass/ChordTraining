package jp.postclass.chordtraining.activity;


import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.AudioTrack;
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
//    private boolean includeHigh;
//    private boolean includeMiddle;
//    private boolean includeLow;

    public ToneActivityHelper(Qa1Activity activity) {
        super(activity);
    }

    @Override
    protected void init() {
        this.keymode = preferences.getString(Constants.PREF_TONE_KEYMODE, Constants.KEYMODE_MAJOR);
//        this.includeHigh = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_HIGH, true);
//        this.includeMiddle = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_MIDDLE, true);
//        this.includeLow = preferences.getBoolean(Constants.PREF_TONE_INCLUDE_LOW, true);

        this.highestScorePrefKeySuffix = "_" + this.keymode + this.countDownSecond;
        if (Constants.KEYMODE_MAJOR.equals(this.keymode)) {
            this.highestScorePrefKeySuffix += "" + preferences.getBoolean("pref.tone." + Constants.KEY_Csh, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Fsh, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_B, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_E, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_A, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_D, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_G, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_C, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_F, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Bb, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Eb, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Ab, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Db, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Gb, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Cb, true);
        } else {
            this.highestScorePrefKeySuffix += "" + preferences.getBoolean("pref.tone." + Constants.KEY_Ashm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Dshm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Gshm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Cshm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Fshm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Bm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Em, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Am, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Dm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Gm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Cm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Fm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Bbm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Ebm, true)
                    + preferences.getBoolean("pref.tone." + Constants.KEY_Abm, true);
        }



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
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Csh, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Csh);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Fsh, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Fsh);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_B, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_B);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_E, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_E);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_A, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_A);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_D, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_D);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_G, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_G);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_C, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_C);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_F, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_F);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Bb, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Bb);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Eb, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Eb);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Ab, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Ab);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Db, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Db);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Gb, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Gb);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Cb, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Cb);
            }
        }

        if (Constants.KEYMODE_MINOR.equals(this.keymode)) {
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Ashm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Ashm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Dshm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Dshm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Gshm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Gshm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Cshm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Cshm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Fshm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Fshm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Bm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Bm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Em, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Em);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Am, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Am);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Dm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Dm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Gm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Gm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Cm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Cm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Fm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Fm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Bbm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Bbm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Ebm, true)) {
                this.noTonenameMap.put(tmpToneNo++, Constants.KEY_Ebm);
            }
            if (preferences.getBoolean("pref.tone." + Constants.KEY_Abm, true)) {
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
