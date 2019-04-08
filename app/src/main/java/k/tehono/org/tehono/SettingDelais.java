package k.tehono.org.tehono;

import android.os.Bundle;
import android.app.Activity;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import k.tehono.org.tehono.Preferences;

public class SettingDelais extends AppCompatActivity {
    private SeekBar volumeControl = null;
    private SeekBar timeout = null;
    private TextView tv;
    private TextView tvtimout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_delais);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setBackgroundDrawableResource(R.drawable.palme1);

        Preferences yourPrefrence = Preferences.getInstance(getApplicationContext());
        int delai_avant_recherche = yourPrefrence.getIntData("delai_avant_recherche");
        if(delai_avant_recherche < 2000) {
            delai_avant_recherche = 2000;
            yourPrefrence.saveIntData("delai_avant_recherche",2000);
        }
        if(delai_avant_recherche > 10000) delai_avant_recherche = 10000;
        float val = (delai_avant_recherche - 2000)/80;
        int delai_for_seak = Math.round(val * 100) / 100;

        tv = (TextView)  findViewById(R.id.temps_frappe_recherche);
        int s = Math.round((delai_avant_recherche/1000) * 100 ) / 100;
        tv.setText(s+" secondes");


        volumeControl = (SeekBar) findViewById(R.id.seekBar);
        volumeControl.setProgress(delai_for_seak);
        //Log.i("SettingDelais", "SettingDelais delai_for_seak ------"+delai_for_seak);

        volumeControl.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;



            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;

                int delai_avant_recherche_milli = progressChanged*80 + 2000;

                float delai = (float)Math.round(delai_avant_recherche_milli/1000 * 100) / 100;
                int sx = Math.round(delai* 100) / 100;
                tv.setText(sx+" secondes");


            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

                if(progressChanged < 0)  return;
                if(progressChanged > 100) return;
                int delai_avant_recherche = progressChanged*80 + 2000;
                int sxx = Math.round(delai_avant_recherche/ 1000) *1000;
                Toast.makeText(SettingDelais.this,"Délais fixé à  "+sxx +" ms ",
                        Toast.LENGTH_SHORT).show();
                Preferences p = Preferences.getInstance(getApplicationContext());
                p.saveIntData("delai_avant_recherche",sxx);

            }
        });



        /*******************************/
        Preferences yourPrefrence2 = Preferences.getInstance(getApplicationContext());
        int timeoutx = yourPrefrence.getIntData("timeout");
        if(timeoutx < 10000) {
            timeoutx = 10000;
            yourPrefrence2.saveIntData("timeout",10000);
        }
        if(timeoutx > 50000) timeoutx = 50000;
        float val2 = (timeoutx - 10000)/400;
        int delai_for_seak2 = Math.round(val2 * 100) / 100;

        tvtimout = (TextView)  findViewById(R.id.timeouttext);
        int s2 = Math.round((timeoutx/1000) * 100 ) / 100;
        tvtimout.setText(s2+" secondes");


        timeout = (SeekBar) findViewById(R.id.timeout);
        timeout.setProgress(delai_for_seak2);
        //Log.i("SettingDelais", "timeoutx delai_for_seak2 ------"+delai_for_seak2);

        timeout.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progressChanged = 0;



            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser){
                progressChanged = progress;

                int delai_avant_recherche_milli2 = progressChanged*400 + 10000;

                float delai = (float)Math.round(delai_avant_recherche_milli2/1000 * 100) / 100;
                int sx = Math.round(delai* 100) / 100;
                tvtimout.setText(sx+" secondes");


            }

            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            public void onStopTrackingTouch(SeekBar seekBar) {

                if(progressChanged < 0)  return;
                if(progressChanged > 100) return;
                int delai_avant_recherche2 = progressChanged*400 + 10000;
                int sxx = Math.round(delai_avant_recherche2/ 1000) *1000;
                Toast.makeText(SettingDelais.this,"TimeOut fixé à  "+sxx +" ms ",
                        Toast.LENGTH_SHORT).show();
                Preferences p = Preferences.getInstance(getApplicationContext());
                p.saveIntData("delai_avant_recherche",sxx);

            }
        });

    }

}
