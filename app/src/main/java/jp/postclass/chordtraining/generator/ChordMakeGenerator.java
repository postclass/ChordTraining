package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import static jp.postclass.chordtraining.common.Constants.*;
import jp.postclass.chordtraining.common.UtCommon;

public class ChordMakeGenerator {

    private static final String newLine = "\r\n";
    private static final String baseDrive = "D:";
    private static final File baseDir = new File("D:/tmp/a");
    private static final String psPath = "\"C:\\Program Files (x86)\\EasyABC\\bin\\abcm2ps.exe\"";
    private static final String mdPath = "\"C:\\Program Files (x86)\\EasyABC\\bin\\abc2midi.exe\"";

    public static void main(String[] args) throws Exception{
        new ChordMakeGenerator().start();
    }

    private void start() throws Exception {
        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "make.bat"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                writer.write(getMajorString("A"));
                writer.write(getMinorString("A"));
                writer.write(getMajorString("Ab"));
                writer.write(getMinorString("Ab"));
                writer.write(getMajorString("Ash"));
                writer.write(getMinorString("Ash"));
                writer.write(getMajorString("B"));
                writer.write(getMinorString("B"));
                writer.write(getMajorString("Bb"));
                writer.write(getMinorString("Bb"));
                writer.write(getMajorString("Bsh"));
                writer.write(getMinorString("Bsh"));
                writer.write(getMajorString("C"));
                writer.write(getMinorString("C"));
                writer.write(getMajorString("Cb"));
                writer.write(getMinorString("Cb"));
                writer.write(getMajorString("Csh"));
                writer.write(getMinorString("Csh"));
                writer.write(getMajorString("D"));
                writer.write(getMinorString("D"));
                writer.write(getMajorString("Db"));
                writer.write(getMinorString("Db"));
                writer.write(getMajorString("Dsh"));
                writer.write(getMinorString("Dsh"));
                writer.write(getMajorString("E"));
                writer.write(getMinorString("E"));
                writer.write(getMajorString("Eb"));
                writer.write(getMinorString("Eb"));
                writer.write(getMajorString("Esh"));
                writer.write(getMinorString("Esh"));
                writer.write(getMajorString("F"));
                writer.write(getMinorString("F"));
                writer.write(getMajorString("Fb"));
                writer.write(getMinorString("Fb"));
                writer.write(getMajorString("Fsh"));
                writer.write(getMinorString("Fsh"));
                writer.write(getMajorString("G"));
                writer.write(getMinorString("G"));
                writer.write(getMajorString("Gb"));
                writer.write(getMinorString("Gb"));
                writer.write(getMajorString("Gsh"));
                writer.write(getMinorString("Gsh"));
                writer.flush();
            }
        }
    }

    private String getMajorString(String root) {
        return baseDrive + newLine
                + "cd " + baseDir.getAbsolutePath() + "\\chord\\root\\" + root + newLine
                + newLine
                + getAbgAndMdString(root)
                + getAbgAndMdString(root + "sus4")
                + getAbgAndMdString(root + "b5")
                + getAbgAndMdString(root + "aug")
                + getAbgAndMdString(root + "7")
                + getAbgAndMdString(root + "7b5")
                + getAbgAndMdString(root + "7sh5")
                + getAbgAndMdString(root + "7sus4")
                + getAbgAndMdString(root + "M7")
                + getAbgAndMdString(root + UtCommon.getTensionChordName(TENSION_NATURAL, TENSION_NONE, TENSION_NONE, TENSION_NONE))
                + getAbgAndMdString(root + UtCommon.getTensionChordName(TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE))
                + getAbgAndMdString(root + UtCommon.getTensionChordName(TENSION_NONE, TENSION_NONE, TENSION_NATURAL, TENSION_NONE))
                + getAbgAndMdString(root + UtCommon.getTensionChordName(TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL))
                + newLine
                ;
    }

    private String getMinorString(String root) {
        return baseDrive + newLine
                + "cd " + baseDir.getAbsolutePath() + "\\chord\\root\\" + root + "m" + newLine
                + newLine
                + getAbgAndMdString(root + "m")
                + getAbgAndMdString(root + "dim")
                + getAbgAndMdString(root + "msh5")
                + getAbgAndMdString(root + "m7")
                + getAbgAndMdString(root + "m7b5")
                + getAbgAndMdString(root + "m7sh5")
                + getAbgAndMdString(root + "mM7")
                + getAbgAndMdString(root + "m" + UtCommon.getTensionChordName(TENSION_NATURAL, TENSION_NONE, TENSION_NONE, TENSION_NONE))
                + getAbgAndMdString(root + "m" + UtCommon.getTensionChordName(TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE))
                + getAbgAndMdString(root + "m" + UtCommon.getTensionChordName(TENSION_NONE, TENSION_NONE, TENSION_NATURAL, TENSION_NONE))
                + getAbgAndMdString(root + "m" + UtCommon.getTensionChordName(TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL))
                + newLine
                ;
    }

    private String getSvgString(String chord) {
        return psPath + " -v " + chord + ".abc -O " + chord + "_.svg" + newLine;
    }

    private String getMdString(String chord) {
        return mdPath + " " + chord + ".abc -o " + chord + ".mid" + newLine;
    }


    private String getAbgAndMdString(String chord) {
        String result = "";
        result += getSvgString(chord );
        result += getMdString(chord );
        result += newLine;
        return result;
    }

//    private String getTensionCombination(String chord) {
//
//        String result = "";
//
//        for (int i6 = Constants.TENSION_NONE; i6 <= Constants.TENSION_FL; i6++) {
//            for (int i9 = Constants.TENSION_NONE; i9 <= Constants.TENSION_FL; i9++) {
//                for (int i11 = Constants.TENSION_NONE; i11 <= Constants.TENSION_FL; i11++) {
//                    for (int i13 = Constants.TENSION_NONE; i13 <= Constants.TENSION_FL; i13++) {
//                        String tensionString = UtCommon.getTensionChordName(i6, i9, i11, i13);
//                        result += getSvgString(chord + tensionString);
//                    }
//                }
//            }
//        }
//
//        for (int i6 = Constants.TENSION_NONE; i6 <= Constants.TENSION_FL; i6++) {
//            for (int i9 = Constants.TENSION_NONE; i9 <= Constants.TENSION_FL; i9++) {
//                for (int i11 = Constants.TENSION_NONE; i11 <= Constants.TENSION_FL; i11++) {
//                    for (int i13 = Constants.TENSION_NONE; i13 <= Constants.TENSION_FL; i13++) {
//                        String tensionString = UtCommon.getTensionChordName(i6, i9, i11, i13);
//                        result += getMdString(chord + tensionString);
//                    }
//                }
//            }
//        }
//
//        result += newLine;
//
//        return result;
//    }


    
}
