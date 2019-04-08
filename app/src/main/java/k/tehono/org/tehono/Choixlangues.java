package k.tehono.org.tehono;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.util.SparseBooleanArray;
import android.widget.TextView;


import java.util.ArrayList;

public class Choixlangues extends AppCompatActivity {
    Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
    public String type;
    public String nid;
    public  String titlex;
    public String op;
    private ArrayList<String> trads;
    public TextView motTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choixlangues);
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
        getSupportActionBar().setHomeButtonEnabled(true);

        Typeface custom_font =   Typeface.createFromAsset(this.getAssets(), "fertigo.ttf");
        for(int j = 0; j < toolbar.getChildCount(); j++)
        { View view = toolbar.getChildAt(j);
            if(view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextSize(15.0f);
                textView.setTypeface(custom_font);
            }
        }

        getWindow().setBackgroundDrawableResource(R.drawable.palme1);
        setTitle("Langues cibles");
        findViewsById();


        Intent i = getIntent();
        type = i.getStringExtra("type");
        nid = i.getStringExtra("nid");
        titlex = i.getStringExtra("titlex");
        op = i.getStringExtra("op");
        trads = new ArrayList<>();

        motTv = (TextView) findViewById(R.id.mot);
        motTv.setText(i.getStringExtra("titlex"));

        listView = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.testbutton);


        LanguagesPref pref = LanguagesPref.getInstance(getApplicationContext());
        ArrayList<Language> all_languages = pref.getdefaults();
        ArrayList<Language> dataModels = new ArrayList<>();
        int j = 0;
        for(int k = 0; k < all_languages.size(); k++){
            Language xx = all_languages.get(k);
            if(!xx.getType().equals(type)){
                xx.setOrder(j);
                j++;
                dataModels.add(xx);
            }
            Log.i("KK LanguagesPref", "dataModels: "+xx.getType()+" => "+xx.getOrder());
        }


        ArrayList<String> tradlist = new ArrayList<>();

        MonAdaptateurDeListe adaptateur = new MonAdaptateurDeListe(getApplicationContext(), 0, dataModels);

          listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
          listView.setAdapter(adaptateur);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtoNext();
            }
        });
        //listView.setOnItemClickListener(this);
        /*String[] sports = {"Circle", "Square", "Rectangle", "Hexagon"};
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, sports);
        //android:background="#AA052469"
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);*/
       // button.setOnClickListener(this)
    }

    private void findViewsById() {

    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.choixlangues, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Log.e("Choixlangues", "onOptionsItemSelected");
        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            Log.e("Choixlangues","next !");
            sendtoNext();
            /*Intent intent = new Intent(MainActivity.this,AllSettings.class);
            startActivity(intent);*/
            return true;
        }
        if (id == android.R.id.home) {
            Log.e("Choixlangues","home !");
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        // your logic
        Log.e("Choixlangues","bakpressed");
        //Intent output = new Intent();
        //setResult(RESULT_OK, output);
        //finish();

        //setResult(RESULT_OK);

        super.onBackPressed();
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("Choixlangues","bakpressed");
        trads.clear();
        //Intent output = new Intent();
        //setResult(RESULT_OK, output);
        //finish();

        //setResult(RESULT_OK);


    }


    public class MonAdaptateurDeListe extends ArrayAdapter<Language> {

        private Context context;
        private ArrayList<Language> rentalProperties;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            //get the inflater and inflate the XML layout for each item

            View view = inflater.inflate(R.layout.choixlangrow, null);

            if(convertView == null ) {
                Language property = rentalProperties.get(position);
                //TextView texttrad = (TextView) view.findViewById(R.id.trad);
                CheckBox box = (CheckBox) view.findViewById(R.id.checkbox) ;
                //box.setBackgroundColor(Color.WHITE);
                //box.getHighlightColor(@C);
                box.setTag(property.getOrder());
                ImageView drap = (ImageView) view.findViewById(R.id.drap);
                TextView version_numberView = (TextView) view.findViewById(R.id.version_number);

                //texttrad.setVisibility(View.GONE);

                drap.setImageDrawable(property.getDrapeau());
                version_numberView.setText(property.getTitle());

                box.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.e("Choixlangues","buttonView "+buttonView.getTag());
                        int posx = (int)buttonView.getTag();
                        Language l =  rentalProperties.get(posx);
                        if(isChecked) trads.add(l.getType());
                        else trads.remove(l.getType());
                        if(trads.size() > 0) button.setTextColor(Color.parseColor("#36dc15"));
                        else button.setTextColor(Color.parseColor("#FFFFFF"));
                        Log.e("Choixlangues","trads "+trads);
                    }

                });

            }
            else {
                view = (View)convertView;
            }
            return view;
        }

        public MonAdaptateurDeListe(Context context, int resource, ArrayList<Language> objects) {
            super(context, resource, objects);
            this.context = context;

           /* ArrayList<DetailRow> det = new ArrayList<>();
            for (int ii = 0; ii < objects.size(); ii++) {
                DetailRow detrow = objects.get(ii);
                Log.i("K", "DetailRow "+detrow.getTrad().toString() +" => " +ii);

                if(detrow.getTrad().toString().length() > 0) det.add(detrow);
            }*/


            this.rentalProperties = objects;
        }
    }

    public void sendtoNext() {

        if(trads.size() <= 0) {
            launchAlert("Selection vide","vous devez choisir au moins une langue");
        } else {

            Intent intent = new Intent(Choixlangues.this,FormSend.class);

            intent.putExtra("nid",nid);
            intent.putExtra("titlex",titlex);
            intent.putExtra("type",type);
            intent.putExtra("op",op);


            //String[] myStrings = new String[trads.size()];
            String[] myStrings =  trads.toArray(new String[trads.size()]);
            intent.putExtra("choices", myStrings);

            Log.i("K", "Snackbar "+" op "+op+" nid "+ nid +"nid "+"title "+titlex+ " type "+type);

            startActivity(intent);
        }

    }


    public void launchAlert(String tit,String mes ) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(tit);
        String alert1 =mes;
        //String alert2 = "Message here ";
        // Stringalert3 = "Message here " ;
        // alertDialog.setMessage(alert1 +"\n"+ alert2 +"\n"+ alert3);
        alertDialog.setMessage(alert1);
        AlertDialog alert = alertDialog.create();
        alert.show();
    }
}
