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
        initAbcMap(sAbcMap, fAbcMap);
        initRootnameMap(rootnameRootnoMap, rootnameAbcmapMap);
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
        return "<div class=\"score\">" + NEW_LINE +
                abc + NEW_LINE +
                "</div>";

    }

    public static final String getAbcHeader() {
        return getAbcHeader("C treble", 100);
    }

    public static final String getAbcHeader(String key, Integer tempo) {
        String result =
                "I:abc-charset utf-8" + NEW_LINE +
                "X:1" + NEW_LINE +
                "T:" + NEW_LINE +
                "M:4/4" + NEW_LINE +
                "L:1/8" + NEW_LINE +
                "K:" + key + NEW_LINE;

        if (tempo != null) {
            result += "Q:1/4=" + tempo + NEW_LINE;
        }

        return result;
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


    public static void initAbcMap(Map<Integer, String> _sAbcMap, Map<Integer, String> _fAbcMap) {
        int no = 88;

        _sAbcMap.put(no, "C,");
        _fAbcMap.put(no, "C,");

        no++;
        _sAbcMap.put(no, "^C,");
        _fAbcMap.put(no, "_C,");

        no++;
        _sAbcMap.put(no, "D,");
        _fAbcMap.put(no, "D,");

        no++;
        _sAbcMap.put(no, "^D,");
        _fAbcMap.put(no, "_E,");

        no++;
        _sAbcMap.put(no, "E,");
        _fAbcMap.put(no, "E,");

        no++;
        _sAbcMap.put(no, "F,");
        _fAbcMap.put(no, "F,");

        no++;
        _sAbcMap.put(no, "^F,");
        _fAbcMap.put(no, "_G,");

        no++;
        _sAbcMap.put(no, "G,");
        _fAbcMap.put(no, "G,");

        no++;
        _sAbcMap.put(no, "^G,");
        _fAbcMap.put(no, "_A,");

        no++;
        _sAbcMap.put(no, "A,");
        _fAbcMap.put(no, "A,");

        no++;
        _sAbcMap.put(no, "^A,");
        _fAbcMap.put(no, "_B,");

        no++;
        _sAbcMap.put(no, "B,");
        _fAbcMap.put(no, "B,");


        no++; // 100
        _sAbcMap.put(no, "C");
        _fAbcMap.put(no, "C");

        no++;
        _sAbcMap.put(no, "^C");
        _fAbcMap.put(no, "_C");

        no++;
        _sAbcMap.put(no, "D");
        _fAbcMap.put(no, "D");

        no++;
        _sAbcMap.put(no, "^D");
        _fAbcMap.put(no, "_E");

        no++;
        _sAbcMap.put(no, "E");
        _fAbcMap.put(no, "E");

        no++;
        _sAbcMap.put(no, "F");
        _fAbcMap.put(no, "F");

        no++;
        _sAbcMap.put(no, "^F");
        _fAbcMap.put(no, "_G");

        no++;
        _sAbcMap.put(no, "G");
        _fAbcMap.put(no, "G");

        no++;
        _sAbcMap.put(no, "^G");
        _fAbcMap.put(no, "_A");

        no++;
        _sAbcMap.put(no, "A");
        _fAbcMap.put(no, "A");

        no++;
        _sAbcMap.put(no, "^A");
        _fAbcMap.put(no, "_B");

        no++;
        _sAbcMap.put(no, "B");
        _fAbcMap.put(no, "B");


        no++; // 124
        _sAbcMap.put(no, "c");
        _fAbcMap.put(no, "c");

        no++;
        _sAbcMap.put(no, "^c");
        _fAbcMap.put(no, "_c");

        no++;
        _sAbcMap.put(no, "d");
        _fAbcMap.put(no, "d");

        no++;
        _sAbcMap.put(no, "^d");
        _fAbcMap.put(no, "_e");

        no++;
        _sAbcMap.put(no, "e");
        _fAbcMap.put(no, "e");

        no++;
        _sAbcMap.put(no, "f");
        _fAbcMap.put(no, "f");

        no++;
        _sAbcMap.put(no, "^f");
        _fAbcMap.put(no, "_g");

        no++;
        _sAbcMap.put(no, "g");
        _fAbcMap.put(no, "g");

        no++;
        _sAbcMap.put(no, "^g");
        _fAbcMap.put(no, "_a");

        no++;
        _sAbcMap.put(no, "a");
        _fAbcMap.put(no, "a");

        no++;
        _sAbcMap.put(no, "^a");
        _fAbcMap.put(no, "_b");

        no++;
        _sAbcMap.put(no, "b");
        _fAbcMap.put(no, "b");


        no++; // 148
        _sAbcMap.put(no, "c'");
        _fAbcMap.put(no, "c'");

        no++;
        _sAbcMap.put(no, "^c'");
        _fAbcMap.put(no, "_c'");

        no++;
        _sAbcMap.put(no, "d'");
        _fAbcMap.put(no, "d'");

        no++;
        _sAbcMap.put(no, "^d'");
        _fAbcMap.put(no, "_e'");

        no++;
        _sAbcMap.put(no, "e'");
        _fAbcMap.put(no, "e'");

        no++;
        _sAbcMap.put(no, "f'");
        _fAbcMap.put(no, "f'");

        no++;
        _sAbcMap.put(no, "^f'");
        _fAbcMap.put(no, "_g'");

        no++;
        _sAbcMap.put(no, "g'");
        _fAbcMap.put(no, "g'");

        no++;
        _sAbcMap.put(no, "^g'");
        _fAbcMap.put(no, "_a'");

        no++;
        _sAbcMap.put(no, "a'");
        _fAbcMap.put(no, "a'");

        no++;
        _sAbcMap.put(no, "^a'");
        _fAbcMap.put(no, "_b'");

        no++;
        _sAbcMap.put(no, "b'");
        _fAbcMap.put(no, "b'");
    }

    public static void initRootnameMap(Map<String, Integer> _rootnameRootnoMap, Map<String, Map<Integer, String>> _rootnameAbcmapMap) {
        _rootnameRootnoMap.put("A", 97);
        _rootnameAbcmapMap.put("A", sAbcMap);
        _rootnameRootnoMap.put("Ab", 97);
        _rootnameAbcmapMap.put("Ab", getFlatMap(sAbcMap));
        _rootnameRootnoMap.put("Ash", 97);
        _rootnameAbcmapMap.put("Ash", getSharpMap(sAbcMap));
        _rootnameRootnoMap.put("B", 99);
        _rootnameAbcmapMap.put("B", sAbcMap);
        _rootnameRootnoMap.put("Bb", 99);
        _rootnameAbcmapMap.put("Bb", getFlatMap(sAbcMap));
        _rootnameRootnoMap.put("Bsh", 99);
        _rootnameAbcmapMap.put("Bsh", getSharpMap(sAbcMap));
        _rootnameRootnoMap.put("C", 100);
        _rootnameAbcmapMap.put("C", sAbcMap);
        _rootnameRootnoMap.put("Cb", 100);
        _rootnameAbcmapMap.put("Cb", getFlatMap(sAbcMap));
        _rootnameRootnoMap.put("Csh", 100);
        _rootnameAbcmapMap.put("Csh", getSharpMap(sAbcMap));
        _rootnameRootnoMap.put("D", 102);
        _rootnameAbcmapMap.put("D", sAbcMap);
        _rootnameRootnoMap.put("Db", 102);
        _rootnameAbcmapMap.put("Db", getFlatMap(sAbcMap));
        _rootnameRootnoMap.put("Dsh", 102);
        _rootnameAbcmapMap.put("Dsh", getSharpMap(sAbcMap));
        _rootnameRootnoMap.put("E", 104);
        _rootnameAbcmapMap.put("E", sAbcMap);
        _rootnameRootnoMap.put("Eb", 104);
        _rootnameAbcmapMap.put("Eb", getFlatMap(sAbcMap));
        _rootnameRootnoMap.put("Esh", 104);
        _rootnameAbcmapMap.put("Esh", getSharpMap(sAbcMap));
        _rootnameRootnoMap.put("F", 105);
        _rootnameAbcmapMap.put("F", sAbcMap);
        _rootnameRootnoMap.put("Fb", 105);
        _rootnameAbcmapMap.put("Fb", getFlatMap(sAbcMap));
        _rootnameRootnoMap.put("Fsh", 105);
        _rootnameAbcmapMap.put("Fsh", getSharpMap(sAbcMap));
        _rootnameRootnoMap.put("G", 107);
        _rootnameAbcmapMap.put("G", sAbcMap);
        _rootnameRootnoMap.put("Gb", 107);
        _rootnameAbcmapMap.put("Gb", getFlatMap(sAbcMap));
        _rootnameRootnoMap.put("Gsh", 107);
        _rootnameAbcmapMap.put("Gsh", getSharpMap(sAbcMap));
    }
}
