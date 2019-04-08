package k.tehono.org.tehono;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FormSend extends AppCompatActivity implements View.OnFocusChangeListener {

    public String type;
    public String nid;
    public String mot;
    public String op;
    public JSONObject postData;
    public ListView listView;
    RelativeLayout lay0;
    public ProgressDialog modif_loading;
    public ProgressDialog loading;
    public Button buttonsend;
    ArrayList<Language> all_languages;
    public ArrayList<DetailRow> tradlist;

    private int currentView;
    private TableLayout tableLayout;
    private boolean ok;
    //private ArrayList<String> errors_trads;
    private Map<String,String> errors_trads;

    String json;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formsend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        postData = new JSONObject();

        Intent i = getIntent();
        type = i.getStringExtra("type");
        nid = i.getStringExtra("nid");
        mot = i.getStringExtra("titlex");
        op = i.getStringExtra("op");

        errors_trads = new HashMap<>();
        json = "";

        String[] myStrings = i.getStringArrayExtra("choices");
        for (int x = 0; x < myStrings.length; x++) {
            String val = myStrings[x];
            Log.e("FormSend", "received title : " + val);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "op " + op + " nid " + nid + "nid " + "title " + mot + " type " + type, Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setBackgroundDrawableResource(R.drawable.palme1);


        modif_loading = new ProgressDialog(FormSend.this);
        modif_loading.setCancelable(true);
        modif_loading.setMessage("Envoi de la modification");
        modif_loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);




        lay0 = (RelativeLayout) findViewById(R.id.content_form_send);


        lay0.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent ev) {
                        hideKeyboard(lay0);
                        //Log.i("KK hideKeyboard", "lay0.setOnTouchListener");

                        return false;
                    }
                });


        Typeface custom_font = Typeface.createFromAsset(this.getAssets(), "fertigo.ttf");
        for (int j = 0; j < toolbar.getChildCount(); j++) {
            View view = toolbar.getChildAt(j);
            if (view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextSize(15.0f);
                textView.setTypeface(custom_font);
            }
        }

        listView = (ListView) findViewById(R.id.list);
        // buttonsend = (Button) findViewById(R.id.sendbtn);

        LanguagesPref pref = LanguagesPref.getInstance(getApplicationContext());
        all_languages = pref.getdefaults();
        tradlist = new ArrayList<>();

        ArrayList listlangschoices = new ArrayList(Arrays.asList(myStrings));

        DetailRow rowfirst = new DetailRow();
        for (int j = 0; j < all_languages.size(); j++) {
            Language langmot = all_languages.get(j);
            if (langmot.getType().equals(type)) {
                rowfirst.setDrapeau(langmot.getDrapeau());
                rowfirst.setLabel(langmot.getLabel());
                rowfirst.setOrder(0);
                rowfirst.setType(type);
                /****************/
                rowfirst.setTrad(mot);
                /******************/
                tradlist.add(rowfirst);
            }
        }

        int k = 1;
        for (int ii = 0; ii < all_languages.size(); ii++) {
            Language dataModel = all_languages.get(ii);
            //Log.e("dataModel", "dataModel title : " + dataModel.getTitle() + " type  " + dataModel.getType() );

            if (listlangschoices.contains(dataModel.getType())) {
                // my array has silly !

                DetailRow row = new DetailRow();
                row.setDrapeau(dataModel.getDrapeau());
                row.setLabel(dataModel.getLabel());
                row.setType(dataModel.getType());
                row.setOrder(k);
                row.setTrad("null");
                row.setValid("null");
                errors_trads.put(dataModel.getType(),"empty");
                tradlist.add(row);
                k++;
            }
        }


        currentView = 0;

        // get the add view button and register it's listener
        //Button addViewButton = (Button) findViewById(R.id.addViewButton);
        //addViewButton.setOnClickListener(addButtonListener);
        tableLayout = (TableLayout) findViewById(R.id.tableLayout);
        for (int kx = 0; kx < tradlist.size(); kx++) {
            addview(kx);
        }
    }


    // create a new edit text and add it to the ScrollView
    public void addview(int kx) {
        // get a reference to the LayoutInflater service
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DetailRow dr = tradlist.get(kx);
        final DetailRow dr2 = dr;
        // inflate add_view.xml to create new edit text views
         /*   View newView = inflater.inflate(R.layout.formsend_add_view_row, null);
            EditText editText = (EditText) newView.findViewById(R.id.editText);
            String text = "row "+ (currentView+1);
            editText.setText(text);
            tableLayout.addView(newView, currentView);
            currentView++;*/


        View newView = inflater.inflate(R.layout.formsend_row, null);
        RelativeLayout backlayout = (RelativeLayout) newView.findViewById(R.id.rel00);
        final EditText editText = (EditText) newView.findViewById(R.id.trad);
        TextView textView1 = (TextView) newView.findViewById(R.id.version_number);
        ImageView imageview1 = (ImageView) newView.findViewById(R.id.drap);
        String text = dr.getTrad();
        textView1.setText(dr.getLabel());
        if (text.equals("null")) text = "";
        editText.setText(text);
        //dr2.setValid(1);


        imageview1.setImageDrawable(dr.getDrapeau());
        if (currentView <= 0) {
            editText.setText(dr.getTrad());
            editText.setEnabled(false);
        } else {
            editText.setText("");
            editText.setEnabled(true);





            editText.addTextChangedListener(new TextWatcher() {
                boolean editing=false;
                @Override
                public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {

                }

                @Override
                public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                              int arg3) {

                }

                @Override
                public void afterTextChanged(Editable arg0) {

                    String right;
                    Log.e("K", arg0.toString());
                    ok = false;
                    if(dr2.getType().equals("fra")) {
                        ok = isValidationTypeFra(arg0.toString());
                    } else {
                        ok = isValidationTypeTah(arg0.toString());
                        //right = getValidTypeTah(arg0.toString());
                    }
                    if(!ok) {

                        errors_trads.put(dr2.getType(),"wrong");
                        dr2.setValid("error");
                        editText.setBackgroundColor(Color.RED);
                        editText.setTextColor(Color.WHITE);
                    } else {
                        errors_trads.put(dr2.getType(),"ok");
                        dr2.setValid("ok");
                        editText.setBackgroundColor(Color.WHITE);
                        editText.setTextColor(Color.BLACK);
                    }
                    if(arg0.toString().length() <= 0) errors_trads.put(dr2.getType(),"empty");
                    setTradForType(dr2.getType(), arg0.toString());

                    Log.e("KKK", "errors_trads = " +errors_trads.toString());

                }
            });

        final EditText f = editText;

         /*   int colorFrom = getResources().getColor(R.color.textColorPrimary);
            int colorTo = getResources().getColor(R.color.navigationBarColor);
            ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
            colorAnimation.setDuration(4000); // milliseconds
            colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                @Override
                public void onAnimationUpdate(ValueAnimator animator) {
                    f.setBackgroundColor((int) animator.getAnimatedValue());
                }

            });
            colorAnimation.start();
*/

            /*


            int colorFrom = Color.RED;
            int colorTo = Color.GREEN;
            int duration = 1000;
            ObjectAnimator.ofObject(editText, "backgroundColor", new ArgbEvaluator(), colorFrom, colorTo)
                    .setDuration(duration)
                    .start();
*/
        }


        tableLayout.addView(newView, currentView);
        currentView++;

        //holder.textView1 = (TextView) convertView.findViewById(R.id.version_number);
    }


    private void setTradForType(String receivedtaype, String traduction) {
        try {
            postData.put(receivedtaype, traduction);
            Log.e("KKK", "receivedtaype = " + receivedtaype + "/ string = " + traduction);
            Log.e("KKK", "postData = " + postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        Log.i("K", "onFocusChange  => " + v.getTag());

    }

    /*@Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

        if (actionId == EditorInfo.IME_ACTION_DONE) {
            Log.e("K", "Clicke IME_ACTION_DONE");
            hideKeyboard(lay0);
            // Do your Stuffs
            return true;
        }
        return false;

    }*/


    public void globalVerif() {
        Log.i("K", "globalVerif  => ");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sendtrad, menu);
        // MenuItem shareItem = menu.findItem(R.id.menu_action_share);

        // show the button when some condition is true
        //if (someCondition) {
        //shareItem.setVisible(true);
        //}

        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item)

    {
        Log.e("dd", "bakpressed" + item.getItemId());
        switch (item.getItemId())

        {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.action_ic_menu_save:
                global_chek();
                return true;


        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onBackPressed() {

        this.finish();

    }

    private void hideKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void setEditAlarm(View  tv) {
        //ColorDrawable[] color = {new ColorDrawable(Color.RED), new ColorDrawable(Color.WHITE)};
        ColorDrawable[] color = { new ColorDrawable(Color.WHITE),new ColorDrawable(Color.RED)};
        TransitionDrawable trans = new TransitionDrawable(color);
        tv.setBackground(trans);
        trans.startTransition(500);
    }
    public void removeEditAlarm(View  tv) {
        ColorDrawable[] color = {new ColorDrawable(Color.RED), new ColorDrawable(Color.WHITE)};
        // ColorDrawable[] color = { new ColorDrawable(Color.WHITE),new ColorDrawable(Color.RED)};
        TransitionDrawable trans = new TransitionDrawable(color);
        tv.setBackground(trans);
        trans.startTransition(500);
    }
    /*******************************************************/
     /*public void correctWord(EditText edit, String s, String  xtype) {

            String message = "";
           // boolean x = global_afterTextChanged(s.toString(),"mot") && s.toString().length() > 1 ;
            boolean x = true;
            if(!x) {
                if (s.toString().length() <= 1) {
                    message = "Le mot à définir est trop court";
                }


            String message = "";
            boolean x = global_afterTextChanged(s.toString(),"fra") && s.toString().length() > 1 ;
                //okmap.put("fra", x);
            if(!x) {
                //    if (s.toString().length() <= 1) {
                //     message = "Ce mot est trop court";
                //  }
                if (!global_afterTextChanged(s.toString(), "fra")) {
                    if(message.equals("")) message = "Ce mot contient des caractères invalides";
                    else message += System.getProperty("line.separator") + "Ce mot contient des caractères invalides";
                    editfra.setTextColor(Color.RED);
                }
                res_fra.setText(message);
                res_fra.setVisibility(View.VISIBLE);
            }
            else { editfra.setTextColor(Color.BLACK); res_fra.setVisibility(View.INVISIBLE); res_fra.setText("");}
            if(s.toString().length() <= 0) {res_fra.setVisibility(View.INVISIBLE);res_fra.setText("");}
        }

        }*/
    public String getValidTypeFra(String text) {
       return text.replaceAll("[a-zA-ZçÇÀÁÂàáâÉÈéèêëÍÌÎîïÓÒÔÖôÚÙÛúùû' ]","");
        //return text.matches("[a-zA-ZçÇÀÁÂàáâÉÈéèêëÍÌÎîïÓÒÔÖôÚÙÛúùû' ]*");
    }
    public String getValidTypeTah(String text) {
        return text.replaceAll("[agertuiopfhkmvnAGERTUIOPFHKMVNÀÁÂĀÄàáâäāÉÈĒËĒéèêēëÍÌÎĪíìîīïÓÒÔÖóôōöÚÙÛŪúùûūü' ]","");
        // return text.matches("[agertuiopfhkmvnAGERTUIOPFHKMVNÀÁÂĀÄàáâäāÉÈĒËĒéèêēëÍÌÎĪíìîīïÓÒÔÖóôōöÚÙÛŪúùûūü' ]*");
    }


    public boolean isValidationTypeFra(String text) {
        return text.matches("[a-zA-ZçÇÀÁÂàáâÉÈéèêëÍÌÎîïÓÒÔÖôÚÙÛúùû' ]*");
    }
    public boolean isValidationTypeTah(String text) {
        return text.matches("[agertuiopfhkmvnAGERTUIOPFHKMVNÀÁÂĀÄàáâäāÉÈĒËĒéèêēëÍÌÎĪíìîīïÓÒÔÖóôōöÚÙÛŪúùûūü' ]*");
    }

    public void global_chek() {

        int errors = 0;
        int empty = 0;
        for (Map.Entry<String, String> e : errors_trads.entrySet()) {
            //to get key
            e.getKey();
            //and to get value
            e.getValue();
            if(e.getValue().equals("wrong")) errors++;
            if(e.getValue().equals("empty")) empty++;

            Log.e("K",e.getKey()+ " / "+e.getValue());
        }

        if(errors > 0 ) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Certaines traductions contiennent des erreurs")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(true)
                    .setMessage("Le formulaire ne sera pas envoyé")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            return;
        }
        Log.e("K","empty "+empty+ " / tradlist "+ tradlist.size());
        if(empty == tradlist.size() -1) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Veuillez remplir au moins une traduction")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(true)
                    .setMessage("Le formulaire ne sera pas envoyé")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
            return;
        }

        if(empty > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Certaines traductions sont manquantes")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(true)
                    .setMessage("Souhaitez-vous envoyer le formulaire ?")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if(op.equals("modif")) new SendModif().execute("http://tehono.org/app/createmodif");
                            if(op.equals("insert")) new SendNewtrad().execute("http://tehono.org/app/createnode");
                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Souhaitez-vous envoyer les traductions ?")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(true)
                    .setMessage("le formulaire est sur le point d'être envoyé")
                    .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            if(op.equals("modif")) new SendModif().execute("http://tehono.org/app/createmodif");
                            if(op.equals("insert")) new SendNewtrad().execute("http://tehono.org/app/createnode");

                        }
                    })
                    .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }

    }







    /*********************************************************/
    public class SendModif extends AsyncTask<String, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            modif_loading.setTitle("Envoi des traductions");
            modif_loading.show();
            //ObjectMapper mapper = new ObjectMapper();
            // "";

          /*  HashMap<String, String> modif_map = new HashMap<>();
            modif_map.put("type", type);
            modif_map.put("nid", String.valueOf(nid));

            for (Map.Entry<String,String> entry : errors_trads.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

               // boolean k = !value.equals("wrong") && !value.equals("empty");
               // if(k && !entry.getKey().equals(type))  modif_map.put(key, value);
            }*/


           // postData = new JSONObject();
            try {
              /*  for (Map.Entry<String,String> entry : modif_map.entrySet()) {

                        postData.put(entry.getKey(),entry.getValue());

                }*/
                postData.put("nid", nid);
                postData.put("title", mot);
                postData.put("type", type);
                postData.put("deviceID", "deviceID");

                // new SendDeviceDetails().execute("http://tehono.org/app/createnode", postData.toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }

            json = postData.toString();

        }
        @Override
        protected String doInBackground(String... params) {
            String data = "";
            HttpURLConnection httpURLConnection = null;
            try {

                httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                httpURLConnection.setRequestMethod("POST");

                httpURLConnection.setDoOutput(true);


                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
                writer.write("PostData=" + json);
                writer.close();
                wr.close();

                InputStream in = httpURLConnection.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(in);

                int inputStreamData = inputStreamReader.read();
                while (inputStreamData != -1) {
                    char current = (char) inputStreamData;
                    inputStreamData = inputStreamReader.read();
                    data += current;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (httpURLConnection != null) {
                    httpURLConnection.disconnect();
                }
            }

            return data;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            modif_loading.dismiss();
            if(result.equals("ok"))  {
                /*Toast.makeText(getApplicationContext(), ", ",
                        Toast.LENGTH_LONG).show();*/
                //finish();
                String messx = "Merci pour votre participation !";
                messx += System.getProperty("line.separator")+ "Votre proposition de modification a maintenant bien été soumise à l'équipe";
                AlertDialog.Builder builder = new AlertDialog.Builder(FormSend.this);
                builder.setTitle("Modification envoyée !")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setCancelable(false)
                        .setMessage(messx)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                                finish();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.show();

            }
            else if(result.equals("ko"))  Toast.makeText(getApplicationContext(), "Un problème est survenu lors de votre requête, veuillez réessayer...",
                    Toast.LENGTH_LONG).show();
            // this is expecting a response code to be sent from your server upon receiving the POST data
            Log.e("TAG", result);
        }
    }



/****************************************/
public class SendNewtrad extends AsyncTask<String, Void, String> {

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        modif_loading.setTitle("Envoi des données");
        modif_loading.show();

        try {
              /*  for (Map.Entry<String,String> entry : modif_map.entrySet()) {

                        postData.put(entry.getKey(),entry.getValue());

                }*/
            //postData.put("nid", nid);
            postData.put("mot", mot);
            //postData.put("title", mot);
            postData.put("type", type);
            postData.put("deviceID", "deviceID");

            // new SendDeviceDetails().execute("http://tehono.org/app/createnode", postData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        json = postData.toString();

    }
    @Override
    protected String doInBackground(String... params) {
        String data = "";
        HttpURLConnection httpURLConnection = null;
        try {

            httpURLConnection = (HttpURLConnection) new URL(params[0]).openConnection();
            httpURLConnection.setRequestMethod("POST");

            httpURLConnection.setDoOutput(true);


            DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(wr, "UTF-8"));
            writer.write("PostData=" + json);
            writer.close();
            wr.close();

            InputStream in = httpURLConnection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(in);

            int inputStreamData = inputStreamReader.read();
            while (inputStreamData != -1) {
                char current = (char) inputStreamData;
                inputStreamData = inputStreamReader.read();
                data += current;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        return data;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        modif_loading.dismiss();
        if(result.equals("ok"))  {
                /*Toast.makeText(getApplicationContext(), ", ",
                        Toast.LENGTH_LONG).show();*/
            //finish();
            String messx = "Merci pour votre participation !";
            messx += System.getProperty("line.separator")+ "Votre proposition d'une nouvelle traduction a maintenant bien été soumise à l'équipe";
            AlertDialog.Builder builder = new AlertDialog.Builder(FormSend.this);
            builder.setTitle("Traduction envoyée !")
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setCancelable(false)
                    .setMessage(messx)
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            finish();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();

        }
        else if(result.equals("ko"))  Toast.makeText(getApplicationContext(), "Un problème est survenu lors de votre requête, veuillez réessayer...",
                Toast.LENGTH_LONG).show();
        // this is expecting a response code to be sent from your server upon receiving the POST data
        Log.e("TAG", result);
    }
}


}