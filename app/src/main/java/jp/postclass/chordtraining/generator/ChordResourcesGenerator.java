package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

public class ChordResourcesGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a");

    public static void main(String[] args) throws Exception{
        new ChordResourcesGenerator().start();
    }

    private void start() throws Exception {
        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "chordstrings.xml"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {

                writer.write("<!--auto generated.-->" + newLine);
                writer.write("<resources>" + newLine);

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

                writer.write("</resources>");

                writer.flush();
            }
        }
    }

    private String getMajorString(String root) {
        String rootLabel = root.replace('s', '#');
        
        return getOneString(root, root,rootLabel)
                + getOneString(root, root + "sus4",rootLabel + "sus4")
                + getOneString(root, root + "f5",rootLabel + "-5")
                + getOneString(root, root + "aug",rootLabel + "aug")
                + getOneString(root, root + "6",rootLabel + "6")
                + getOneString(root, root + "7",rootLabel + "7")
                + getOneString(root, root + "M7",rootLabel + "M7")
                + getOneString(root, root + "add9",rootLabel + "add9")
                + getOneString(root, root + "11",rootLabel + "11")
                + newLine
                ;
    }

    private String getMinorString(String root) {
        String rootLabel = root.replace('s', '#');

        return getOneString(root + "m", root + "m",rootLabel + "m")
                + getOneString(root + "m", root + "dim",rootLabel + "dim")
                + getOneString(root + "m", root + "ms5",rootLabel + "m+5")
                + getOneString(root + "m", root + "m6",rootLabel + "m6")
                + getOneString(root + "m", root + "m7",rootLabel + "m7")
                + getOneString(root + "m", root + "m7f5",rootLabel + "m7-5")
                + getOneString(root + "m", root + "mM7",rootLabel + "mM7")
                + getOneString(root + "m", root + "madd9",rootLabel + "madd9")
                + getOneString(root + "m", root + "m11",rootLabel + "m11")
                + newLine
                ;
    }

    private String getOneString(String rootConstants, String chordConstants, String chordLabel) {
        return "    <string name=\"chord_name_" + rootConstants + "_" + chordConstants + "\">" + chordLabel + "</string>" + newLine;
    }


}
