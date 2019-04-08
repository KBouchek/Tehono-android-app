package k.tehono.org.tehono;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;//

public class AllSettings extends AppCompatActivity {
    ListView listView ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_allsettings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Réglages");
        getWindow().setBackgroundDrawableResource(R.drawable.palme1);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab.setVisibility(View.INVISIBLE);
        listView = (ListView) findViewById(R.id.list);

        String[] values = new String[] { "Ordre des langues",
                "Délais des recherches",
                "Options du clavier"};

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id., values);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,R.layout.mytextview,arr);*/
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_list_item_1, values) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = super.getView(position, convertView, parent);
                TextView text = (TextView) view.findViewById(android.R.id.text1);
                text.setTextColor(Color.WHITE);
                return view;
            }
        };
        listView.setAdapter(adapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;
                if (itemPosition == 0) {
                    Intent intent = new Intent(AllSettings.this,SettingLangsOrder.class);
                    startActivity(intent);
                }
                if (itemPosition == 1) {

                    Intent intent = new Intent(AllSettings.this,SettingDelais.class);
                    startActivity(intent);
                }
                if (itemPosition == 2) {
                    Intent intent = new Intent(AllSettings.this,SettingsClavier.class);
                    startActivity(intent);

                }
                // ListView Clicked item value
                //String  itemValue    = (String) listView.getItemAtPosition(position);

                // Show Alert
                //Toast.makeText(getApplicationContext(),
                //     "Position :"+itemPosition+"  ListItem : " +itemValue , Toast.LENGTH_LONG)
                //    .show();

            }

        });
    }

}
