package jp.postclass.chordtraining.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.Globals;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case  R.id.settingButton:
                Intent intent = new Intent(getApplication(), SettingsActivity.class);
                startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClickTone(View view) {
        Globals.category = Constants.CATEGORY_TONE;
        Intent intent = new Intent(getApplication(), Qa1Activity.class);
        startActivity(intent);
    }

    public void onClickChordRoot(View view) {
        Globals.category = Constants.CATEGORY_CHORD_ROOT;
        Intent intent = new Intent(getApplication(), Qa1Activity.class);
        startActivity(intent);
    }

    public void onClickChordDegree(View view) {
        Globals.category = Constants.CATEGORY_CHORD_DEGREE;
        Intent intent = new Intent(getApplication(), Qa1Activity.class);
        startActivity(intent);
    }

    public void onClickBook(View view) {
        Intent intent = new Intent(getApplication(), BookSelectActivity.class);
        startActivity(intent);
    }

    public void onClickReference(View view) {
        Intent intent = new Intent(getApplication(), ReferenceActivity.class);
        startActivity(intent);
    }

}
