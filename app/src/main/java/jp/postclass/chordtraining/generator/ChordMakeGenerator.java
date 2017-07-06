package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import jp.postclass.chordtraining.common.Constants;
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
                + getTensionCombination(root)
                + getTensionCombination(root + "sus4")
                + getTensionCombination(root + "b5")
                + getTensionCombination(root + "aug")
                + getTensionCombination(root + "7")
                + getTensionCombination(root + "7b5")
                + getTensionCombination(root + "7sh5")
                + getTensionCombination(root + "7sus4")
                + getTensionCombination(root + "M7")

//
//                + newLine
//                + getMdString(root)
//                + getMdString(root + "sus4")
//                + getMdString(root + "f5")
//                + getMdString(root + "aug")
//                + getMdString(root + "6")
//                + getMdString(root + "7")
//                + getMdString(root + "7f5")
//                + getMdString(root + "7sus4")
//                + getMdString(root + "7sus4_9")
//                + getMdString(root + "M7")
//                + getMdString(root + "M7_9")
//                + getMdString(root + "9")
//                + getMdString(root + "11")
//                + newLine
//                + newLine
                + newLine
                ;
    }

    private String getMinorString(String root) {
        return baseDrive + newLine
                + "cd " + baseDir.getAbsolutePath() + "\\chord\\root\\" + root + "m" + newLine
                + newLine
                + getTensionCombination(root + "m")
                + getTensionCombination(root + "dim")
                + getTensionCombination(root + "msh5")
                + getTensionCombination(root + "m7")
                + getTensionCombination(root + "m7b5")
                + getTensionCombination(root + "m7sh5")
                + getTensionCombination(root + "mM7")

//                + newLine
//                + getMdString(root + "m")
//                + getMdString(root + "dim")
//                + getMdString(root + "ms5")
//                + getMdString(root + "m6")
//                + getMdString(root + "m7")
//                + getMdString(root + "m7f5")
//                + getMdString(root + "mM7")
//                + getMdString(root + "mM7_9")
//                + getMdString(root + "m9")
//                + getMdString(root + "m11")
//                + newLine
//                + newLine
                + newLine
                ;
    }

    private String getSvgString(String chord) {
        return psPath + " -v " + chord + ".abc -O " + chord + "_.svg" + newLine;
    }

    private String getMdString(String chord) {
        return mdPath + " " + chord + ".abc -o " + chord + ".mid" + newLine;
    }


    private String getTensionCombination(String chord) {

        String result = "";

        for (int i6 = Constants.TENSION_NONE; i6 <= Constants.TENSION_FL; i6++) {
            for (int i9 = Constants.TENSION_NONE; i9 <= Constants.TENSION_FL; i9++) {
                for (int i11 = Constants.TENSION_NONE; i11 <= Constants.TENSION_FL; i11++) {
                    for (int i13 = Constants.TENSION_NONE; i13 <= Constants.TENSION_FL; i13++) {
                        String tensionString = UtCommon.getTensionChordName(i6, i9, i11, i13);
                        result += getSvgString(chord + tensionString);
                    }
                }
            }
        }

        for (int i6 = Constants.TENSION_NONE; i6 <= Constants.TENSION_FL; i6++) {
            for (int i9 = Constants.TENSION_NONE; i9 <= Constants.TENSION_FL; i9++) {
                for (int i11 = Constants.TENSION_NONE; i11 <= Constants.TENSION_FL; i11++) {
                    for (int i13 = Constants.TENSION_NONE; i13 <= Constants.TENSION_FL; i13++) {
                        String tensionString = UtCommon.getTensionChordName(i6, i9, i11, i13);
                        result += getMdString(chord + tensionString);
                    }
                }
            }
        }
        
        result += newLine;

        return result;
    }


    
}
