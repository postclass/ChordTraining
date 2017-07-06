package jp.postclass.chordtraining.common;

import java.io.StringBufferInputStream;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */

public final class UtAbc {
    
    public static final String NEW_LINE = "\r\n";


    public static Map<Integer, String> sAbcMap = new HashMap<>();
    public static Map<Integer, String> fAbcMap = new HashMap<>();
    public static Map<String, Integer> rootnameRootnoMap = new HashMap<>();
    public static Map<String, Map<Integer, String>> rootnameAbcmapMap = new HashMap<>();

    static {
        initAbcMap();
        initRootnameMap();
    }
    

    public static String getHtml(String body, String abcPath) {
        return "<!DOCTYPE HTML>" + NEW_LINE +
                "<html>" + NEW_LINE +
                "<head>" + NEW_LINE +
                "\t<meta http-equiv=\"content-type\" content=\"text/html; charset=UTF-8\" />" + NEW_LINE +
                "\t<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + NEW_LINE +
                "\t<link href=\"" + abcPath + "abcjs-midi.css\" media=\"all\" rel=\"stylesheet\" type=\"text/css\" />" + NEW_LINE +
                "\t<script src=\"" + abcPath + "abcjs_basic_3.1.2-min.js\" type=\"text/javascript\"></script>" + NEW_LINE +
                "</head>" + NEW_LINE +
                "<body>" + NEW_LINE +

                body + NEW_LINE +

                "<script>" + NEW_LINE +
                "\tvar scoreObj = document.getElementsByClassName(\"score\");" + NEW_LINE +
                "\tfor( var i in scoreObj ){" + NEW_LINE +
                "\t\tABCJS.renderAbc( scoreObj[i], scoreObj[i].innerHTML );" + NEW_LINE +
                "\t}" + NEW_LINE +
                "</script>" + NEW_LINE +
                "" + NEW_LINE +
                "</body>" + NEW_LINE +
                "</html>" + NEW_LINE;
    }

    public static final String getScoreTag(String abc) {
        return getScoreTag(abc, "C treble");
    }

    public static final String getScoreTag(String abc, String key) {

        return "<div class=\"score\">" + NEW_LINE +
                "I:abc-charset utf-8" + NEW_LINE +
                "X:1" + NEW_LINE +
                "T:" + NEW_LINE +
                "M:4/4" + NEW_LINE +
                "L:1/8" + NEW_LINE +
                "K:" + key + NEW_LINE +
                abc + NEW_LINE +
                "</div>";

    }

    public static String getChordAbc(String chordConstants) {
        return getChordAbc(chordConstants, Constants.TENSION_NONE, Constants.TENSION_NONE, Constants.TENSION_NONE, Constants.TENSION_NONE, 4);
    }

    public static String getChordAbc(String chordConstants, int len) {
        return getChordAbc(chordConstants, Constants.TENSION_NONE, Constants.TENSION_NONE, Constants.TENSION_NONE, Constants.TENSION_NONE, len);
    }

    public static String getChordAbc(String chordConstants, int t6, int t9, int t11, int t13) {
        return getChordAbc(chordConstants, t6, 69, t11, t13, 4);
    }

    public static String getChordAbc(String chordConstants, int t6, int t9, int t11, int t13, int len) {

        String rootName = chordConstants.split("__")[0];
        String chordName = chordConstants.split("__")[1];
        String chordLabel = UtCommon.getChordLabel(chordConstants, t6, t9, t11, t13);

        int rootNo = rootnameRootnoMap.get(rootName.replace("m", ""));
        Map<Integer, String> baseMap = rootnameAbcmapMap.get(rootName.replace("m", ""));

        boolean minor = rootName.endsWith("m");


        String chordAbc = "\"" + chordLabel + "\"[";

        // 第一音
        chordAbc += baseMap.get(rootNo);

        // 第二音
        if (chordName.endsWith("sus4")) {
            chordAbc += baseMap.get(rootNo + 5);
        } else if (minor) {
            chordAbc += getFlat(baseMap, rootNo + 4);
        } else {
            chordAbc += baseMap.get(rootNo + 4);
        }

        // 第三音
        if (chordName.endsWith("b5") || chordName.endsWith("dim")) {
            chordAbc += getFlat(baseMap, rootNo + 7);
        } else if (chordName.endsWith("sh5") || chordName.endsWith("aug")) {
            chordAbc += getSharp(baseMap, rootNo + 7);
        } else {
            chordAbc += baseMap.get(rootNo + 7);
        }

        // 第四音
        if (chordAbc.contains("M7")) {
            chordAbc += baseMap.get(rootNo + 11);
        } else if (chordAbc.contains("7")) {
            chordAbc += getFlat(baseMap, rootNo + 11);
        }

        chordAbc += getTensionAbc(rootNo, baseMap, t6, t9, t11, t13);

        chordAbc += "]" + len + " ";

        return chordAbc;
    }


    public static String getTensionAbc(int rootNo, Map<Integer, String> baseMap, int t6, int t9, int t11, int t13) {

        String result = "";

        switch (t6) {
            case Constants.TENSION_NATURAL:
                result += baseMap.get(rootNo + 9);
                break;
            case Constants.TENSION_SH:
                result += getSharp(baseMap, rootNo + 9);
                break;
            case Constants.TENSION_FL:
                result += getFlat(baseMap, rootNo + 9);
                break;
        }

        switch (t9) {
            case Constants.TENSION_NATURAL:
                result += baseMap.get(rootNo + 14);
                break;
            case Constants.TENSION_SH:
                result += getSharp(baseMap, rootNo + 14);
                break;
            case Constants.TENSION_FL:
                result += getFlat(baseMap, rootNo + 14);
                break;
        }

        switch (t11) {
            case Constants.TENSION_NATURAL:
                result += baseMap.get(rootNo + 17);
                break;
            case Constants.TENSION_SH:
                result += getSharp(baseMap, rootNo + 17);
                break;
            case Constants.TENSION_FL:
                result += getFlat(baseMap, rootNo + 17);
                break;
        }

        switch (t13) {
            case Constants.TENSION_NATURAL:
                result += baseMap.get(rootNo + 21);
                break;
            case Constants.TENSION_SH:
                result += getSharp(baseMap, rootNo + 21);
                break;
            case Constants.TENSION_FL:
                result += getFlat(baseMap, rootNo + 21);
                break;
        }

        return result;
    }



    public static String getFlat(Map<Integer, String> baseMap, int no) {
        String result = "_" + baseMap.get(no);
        result = result.replace("_^", "");
        result = result.replace("^_", "");

        if (result.contains("___")) {
            result = baseMap.get(no - 1);
            result = result.replace("_^", "");
            result = result.replace("^_", "");
        }

        return result;
    }

    public static String getSharp(Map<Integer, String> baseMap, int no) {
        String result = "^" + baseMap.get(no);
        result = result.replace("_^", "");
        result = result.replace("^_", "");

        if (result.contains("^^^")) {
            result = baseMap.get(no + 1);
            result = result.replace("_^", "");
            result = result.replace("^_", "");
        }

        return result;
    }

    public static Map<Integer, String> getFlatMap(Map<Integer, String> baseMap) {
        Map<Integer, String> result = new HashMap<>();

        for (Integer key : baseMap.keySet()) {
            result.put(key, getFlat(baseMap, key));
        }

        return result;
    }

    public static Map<Integer, String> getSharpMap(Map<Integer, String> baseMap) {
        Map<Integer, String> result = new HashMap<>();

        for (Integer key : baseMap.keySet()) {
            result.put(key, getSharp(baseMap, key));
        }

        return result;
    }


    public static void initAbcMap() {
        int no = 88;

        sAbcMap.put(no, "C,");
        fAbcMap.put(no, "C,");

        no++;
        sAbcMap.put(no, "^C,");
        fAbcMap.put(no, "_C,");

        no++;
        sAbcMap.put(no, "D,");
        fAbcMap.put(no, "D,");

        no++;
        sAbcMap.put(no, "^D,");
        fAbcMap.put(no, "_E,");

        no++;
        sAbcMap.put(no, "E,");
        fAbcMap.put(no, "E,");

        no++;
        sAbcMap.put(no, "F,");
        fAbcMap.put(no, "F,");

        no++;
        sAbcMap.put(no, "^F,");
        fAbcMap.put(no, "_G,");

        no++;
        sAbcMap.put(no, "G,");
        fAbcMap.put(no, "G,");

        no++;
        sAbcMap.put(no, "^G,");
        fAbcMap.put(no, "_A,");

        no++;
        sAbcMap.put(no, "A,");
        fAbcMap.put(no, "A,");

        no++;
        sAbcMap.put(no, "^A,");
        fAbcMap.put(no, "_B,");

        no++;
        sAbcMap.put(no, "B,");
        fAbcMap.put(no, "B,");


        no++; // 100
        sAbcMap.put(no, "C");
        fAbcMap.put(no, "C");

        no++;
        sAbcMap.put(no, "^C");
        fAbcMap.put(no, "_C");

        no++;
        sAbcMap.put(no, "D");
        fAbcMap.put(no, "D");

        no++;
        sAbcMap.put(no, "^D");
        fAbcMap.put(no, "_E");

        no++;
        sAbcMap.put(no, "E");
        fAbcMap.put(no, "E");

        no++;
        sAbcMap.put(no, "F");
        fAbcMap.put(no, "F");

        no++;
        sAbcMap.put(no, "^F");
        fAbcMap.put(no, "_G");

        no++;
        sAbcMap.put(no, "G");
        fAbcMap.put(no, "G");

        no++;
        sAbcMap.put(no, "^G");
        fAbcMap.put(no, "_A");

        no++;
        sAbcMap.put(no, "A");
        fAbcMap.put(no, "A");

        no++;
        sAbcMap.put(no, "^A");
        fAbcMap.put(no, "_B");

        no++;
        sAbcMap.put(no, "B");
        fAbcMap.put(no, "B");


        no++; // 124
        sAbcMap.put(no, "c");
        fAbcMap.put(no, "c");

        no++;
        sAbcMap.put(no, "^c");
        fAbcMap.put(no, "_c");

        no++;
        sAbcMap.put(no, "d");
        fAbcMap.put(no, "d");

        no++;
        sAbcMap.put(no, "^d");
        fAbcMap.put(no, "_e");

        no++;
        sAbcMap.put(no, "e");
        fAbcMap.put(no, "e");

        no++;
        sAbcMap.put(no, "f");
        fAbcMap.put(no, "f");

        no++;
        sAbcMap.put(no, "^f");
        fAbcMap.put(no, "_g");

        no++;
        sAbcMap.put(no, "g");
        fAbcMap.put(no, "g");

        no++;
        sAbcMap.put(no, "^g");
        fAbcMap.put(no, "_a");

        no++;
        sAbcMap.put(no, "a");
        fAbcMap.put(no, "a");

        no++;
        sAbcMap.put(no, "^a");
        fAbcMap.put(no, "_b");

        no++;
        sAbcMap.put(no, "b");
        fAbcMap.put(no, "b");


        no++; // 148
        sAbcMap.put(no, "c'");
        fAbcMap.put(no, "c'");

        no++;
        sAbcMap.put(no, "^c'");
        fAbcMap.put(no, "_c'");

        no++;
        sAbcMap.put(no, "d'");
        fAbcMap.put(no, "d'");

        no++;
        sAbcMap.put(no, "^d'");
        fAbcMap.put(no, "_e'");

        no++;
        sAbcMap.put(no, "e'");
        fAbcMap.put(no, "e'");

        no++;
        sAbcMap.put(no, "f'");
        fAbcMap.put(no, "f'");

        no++;
        sAbcMap.put(no, "^f'");
        fAbcMap.put(no, "_g'");

        no++;
        sAbcMap.put(no, "g'");
        fAbcMap.put(no, "g'");

        no++;
        sAbcMap.put(no, "^g'");
        fAbcMap.put(no, "_a'");

        no++;
        sAbcMap.put(no, "a'");
        fAbcMap.put(no, "a'");

        no++;
        sAbcMap.put(no, "^a'");
        fAbcMap.put(no, "_b'");

        no++;
        sAbcMap.put(no, "b'");
        fAbcMap.put(no, "b'");
    }


    private static void initRootnameMap() {
        rootnameRootnoMap.put("A", 97);
        rootnameAbcmapMap.put("A", sAbcMap);
        rootnameRootnoMap.put("Ab", 97);
        rootnameAbcmapMap.put("Ab", getFlatMap(sAbcMap));
        rootnameRootnoMap.put("Ash", 97);
        rootnameAbcmapMap.put("Ash", getSharpMap(sAbcMap));
        rootnameRootnoMap.put("B", 99);
        rootnameAbcmapMap.put("B", sAbcMap);
        rootnameRootnoMap.put("Bb", 99);
        rootnameAbcmapMap.put("Bb", getFlatMap(sAbcMap));
        rootnameRootnoMap.put("Bsh", 99);
        rootnameAbcmapMap.put("Bsh", getSharpMap(sAbcMap));
        rootnameRootnoMap.put("C", 100);
        rootnameAbcmapMap.put("C", sAbcMap);
        rootnameRootnoMap.put("Cb", 100);
        rootnameAbcmapMap.put("Cb", getFlatMap(sAbcMap));
        rootnameRootnoMap.put("Csh", 100);
        rootnameAbcmapMap.put("Csh", getSharpMap(sAbcMap));
        rootnameRootnoMap.put("D", 102);
        rootnameAbcmapMap.put("D", sAbcMap);
        rootnameRootnoMap.put("Db", 102);
        rootnameAbcmapMap.put("Db", getFlatMap(sAbcMap));
        rootnameRootnoMap.put("Dsh", 102);
        rootnameAbcmapMap.put("Dsh", getSharpMap(sAbcMap));
        rootnameRootnoMap.put("E", 104);
        rootnameAbcmapMap.put("E", sAbcMap);
        rootnameRootnoMap.put("Eb", 104);
        rootnameAbcmapMap.put("Eb", getFlatMap(sAbcMap));
        rootnameRootnoMap.put("Esh", 104);
        rootnameAbcmapMap.put("Esh", getSharpMap(sAbcMap));
        rootnameRootnoMap.put("F", 105);
        rootnameAbcmapMap.put("F", sAbcMap);
        rootnameRootnoMap.put("Fb", 105);
        rootnameAbcmapMap.put("Fb", getFlatMap(sAbcMap));
        rootnameRootnoMap.put("Fsh", 105);
        rootnameAbcmapMap.put("Fsh", getSharpMap(sAbcMap));
        rootnameRootnoMap.put("G", 107);
        rootnameAbcmapMap.put("G", sAbcMap);
        rootnameRootnoMap.put("Gb", 107);
        rootnameAbcmapMap.put("Gb", getFlatMap(sAbcMap));
        rootnameRootnoMap.put("Gsh", 107);
        rootnameAbcmapMap.put("Gsh", getSharpMap(sAbcMap));
    }
}
