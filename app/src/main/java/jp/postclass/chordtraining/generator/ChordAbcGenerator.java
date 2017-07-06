package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import jp.postclass.chordtraining.common.Constants;

public class ChordAbcGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a/chord/root");

    private Map<Integer, String> sMap = new HashMap<>();
    private Map<Integer, String> fMap = new HashMap<>();

    public static void main(String[] args) throws Exception{
        new ChordAbcGenerator().start();

    }

    private void start() throws Exception {
        getAbcMap();
        writeChordMj("A", 97, sMap);
        writeChordMn("A", 97, sMap);
        writeChordMj("Ab", 97, getFlatMap(sMap));
        writeChordMn("Ab", 97, getFlatMap(sMap));
        writeChordMj("Ash", 97, getSharpMap(sMap));
        writeChordMn("Ash", 97, getSharpMap(sMap));
        writeChordMj("B", 99, sMap);
        writeChordMn("B", 99, sMap);
        writeChordMj("Bb", 99, getFlatMap(sMap));
        writeChordMn("Bb", 99, getFlatMap(sMap));
        writeChordMj("Bsh", 99, getSharpMap(sMap));
        writeChordMn("Bsh", 99, getSharpMap(sMap));
        writeChordMj("C", 100, sMap);
        writeChordMn("C", 100, sMap);
        writeChordMj("Cb", 100, getFlatMap(sMap));
        writeChordMn("Cb", 100, getFlatMap(sMap));
        writeChordMj("Csh", 100, getSharpMap(sMap));
        writeChordMn("Csh", 100, getSharpMap(sMap));
        writeChordMj("D", 102, sMap);
        writeChordMn("D", 102, sMap);
        writeChordMj("Db", 102, getFlatMap(sMap));
        writeChordMn("Db", 102, getFlatMap(sMap));
        writeChordMj("Dsh", 102, getSharpMap(sMap));
        writeChordMn("Dsh", 102, getSharpMap(sMap));
        writeChordMj("E", 104, sMap);
        writeChordMn("E", 104, sMap);
        writeChordMj("Eb", 104, getFlatMap(sMap));
        writeChordMn("Eb", 104, getFlatMap(sMap));
        writeChordMj("Esh", 104, getSharpMap(sMap));
        writeChordMn("Esh", 104, getSharpMap(sMap));
        writeChordMj("F", 105, sMap);
        writeChordMn("F", 105, sMap);
        writeChordMj("Fb", 105, getFlatMap(sMap));
        writeChordMn("Fb", 105, getFlatMap(sMap));
        writeChordMj("Fsh", 105, getSharpMap(sMap));
        writeChordMn("Fsh", 105, getSharpMap(sMap));
        writeChordMj("G", 107, sMap);
        writeChordMn("G", 107, sMap);
        writeChordMj("Gb", 107, getFlatMap(sMap));
        writeChordMn("Gb", 107, getFlatMap(sMap));
        writeChordMj("Gsh", 107, getSharpMap(sMap));
        writeChordMn("Gsh", 107, getSharpMap(sMap));
    }

    public void writeChordMj(String rootName, int rootNo, Map<Integer, String> baseMap) throws Exception {

        File dir = new File(baseDir, rootName);
        dir.mkdir();

        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            writeTensionCombination(rootName, baseChordAbc, rootNo, baseMap, dir);
        }

        // sus4
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 5);
            baseChordAbc += baseMap.get(rootNo + 7);
            writeTensionCombination(rootName + "sus4", baseChordAbc, rootNo, baseMap, dir);
        }


        // -5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            writeTensionCombination(rootName + "b5", baseChordAbc, rootNo, baseMap, dir);
        }


        // aug
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getSharp(baseMap, rootNo + 7);
            writeTensionCombination(rootName + "aug", baseChordAbc, rootNo, baseMap, dir);
        }

        // 7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeTensionCombination(rootName + "7", baseChordAbc, rootNo, baseMap, dir);
        }

        // 7-5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeTensionCombination(rootName + "7b5", baseChordAbc, rootNo, baseMap, dir);
        }

        // 7+5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getSharp(baseMap, rootNo + 11);
            writeTensionCombination(rootName + "7sh5", baseChordAbc, rootNo, baseMap, dir);
        }

        // 7sus4
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 5);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeTensionCombination(rootName + "7sus4", baseChordAbc, rootNo, baseMap, dir);
        }

        // M7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += baseMap.get(rootNo + 11);
            writeTensionCombination(rootName + "M7", baseChordAbc, rootNo, baseMap, dir);
        }

    }

    public void writeChordMn(String rootName, int rootNo, Map<Integer, String> baseMap) throws Exception {

        File dir = new File(baseDir, rootName + "m");
        dir.mkdir();

        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            writeTensionCombination(rootName + "m", baseChordAbc, rootNo, baseMap, dir);
        }

        // -5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            writeTensionCombination(rootName + "dim", baseChordAbc, rootNo, baseMap, dir);
        }

        // +5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getSharp(baseMap, rootNo + 7);
            writeTensionCombination(rootName + "msh5", baseChordAbc, rootNo, baseMap, dir);
        }

        // 7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeTensionCombination(rootName + "m7", baseChordAbc, rootNo, baseMap, dir);
        }

        // 7-5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeTensionCombination(rootName + "m7b5", baseChordAbc, rootNo, baseMap, dir);
        }

        // 7+5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getSharp(baseMap, rootNo + 11);
            writeTensionCombination(rootName + "m7sh5", baseChordAbc, rootNo, baseMap, dir);
        }

        // M7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += baseMap.get(rootNo + 11);
            writeTensionCombination(rootName + "mM7", baseChordAbc, rootNo, baseMap, dir);
        }
    }


    private void writeTensionCombination(String baseCodeName, String baseCodeAbc, int rootNo, Map<Integer, String> baseMap, File dir) throws Exception {
        
        for (int i6 = Constants.TENSION_NONE; i6 <= Constants.TENSION_FL; i6++) {
            for (int i9 = Constants.TENSION_NONE; i9 <= Constants.TENSION_FL; i9++) {
                for (int i11 = Constants.TENSION_NONE; i11 <= Constants.TENSION_FL; i11++) {
                    for (int i13 = Constants.TENSION_NONE; i13 <= Constants.TENSION_FL; i13++) {
                        
                        NameAndAbc nameAbcAdd = getTension(rootNo, baseMap, i6, i9, i11, i13);

                        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, baseCodeName + nameAbcAdd.addName + ".abc"))) {
                            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                                String fileString = getHeader();
                                fileString += "[";
                                fileString += baseCodeAbc;
                                fileString += nameAbcAdd.addAbc;
                                fileString += "]4 |";

                                writer.write(fileString);
                                writer.flush();
                            }
                        }
                    }
                }
            }
        }
    }

    private class NameAndAbc {
        public String addName = "";
        public String addAbc = "";
    }
    
    private NameAndAbc getTension(int rootNo, Map<Integer, String> baseMap, int t6, int t9, int t11, int t13) throws Exception {

        NameAndAbc result = new NameAndAbc();

        switch (t6) {
            case Constants.TENSION_NATURAL:
                result.addName += "_6";
                result.addAbc += baseMap.get(rootNo + 9);
                break;
            case Constants.TENSION_SH:
                result.addName += "_sh6";
                result.addAbc += getSharp(baseMap, rootNo + 9);
                break;
            case Constants.TENSION_FL:
                result.addName += "_b6";
                result.addAbc += getFlat(baseMap, rootNo + 9);
                break;
        }

        switch (t9) {
            case Constants.TENSION_NATURAL:
                result.addName += "_9";
                result.addAbc += baseMap.get(rootNo + 14);
                break;
            case Constants.TENSION_SH:
                result.addName += "_sh9";
                result.addAbc += getSharp(baseMap, rootNo + 14);
                break;
            case Constants.TENSION_FL:
                result.addName += "_b9";
                result.addAbc += getFlat(baseMap, rootNo + 14);
                break;
        }

        switch (t11) {
            case Constants.TENSION_NATURAL:
                result.addName += "_11";
                result.addAbc += baseMap.get(rootNo + 17);
                break;
            case Constants.TENSION_SH:
                result.addName += "_sh11";
                result.addAbc += getSharp(baseMap, rootNo + 17);
                break;
            case Constants.TENSION_FL:
                result.addName += "_b11";
                result.addAbc += getFlat(baseMap, rootNo + 17);
                break;
        }

        switch (t13) {
            case Constants.TENSION_NATURAL:
                result.addName += "_13";
                result.addAbc += baseMap.get(rootNo + 21);
                break;
            case Constants.TENSION_SH:
                result.addName += "_sh13";
                result.addAbc += getSharp(baseMap, rootNo + 21);
                break;
            case Constants.TENSION_FL:
                result.addName += "_b13";
                result.addAbc += getFlat(baseMap, rootNo + 21);
                break;
        }
        
        return result;
    }



    private Map<Integer, String> getFlatMap(Map<Integer, String> baseMap) {
        Map<Integer, String> result = new HashMap<>();

        for (Integer key : baseMap.keySet()) {
            result.put(key, getFlat(baseMap, key));
        }

        return result;
    }

    private Map<Integer, String> getSharpMap(Map<Integer, String> baseMap) {
        Map<Integer, String> result = new HashMap<>();

        for (Integer key : baseMap.keySet()) {
            result.put(key, getSharp(baseMap, key));
        }

        return result;
    }

    private String getFlat(Map<Integer, String> baseMap, int no) {
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

    private String getSharp(Map<Integer, String> baseMap, int no) {
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

    private String getHeader() {
        return "I:abc-charset utf-8" + newLine
                + "X:1" + newLine
                + "T:" + newLine
                + "M:4/4" + newLine
                + "L:1/8" + newLine
                + "K:C treble" + newLine
                + "%" + newLine;
    }

    private void getAbcMap() {
        int no = 88;

        sMap.put(no, "C,");
        fMap.put(no, "C,");

        no++;
        sMap.put(no, "^C,");
        fMap.put(no, "_C,");

        no++;
        sMap.put(no, "D,");
        fMap.put(no, "D,");

        no++;
        sMap.put(no, "^D,");
        fMap.put(no, "_E,");

        no++;
        sMap.put(no, "E,");
        fMap.put(no, "E,");

        no++;
        sMap.put(no, "F,");
        fMap.put(no, "F,");

        no++;
        sMap.put(no, "^F,");
        fMap.put(no, "_G,");

        no++;
        sMap.put(no, "G,");
        fMap.put(no, "G,");

        no++;
        sMap.put(no, "^G,");
        fMap.put(no, "_A,");

        no++;
        sMap.put(no, "A,");
        fMap.put(no, "A,");

        no++;
        sMap.put(no, "^A,");
        fMap.put(no, "_B,");

        no++;
        sMap.put(no, "B,");
        fMap.put(no, "B,");


        no++; // 100
        sMap.put(no, "C");
        fMap.put(no, "C");

        no++;
        sMap.put(no, "^C");
        fMap.put(no, "_C");

        no++;
        sMap.put(no, "D");
        fMap.put(no, "D");

        no++;
        sMap.put(no, "^D");
        fMap.put(no, "_E");

        no++;
        sMap.put(no, "E");
        fMap.put(no, "E");

        no++;
        sMap.put(no, "F");
        fMap.put(no, "F");

        no++;
        sMap.put(no, "^F");
        fMap.put(no, "_G");

        no++;
        sMap.put(no, "G");
        fMap.put(no, "G");

        no++;
        sMap.put(no, "^G");
        fMap.put(no, "_A");

        no++;
        sMap.put(no, "A");
        fMap.put(no, "A");

        no++;
        sMap.put(no, "^A");
        fMap.put(no, "_B");

        no++;
        sMap.put(no, "B");
        fMap.put(no, "B");


        no++; // 124
        sMap.put(no, "c");
        fMap.put(no, "c");

        no++;
        sMap.put(no, "^c");
        fMap.put(no, "_c");

        no++;
        sMap.put(no, "d");
        fMap.put(no, "d");

        no++;
        sMap.put(no, "^d");
        fMap.put(no, "_e");

        no++;
        sMap.put(no, "e");
        fMap.put(no, "e");

        no++;
        sMap.put(no, "f");
        fMap.put(no, "f");

        no++;
        sMap.put(no, "^f");
        fMap.put(no, "_g");

        no++;
        sMap.put(no, "g");
        fMap.put(no, "g");

        no++;
        sMap.put(no, "^g");
        fMap.put(no, "_a");

        no++;
        sMap.put(no, "a");
        fMap.put(no, "a");

        no++;
        sMap.put(no, "^a");
        fMap.put(no, "_b");

        no++;
        sMap.put(no, "b");
        fMap.put(no, "b");


        no++; // 148
        sMap.put(no, "c'");
        fMap.put(no, "c'");

        no++;
        sMap.put(no, "^c'");
        fMap.put(no, "_c'");

        no++;
        sMap.put(no, "d'");
        fMap.put(no, "d'");

        no++;
        sMap.put(no, "^d'");
        fMap.put(no, "_e'");

        no++;
        sMap.put(no, "e'");
        fMap.put(no, "e'");

        no++;
        sMap.put(no, "f'");
        fMap.put(no, "f'");

        no++;
        sMap.put(no, "^f'");
        fMap.put(no, "_g'");

        no++;
        sMap.put(no, "g'");
        fMap.put(no, "g'");

        no++;
        sMap.put(no, "^g'");
        fMap.put(no, "_a'");

        no++;
        sMap.put(no, "a'");
        fMap.put(no, "a'");

        no++;
        sMap.put(no, "^a'");
        fMap.put(no, "_b'");

        no++;
        sMap.put(no, "b'");
        fMap.put(no, "b'");
    }


}
