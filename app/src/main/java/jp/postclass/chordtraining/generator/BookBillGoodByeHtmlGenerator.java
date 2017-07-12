package jp.postclass.chordtraining.generator;


import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.security.cert.CertificateNotYetValidException;

import jp.postclass.chordtraining.common.ChordConstants;
import static jp.postclass.chordtraining.common.Constants.*;

import jp.postclass.chordtraining.common.UtAbc;
import jp.postclass.chordtraining.common.UtCommon;

public class BookBillGoodByeHtmlGenerator {

    private static final String newLine = "\r\n";
    private static final File baseDir = new File("D:/tmp/a/book/bill");


    public static void main(String[] args) throws Exception{
        new BookBillGoodByeHtmlGenerator().start();
    }

    private void start() throws Exception {
        String chordAbc = "";
        chordAbc += "z2 z2 ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7b5, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_C__C7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_B__B7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bb__Bb7sus4, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Em__Em7, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__EbM7, 3);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_A__A7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 1);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Ab__AbM7, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Dm__Dm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7, TENSION_NONE, TENSION_FL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Db__Db7, TENSION_NONE, TENSION_NATURAL, TENSION_SH, TENSION_NONE, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cm__Cm7, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7b5, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_C__C7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7, 3);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_B__B7, TENSION_NONE, TENSION_NATURAL, TENSION_SH, TENSION_NATURAL, 1);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bb__Bb7sus4, 3);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_E__E7, TENSION_NONE, TENSION_NATURAL, TENSION_SH, TENSION_NATURAL, 1);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__EbM7, 3);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_A__A7, 1);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Ab__AbM7, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Dm__Dm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7, TENSION_NONE, TENSION_FL, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cm__Cm7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__EbM7, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Ab__AbM7, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__Eb, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__Eb, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Ab__AbM7, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7, TENSION_NONE, TENSION_FL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cm__Cm7, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cm__Cm, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7b5, 8);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_D__D7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 8);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_C__CM7, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_D__D7, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bm__Bm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_E__E7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__AmM7, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7, 2);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fshm__Fshm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_C__C7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_B__B7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Em__Em, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Em__Em, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cshm__Cshm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fsh__Fsh7, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bm__Bm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_E__E7, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bbm__Bbm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__Eb7, 4);
        chordAbc += "| \n";

        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_C__C7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_SH, 2);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_F__F7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bb__Bb7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__Eb, TENSION_NATURAL, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Ab__AbM7, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Dm__Dm7b5, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_FL, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cm__Cm, TENSION_NATURAL, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_FL, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_C__C7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_F__F7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bb__Bb7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, 2);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__Eb, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Ab__AbM7, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Db__DbM7, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7,  TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_FL, 4);
        chordAbc += "| \n";

        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cm__Cm, TENSION_NATURAL, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 8);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Ab__AbM7, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Gm__Gm7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fm__Fm7,  TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 6);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_G__G7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_FL, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cm__Cm, TENSION_NATURAL, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 8);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7b5, 8);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_D__D7, TENSION_NATURAL, TENSION_FL, TENSION_NATURAL, TENSION_NATURAL, 8);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_D__D7, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bm__Bm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bm__Bm7b5, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_D__D7, TENSION_NONE, TENSION_SH, TENSION_NONE, TENSION_NONE, 2);
        chordAbc += "| \n";

        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7, TENSION_NONE, TENSION_NATURAL, TENSION_NONE, TENSION_NONE, 8);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fshm__Fshm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_C__C7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, 2);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_B__B7, TENSION_NONE, TENSION_NONE, TENSION_NONE, TENSION_NATURAL, 2);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Em__Em7, 6);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Em__Em, 2);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Cshm__Cshm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Fsh__Fsh7, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bm__Bm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_E__E7, 4);
        chordAbc += "| ";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Bbm__Bbm7b5, 4);
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Eb__Eb7, 4);
        chordAbc += "| \n";
        chordAbc += UtAbc.getChordAbc(ChordConstants.CHORD_Am__Am7b5, 8);

        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "good_bye_mid.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String noChordAbd = chordAbc.replaceAll("\".*?\"", "");
                writer.write(UtAbc.getAbcHeader() + noChordAbd);
                writer.flush();
            }
        }

        String abc = UtAbc.getAbcHeader("C treble", null) + chordAbc;

        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "good_bye_svg.abc"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                writer.write(abc);
                writer.flush();
            }
        }

        try (FileOutputStream outputStream = new FileOutputStream(new File(baseDir, "good_bye.html"))) {
            try (OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8")) {
                String scoreTag = UtAbc.getScoreTag(abc);
                writer.write(UtAbc.getHtml(scoreTag, "../../abcjs/"));
                writer.flush();
            }
        }
    }

}
