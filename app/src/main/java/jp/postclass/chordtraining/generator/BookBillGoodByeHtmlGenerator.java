package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import jp.postclass.chordtraining.activity.ChordDegreeActivityHelper;
import jp.postclass.chordtraining.common.ChordConstants;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.UtCommon;

public class BookBillGoodByeHtmlGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a");


    public static void main(String[] args) throws Exception{
        new BookBillGoodByeHtmlGenerator().start();
    }

    private void start() throws Exception {

        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "book_bill_good_bye.html"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {

                writer.write("<!DOCTYPE html>" + newLine +
                        "<html>" + newLine +
                        "<head>" + newLine +
                        "<meta charset=\"utf-8\"/>" + newLine +
                        "<style type=\"text/css\">" + newLine +
                        ".main {" + newLine +
                        "display: -webkit-flex; /* Safari */" + newLine +
                        "display: flex;" + newLine +
                        "" + newLine +
                        "-webkit-flex-direction: row; /* Safari */" + newLine +
                        "flex-direction:         row;" + newLine +
                        "" + newLine +
                        "-webkit-flex-wrap: wrap; /* Safari */" + newLine +
                        "flex-wrap:         wrap;" + newLine +
                        "" + newLine +
                        "}" + newLine +
                        ".main span {" + newLine +
                                "border: 1px solid #ccc;" + newLine +
                                "padding-left: 2px;" + newLine +
                                "}" + newLine +
                        "</style>" + newLine +
                        "</head>" + newLine +
                        "<body>" + newLine +
                        "<div class=\"main\">" + newLine);

                writer.write(getChordHtml(ChordConstants.CHORD_A_A));
                writer.write(getChordHtml(ChordConstants.CHORD_B_B11));
                writer.write(getChordHtml(ChordConstants.CHORD_A_A));
                writer.write(getChordHtml(ChordConstants.CHORD_B_B11));
                writer.write(getChordHtml(ChordConstants.CHORD_A_A));
                writer.write(getChordHtml(ChordConstants.CHORD_B_B11));
                writer.write(getChordHtml(ChordConstants.CHORD_A_A));
                writer.write(getChordHtml(ChordConstants.CHORD_B_B11));

                writer.write("</div>" + newLine +
                                        "</body>" + newLine +
                                        "</html>");

                writer.flush();
            }
        }
    }



    private String getChordHtml(String chordName) {
        String chordLabel = chordName.split("_")[1];
        chordLabel = chordLabel.replace("f5", "-5");
        chordLabel = chordLabel.replace("s", "#");
        String result = "<span>" + chordLabel + "<br/>";
        result += "<img src='" + UtCommon.getChordPath(chordName) + "_001.svg' width='120' height='120'></img>";
        result += "</span>" + newLine;
        return result;
    }



}
