package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

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
                writer.write(getMajorString("As"));
                writer.write(getMinorString("As"));
                writer.write(getMajorString("B"));
                writer.write(getMinorString("B"));
                writer.write(getMajorString("Bb"));
                writer.write(getMinorString("Bb"));
                writer.write(getMajorString("Bs"));
                writer.write(getMinorString("Bs"));
                writer.write(getMajorString("C"));
                writer.write(getMinorString("C"));
                writer.write(getMajorString("Cb"));
                writer.write(getMinorString("Cb"));
                writer.write(getMajorString("Cs"));
                writer.write(getMinorString("Cs"));
                writer.write(getMajorString("D"));
                writer.write(getMinorString("D"));
                writer.write(getMajorString("Db"));
                writer.write(getMinorString("Db"));
                writer.write(getMajorString("Ds"));
                writer.write(getMinorString("Ds"));
                writer.write(getMajorString("E"));
                writer.write(getMinorString("E"));
                writer.write(getMajorString("Eb"));
                writer.write(getMinorString("Eb"));
                writer.write(getMajorString("Es"));
                writer.write(getMinorString("Es"));
                writer.write(getMajorString("F"));
                writer.write(getMinorString("F"));
                writer.write(getMajorString("Fb"));
                writer.write(getMinorString("Fb"));
                writer.write(getMajorString("Fs"));
                writer.write(getMinorString("Fs"));
                writer.write(getMajorString("G"));
                writer.write(getMinorString("G"));
                writer.write(getMajorString("Gb"));
                writer.write(getMinorString("Gb"));
                writer.write(getMajorString("Gs"));
                writer.write(getMinorString("Gs"));

                writer.write("}");

                writer.flush();
            }
        }
    }

    private String getMajorString(String root) {
        return getOneString(root, root)
                + getOneString(root, root + "sus4")
                + getOneString(root, root + "f5")
                + getOneString(root, root + "aug")
                + getOneString(root, root + "6")
                + getOneString(root, root + "7")
                + getOneString(root, root + "M7")
                + getOneString(root, root + "add9")
                + getOneString(root, root + "11")
                + newLine
                ;
    }

    private String getMinorString(String root) {
        return getOneString(root + "m", root + "m")
                + getOneString(root + "m", root + "dim")
                + getOneString(root + "m", root + "ms5")
                + getOneString(root + "m", root + "m6")
                + getOneString(root + "m", root + "m7")
                + getOneString(root + "m", root + "m7f5")
                + getOneString(root + "m", root + "mM7")
                + getOneString(root + "m", root + "madd9")
                + getOneString(root + "m", root + "m11")
                + newLine
                ;
    }

    private String getOneString(String root, String chord) {
        String name = root + "_" + chord;
        return "    public static final String CODE_" + name + " = \"" + name + "\";" + newLine;
    }


}
