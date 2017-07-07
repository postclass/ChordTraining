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

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;
import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.UtCommon;

public class ChordDegreeActivityHelper extends Qa1ActivityHelper {

    private int soundPoolElements[];
    private SoundPool soundPool;
    private Map<Integer, String> noChordnameMap = new HashMap<>();
    private Map<Integer, String> rootMapS = new HashMap<>();
    private Map<Integer, String> rootMapF = new HashMap<>();

    private String key;

    public ChordDegreeActivityHelper(Qa1Activity activity) {
        super(activity);
    }

    @Override
    protected void init() {

        this.key = preferences.getString(Constants.PREF_CHORD_KEY, Constants.KEY_C);

        this.highestScorePrefKeySuffix = "_" + this.key + "_" + this.countDownSecond;
        this.highestScore = preferences.getInt(Constants.DATA_CHORD_HIGHESTSCORE + this.highestScorePrefKeySuffix, 0);
        this.highestScoreDate = preferences.getString(Constants.DATA_CHORD_HIGHESTSCORE_DATE + this.highestScorePrefKeySuffix, "");

        ActionBar actionBar = getSupportActionBar();
//        actionBar.setTitle(R.string.chord_degree_title);
        String keyName = getString(getResources().getIdentifier("key_name_" + this.key, "string", getPackageName()));
        actionBar.setTitle(keyName);


        AudioAttributes soundPoolAttributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .build();

        setRootMap(this.rootMapS, this.rootMapF);

        int tmpChordNo = 0;

        if (Constants.KEY_Cb.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getFlatAddMap(this.rootMapF), 100, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_C.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(this.rootMapF, 100, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Csh.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getSharpAddMap(this.rootMapF), 100, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Db.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getFlatAddMap(this.rootMapF), 102, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_D.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(this.rootMapF, 102, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Eb.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getFlatAddMap(this.rootMapF), 104, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_E.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(this.rootMapF, 104, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_F.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(this.rootMapF, 105, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Fsh.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getSharpAddMap(this.rootMapF), 105, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Gb.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getFlatAddMap(this.rootMapF), 107, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_G.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(this.rootMapF, 107, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Ab.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getFlatAddMap(this.rootMapF), 109, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_A.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(this.rootMapF, 109, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Bb.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(getFlatAddMap(this.rootMapF), 111, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_B.equals(this.key)) {
            tmpChordNo = setChordnameMapMajor(this.rootMapF, 111, tmpChordNo, this.noChordnameMap);

        } else if (Constants.KEY_Cm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(this.rootMapF, 100, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Cshm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getSharpAddMap(this.rootMapF), 100, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Dm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(this.rootMapF, 102, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Dshm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getSharpAddMap(this.rootMapF), 102, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Ebm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getFlatAddMap(this.rootMapF), 104, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Em.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(this.rootMapF, 104, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Fm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(this.rootMapF, 105, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Fshm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getSharpAddMap(this.rootMapF), 105, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Gm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(this.rootMapF, 107, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Gshm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getSharpAddMap(this.rootMapF), 107, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Abm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getFlatAddMap(this.rootMapF), 109, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Am.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(this.rootMapF, 109, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Ashm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getSharpAddMap(this.rootMapF), 109, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Bbm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(getFlatAddMap(this.rootMapF), 111, tmpChordNo, this.noChordnameMap);
        } else if (Constants.KEY_Bm.equals(this.key)) {
            tmpChordNo = setChordnameMapMinor(this.rootMapF, 111, tmpChordNo, this.noChordnameMap);
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
                                    SharedPreferences.Editor preferences = ChordDegreeActivityHelper.super.preferences.edit();
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

        webView.loadUrl("file:///android_asset/" + UtCommon.getChordPath(chordName) + "_001.svg");

        this.soundPool.play(this.soundPoolElements[this.collectNo], 1, 1, 0, 0, 1);

        List<Integer> answerList = new ArrayList<>();
        answerList.add(this.collectNo);
        answerList.add(getRandomNo(this.collectNo));
        answerList.add(getRandomNo(this.collectNo, answerList.get(1)));
        Collections.shuffle(answerList);

        this.answerNo1 = answerList.get(0);
        this.answerNo2 = answerList.get(1);
        this.answerNo3 = answerList.get(2);

        String degreeName1 = getDegreeName(this.answerNo1, this.key);
        String degreeName2 = getDegreeName(this.answerNo2, this.key);
        String degreeName3 = getDegreeName(this.answerNo3, this.key);

        String chordName1 = getString(getResources().getIdentifier("chord_name_" + this.noChordnameMap.get(this.answerNo1), "string", getPackageName()));
        String chordName2 = getString(getResources().getIdentifier("chord_name_" + this.noChordnameMap.get(this.answerNo2), "string", getPackageName()));
        String chordName3 = getString(getResources().getIdentifier("chord_name_" + this.noChordnameMap.get(this.answerNo3), "string", getPackageName()));

        btnAnswer1.setText(degreeName1 + " (" + chordName1 + ")");
        btnAnswer2.setText(degreeName2 + " (" + chordName2 + ")");
        btnAnswer3.setText(degreeName3 + " (" + chordName3 + ")");

        btnAnswer1.setEnabled(true);
        btnAnswer2.setEnabled(true);
        btnAnswer3.setEnabled(true);
        btnSound1.setEnabled(true);
        btnSound2.setEnabled(true);
        btnSound3.setEnabled(true);
    }

    public static String getDegreeName(int idx, String key) {
        boolean major = !key.endsWith("m");

        if (major) {
            switch (idx) {
                case 0:
                    return "Imaj7";
                case 1:
                    return "IIm7";
                case 2:
                    return "IIIm7";
                case 3:
                    return "IVmaj7";
                case 4:
                    return "V7";
                case 5:
                    return "VIm7";
                case 6:
                    return "VIIm7b5";
                default:
                    throw new ApplicationRuntimeException("ありえないidx " + idx);

            }
        } else {
            switch (idx) {
                case 0:
                    return "Im7";
                case 1:
                    return "IIm7b5";
                case 2:
                    return "bIIImaj7";
                case 3:
                    return "IVm7";
                case 4:
                    return "Vm7";
                case 5:
                    return "bVImaj7";
                case 6:
                    return "bVII7";
                default:
                    throw new ApplicationRuntimeException("ありえないidx " + idx);

            }
        }
    }

    public static Map<Integer, String> getFlatAddMap(Map<Integer, String> rootMap) {
        Map<Integer, String> newMap = new HashMap<>();
        for (Integer key : rootMap.keySet()) {
            String newRoot = getFlatRootName(rootMap, key);
            newMap.put(key, newRoot);
        }
        return newMap;
    }

    public static Map<Integer, String> getSharpAddMap(Map<Integer, String> rootMap) {
        Map<Integer, String> newMap = new HashMap<>();
        for (Integer key : rootMap.keySet()) {
            String newRoot = getSharpRootName(rootMap, key);
            newMap.put(key, newRoot);
        }
        return newMap;
    }

    public static String getFlatRootName(Map<Integer, String> rootMap, int rootNo) {
        String rootName = rootMap.get(rootNo);
        if (rootName.endsWith("b")) {
            return rootMap.get(rootNo - 1);
        } else {
            rootName += "b";
            rootName = rootName.replace("shb", "");
            rootName = rootName.replace("bsh", "");
            return rootName;
        }
    }

    public static String getSharpRootName(Map<Integer, String> rootMap, int rootNo) {
        String rootName = rootMap.get(rootNo);
        if (rootName.endsWith("sh")) {
            return rootMap.get(rootNo + 1);
        } else {
            rootName += "sh";
            rootName = rootName.replace("shb", "");
            rootName = rootName.replace("bsh", "");
            return rootName;
        }
    }

    public static int setChordnameMapMajor(Map<Integer, String> rootMap, int rootNo, int tmpChordNo, Map<Integer, String> noChordnameMap) {
        String rootName1 = rootMap.get(rootNo);
        noChordnameMap.put(tmpChordNo++, rootName1 + "__" + rootName1 + "M7");

        String rootName2 = rootMap.get(rootNo + 2);
        noChordnameMap.put(tmpChordNo++, rootName2 + "m__" + rootName2 + "m7");

        String rootName3 = rootMap.get(rootNo + 4);
        noChordnameMap.put(tmpChordNo++, rootName3 + "m__" + rootName3 + "m7");

        String rootName4 = rootMap.get(rootNo + 5);
        noChordnameMap.put(tmpChordNo++, rootName4 + "__" + rootName4 + "M7");

        String rootName5 = rootMap.get(rootNo + 7);
        noChordnameMap.put(tmpChordNo++, rootName5 + "__" + rootName5 + "7");

        String rootName6 = rootMap.get(rootNo + 9);
        noChordnameMap.put(tmpChordNo++, rootName6 + "m__" + rootName6 + "m7");

        String rootName7 = rootMap.get(rootNo + 11);
        noChordnameMap.put(tmpChordNo++, rootName7 + "m__" + rootName7 + "m7b5");

        return tmpChordNo;
    }

    public static int setChordnameMapMinor(Map<Integer, String> rootMap, int rootNo, int tmpChordNo, Map<Integer, String> noChordnameMap) {

        String rootName1 = rootMap.get(rootNo);
        noChordnameMap.put(tmpChordNo++, rootName1 + "m__" + rootName1 + "m7");

        String rootName2 = rootMap.get(rootNo + 2);
        noChordnameMap.put(tmpChordNo++, rootName2 + "m__" + rootName2 + "m7b5");

        String rootName3 = getFlatRootName(rootMap, rootNo + 4);
        noChordnameMap.put(tmpChordNo++, rootName3 + "__" + rootName3 + "M7");

        String rootName4 = rootMap.get(rootNo + 5);
        noChordnameMap.put(tmpChordNo++, rootName4 + "m__" + rootName4 + "m7");

        String rootName5 = rootMap.get(rootNo + 7);
        noChordnameMap.put(tmpChordNo++, rootName5 + "m__" + rootName5 + "m7");

        String rootName6 = getFlatRootName(rootMap, rootNo + 9);
        noChordnameMap.put(tmpChordNo++, rootName6 + "__" + rootName6 + "M7");

        String rootName7 = getFlatRootName(rootMap, rootNo + 11);
        noChordnameMap.put(tmpChordNo++, rootName7 + "__" + rootName7 + "7");

        return tmpChordNo;
    }



    public static void setRootMap(Map<Integer, String> rootMapS, Map<Integer, String> rootMapF) {

        int no = 88;
        rootMapS.put(no, "C");
        rootMapF.put(no, "C");

        no++;
        rootMapS.put(no, "Csh");
        rootMapF.put(no, "Db");

        no++;
        rootMapS.put(no, "D");
        rootMapF.put(no, "D");

        no++;
        rootMapS.put(no, "Dsh");
        rootMapF.put(no, "Eb");

        no++;
        rootMapS.put(no, "E");
        rootMapF.put(no, "E");

        no++;
        rootMapS.put(no, "F");
        rootMapF.put(no, "F");

        no++;
        rootMapS.put(no, "Fsh");
        rootMapF.put(no, "Gb");

        no++;
        rootMapS.put(no, "G");
        rootMapF.put(no, "G");

        no++;
        rootMapS.put(no, "Gsh");
        rootMapF.put(no, "Ab");

        no++;
        rootMapS.put(no, "A");
        rootMapF.put(no, "A");

        no++;
        rootMapS.put(no, "Ash");
        rootMapF.put(no, "Bb");

        no++;
        rootMapS.put(no, "B");
        rootMapF.put(no, "B");


        // 100
        no++;
        rootMapS.put(no, "C");
        rootMapF.put(no, "C");

        no++;
        rootMapS.put(no, "Csh");
        rootMapF.put(no, "Db");

        no++;
        rootMapS.put(no, "D");
        rootMapF.put(no, "D");

        no++;
        rootMapS.put(no, "Dsh");
        rootMapF.put(no, "Eb");

        no++;
        rootMapS.put(no, "E");
        rootMapF.put(no, "E");

        no++;
        rootMapS.put(no, "F");
        rootMapF.put(no, "F");

        no++;
        rootMapS.put(no, "Fsh");
        rootMapF.put(no, "Gb");

        no++;
        rootMapS.put(no, "G");
        rootMapF.put(no, "G");

        no++;
        rootMapS.put(no, "Gsh");
        rootMapF.put(no, "Ab");

        no++;
        rootMapS.put(no, "A");
        rootMapF.put(no, "A");

        no++;
        rootMapS.put(no, "Ash");
        rootMapF.put(no, "Bb");

        no++;
        rootMapS.put(no, "B");
        rootMapF.put(no, "B");




        // 112
        no++;
        rootMapS.put(no, "C");
        rootMapF.put(no, "C");

        no++;
        rootMapS.put(no, "Csh");
        rootMapF.put(no, "Db");

        no++;
        rootMapS.put(no, "D");
        rootMapF.put(no, "D");

        no++;
        rootMapS.put(no, "Dsh");
        rootMapF.put(no, "Eb");

        no++;
        rootMapS.put(no, "E");
        rootMapF.put(no, "E");

        no++;
        rootMapS.put(no, "F");
        rootMapF.put(no, "F");

        no++;
        rootMapS.put(no, "Fsh");
        rootMapF.put(no, "Gb");

        no++;
        rootMapS.put(no, "G");
        rootMapF.put(no, "G");

        no++;
        rootMapS.put(no, "Gsh");
        rootMapF.put(no, "Ab");

        no++;
        rootMapS.put(no, "A");
        rootMapF.put(no, "A");

        no++;
        rootMapS.put(no, "Ash");
        rootMapF.put(no, "Bb");

        no++;
        rootMapS.put(no, "B");
        rootMapF.put(no, "B");
    }



}
