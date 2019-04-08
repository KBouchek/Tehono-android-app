package k.tehono.org.tehono;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;

public class SettingsClavier extends AppCompatActivity {
    CheckBox checkBox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingsclavier);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setBackgroundDrawableResource(R.drawable.palme1);

         checkBox = (CheckBox) findViewById(R.id.checkBox);
        Preferences p = Preferences.getInstance(getApplicationContext());
        String x = p.getStringData("keyboard_open_auto");
        if(x.equals("")) {
            x = "on";
            p.saveStringData("keyboard_open_auto","on");
            setOk();
        }
        if (x.equals("on"))  setOk();  else setPasOk();

        checkBox.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if(checkBox.isChecked()){
                    setOk();
                    Preferences px = Preferences.getInstance(getApplicationContext());
                    px.saveStringData("keyboard_open_auto","on");
                }else{
                    setPasOk();
                    Preferences px = Preferences.getInstance(getApplicationContext());
                    px.saveStringData("keyboard_open_auto","off");
                }
            }
        });

    }

    void setOk() {
        checkBox.setChecked(true);
        checkBox.setText("Activée");
    }
    void setPasOk() {
        checkBox.setChecked(false);
        checkBox.setText("Désactivée");
    }
}
