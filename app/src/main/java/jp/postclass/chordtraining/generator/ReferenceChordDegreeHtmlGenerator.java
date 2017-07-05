package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import jp.postclass.chordtraining.activity.ChordDegreeActivityHelper;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.UtCommon;

import static jp.postclass.chordtraining.activity.ChordDegreeActivityHelper.getFlatAddMap;
import static jp.postclass.chordtraining.activity.ChordDegreeActivityHelper.getSharpAddMap;

public class ReferenceChordDegreeHtmlGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a");

    private Map<Integer, String> rootMapS = new HashMap<>();
    private Map<Integer, String> rootMapF = new HashMap<>();

    public static void main(String[] args) throws Exception{
        new ReferenceChordDegreeHtmlGenerator().start();
    }

    private void start() throws Exception {

        ChordDegreeActivityHelper.setRootMap(this.rootMapS, this.rootMapF);

        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "reference/reference_degree_absolute.html"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {

                writer.write("<html><table border='1' cellspacing='0'>" + newLine);

                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 100, Constants.KEY_Cb));
                writer.write(getKeyUnitString(this.rootMapF, 100, Constants.KEY_C));
                writer.write(getKeyUnitString(getSharpAddMap(this.rootMapF), 100, Constants.KEY_Cs));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 102, Constants.KEY_Db));
                writer.write(getKeyUnitString(this.rootMapF, 102, Constants.KEY_D));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 104, Constants.KEY_Eb));
                writer.write(getKeyUnitString(this.rootMapF, 104, Constants.KEY_E));
                writer.write(getKeyUnitString(this.rootMapF, 105, Constants.KEY_F));
                writer.write(getKeyUnitString(getSharpAddMap(this.rootMapF), 105, Constants.KEY_Fs));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 107, Constants.KEY_Gb));
                writer.write(getKeyUnitString(this.rootMapF, 107, Constants.KEY_G));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 109, Constants.KEY_Ab));
                writer.write(getKeyUnitString(this.rootMapF, 109, Constants.KEY_A));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 111, Constants.KEY_Bb));
                writer.write(getKeyUnitString(this.rootMapF, 111, Constants.KEY_B));

                writer.write(getKeyUnitString(this.rootMapF, 100, Constants.KEY_Cm));
                writer.write(getKeyUnitString(getSharpAddMap(this.rootMapF), 100, Constants.KEY_Csm));
                writer.write(getKeyUnitString(this.rootMapF, 102, Constants.KEY_Dm));
                writer.write(getKeyUnitString(getSharpAddMap(this.rootMapF), 102, Constants.KEY_Dsm));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 104, Constants.KEY_Ebm));
                writer.write(getKeyUnitString(this.rootMapF, 104, Constants.KEY_Em));
                writer.write(getKeyUnitString(this.rootMapF, 105, Constants.KEY_Fm));
                writer.write(getKeyUnitString(getSharpAddMap(this.rootMapF), 105, Constants.KEY_Fsm));
                writer.write(getKeyUnitString(this.rootMapF, 107, Constants.KEY_Gm));
                writer.write(getKeyUnitString(getSharpAddMap(this.rootMapF), 107, Constants.KEY_Gsm));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 109, Constants.KEY_Abm));
                writer.write(getKeyUnitString(this.rootMapF, 109, Constants.KEY_Am));
                writer.write(getKeyUnitString(getSharpAddMap(this.rootMapF), 109, Constants.KEY_Asm));
                writer.write(getKeyUnitString(getFlatAddMap(this.rootMapF), 111, Constants.KEY_Bbm));
                writer.write(getKeyUnitString(this.rootMapF, 111, Constants.KEY_Bm));

                writer.write("</table></html>");

                writer.flush();
            }
        }
    }



    private String getKeyUnitString(Map<Integer, String> rootMap, int rootNo, String key) {
        Map<Integer, String> noChordnameMap = new HashMap<>();

        if (key.endsWith("m")) {
            ChordDegreeActivityHelper.setChordnameMapMinor(rootMap, rootNo, 0, noChordnameMap);
        } else {
            ChordDegreeActivityHelper.setChordnameMapMajor(rootMap, rootNo, 0, noChordnameMap);
        }


        String result = "<tr><td>" + key.replace('s', '#') + "</td>";

        for (Integer chordnameNo : noChordnameMap.keySet()) {
            String digreeName = ChordDegreeActivityHelper.getDegreeName(chordnameNo, key);
            String chordName = noChordnameMap.get(chordnameNo);
            String chordLabel = chordName.split("_")[1];
            chordLabel = chordLabel.replace("f5", "-5");
            chordLabel = chordLabel.replace("s", "#");

            result += "<td><span>" + digreeName + "(" + chordLabel + ")" + "</span><br/>";
            result += "<img src='" + UtCommon.getChordPath(chordName) + "_001.svg' width='120' height='120'></img></td>";
        }

        result += "</tr>" + newLine;

        return result;
    }



}
