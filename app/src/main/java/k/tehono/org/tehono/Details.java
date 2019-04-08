package k.tehono.org.tehono;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import static k.tehono.org.tehono.Languages.getInstance;

public class Details extends AppCompatActivity {

    public Partialresult partial;
    private ListView lv;
    private TextView tradtitle;
    private TextView typetitle;
    public String type;
    public Definition def;
    public ArrayList<DetailRow> tradlist;
    private ProgressBar loading;
    private ProgressBar progressBar2;
    ArrayList<Language> all_languages;
    private ImageView d;

    public String nid;
    public String title;
    public String op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
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


        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setBackgroundDrawableResource(R.drawable.palme1);



        lv = (ListView) findViewById(R.id.listView);
        d = (ImageView) findViewById(R.id.drap);
        //lv.setVisibility(View.INVISIBLE);
        tradtitle = (TextView) findViewById(R.id.version_number);
        typetitle = (TextView) findViewById(R.id.type);

        //View head = (View) this.getLayoutInflater().inflate(R.layout.detail_header, null);
        tradlist = new ArrayList<>();




        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        Intent i = getIntent();
         //Log.e("Details", "Details received title : " + datailtitle + " nid  " + nid);
        Partialresult p = new Partialresult(i.getStringExtra("titlex"),i.getStringExtra("nid"),i.getStringExtra("type"));
        //Log.e("Details", "Details received title : " + i.getStringExtra("title") + " nid  " + i.getStringExtra("nid") +" type = "+i.getStringExtra("type"));
        partial = p;

        //Typeface custom_font =  Typeface.createFromAsset(this.getAssets(), "fertigo.ttf");
        Typeface custom_font =  custom_font = Typeface.createFromAsset(this.getAssets(), "fertigo.ttf");
        for(int j = 0; j < toolbar.getChildCount(); j++)
        { View view = toolbar.getChildAt(j);
            if(view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextSize(16.0f);
                textView.setTypeface(custom_font);
            }
        }
        setTitle("Traduction: "+i.getStringExtra("titlex"));
        title = i.getStringExtra("titlex");
        nid = i.getStringExtra("nid");
        tradtitle.setText(partial.getTitle());
        type = i.getStringExtra("type");
        op = i.getStringExtra("op");

        LanguagesPref pref = LanguagesPref.getInstance(getApplicationContext());
        all_languages = pref.getdefaults();
        for(int ii = 0; ii < all_languages.size(); ii++){
            Language dataModel = all_languages.get(ii);
            Log.e("dataModel", "dataModel title : " + dataModel.getTitle() + " type  " + dataModel.getType() );
            if(type.equals(dataModel.getType())) {
                typetitle.setText(dataModel.getLabel());
                d.setImageDrawable(dataModel.getDrapeau());
            }
        }


        loading = (ProgressBar) findViewById(R.id.progressBar1);

        loading.setVisibility(View.VISIBLE);
        loading.getIndeterminateDrawable().setColorFilter(0xFF46F600, android.graphics.PorterDuff.Mode.MULTIPLY);

        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.VISIBLE);
        progressBar2.getProgressDrawable().setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        //progressBar2.setProgress(0);
       // progressBar2.getIndeterminateDrawable().setColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY);
        grabURL("http://tehono.org/app/searchbynid/"+p.getNid());
        /*
        String output = getUrlContents("http://tehono.org/app/searchbynid/"+p.getNid());
        def = new Definition(partial.getNid(),partial.getTitle());

        if (output != null) {
            try {
                JSONObject jsonObj = new JSONObject(output);
                JSONArray results = jsonObj.getJSONArray("results");
                //JSONArray jsonObjx = results.getJSONArray("field_tah_fra");
                Log.e("results",results.toString());
                for (int iii = 0; iii < results.length(); iii++) {
                    JSONObject object = results.optJSONObject(iii);
                    //JSONArray objectarray = results.getJSONArray(iii);
                    Iterator<String> iterator = object.keys();
                    while(iterator.hasNext()) {
                        String currentKey = iterator.next();

                        if(currentKey.contains("field_"+type)) {
                            DetailRow row = new DetailRow();

                            String substr = currentKey.substring(10,13);
                            JSONObject obj = results.getJSONObject(iii).getJSONArray(currentKey).getJSONObject(0);
                            String t = obj.getString("value");

                            row.setType(type);
                            row.setTrad(t);
                            for(int ii = 0; ii < all_languages.size(); ii++){
                                Language dataModel = all_languages.get(ii);
                                if(dataModel.getType().equals(substr)) {
                                    row.setLabel(dataModel.getLabel());
                                    row.setDrapeau(dataModel.getDrapeau());
                                    row.setOrder( dataModel.getOrder());
                                }
                            }
                            tradlist.add(row);

                            def.setTrad(substr,t);

                        }

                    }


                }

                Collections.sort(tradlist, new Comparator<DetailRow>() {
                    @Override
                    public int compare(DetailRow fruit2, DetailRow fruit1)
                    {
                        return   fruit2.getOrder() - fruit1.getOrder();
                    }
                });
                Log.e("tradlist", tradlist.toString());
                MonAdaptateurDeListe adaptateur = new MonAdaptateurDeListe(getApplicationContext(), 0, tradlist);
                lv.setAdapter(adaptateur);



            } catch (final JSONException e) {
                Log.e("kk", "Json parsing error: " + e.getMessage());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),
                                "Json parsing error: " + e.getMessage(),
                                Toast.LENGTH_LONG).show();
                    }
                });

            }

        } else Log.d("getUrlContents", "wrog: ");


        loading.setVisibility(View.GONE);*/

    }

    public void grabURL(String url) {
        new GrabURL().execute(url);
    }

    private class GrabURL extends AsyncTask<String, Integer, String> {


        protected void onPreExecute() {
            loading.setVisibility(View.VISIBLE);
            loading.setProgress(100);
            progressBar2.setVisibility(View.VISIBLE);
            progressBar2.setProgress(0);
            lv.setVisibility(View.GONE);

        }

        protected String doInBackground(String... urls) {

            //String filename = "MySampleFile.png";
            publishProgress(0);

            try {
                //URL url = new URL(urls[0]);
                String u = urls[0];
               // String output = getUrlContents("http://tehono.org/app/searchbynid/"+p.getNid());
                String output = getUrlContents(u);
                def = new Definition(partial.getNid(),partial.getTitle());
                if (output != null) {
                    publishProgress(50);
                    //SystemClock.sleep(7000);
                    try {
                        JSONObject jsonObj = new JSONObject(output);
                        JSONArray results = jsonObj.getJSONArray("results");
                        //JSONArray jsonObjx = results.getJSONArray("field_tah_fra");
                        Log.e("results",results.toString());
                        for (int iii = 0; iii < results.length(); iii++) {
                            JSONObject object = results.optJSONObject(iii);
                            //JSONArray objectarray = results.getJSONArray(iii);
                            Iterator<String> iterator = object.keys();
                            publishProgress(50 + (iii * 50) / results.length());
                            while(iterator.hasNext()) {
                                String currentKey = iterator.next();

                                if(currentKey.contains("field_"+type)) {
                                    DetailRow row = new DetailRow();

                                    String substr = currentKey.substring(10,13);
                                    JSONObject obj = results.getJSONObject(iii).getJSONArray(currentKey).getJSONObject(0);
                                    String t = obj.getString("value");
                                    Log.e("kk", "Json getString " + t);
                                    if(obj.getString("value") != null && !obj.getString("value").equals("null")) {
                                        Log.e("kk", "Json value " + t);
                                        row.setType(type);
                                        row.setTrad(t);
                                        for (int ii = 0; ii < all_languages.size(); ii++) {
                                            Language dataModel = all_languages.get(ii);
                                            if (dataModel.getType().equals(substr)) {
                                                row.setLabel(dataModel.getLabel());
                                                row.setDrapeau(dataModel.getDrapeau());
                                                row.setOrder(dataModel.getOrder());
                                            }
                                        }
                                        tradlist.add(row);

                                        def.setTrad(substr, t);
                                    }

                                }

                            }


                        }

                        Collections.sort(tradlist, new Comparator<DetailRow>() {
                            @Override
                            public int compare(DetailRow fruit2, DetailRow fruit1)
                            {
                                return   fruit2.getOrder() - fruit1.getOrder();
                            }
                        });
                        Log.e("tradlist", tradlist.toString());
                        final MonAdaptateurDeListe adaptateur = new MonAdaptateurDeListe(getApplicationContext(), 0, tradlist);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                lv.setAdapter(adaptateur);
                            }
                        });

                    } catch (final JSONException e) {

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setMessage("Erreur décodage Json");
                            }
                        });
                        //Log.e("kk", "Json parsing error: " + e.getMessage());
                       /* runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "Json parsing error: " + e.getMessage(),
                                        Toast.LENGTH_LONG).show();
                            }
                        });*/

                    }

                } else runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMessage("Impossible d'obtenir les traductions");
                    }
                });



            } catch (Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMessage("Impossible d'accéder à internet");
                    }
                });
                //e.printStackTrace();

            }
        return "ok";

        }

        protected void onProgressUpdate(Integer... progress) {
            loading.setVisibility(View.VISIBLE);
            progressBar2.setProgress(progress[0]);
            //finished.setText(String.valueOf(progress[0]) + "%");
            //progressBar2.setProgress(progress[0]);
        }

        protected void onCancelled() {
          /*  Toast toast = Toast.makeText(getBaseContext(),
                    "Error connecting to Server", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.TOP, 25, 400);
            toast.show();*/

        }

        protected void onPostExecute(String filename) {
            loading.setVisibility(View.GONE);
           progressBar2.setProgress(100);
            lv.setVisibility(View.VISIBLE);
           progressBar2.setVisibility(View.GONE);
            /*finished.setVisibility(View.VISIBLE);
            finished.setText("Finished downloading...");
            File myFile = new File(directory , filename);
            ImageView myImage = (ImageView) findViewById(R.id.imageView1);
            myImage.setImageBitmap(BitmapFactory.decodeFile(myFile.getAbsolutePath()));*/

        }

    }


    public String getUrlContents(String theUrl) {
        // SystemClock.sleep(2000);
        StringBuilder content = new StringBuilder();
        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            setMessage(e.getLocalizedMessage());
            //e.printStackTrace();
        }

        // Toast.makeText(c,"requesting "+theUrl, Toast.LENGTH_SHORT);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date());

        Log.i("KK", "requesting " + theUrl + " - at time : " + time);
        return content.toString();


    }


public void setMessage(String mess){
    Toast.makeText(getApplicationContext(),"Une erreur est survenue\n "+mess, Toast.LENGTH_SHORT).show();
}




    public class MonAdaptateurDeListe extends ArrayAdapter<DetailRow> {

        private Context context;
        private ArrayList<DetailRow> rentalProperties;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater)
                    getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);



            //get the inflater and inflate the XML layout for each item

            View view = inflater.inflate(R.layout.detail_row, null);

            //  if(convertView == null ) {
                DetailRow property = rentalProperties.get(position);
            TextView texttrad = (TextView) view.findViewById(R.id.trad);
            ImageView drap = (ImageView) view.findViewById(R.id.drap);
            TextView version_numberView = (TextView) view.findViewById(R.id.version_number);
                texttrad.setText(property.getTrad());
                drap.setImageDrawable(property.getDrapeau());
                version_numberView.setText(property.getLabel());
            //   }
            //   else {
            //      view = (View)convertView;
            // }

            return view;
        }

        public MonAdaptateurDeListe(Context context, int resource, ArrayList<DetailRow> objects) {
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




/*
    public class DetailAdapter extends ArrayAdapter<Item> {

        public ListAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        public ListAdapter(Context context, int resource, List<Item> items) {
            super(context, resource, items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;

            if (v == null) {
                LayoutInflater vi;
                vi = LayoutInflater.from(getContext());
                v = vi.inflate(R.layout.itemlistrow, null);
            }

            Item p = getItem(position);

            if (p != null) {
                TextView tt1 = (TextView) v.findViewById(R.id.id);
                TextView tt2 = (TextView) v.findViewById(R.id.categoryId);
                TextView tt3 = (TextView) v.findViewById(R.id.description);

                if (tt1 != null) {
                    tt1.setText(p.getId());
                }

                if (tt2 != null) {
                    tt2.setText(p.getCategory().getId());
                }

                if (tt3 != null) {
                    tt3.setText(p.getDescription());
                }
            }

            return v;
        }

    }
*/

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detailmenu, menu);
        MenuItem shareItem = menu.findItem(R.id.menu_action_share);

        // show the button when some condition is true
        //if (someCondition) {
            shareItem.setVisible(true);
        //}

        return true;
    }
    @Override

    public boolean onOptionsItemSelected (MenuItem item)

    {
        Log.e("dd","bakpressed"+item.getItemId());
        switch(item.getItemId())

        {
            case android.R.id.home:
                onBackPressed();
                return true;

            case R.id.menu_action_share:
                shareIntend("http://tehono.org/node/"+partial.nid);
                return true;
            case R.id.menu_action_change:
                Intent modif = new Intent(Details.this,Choixlangues.class);
                // modif.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                modif.putExtra("nid", nid);
                modif.putExtra("titlex",title);
                modif.putExtra("type",type);
                modif.putExtra("op","modif");
                startActivity(modif);
                return  true;
        }


        return super.onOptionsItemSelected(item);

    }

    public void shareIntend(String message) {

        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        String header_message = "Te hono : traduction du mot: "+ partial.getTitle()+" ("+partial.getType()+")";
        share.putExtra(Intent.EXTRA_SUBJECT, header_message);
        share.putExtra(Intent.EXTRA_TEXT, message);
        startActivity(Intent.createChooser(share, "Partagez cette traduction"));
    }

    @Override
    public void onBackPressed() {
        // your logic
        Log.e("dd","bakpressed");
        Intent output = new Intent();

        setResult(RESULT_OK, output);
        finish();

        //setResult(RESULT_OK);

        super.onBackPressed();
    }


}
