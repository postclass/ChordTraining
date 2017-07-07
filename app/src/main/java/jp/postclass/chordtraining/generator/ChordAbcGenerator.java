package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import static jp.postclass.chordtraining.common.Constants.*;
import static jp.postclass.chordtraining.common.UtAbc.*;

public class ChordAbcGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a/chord/root");

    private Map<Integer, String> sMap = new HashMap<>();
    private Map<Integer, String> fMap = new HashMap<>();
    public static Map<String, Integer> rootnameRootnoMap = new HashMap<>();
    public static Map<String, Map<Integer, String>> rootnameAbcmapMap = new HashMap<>();

    public static void main(String[] args) throws Exception{
        new ChordAbcGenerator().start();
    }

    private void start() throws Exception {
        initAbcMap(sMap, fMap);
        initRootnameMap(rootnameRootnoMap, rootnameAbcmapMap);

        writeChordMj("A");
        writeChordMn("A");
        writeChordMj("Ab");
        writeChordMn("Ab");
        writeChordMj("Ash");
        writeChordMn("Ash");
        writeChordMj("B");
        writeChordMn("B");
        writeChordMj("Bb");
        writeChordMn("Bb");
        writeChordMj("Bsh");
        writeChordMn("Bsh");
        writeChordMj("C");
        writeChordMn("C");
        writeChordMj("Cb");
        writeChordMn("Cb");
        writeChordMj("Csh");
        writeChordMn("Csh");
        writeChordMj("D");
        writeChordMn("D");
        writeChordMj("Db");
        writeChordMn("Db");
        writeChordMj("Dsh");
        writeChordMn("Dsh");
        writeChordMj("E");
        writeChordMn("E");
        writeChordMj("Eb");
        writeChordMn("Eb");
        writeChordMj("Esh");
        writeChordMn("Esh");
        writeChordMj("F");
        writeChordMn("F");
        writeChordMj("Fb");
        writeChordMn("Fb");
        writeChordMj("Fsh");
        writeChordMn("Fsh");
        writeChordMj("G");
        writeChordMn("G");
        writeChordMj("Gb");
        writeChordMn("Gb");
        writeChordMj("Gsh");
        writeChordMn("Gsh");
    }

    public void writeChordMj(String rootName) throws Exception {

        int rootNo = rootnameRootnoMap.get(rootName);
        Map<Integer, String> baseMap = rootnameAbcmapMap.get(rootName);

        File dir = new File(baseDir, rootName);
        dir.mkdir();

        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            writeFile(rootName, baseChordAbc, dir);
        }

        // sus4
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 5);
            baseChordAbc += baseMap.get(rootNo + 7);
            writeFile(rootName + "sus4", baseChordAbc, dir);
        }


        // -5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            writeFile(rootName + "b5", baseChordAbc, dir);
        }


        // aug
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getSharp(baseMap, rootNo + 7);
            writeFile(rootName + "aug", baseChordAbc, dir);
        }

        // 7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeFile(rootName + "7", baseChordAbc, dir);
        }

        // 7-5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeFile(rootName + "7b5", baseChordAbc, dir);
        }

        // 7+5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getSharp(baseMap, rootNo + 11);
            writeFile(rootName + "7sh5", baseChordAbc, dir);
        }

        // 7sus4
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 5);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeFile(rootName + "7sus4", baseChordAbc, dir);
        }

        // M7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += baseMap.get(rootNo + 11);
            writeFile(rootName + "M7", baseChordAbc, dir);
        }


        // tension
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += baseMap.get(rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            
            //6
            String chordName6 = rootName + "_6";
            String chordAbc6 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, TENSION_NONE);
            writeFile(chordName6, chordAbc6, dir);

            //9
            String chordName9 = rootName + "_9";
            String chordAbc9 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE);
            writeFile(chordName9, chordAbc9, dir);

            //11
            String chordName11 = rootName + "_11";
            String chordAbc11 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, TENSION_NONE);
            writeFile(chordName11, chordAbc11, dir);

            //13
            String chordName13 = rootName + "_13";
            String chordAbc13 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL);
            writeFile(chordName13, chordAbc13, dir);
        }
    }

    public void writeChordMn(String rootName) throws Exception {

        int rootNo = rootnameRootnoMap.get(rootName);
        Map<Integer, String> baseMap = rootnameAbcmapMap.get(rootName);

        File dir = new File(baseDir, rootName + "m");
        dir.mkdir();

        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            writeFile(rootName + "m", baseChordAbc, dir);
        }

        // -5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            writeFile(rootName + "dim", baseChordAbc, dir);
        }

        // +5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getSharp(baseMap, rootNo + 7);
            writeFile(rootName + "msh5", baseChordAbc, dir);
        }

        // 7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeFile(rootName + "m7", baseChordAbc, dir);
        }

        // 7-5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getFlat(baseMap, rootNo + 11);
            writeFile(rootName + "m7b5", baseChordAbc, dir);
        }

        // 7+5
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += getFlat(baseMap, rootNo + 7);
            baseChordAbc += getSharp(baseMap, rootNo + 11);
            writeFile(rootName + "m7sh5", baseChordAbc, dir);
        }

        // M7
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);
            baseChordAbc += baseMap.get(rootNo + 11);
            writeFile(rootName + "mM7", baseChordAbc, dir);
        }


        // tension
        {
            String baseChordAbc = baseMap.get(rootNo);
            baseChordAbc += getFlat(baseMap, rootNo + 4);
            baseChordAbc += baseMap.get(rootNo + 7);

            //6
            String chordName6 = rootName + "m_6";
            String chordAbc6 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, TENSION_NONE);
            writeFile(chordName6, chordAbc6, dir);

            //9
            String chordName9 = rootName + "m_9";
            String chordAbc9 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE);
            writeFile(chordName9, chordAbc9, dir);

            //11
            String chordName11 = rootName + "m_11";
            String chordAbc11 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, TENSION_NONE);
            writeFile(chordName11, chordAbc11, dir);

            //13
            String chordName13 = rootName + "m_13";
            String chordAbc13 = baseChordAbc + getTensionAbc(rootNo, baseMap, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL);
            writeFile(chordName13, chordAbc13, dir);
        }
    }


//    private void writeTensionCombination(String baseChordName, String baseChordAbc, int rootNo, Map<Integer, String> baseMap, File dir) throws Exception {
//
//        for (int i6 = TENSION_NONE; i6 <= TENSION_FL; i6++) {
//            for (int i9 = TENSION_NONE; i9 <= TENSION_FL; i9++) {
//                for (int i11 = TENSION_NONE; i11 <= TENSION_FL; i11++) {
//                    for (int i13 = TENSION_NONE; i13 <= TENSION_FL; i13++) {
//
//                        NameAndAbc nameAbcAdd = getTension(rootNo, baseMap, i6, i9, i11, i13);
//
//                        writeFile(baseChordName + nameAbcAdd.addName, baseChordAbc + nameAbcAdd.addAbc, dir);
//                    }
//                }
//            }
//        }
//    }

    private void writeFile(String chordName, String chordAbc, File dir) throws Exception {
        try (FileOutputStream outputStream = new FileOutputStream(new File(dir, chordName + ".abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String fileString = getHeader();
                fileString += "[";
                fileString += chordAbc;
                fileString += "]4 |";
                writer.write(fileString);
                writer.flush();
            }
        }
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

//
//    private class NameAndAbc {
//        public String addName = "";
//        public String addAbc = "";
//    }
//
//    private NameAndAbc getTension(int rootNo, Map<Integer, String> baseMap, int t6, int t9, int t11, int t13) throws Exception {
//
//        NameAndAbc result = new NameAndAbc();
//
//        switch (t6) {
//            case TENSION_NATURAL:
//                result.addName += "_6";
//                result.addAbc += baseMap.get(rootNo + 9);
//                break;
//            case TENSION_SH:
//                result.addName += "_sh6";
//                result.addAbc += getSharp(baseMap, rootNo + 9);
//                break;
//            case TENSION_FL:
//                result.addName += "_b6";
//                result.addAbc += getFlat(baseMap, rootNo + 9);
//                break;
//        }
//
//        switch (t9) {
//            case TENSION_NATURAL:
//                result.addName += "_9";
//                result.addAbc += baseMap.get(rootNo + 14);
//                break;
//            case TENSION_SH:
//                result.addName += "_sh9";
//                result.addAbc += getSharp(baseMap, rootNo + 14);
//                break;
//            case TENSION_FL:
//                result.addName += "_b9";
//                result.addAbc += getFlat(baseMap, rootNo + 14);
//                break;
//        }
//
//        switch (t11) {
//            case TENSION_NATURAL:
//                result.addName += "_11";
//                result.addAbc += baseMap.get(rootNo + 17);
//                break;
//            case TENSION_SH:
//                result.addName += "_sh11";
//                result.addAbc += getSharp(baseMap, rootNo + 17);
//                break;
//            case TENSION_FL:
//                result.addName += "_b11";
//                result.addAbc += getFlat(baseMap, rootNo + 17);
//                break;
//        }
//
//        switch (t13) {
//            case TENSION_NATURAL:
//                result.addName += "_13";
//                result.addAbc += baseMap.get(rootNo + 21);
//                break;
//            case TENSION_SH:
//                result.addName += "_sh13";
//                result.addAbc += getSharp(baseMap, rootNo + 21);
//                break;
//            case TENSION_FL:
//                result.addName += "_b13";
//                result.addAbc += getFlat(baseMap, rootNo + 21);
//                break;
//        }
//
//        return result;
//    }





}
