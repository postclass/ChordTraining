package jp.postclass.chordtraining.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;
import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.BookData;
import jp.postclass.chordtraining.common.Globals;
import jp.postclass.chordtraining.common.LabelValueBean;

public class BookSelectActivity extends AppCompatActivity {

    private Spinner composerSelectSpinner;
    private ListView pieceSelectList;

    private BookData bookData;

    private BookData.BookComposer selectedComposer;
    private LabelValueBean selectedPiece;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_select);

        this.composerSelectSpinner = (Spinner) findViewById(R.id.bookSelectComposerSelectSpinner);
        this.pieceSelectList = (ListView) findViewById(R.id.bookSelectPieceSelectList);

        try {
            this.bookData = new BookData(getAssets().open("book/list.json"));
        } catch (Exception e) {
            throw new ApplicationRuntimeException(e);
        };





        ArrayAdapter<String> composerSpinnerAdapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_spinner_item,
                        bookData.getComposerLabelList());
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        for (BookData.BookComposer composer : this.bookData.getComposers()) {
//            adapter.add(composer.getLabel());
//        }

        composerSelectSpinner.setAdapter(composerSpinnerAdapter);

        composerSelectSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedComposer = bookData.getComposer(i);
                updatePieceList();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        pieceSelectList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedPiece = selectedComposer.getPiece(i);
                Globals.bookReadUrl = "file:///android_asset/book/" + selectedComposer.getValue() + "/" + selectedPiece.getValue() + "_001.svg";
                Globals.bookSoundUrl = "book/" + selectedComposer.getValue() + "/" + selectedPiece.getValue() + ".mid";
                Intent intent = new Intent(getApplication(), BookReadActivity.class);
                startActivity(intent);
            }
        });



    }

    private void updatePieceList() {
        ArrayAdapter<String> pieceListAdapter =
                new ArrayAdapter<String>(
                        this,
                        android.R.layout.simple_expandable_list_item_1,
                        selectedComposer.getPieceLabelList());
        pieceSelectList.setAdapter(pieceListAdapter);
    }
}
