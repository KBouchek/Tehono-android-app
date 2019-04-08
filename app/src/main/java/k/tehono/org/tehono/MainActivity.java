package k.tehono.org.tehono;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.internal.widget.AdapterViewCompat;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v4.widget.DrawerLayout;

import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import  android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.TimeZone;
import java.util.Timer;

import static k.tehono.org.tehono.R.id.container;


public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener {

    LinearLayout lay0,linearLayoutPub,LinearLayoutPub2;
    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;

    ListView listView ;
    ViewFlipper vf;
    EditText searchBox;
    ArrayList<String> partiaresults = new ArrayList<String>();
    ArrayList<Partialresult> lastPartialResults = new ArrayList<Partialresult>();
    public String[] values;
    public Handler handler;
    public Runnable myRunnable;
    public ArrayAdapter<String> adapter;
    int delai_avant_requete;
    public ProgressBar bar;
    public String type;
    public Typeface custom_font;
    public Boolean setting_clavier_autoclose;
    private ProgressBar progressBar2;
    private CountDownTimer mCountDownTimer;
    public RelativeLayout rel2;
    public RelativeLayout noresultslayout;
    public Spinner sp;
    ArrayList<Language> dataModelsannexe;
    boolean Touched;
    public AdapterView.OnItemSelectedListener listener;
    public SpinnerAdapter spinneradapter;
    public Button clearnoresult;
    public Button traduire;
    public TextView noresmot;
    private Menu mMenu;
    private ArrayList<FrameLayout> frames;
    private ArrayList<String> xLabels;
    private String xLabel;
    private WebView t1;
    private boolean loaded1;
    private boolean loaded2;
    private WebView t2,pubwebview;
    public  TeHono application;
    public Tracker mTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        FrameLayout frame0 = (FrameLayout) findViewById(R.id.container_body);
        FrameLayout frame1 = (FrameLayout) findViewById(R.id.container_body1);
        FrameLayout frame2 = (FrameLayout) findViewById(R.id.container_body2);
        frames = new ArrayList<>();
        frames.add(0,frame0);
        frames.add(1,frame1);
        frames.add(2,frame2);
        xLabel = new String();
        xLabel = "";

        xLabels = new ArrayList<String>();
        xLabels.add(0,"Te Hono");
        xLabels.add(1,"Te Hono: Le projet");
        xLabels.add(2,"Te Hono: L'application");
       // xLabels.add(3,"Te Hono: À propos");

        t1 = new WebView(this);
       t1 = (WebView) findViewById(R.id.text1);
        t1.setBackgroundColor(0x00000000);
        t1.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        t1.getSettings().setUserAgentString("Android WebView");
        //t1.getSettings().setUseWideViewPort(true);
        //t1.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        //t1.loadData(getString(R.string.defaultDetail1), "text/html; charset=utf-8", "utf-8");

        t1.getSettings().setJavaScriptEnabled(true);


        t2 = new WebView(this);
        t2 = (WebView) findViewById(R.id.text2);
        t2.setBackgroundColor(0x00000000);
        t2.getSettings().setUserAgentString("Android WebView");
        t2.getSettings().setJavaScriptEnabled(true);
        t2.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        // t2.getSettings().setUseWideViewPort(true);
        setUpWebView();

        LinearLayoutPub2 = (LinearLayout) findViewById(R.id.LinearLayoutPub2);
        LinearLayoutPub2.setBackgroundColor(Color.parseColor("#000000"));

        pubwebview = (WebView) findViewById(R.id.webview);
        pubwebview.getSettings().setLoadsImagesAutomatically(true);
        pubwebview.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url != null && url.startsWith("http")) {
                    /*view.getContext().startActivity(
                            new Intent(Intent.ACTION_VIEW, Uri.parse(url)));*/
                   Intent launchBrowser = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(launchBrowser);
                    return true;
                } else {
                    return false;
                }
            }
        });
        pubwebview.getSettings().setJavaScriptEnabled(true);
        pubwebview.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        pubwebview.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //pubwebview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        pubwebview.loadUrl("http://tehono.org/pub/index.html");
        //.setBackgroundColor(Color.parseColor("#ffff00"));
        //t1.setLayerType(WebView.LAYER_TYPE_SOFTWARE, null);
        //t2.loadData(getString(R.string.defaultDetail2), "text/html; charset=utf-8", "utf-8");
        //t1.loadData(myData);
        //t1.setText(Html.fromHtml(getString(R.string.defaultDetail1)));
      /*  SharedPreferences prefs = getApplicationContext().getSharedPreferences("Tehono", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.commit();*/

        Preferences yourPrefrence = Preferences.getInstance(getApplicationContext());
        String type_pref = yourPrefrence.getStringData("type");
        if(type_pref.length() <=0 ) {
            // Log.e("kk","creation type ");
            yourPrefrence.saveStringData("type","tah");
        }
        type_pref = yourPrefrence.getStringData("type");
        this.type = type_pref;

        custom_font = Typeface.createFromAsset(this.getAssets(), "fertigo.ttf");
        for(int i = 0; i < mToolbar.getChildCount(); i++)
        { View view = mToolbar.getChildAt(i);
            if(view instanceof TextView) {
                TextView textView = (TextView) view;
                textView.setTextSize(16.0f);
                textView.setTypeface(custom_font);
            }
        }




       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });



        drawerFragment = (FragmentDrawer)
                getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);
        //displayView(0);
        /*mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"your icon was clicked",Toast.LENGTH_SHORT).show();
            }
        });*/

        getWindow().setBackgroundDrawableResource(R.drawable.palme1);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy =
                    new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        /*Preferences p = Preferences.getInstance(getApplicationContext());
        delai_avant_requete = p.getIntData("delai_avant_recherche");
        if(delai_avant_requete < 2000) {
            p.saveIntData("delai_avant_recherche",2000);
            delai_avant_requete = 2000;
        }*/
        clearnoresult = (Button) findViewById(R.id.angry_btn2);
        clearnoresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });


       sp = (Spinner) findViewById(R.id.spinner);
        LanguagesPref langpref = LanguagesPref.getInstance(getApplicationContext());
       dataModelsannexe = langpref.getdefaults();
        spinneradapter = new SpinnerAdapter(this,
                R.layout.spinner_layout, R.id.txt, dataModelsannexe);
        sp.setAdapter(spinneradapter);
        setlistneronspin();
       /* LanguagesPref langpref = LanguagesPref.getInstance(getApplicationContext());
        dataModelsannexe = langpref.getdefaults();
        spinneradapter = new SpinnerAdapter(this,
                R.layout.spinner_layout, R.id.txt, dataModelsannexe);
        sp.setAdapter(spinneradapter);*/
       // setlistneronspin();

       /* sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                         @Override
                                         public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                             if(dataModelsannexe.size() <= 0) return;
                                             //Language data = dataModelsannexe.get(position);
                                             hideKeyboard(view);
                                             clear();;
                                             //Log.i("KK hideKeyboard", "sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener");
                                             //listviewresult.setAdapter(null);
                                             ImageView hs = (ImageView) findViewById(R.id.hr);
                                             hs.setVisibility(View.GONE);
                                             Language selectedlangage = dataModelsannexe.get(position);
                                             Toast.makeText(parent.getContext(),
                                                     "Recherche en "+selectedlangage.getTitle(),
                                                     Toast.LENGTH_SHORT).show();
                                             setTitle("Te Hono: "+selectedlangage.getLabel());
                                             type = selectedlangage.getType();

                                             //acTextView.setText("");
                                             /*if (position == 0) {
                                                 Toast.makeText(parent.getContext(),
                                                         "Recherche en Tahitien ",
                                                         Toast.LENGTH_SHORT).show();
                                                 setTitle("Te Hono: Tahitien");
                                                 type = "tah";
                                                 //showKeyBoard();
                                             }
                                             if (position == 1) {
                                                 Toast.makeText(parent.getContext(),
                                                         "Recherche en Français ",
                                                         Toast.LENGTH_SHORT).show();
                                                 setTitle("Te Hono: Français");
                                                 type = "fra";
                                                 //showKeyBoard();
                                             }
                                             if (position == 2) {
                                                 Toast.makeText(parent.getContext(),
                                                         "Recherche en Pa'umotu ",
                                                         Toast.LENGTH_SHORT).show();
                                                 setTitle("Te Hono: Pa'umotu");
                                                 type = "pau";
                                                 //showKeyBoard();
                                             }
                                             if (position == 3) {
                                                 Toast.makeText(parent.getContext(),
                                                         "Recherche en Marquisien ",
                                                         Toast.LENGTH_SHORT).show();
                                                 setTitle("Te Hono: Marquisien");
                                                 type = "mar";
                                                 //showKeyBoard();
                                             }
                                             if (position == 4) {
                                                 Toast.makeText(parent.getContext(),
                                                         "Recherche en langues Australes ",
                                                         Toast.LENGTH_SHORT).show();
                                                 setTitle("Te Hono: Marquisien");
                                                 type = "aus";
                                                 //showKeyBoard();
                                             }
                                             if (position == 5) {
                                                 Toast.makeText(parent.getContext(),
                                                         "Recherche en  Mangarevien ",
                                                         Toast.LENGTH_SHORT).show();
                                                 setTitle("Te Hono: Mangarevien");
                                                 type = "man";
                                                 //showKeyBoard();
                                             }
                                             if (position == 6) {
                                                 Toast.makeText(parent.getContext(),
                                                         "Recherche en  Rapa ",
                                                         Toast.LENGTH_SHORT).show();
                                                 setTitle("Te Hono: Rapa");
                                                 type = "rap";
                                                 // showKeyBoard();
                                             }
                                             //lastnid.setText("0");
                                             //s3.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparent)));
                                             //s2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparent)));
                                         }

                                         @Override
                                         public void onNothingSelected(AdapterView<?> parent) {
                                         }
                                     });*/


        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar2.setVisibility(View.VISIBLE);
        progressBar2.getProgressDrawable().setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        progressBar2.setProgress(0);


        rel2 = (RelativeLayout) findViewById(R.id.RelativeLayout2) ;
        rel2.setVisibility(View.GONE);

        noresultslayout = (RelativeLayout) findViewById(R.id.RelativeLayoutNoResults) ;
        noresmot = (TextView) findViewById(R.id.nores2);
        //noresultslayout.setVisibility(View.INVISIBLE);
        noresultslayout.setAlpha(0.0f);
        noresultslayout.setVisibility(View.GONE);

        progressBar2.getProgressDrawable().setColorFilter(
                Color.GREEN, android.graphics.PorterDuff.Mode.SRC_IN);
        handler = new Handler();

        linearLayoutPub = (LinearLayout) findViewById(R.id.LinearLayoutPub);
        listView = (ListView) findViewById(R.id.listView);
        bar = (ProgressBar) findViewById(R.id.progressBar1);
        bar.setVisibility(View.INVISIBLE);
        traduire = (Button)  findViewById(R.id.angry_btn1);
        partiaresults = new ArrayList<String>();
        lastPartialResults = new ArrayList<Partialresult>();
        values = new String[0];

        LayoutInflater inflaterx = getLayoutInflater();
        //View proj = inflaterx.inflate(R.layout.frag_leprojet, null);

        //LayoutInflater inflater = getLayoutInflater();
        /*LayoutInflater infalter = getLayoutInflater();
        ViewGroup header = (ViewGroup) infalter.inflate(R.layout.main_headerlistrow, listView, false);
        listView.addHeaderView(header);*/
       // listView.addHeaderView(findViewById(R.layout.main_headerlistrow));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Traduction")
                        .setAction("Traduction Mobile")
                        .build());

                int itemPosition     = position;
                String  itemValue    = (String) listView.getItemAtPosition(position);
                Partialresult p = lastPartialResults.get(position);


                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Traduction")
                        .setAction("Traduction Android")
                        .build());
                /*Toast.makeText(getApplicationContext(),
                       "nid :"+p.getNid()+"  title : " +p.getTitle() +" position = "+position, Toast.LENGTH_LONG)
                       .show();*/
                Intent intent = new Intent(MainActivity.this,Details.class);

                intent.putExtra("nid",p.getNid());
                intent.putExtra("titlex",p.getTitle());
                intent.putExtra("type",type);
                intent.putExtra("op","modif");
                //overridePendingTransition(R.anim.flipfadein, R.anim.flipfadeout);
               /* Toast.makeText(getApplicationContext(),
                        "nid :"+p.getNid()+"  title : " +p.getTitle() , Toast.LENGTH_LONG)
                        .show();*/

                mTracker.send(new HitBuilders.EventBuilder()
                        .setCategory("Traduction de mot")
                        .setAction("("+type+")"+" mot: "+p.getTitle())
                        .build());

                startActivityForResult(intent, 1);
                //startActivity(intent);


                /*Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("nid",itemPosition+"");
                intent.putExtra("type","tah");
                intent.putExtra("title","Tutu");
                startActivity(intent);*/
            }

        });


        lay0 = (LinearLayout) findViewById(R.id.LinearLayout0);


        lay0.setOnTouchListener(
                new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent ev) {
                        hideKeyboard(lay0);
                        //Log.i("KK hideKeyboard", "lay0.setOnTouchListener");

                        return false;
                    }
                });

        vf = (ViewFlipper) findViewById(R.id.ViewFlipper01);
        vf.setFlipInterval(5000);
        //vf.setFlipInterval(2000);

        vf.startFlipping();



        searchBox = (EditText)findViewById(R.id.autoComplete);
        //searchBox.setTypeface(custom_font);
        searchBox.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                progressBar2.setProgress(0);
              //  listView.setVisibility(View.INVISIBLE);
                mCountDownTimer.cancel();
                rel2.setVisibility(View.GONE);
                bar.setVisibility(View.INVISIBLE);
                if(noresultslayout.getVisibility() == View.VISIBLE) {
                    fadeOuTNoresults();
                }
                searchBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
                if (s.length() > 0) {
                    bar.setVisibility(View.VISIBLE);
                    //searchBox.setBackground(ContextCompat.getDrawable(this.getClass(), R.drawable.gradient_borderx));
                    searchBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_borderx));
                    //bar.setVisibility(View.VISIBLE);
                    progressBar2.setVisibility(View.VISIBLE);
                    handler.removeCallbacksAndMessages(null);
                    myRunnable = new MyRunnable("http://tehono.org/app/search/"+type+"/"+s.toString());
                    handler.postDelayed(myRunnable,delai_avant_requete);


                    mCountDownTimer.start();



                    //Log.i("KK hideKeyboard", "afterTextChanged: "+s.toString());
                    //closefab.setVisibility(View.VISIBLE);
                } else {
                    handler.removeCallbacksAndMessages(null);
                }
            }
        });


        traduire.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent modif = new Intent(MainActivity.this,Choixlangues.class);
               // modif.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                modif.putExtra("nid", "0");
                modif.putExtra("titlex",searchBox.getText().toString());
                modif.putExtra("type",type);
                modif.putExtra("op","insert");


                startActivity(modif);
            }
        });

        application = (TeHono) getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    protected void onPause() {


        // Log.i("Life Cycle", "onPause");
        //  sp.clearFocus();
        super.onPause();
    }
    @Override
    protected void onResume() {
        super.onResume();
        mTracker.setScreenName("PageDeRecherche");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }


   @Override
   public void onStart() {
    super.onStart();
       //// Log.i("Life Cycle", "onStart");
       Preferences p = Preferences.getInstance(getApplicationContext());
       progressBar2.setScaleY(0.2f);
       delai_avant_requete = p.getIntData("delai_avant_recherche");
       if(delai_avant_requete < 2000) {
           p.saveIntData("delai_avant_recherche",2000);
           delai_avant_requete = 2000;
       }
       String x = p.getStringData("keyboard_open_auto");


       if(x.equals("")) {
           p.saveStringData("keyboard_open_auto","on");
           setting_clavier_autoclose = true;
       }
       else if(x.equals("on")) {
           setting_clavier_autoclose = true;
       } else setting_clavier_autoclose = false;


       mCountDownTimer = new CountDownTimer(delai_avant_requete, 100) {
           @Override
           public void onTick(long millisUntilFinished) {
               //done every 1000 milliseconds
               int progress = Math.round((delai_avant_requete - millisUntilFinished)*100  / delai_avant_requete);
               // Log.i("KK hideKeyboard", "progress: "+"seconds remaining: " + millisUntilFinished * 100/ delai_avant_requete);
               progressBar2.setProgress(progress);
           }

           @Override
           public void onFinish() {
               progressBar2.setProgress(0);
               progressBar2.setVisibility(View.INVISIBLE);
               bar.setVisibility(View.INVISIBLE);
           }

       };


       LanguagesPref langpref = LanguagesPref.getInstance(getApplicationContext());
       dataModelsannexe = langpref.getdefaults();
       if(dataModelsannexe.size() <= 0) {
           // Log.e("KK", "dataModelsannexe empty");
           return;
       }
       else {
           // Log.e("KK", "SpinnerAdapter ok this.type is "+this.type+ "/");
           // LanguagesPref langpref = LanguagesPref.getInstance(getApplicationContext());
           // dataModelsannexe = langpref.getdefaults();
           // spinneradapter = new SpinnerAdapter(this,
           // R.layout.spinner_layout, R.id.txt, dataModelsannexe);
           // sp.setAdapter(spinneradapter);
           // spinneradapter.notifyDataSetChanged();
                spinneradapter.replaceAll(dataModelsannexe);

           /*Preferences yourPrefrence = Preferences.getInstance(getApplicationContext());
           String type_pref = yourPrefrence.getStringData("type");*/

           for(int i = 0; i < dataModelsannexe.size(); i++) {
               // Log.e("KK", "dataModelsannexe is "+this.type+ "/ "+dataModelsannexe.get(i).getType()+" "+dataModelsannexe.get(i).getOrder()+" i= "+i);
               if(dataModelsannexe.get(i).getType().equals(this.type)){
                   //Language l = dataModelsannexe.get(i);
                   //Language selected = dataModelsannexe.get(i);
                   //sp.setSelection(dataModelsannexe.get(i).getOrder());
                   //   Log.e("KK", "setSelection is "+this.type+ "/ "+dataModelsannexe.get(i).getType()+" "+dataModelsannexe.get(i).getOrder()+" i= "+i);

                   sp.setSelection(dataModelsannexe.get(i).getOrder());
                   setTitle("Te Hono: "+dataModelsannexe.get(i).getTitle());
                   xLabel = "Te Hono: "+dataModelsannexe.get(i).getTitle();
                   for(int ii = 0; ii < dataModelsannexe.size(); ii++) {
                       //  Log.e("KK", "dataModelsannexe  boucle is "+dataModelsannexe.get(ii).getType()+" dataModelsannexe order = "+dataModelsannexe.get(ii).getOrder()+" i= "+ii);

                   }
                   break;
                   //sp.setSelection(i);

               }
           }

       }



    }
@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
    //if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
    // Log.e("KK", "onActivityResult requestCode "+resultCode+" "+data);
    super.onActivityResult(requestCode, resultCode, data);
            if (resultCode == RESULT_OK) {
                // Log.e("KK", "onActivityResult RESULT_OK "+resultCode+" "+data);
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
    //}
    }
public void setlistneronspin() {
    sp.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    // Ensure you call it only once works for JELLY_BEAN and later
                    sp.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    // add the listener
                    sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            // check if pos has changed
                            // then do your work
                            //LanguagesPref langprefx = LanguagesPref.getInstance(getApplicationContext());
                            // dataModelsannexe = langprefx.getdefaults();
                            if(dataModelsannexe.size() <= 0) return;
                           if(lay0 != null) {hideKeyboard(lay0);}


                           clear();
                            //  Log.i("onGlobalLayout","selected position"+ position);
                            //listviewresult.setAdapter(null);
                            ImageView hs = (ImageView) findViewById(R.id.hr);
                            hs.setVisibility(View.GONE);
                            Language selectedlangage = dataModelsannexe.get(position);
                            //  Log.i("onGlobalLayout","selected selectedlangage"+ selectedlangage.getTitle());

                            //setTitle("Te Hono: "+selectedlangage.getLabel());
                            type = selectedlangage.getType();
                            Preferences yourPrefrence = Preferences.getInstance(getApplicationContext());
                            yourPrefrence.saveStringData("type",type);
                            Toast.makeText(parent.getContext(),
                                    "Recherche: "+selectedlangage.getLabel(),
                                    Toast.LENGTH_SHORT).show();
                            setTitle("Te Hono: "+selectedlangage.getTitle());
                            showKeyBoard();
                            //InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                            // getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                            //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                            //imm.showSoftInput(searchBox, InputMethodManager.);

                            //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> arg0) {
                        }

                    });

                }
            });

}
public void setspin() {


    /*    spinneradapter = new SpinnerAdapter(this,
                R.layout.spinner_layout, R.id.txt, dataModelsannexe);

    Log.e("kk", "setspin");*/
      /*  sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(dataModelsannexe.size() <= 0) return;
                if(lay0 != null) {hideKeyboard(lay0);}


                clear();
                //Log.i("KK hideKeyboard", "sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener");
                //listviewresult.setAdapter(null);
                ImageView hs = (ImageView) findViewById(R.id.hr);
                hs.setVisibility(View.GONE);
                Language selectedlangage = dataModelsannexe.get(position);
                Toast.makeText(parent.getContext(),
                        "Recherche en "+selectedlangage.getTitle(),
                        Toast.LENGTH_SHORT).show();
                setTitle("Te Hono: "+selectedlangage.getLabel());
                type = selectedlangage.getType();
                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                //acTextView.setText("");

                //lastnid.setText("0");
                //s3.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparent)));
                //s2.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorPrimaryTransparent)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
  /*  for(int i = 0; i < dataModelsannexe.size(); i++) {
        Log.e("KK", "spinner "+dataModelsannexe.get(i).getType()+" "+this.type);
        if(dataModelsannexe.get(i).getType() == this.type){
            Language l = dataModelsannexe.get(i);
            sp.setSelection(i);
            break;
        }
    }*/





    //sp.setAdapter(spinneradapter);
    }
public void clear() {
    //if(rel2.getVisibility() == View.VISIBLE) rel2.setVisibility(View.GONE);
    rel2.setVisibility(View.GONE);

    progressBar2.setProgress(0);
    progressBar2.setVisibility(View.INVISIBLE);

    if(noresultslayout.getVisibility() == View.VISIBLE) {
        fadeOuTNoresults();
    }

   bar.setVisibility(View.INVISIBLE);
    searchBox.setText("");
/*
    listView.setVisibility(View.INVISIBLE);

*/
    partiaresults.clear();
    lastPartialResults.clear();

    mCountDownTimer.cancel();
    handler.removeCallbacksAndMessages(null);

}
public void fadeInNoresults () {
    noresultslayout.setAlpha(0.0f);
    noresultslayout.setVisibility(View.VISIBLE);
        ObjectAnimator fadeIn = ObjectAnimator.ofFloat(noresultslayout, "alpha", 0.0f, 1f);
        fadeIn.setDuration(500);
        AnimatorSet mAnimationSet = new AnimatorSet();
         mAnimationSet.play(fadeIn);
        mAnimationSet.start();
    }
    public void fadeOuTNoresults () {

        noresultslayout.setAlpha(0.0f);
        noresultslayout.setVisibility(View.GONE);
    }
    public class MyRunnable implements Runnable {
        private String url;

        public MyRunnable(String parameter) {
            this.url = parameter;
        }

        public void run() {

            searchBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient_borderx));
            String retour = getUrlContents(url);

            mTracker.send(new HitBuilders.EventBuilder()
                    .setCategory("Page Centrale Android")
                    .setAction("Requête de traduction")
                    .build());

            if (retour != null) {
                try {
                    JSONObject jsonObj = new JSONObject(retour);
                    JSONArray results = jsonObj.getJSONArray("results");
                    partiaresults.clear();
                    lastPartialResults.clear();
                    for (int i = 0; i < results.length(); i++) {

                        JSONObject c = results.getJSONObject(i);
                        String id = c.getString("id");
                        String name = c.getString("name");
                        Partialresult p = new Partialresult(name,id,type);
                        partiaresults.add(name);
                        lastPartialResults.add(p);
                        //Log.e("kk", "Json response: " + id + " / " + name);
                    }
                    setTable();
                    /*runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setTable();
                        }
                    });*/



                } catch (final JSONException e) {
                    //  Log.e("kk", "Json parsing error: " + e.getMessage());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setMessage("Impossible d'accéder à internet");
                        }
                    });

                }
                //Log.i("KK hideKeyboard", "retour: "+retour);
            } else {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        setMessage("Impossible d'accéder à internet");
                    }
                });
            }
            //searchBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
            bar.setVisibility(View.INVISIBLE);
        }
    }
    public void setMessage(String mess){
        Toast.makeText(getApplicationContext(),"Une erreur est survenue:\n "+mess, Toast.LENGTH_SHORT).show();
    }
    public void setTable (){
        //hideKeyboard(lay0);
        // Log.i("KK", "setTable partialresults are  " +partiaresults.size());
        if(partiaresults.size() <= 0)  {
            noresmot.setTextColor(Color.RED);
            noresmot.setText("Le mot: "+searchBox.getText().toString()+" n'a pas été trouvé");
            fadeInNoresults();
            return;
        }
        //{"results":[]}

        listView.setVisibility(View.VISIBLE);
        rel2.setVisibility(View.VISIBLE);
        if (setting_clavier_autoclose) hideKeyboard(lay0);
        PartialAdapter p = new PartialAdapter(getApplicationContext(), partiaresults);
        listView.setAdapter(p);
    }
    public String getUrlContents(String theUrl) {

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
            e.printStackTrace();
            return "";
        }

        // Toast.makeText(c,"requesting "+theUrl, Toast.LENGTH_SHORT);
        TimeZone tz = TimeZone.getTimeZone("UTC");
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        df.setTimeZone(tz);
        String time = df.format(new Date());

        // Log.i("KK", "requesting " + theUrl + " - at time : " + time);
        //Log.i("KK", "returning " + content.toString());
        searchBox.setBackgroundDrawable(getResources().getDrawable(R.drawable.gradient));
        return content.toString();

    }

    private void hideKeyboard(View v) {
        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Affichage Clavier")
                .setAction("down")
                .build());
        InputMethodManager inputMethodManager =(InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    public void showKeyBoard() {

        mTracker.send(new HitBuilders.EventBuilder()
                .setCategory("Affichage Clavier")
                .setAction("up")
                .build());

        InputMethodManager imm = (InputMethodManager)
                getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        if(imm != null){
            imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, 0);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        mMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        // Log.e("onOptionsItemSelected", "onOptionsItemSelected");
        /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();*/
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this,AllSettings.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_clear) {
            clear();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onDrawerItemSelected(View view, int position) {
        // Log.e("CustomAdapter", " position =====Row button clicked====="+Integer.toString(position));
        displayView(position);
       /* if(position == 0) {
            FrameLayout frame1 = (FrameLayout) findViewById(R.id.container_body1);
            frame1.setVisibility(View.GONE);
            FrameLayout frame = (FrameLayout) findViewById(R.id.container_body);
            frame.setVisibility(View.VISIBLE);

            //  MenuItem Import = mMenu.findItem(R.id.action_settings);
            // Import.setVisible(true);
        }
        if(position == 1) {

            for (int i = 0; i < mToolbar.getChildCount(); i++) {
                View child = mToolbar.getChildAt(i);
                if (child instanceof ActionMenuView)
                {
                    Log.e("K",child.toString());
                    child.setVisibility(View.INVISIBLE);
                }
            }
            //if (mMenu != null) {
            // MenuItem Import = mMenu.findItem(R.id.action_settings);
            // Import.setVisible(false);
            // } //ActivityCompat.invalidateOptionsMenu(MainActivity.this);


            FrameLayout frame = (FrameLayout) findViewById(R.id.container_body);
            frame.setVisibility(View.GONE);
            FrameLayout frame1 = (FrameLayout) findViewById(R.id.container_body1);
            frame1.setVisibility(View.VISIBLE);
        }*/

    }

/*******************************************************************/
private void displayView(int position) {
    t1.scrollTo(0,0);
    t2.scrollTo(0,0);

    if(position == 1 || position == 2 ) {
        if(!loaded1 || !loaded2) setUpWebView();
    }

    for (int i = 0; i < frames.size(); i++)
     {
        if(i == position) {
            frames.get(i).setVisibility(View.VISIBLE);
        }else {
            frames.get(i).setVisibility(View.GONE);
        }
    }

    if(position == 3){

        Intent intent = new Intent(MainActivity.this,AllSettings.class);
        startActivity(intent);
        for (int i = 1; i < frames.size(); i++)
        {
            frames.get(i).setVisibility(View.GONE);
        }
        frames.get(0).setVisibility(View.VISIBLE);
    }
    if(position == 0 || position == 3){
        showRightButtons();
    } else hideRightButtons();
    if(position == 0) {
        setTitle(xLabel);
        //if(setting_clavier_autoclose) showKeyBoard();
    } else if(position < 3) {
        setTitle(xLabels.get(position));
    }
}
    private void hideRightButtons() {
        for (int i = 0; i < mToolbar.getChildCount(); i++) {
            View child = mToolbar.getChildAt(i);
            if (child instanceof ActionMenuView)
            {
                // Log.e("K",child.toString());
                child.setVisibility(View.INVISIBLE);
            }
        }
    }
    private void showRightButtons() {
        for (int i = 0; i < mToolbar.getChildCount(); i++) {
            View child = mToolbar.getChildAt(i);
            if (child instanceof ActionMenuView)
            {
                // Log.e("K",child.toString());
                child.setVisibility(View.VISIBLE);
            }
        }
    }
    /***************************************/


    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }



    public void setUpWebView() {
        boolean itcnnx = haveNetworkConnection();
        if(itcnnx) {
            t1.loadUrl("http://tehono.org/app/android/description/projet");
            loaded1 = true;
        } else {
            t1.loadData("<html><head><body style='color:white'>Pas de connexion internet.<br/>Cette page s'affichera une fois la connexion internet rétablie</body></html>", "text/html; charset=utf-8", "utf-8");
            loaded1 = false;
        }

        if(itcnnx) {
            t2.loadUrl("http://tehono.org/app/android/description/application");
            loaded2 = true;
        } else {
            t2.loadData("<html><head><body style='color:white'>Pas de connexion internet.<br/>Cette page s'affichera une fois la connexion internet rétablie</body></html>", "text/html; charset=utf-8", "utf-8");
            loaded2 = false;
        }
    }
}
