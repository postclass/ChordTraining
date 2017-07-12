package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import jp.postclass.chordtraining.activity.ChordDegreeActivityHelper;
import jp.postclass.chordtraining.common.Constants;

public class ReferenceScaleHtmlGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a");

    private Map<Integer, String> rootMapS = new HashMap<>();
    private Map<Integer, String> rootMapF = new HashMap<>();

    public static void main(String[] args) throws Exception{
        new ReferenceScaleHtmlGenerator().start();
    }

    private void start() throws Exception {

        ChordDegreeActivityHelper.setRootMap(this.rootMapS, this.rootMapF);

        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "reference/reference_scale.html"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {

                writer.write("<html><table border='1' cellspacing='0'>" + newLine);
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_major));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_natural_minor));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_harmonic_minor));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_major_2_dorian));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_major_3_phrygian));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_major_4_lydian));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_major_5_mixolydian));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_major_6_aeolian));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_major_7_locrian));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor_2_dorian_b2));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor_3_lydian_augmented));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor_4_lydian_dominant));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor_5_mixolydian_b6));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor_6_locrian_n2));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor_7_altered));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_melodic_minor_acoustic));
                writer.write(getTagString(Constants.KEY_C, Constants.SCALE_blues));
                writer.write("</table></html>");
                writer.flush();
            }
        }
    }



    private String getTagString(String keyConstants, String scaleConstants) {
        String result = "<tr><td><span>" +  scaleConstants + "</span></td>";
        result += "<td><img src='" +"../scale/" + keyConstants + "/scale_" + keyConstants + "_" + scaleConstants + "_001.svg'></td><tr>" + newLine;
        return result;
    }



}
