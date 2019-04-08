package k.tehono.org.tehono;

import android.content.DialogInterface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import android.app.Activity;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnLongClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class SettingLangsOrder extends AppCompatActivity {
    public ArrayList<Language> dataModels;
    public ArrayList<Language> dataModelsannexe;
    ListView listView;
    private static LangModelAdapter adapter;
    public LayoutInflater inflater;
    public DragLinearLayout dragLinearLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.i("K", "onCreate onStop ------------------------------------");
        setContentView(R.layout.activity_setting_langs_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setBackgroundDrawableResource(R.drawable.palme1);



    }

    @Override
    public void onStop() {
        super.onStop();
    }
    @Override
    public void onStart() {
        super.onStart();
        dataModels= new ArrayList<>();
        dataModelsannexe= new ArrayList<>();
        //dataModelsannexe.clear();
        //dataModels.clear();

        //Log.i("K", "datamodel onStart ------------------------------------ dataModels count = "+ dataModels.size()+ "--- dataModelsannexe count = "+dataModelsannexe.size());
        dragLinearLayout = (DragLinearLayout) findViewById(R.id.container);
        inflater = LayoutInflater.from(getApplicationContext());

        LanguagesPref langpref = LanguagesPref.getInstance(getApplicationContext());
        dataModelsannexe = langpref.getdefaults();
        if(dataModelsannexe.size() <= 0) return;

        for(int i = 0; i < dataModelsannexe.size(); i++){
            View row = inflater.inflate(R.layout.langrowmodel,null);
            Language dataModel = dataModelsannexe.get(i);

            TextView txtVersion = (TextView) row.findViewById(R.id.version_number);
            ImageView drapeau = (ImageView) row.findViewById(R.id.item_drapeau);
            txtVersion.setText(dataModel.getTitle());
            drapeau.setImageDrawable(dataModel.getDrapeau());
            row.setTag(i);
            dragLinearLayout.addView(row);

        }


        for(int i = 0; i < dragLinearLayout.getChildCount(); i++){
            View child = dragLinearLayout.getChildAt(i);
            dragLinearLayout.setViewDraggable(child, child);
        }

        dragLinearLayout.setOnViewSwapListener(new DragLinearLayout.OnViewSwapListener() {
            @Override
            public void onSwap(View firstView, int firstPosition,
                               View secondView, int secondPosition) {
                //  Log.i("K", "swap  => "+firstPosition +" and "+secondPosition);
                Collections.swap(dataModelsannexe, firstPosition, secondPosition);
            }
        });

    }
    @Override
    public void onBackPressed(){
        finish();
        super.onBackPressed();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.order_langs_menu, menu);

        return true;
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.home) {
            return true;
        }
        if (item.getItemId() == R.id.action_ic_menu_save) {
            save();
            return true;
        }
        return false;
    }

    public void save() {
        Preferences p = Preferences.getInstance(getApplicationContext());
        for(int i = 0; i < dataModelsannexe.size(); i++){
            Language xx = dataModelsannexe.get(i);
            String id = xx.getType()+"preforder";
            // Log.i("K", "saveIntData  => "+id + " pos = "+i);
            p.saveIntData(id,i);
        }

        showDialog();
    }

    private void showDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("L'ordre des langues a correctement été sauvegardé");
        DialogInterface.OnClickListener positiveEventAction = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               finish();
            }
        };

        builder.setPositiveButton("Ok", positiveEventAction);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Enregistré");
        int icon = R.drawable.ok; // Optional
        dialog.setIcon(icon);
        dialog.show();
    }
}
