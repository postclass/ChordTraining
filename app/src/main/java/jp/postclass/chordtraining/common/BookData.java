package jp.postclass.chordtraining.common;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BookData {

    private List<BookComposer> composers = new ArrayList<>();

    public BookData(InputStream in) throws JSONException, IOException {
        JSONObject json = new JSONObject(UtCommon.getStringFromStream(in));

        JSONArray jsComposers = json.getJSONArray("composers");

        for (int i = 0; i < jsComposers.length(); i++) {
            JSONObject jsComposer = jsComposers.getJSONObject(i);
            BookComposer composer = new BookComposer();
            composer.setLabel(jsComposer.getString("label"));
            composer.setValue(jsComposer.getString("value"));

            JSONArray jsPieces = jsComposer.getJSONArray("pieces");
            for (int p = 0; p < jsPieces.length(); p++) {
                JSONObject jsPiece = jsPieces.getJSONObject(p);
                LabelValueBean piece = new LabelValueBean();
                piece.setLabel(jsPiece.getString("label"));
                piece.setValue(jsPiece.getString("value"));
                composer.addPiece(piece);
            }

            this.composers.add(composer);
        }
    }

    public List<String> getComposerLabelList() {
        List<String> result = new ArrayList<>();
        for (BookComposer composer : composers) {
            result.add(composer.getLabel());
        }
        return result;
    }

    public BookComposer getComposer(String composersValue) {
        for (BookComposer composer : composers) {
            if (composersValue.equals(composer.getValue())) {
                return composer;
            }
        }
        return null;
    }

    public BookComposer getComposer(int idx) {
        return composers.get(idx);
    }



    public List<BookComposer> getComposers() {
        return composers;
    }

    public void setComposers(List<BookComposer> composers) {
        this.composers = composers;
    }


    public class BookComposer extends LabelValueBean {

        private List<LabelValueBean> pieces = new ArrayList<>();

        public LabelValueBean getPiece(String pieceValue) {
            for (LabelValueBean piece : pieces) {
                if (pieceValue.equals(piece.getValue())) {
                    return piece;
                }
            }
            return null;
        }

        public List<String> getPieceLabelList() {
            List<String> result = new ArrayList<>();
            for (LabelValueBean piece : pieces) {
                result.add(piece.getLabel());
            }
            return result;
        }



        public void addPiece(LabelValueBean piece) {
            this.pieces.add(piece);
        }

        public List<LabelValueBean> getPieces() {
            return pieces;
        }

        public void setPieces(List<LabelValueBean> pieces) {
            this.pieces = pieces;
        }



    }
}
