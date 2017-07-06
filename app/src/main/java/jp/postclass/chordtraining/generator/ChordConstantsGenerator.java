package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import jp.postclass.chordtraining.common.Constants;

public class ChordConstantsGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a");

    public static void main(String[] args) throws Exception{
        new ChordConstantsGenerator().start();
    }

    private void start() throws Exception {
        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "ChordConstants.java"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {

                writer.write("package jp.postclass.chordtraining.common;" + newLine
                        + newLine
                        + "/** auto generated.  */" + newLine
                        + "public final class ChordConstants {" + newLine);

                writer.write(newLine);
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

                writer.write("}");

                writer.flush();
            }
        }
    }

    private String getMajorString(String root) {
        return getTensionCombination(root, root)
                + getTensionCombination(root, root + "sus4")
                + getTensionCombination(root, root + "b5")
                + getTensionCombination(root, root + "aug")
                + getTensionCombination(root, root + "7")
                + getTensionCombination(root, root + "7b5")
                + getTensionCombination(root, root + "7sh5")
                + getTensionCombination(root, root + "7sus4")
                + getTensionCombination(root, root + "M7")
                + newLine
                ;
    }

    private String getMinorString(String root) {
        return getTensionCombination(root + "m", root + "m")
                + getTensionCombination(root + "m", root + "dim")
                + getTensionCombination(root + "m", root + "msh5")
                + getTensionCombination(root + "m", root + "m7")
                + getTensionCombination(root + "m", root + "m7b5")
                + getTensionCombination(root + "m", root + "m7sh5")
                + getTensionCombination(root + "m", root + "mM7")
                + newLine
                ;
    }

    private String getOneString(String root, String chord) {
        String name = root + "__" + chord;
        return "    public static final String CHORD_" + name + " = \"" + name + "\";" + newLine;
    }

    private String getTensionCombination(String root, String chord) {
        
        String result = "";

//        for (int i6 = Constants.TENSION_NONE; i6 <= Constants.TENSION_FL; i6++) {
//            for (int i9 = Constants.TENSION_NONE; i9 <= Constants.TENSION_FL; i9++) {
//                for (int i11 = Constants.TENSION_NONE; i11 <= Constants.TENSION_FL; i11++) {
//                    for (int i13 = Constants.TENSION_NONE; i13 <= Constants.TENSION_FL; i13++) {
//
//                        String tensionString = getTension(i6, i9, i11, i13);
//
//                        result += getOneString(root, chord + tensionString);
//                    }
//                }
//            }
//        }

        result += getOneString(root, chord);

        return result;
    }
}
