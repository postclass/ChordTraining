package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import jp.postclass.chordtraining.activity.ChordDegreeActivityHelper;
import jp.postclass.chordtraining.common.Constants;

public class ReferenceKeyHtmlGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a");

    private Map<Integer, String> rootMapS = new HashMap<>();
    private Map<Integer, String> rootMapF = new HashMap<>();

    public static void main(String[] args) throws Exception{
        new ReferenceKeyHtmlGenerator().start();
    }

    private void start() throws Exception {

        ChordDegreeActivityHelper.setRootMap(this.rootMapS, this.rootMapF);

        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "reference/reference_key.html"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {

                writer.write("<html><table border='1' cellspacing='0'>" + newLine);
                writer.write("<tr>" + newLine);

                writer.write(getKeyUnitString(Constants.KEY_Csh));
                writer.write(getKeyUnitString(Constants.KEY_Fsh));
                writer.write(getKeyUnitString(Constants.KEY_B));
                writer.write(getKeyUnitString(Constants.KEY_E));
                writer.write(getKeyUnitString(Constants.KEY_A));
                writer.write(getKeyUnitString(Constants.KEY_D));
                writer.write(getKeyUnitString(Constants.KEY_G));
                writer.write(getKeyUnitString(Constants.KEY_C));
                writer.write(getKeyUnitString(Constants.KEY_F));
                writer.write(getKeyUnitString(Constants.KEY_Bb));
                writer.write(getKeyUnitString(Constants.KEY_Eb));
                writer.write(getKeyUnitString(Constants.KEY_Ab));
                writer.write(getKeyUnitString(Constants.KEY_Db));
                writer.write(getKeyUnitString(Constants.KEY_Gb));
                writer.write(getKeyUnitString(Constants.KEY_Cb));

                writer.write("</tr><tr>" + newLine);

                writer.write(getKeyUnitString(Constants.KEY_Ashm));
                writer.write(getKeyUnitString(Constants.KEY_Dshm));
                writer.write(getKeyUnitString(Constants.KEY_Gshm));
                writer.write(getKeyUnitString(Constants.KEY_Cshm));
                writer.write(getKeyUnitString(Constants.KEY_Fshm));
                writer.write(getKeyUnitString(Constants.KEY_Bm));
                writer.write(getKeyUnitString(Constants.KEY_Em));
                writer.write(getKeyUnitString(Constants.KEY_Am));
                writer.write(getKeyUnitString(Constants.KEY_Dm));
                writer.write(getKeyUnitString(Constants.KEY_Gm));
                writer.write(getKeyUnitString(Constants.KEY_Cm));
                writer.write(getKeyUnitString(Constants.KEY_Fm));
                writer.write(getKeyUnitString(Constants.KEY_Bbm));
                writer.write(getKeyUnitString(Constants.KEY_Ebm));
                writer.write(getKeyUnitString(Constants.KEY_Abm));

                writer.write("</tr></table></html>");

                writer.flush();
            }
        }
    }



    private String getKeyUnitString(String key) {

        String keymode = Constants.KEYMODE_MAJOR;
        if (key.endsWith("m")) {
            keymode = Constants.KEYMODE_MINOR;
        }

        String result = "<td><span>" +  key.replace("sh", "#") + "</span><br/>";
        result += "<img src='" +"../tone/" + keymode + "/tone_" + key + "_svg001.svg'></td>" + newLine;
        return result;
    }



}
