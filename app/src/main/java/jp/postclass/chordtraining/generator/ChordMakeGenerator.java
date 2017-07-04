package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

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
                writer.flush();
            }
        }
    }

    private String getMajorString(String root) {
        return baseDrive + newLine
                + "cd " + baseDir.getAbsolutePath() + "\\" + root + newLine
                + newLine
                + getSvgString(root)
                + getSvgString(root + "sus4")
                + getSvgString(root + "f5")
                + getSvgString(root + "aug")
                + getSvgString(root + "6")
                + getSvgString(root + "7")
                + getSvgString(root + "M7")
                + getSvgString(root + "add9")
                + getSvgString(root + "11")

                + newLine
                + getMdString(root)
                + getMdString(root + "sus4")
                + getMdString(root + "f5")
                + getMdString(root + "aug")
                + getMdString(root + "6")
                + getMdString(root + "7")
                + getMdString(root + "M7")
                + getMdString(root + "add9")
                + getMdString(root + "11")
                + newLine
                + newLine
                + newLine
                ;
    }

    private String getMinorString(String root) {
        return baseDrive + newLine
                + "cd " + baseDir.getAbsolutePath() + "\\" + root + "m" + newLine
                + newLine
                + getSvgString(root + "m")
                + getSvgString(root + "dim")
                + getSvgString(root + "ms5")
                + getSvgString(root + "m6")
                + getSvgString(root + "m7")
                + getSvgString(root + "m7f5")
                + getSvgString(root + "mM7")
                + getSvgString(root + "madd9")
                + getSvgString(root + "m11")

                + newLine
                + getMdString(root + "m")
                + getMdString(root + "dim")
                + getMdString(root + "ms5")
                + getMdString(root + "m6")
                + getMdString(root + "m7")
                + getMdString(root + "m7f5")
                + getMdString(root + "mM7")
                + getMdString(root + "madd9")
                + getMdString(root + "m11")
                + newLine
                + newLine
                + newLine
                ;
    }

    private String getSvgString(String chord) {
        return psPath + " -v " + chord + ".abc -O " + chord + "_.svg" + newLine;
    }

    private String getMdString(String chord) {
        return mdPath + " " + chord + ".abc -o " + chord + ".mid" + newLine;
    }

}
