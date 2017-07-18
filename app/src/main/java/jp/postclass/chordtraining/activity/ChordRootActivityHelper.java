package jp.postclass.chordtraining.activity;


import android.content.SharedPreferences;
import android.media.AudioAttributes;
import android.media.SoundPool;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
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

public class ChordRootActivityHelper extends Qa1ActivityHelper {

    private int soundPoolElements[];
    private SoundPool soundPool;
    private Map<Integer, String> noChordnameMap = new HashMap<>();

    private boolean includeRootA;
    private boolean includeRootAm;
    private boolean includeRootB;
    private boolean includeRootBm;
    private boolean includeRootC;
    private boolean includeRootCm;
    private boolean includeRootD;
    private boolean includeRootDm;
    private boolean includeRootE;
    private boolean includeRootEm;
    private boolean includeRootF;
    private boolean includeRootFm;
    private boolean includeRootG;
    private boolean includeRootGm;

    private boolean variation_M7;
    private boolean variation_sus4;
    private boolean variation_f5;
    private boolean variation_s5;
    private boolean variation_6;
    private boolean variation_7;
    private boolean variation_9;
    private boolean variation_11;
    private boolean variation_13;

    private boolean plusminusSharp;
    private boolean plusminusNatural;
    private boolean plusminusFlat;

    public ChordRootActivityHelper(Qa1Activity activity) {
        super(activity);
    }

    @Override
    public void onResume() {
        super.onResume();

        this.includeRootA = preferences.getBoolean(Constants.PREF_CHORD_ROOT_A, true);
        this.includeRootAm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Am, true);
        this.includeRootB = preferences.getBoolean(Constants.PREF_CHORD_ROOT_B, true);
        this.includeRootBm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Bm, true);
        this.includeRootC = preferences.getBoolean(Constants.PREF_CHORD_ROOT_C, true);
        this.includeRootCm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Cm, true);
        this.includeRootD = preferences.getBoolean(Constants.PREF_CHORD_ROOT_D, true);
        this.includeRootDm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Dm, true);
        this.includeRootE = preferences.getBoolean(Constants.PREF_CHORD_ROOT_E, true);
        this.includeRootEm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Em, true);
        this.includeRootF = preferences.getBoolean(Constants.PREF_CHORD_ROOT_F, true);
        this.includeRootFm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Fm, true);
        this.includeRootG = preferences.getBoolean(Constants.PREF_CHORD_ROOT_G, true);
        this.includeRootGm = preferences.getBoolean(Constants.PREF_CHORD_ROOT_Gm, true);

        this.variation_M7 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_M7, false);
        this.variation_sus4 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_sus4, false);
        this.variation_f5 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_b5, false);
        this.variation_s5 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_sh5, false);
        this.variation_6 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_6, false);
        this.variation_7 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_7, false);
        this.variation_9 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_9, false);
        this.variation_11 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_11, false);
        this.variation_13 = preferences.getBoolean(Constants.PREF_CHORD_VARIATION_13, false);

        this.plusminusSharp = preferences.getBoolean(Constants.PREF_CHORD_PLUSMINUS_SHARP, false);
        this.plusminusNatural = preferences.getBoolean(Constants.PREF_CHORD_PLUSMINUS_NATURAL, true);
        this.plusminusFlat = preferences.getBoolean(Constants.PREF_CHORD_PLUSMINUS_FLAT, false);

        this.highestScorePrefKeySuffix = "_" + includeRootA +"_" + includeRootAm +"_" + includeRootB +"_" + includeRootBm +"_" + includeRootC +"_" + includeRootCm +"_" + includeRootD +"_" + includeRootDm +"_" + includeRootE +"_" + includeRootEm +"_" + includeRootF +"_" + includeRootFm +"_" + includeRootG +"_" + includeRootGm +"_" + variation_M7 +"_" + variation_sus4 +"_" + variation_f5 +"_" + variation_s5 +"_" + variation_6 +"_" + variation_7 +"_" + variation_9 +"_" + variation_11 +"_" +  this.countDownSecond;
        this.highestScore = preferences.getInt(Constants.DATA_CHORD_HIGHESTSCORE + this.highestScorePrefKeySuffix, 0);
        this.highestScoreDate = preferences.getString(Constants.DATA_CHORD_HIGHESTSCORE_DATE + this.highestScorePrefKeySuffix, "");

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.chord_root_title);

        AudioAttributes soundPoolAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        int tmpChordNo = 0;

        if (this.plusminusSharp) {
            if (this.includeRootA) { tmpChordNo = this.includeChordMajor("Ash", tmpChordNo); }
            if (this.includeRootAm) { tmpChordNo = this.includeChordMinor("Ash", tmpChordNo); }
            if (this.includeRootB) { tmpChordNo = this.includeChordMajor("Bsh", tmpChordNo); }
            if (this.includeRootBm) { tmpChordNo = this.includeChordMinor("Bsh", tmpChordNo); }
            if (this.includeRootC) { tmpChordNo = this.includeChordMajor("Csh", tmpChordNo); }
            if (this.includeRootCm) { tmpChordNo = this.includeChordMinor("Csh", tmpChordNo); }
            if (this.includeRootD) { tmpChordNo = this.includeChordMajor("Dsh", tmpChordNo); }
            if (this.includeRootDm) { tmpChordNo = this.includeChordMinor("Dsh", tmpChordNo); }
            if (this.includeRootE) { tmpChordNo = this.includeChordMajor("Esh", tmpChordNo); }
            if (this.includeRootEm) { tmpChordNo = this.includeChordMinor("Esh", tmpChordNo); }
            if (this.includeRootF) { tmpChordNo = this.includeChordMajor("Fsh", tmpChordNo); }
            if (this.includeRootFm) { tmpChordNo = this.includeChordMinor("Fsh", tmpChordNo); }
            if (this.includeRootG) { tmpChordNo = this.includeChordMajor("Gsh", tmpChordNo); }
            if (this.includeRootGm) { tmpChordNo = this.includeChordMinor("Gsh", tmpChordNo); }
        }

        if (this.plusminusNatural) {
            if (this.includeRootA) { tmpChordNo = this.includeChordMajor("A", tmpChordNo); }
            if (this.includeRootAm) { tmpChordNo = this.includeChordMinor("A", tmpChordNo); }
            if (this.includeRootB) { tmpChordNo = this.includeChordMajor("B", tmpChordNo); }
            if (this.includeRootBm) { tmpChordNo = this.includeChordMinor("B", tmpChordNo); }
            if (this.includeRootC) { tmpChordNo = this.includeChordMajor("C", tmpChordNo); }
            if (this.includeRootCm) { tmpChordNo = this.includeChordMinor("C", tmpChordNo); }
            if (this.includeRootD) { tmpChordNo = this.includeChordMajor("D", tmpChordNo); }
            if (this.includeRootDm) { tmpChordNo = this.includeChordMinor("D", tmpChordNo); }
            if (this.includeRootE) { tmpChordNo = this.includeChordMajor("E", tmpChordNo); }
            if (this.includeRootEm) { tmpChordNo = this.includeChordMinor("E", tmpChordNo); }
            if (this.includeRootF) { tmpChordNo = this.includeChordMajor("F", tmpChordNo); }
            if (this.includeRootFm) { tmpChordNo = this.includeChordMinor("F", tmpChordNo); }
            if (this.includeRootG) { tmpChordNo = this.includeChordMajor("G", tmpChordNo); }
            if (this.includeRootGm) { tmpChordNo = this.includeChordMinor("G", tmpChordNo); }
        }

        if (this.plusminusFlat) {
            if (this.includeRootA) { tmpChordNo = this.includeChordMajor("Ab", tmpChordNo); }
            if (this.includeRootAm) { tmpChordNo = this.includeChordMinor("Ab", tmpChordNo); }
            if (this.includeRootB) { tmpChordNo = this.includeChordMajor("Bb", tmpChordNo); }
            if (this.includeRootBm) { tmpChordNo = this.includeChordMinor("Bb", tmpChordNo); }
            if (this.includeRootC) { tmpChordNo = this.includeChordMajor("Cb", tmpChordNo); }
            if (this.includeRootCm) { tmpChordNo = this.includeChordMinor("Cb", tmpChordNo); }
            if (this.includeRootD) { tmpChordNo = this.includeChordMajor("Db", tmpChordNo); }
            if (this.includeRootDm) { tmpChordNo = this.includeChordMinor("Db", tmpChordNo); }
            if (this.includeRootE) { tmpChordNo = this.includeChordMajor("Eb", tmpChordNo); }
            if (this.includeRootEm) { tmpChordNo = this.includeChordMinor("Eb", tmpChordNo); }
            if (this.includeRootF) { tmpChordNo = this.includeChordMajor("Fb", tmpChordNo); }
            if (this.includeRootFm) { tmpChordNo = this.includeChordMinor("Fb", tmpChordNo); }
            if (this.includeRootG) { tmpChordNo = this.includeChordMajor("Gb", tmpChordNo); }
            if (this.includeRootGm) { tmpChordNo = this.includeChordMinor("Gb", tmpChordNo); }
        }


        this.maxIdx = tmpChordNo;
        this.soundPoolElements = new int[maxIdx];

        this.soundPool = new SoundPool.Builder()
                .setAudioAttributes(soundPoolAttributes)
                .setMaxStreams(2)
                .build();

        for (int i = minIdx; i < maxIdx; i++) {
            String chordName = this.noChordnameMap.get(i);
            this.soundPoolElements[i] =
                    UtCommon.loadSound(
                            soundPool,
                            getResources().getAssets(),
                            UtCommon.getChordPath(chordName) + ".mid");
        }

        stopCountdown();
    }

    private int includeChordMajor(String rootString, int tmpChordNo) {

        String baseChordConstant = rootString + "__" + rootString;

        this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString);

        if (this.variation_M7) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "M7");
        }

        if (this.variation_sus4) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "sus4");
        }

        if (variation_f5) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "b5");
        }

        if (variation_s5) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "aug");
        }

        if (variation_7) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "7");
        }

        if (variation_6) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "_6");
        }

        if (variation_9) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "_9");
        }

        if (variation_11) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "_11");
        }

        if (variation_13) {
            this.noChordnameMap.put(tmpChordNo++, rootString + "__" + rootString + "_13");
        }

        return tmpChordNo;
    }

    private int includeChordMinor(String rootString, int tmpChordNo) {

        String rootStringMinor = rootString + "m";

        this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor) ;

        if (this.variation_M7) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor + "M7");
        }

        if (variation_f5) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootString + "dim");
        }

        if (variation_s5) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor + "sh5");
        }

        if (variation_6) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor + "_6");
        }

        if (variation_7) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor + "7");
        }

        if (variation_9) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor + "_9");
        }

        if (variation_11) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor + "_11");
        }

        if (variation_13) {
            this.noChordnameMap.put(tmpChordNo++, rootStringMinor + "__" + rootStringMinor + "_13");
        }

        return tmpChordNo;
    }

    @Override
    public void onPause() {
        this.soundPool.release();
        this.soundPool = null;
        super.onPause();
    }

//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//
//        this.soundPool.release();
//        this.soundPool = null;
//    }



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
                                    SharedPreferences.Editor preferences = ChordRootActivityHelper.super.preferences.edit();
                                    preferences.putInt(Constants.DATA_CHORD_HIGHESTSCORE + highestScorePrefKeySuffix, highestScore);
                                    preferences.putString(Constants.DATA_CHORD_HIGHESTSCORE_DATE + highestScorePrefKeySuffix, highestScoreDate);
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
        String chordName = this.noChordnameMap.get(this.collectNo);

        try {
            UtCommon.loadSvg(getResources().getAssets(), webView, UtCommon.getChordPath(chordName) + "_001.svg");
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

//        btnAnswer1.setText(getResources().getIdentifier("chord_name_" + this.noChordnameMap.get(this.answerNo1), "string", getPackageName()));
//        btnAnswer2.setText(getResources().getIdentifier("chord_name_" + this.noChordnameMap.get(this.answerNo2), "string", getPackageName()));
//        btnAnswer3.setText(getResources().getIdentifier("chord_name_" + this.noChordnameMap.get(this.answerNo3), "string", getPackageName()));
        btnAnswer1.setText(UtCommon.getChordLabel(this.noChordnameMap.get(this.answerNo1)));
        btnAnswer2.setText(UtCommon.getChordLabel(this.noChordnameMap.get(this.answerNo2)));
        btnAnswer3.setText(UtCommon.getChordLabel(this.noChordnameMap.get(this.answerNo3)));

        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnSound1.setEnabled(true);
        btnSound2.setEnabled(true);
        btnSound3.setEnabled(true);
    }





}
