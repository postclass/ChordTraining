package jp.postclass.chordtraining.activity;

import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import jp.postclass.chordtraining.Exception.ApplicationRuntimeException;
import jp.postclass.chordtraining.R;
import jp.postclass.chordtraining.common.Constants;
import jp.postclass.chordtraining.common.Globals;

public class Qa1Activity extends AppCompatActivity {

    private Qa1ActivityHelper helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qa1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        if (Constants.CATEGORY_TONE.equals(Globals.category)) {
            this.helper = new ToneActivityHelper(this);
        } else if (Constants.CATEGORY_CHORD_ROOT.equals(Globals.category)) {
            this.helper = new ChordRootActivityHelper(this);
        } else if (Constants.CATEGORY_CHORD_DEGREE.equals(Globals.category)) {
            this.helper = new ChordDegreeActivityHelper(this);
        } else if (Constants.CATEGORY_SCALE.equals(Globals.category)) {
            this.helper = new ScaleActivityHelper(this);
        } else {
            throw new ApplicationRuntimeException("illegal state : category : " + Globals.category);
        }

        this.helper.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.helper.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;

            case  R.id.settingButton:
                Intent intent = new Intent(getApplication(), SettingsActivity.class);
                startActivity(intent);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        this.helper.onPause();
        super.onPause();
    }

//    @Override
//    protected void onDestroy() {
//        this.helper.onDestroy();
//        super.onDestroy();
//    }

    public void onClickStart(View view) { this.helper.onClickStart(view); }

    public void onClickAnswer1(View view) { this.helper.onClickAnswer1(view); }

    public void onClickAnswer2(View view) {
        this.helper.onClickAnswer2(view);
    }

    public void onClickAnswer3(View view) {
        this.helper.onClickAnswer3(view);
    }

    public void onClickSound1(View view) {this.helper.onClickSound1(view);}

    public void onClickSound2(View view) {this.helper.onClickSound2(view);}

    public void onClickSound3(View view) { this.helper.onClickSound3(view);}
}
