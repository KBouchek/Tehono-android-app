package k.tehono.org.tehono;


import android.content.Context;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import k.tehono.org.tehono.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by K on 25/01/2017.
 */

public class Languages {
public static final String TEHONO = "TEHONO";
    private static Languages instance;
   public ArrayList<Language> langualist;
    static public Context c;


    public Languages() {}

    public static Languages getInstance(Context cc)
    {
        setContext(cc);
        if (instance == null)
        {

            instance = new Languages();
            instance.langualist = getdefaults();

        }
        return instance;
    }

    static public void setContext(Context x){
      c = x;
     }

    static public ArrayList<Language> getdefaults(){
        ArrayList<Language> dataModels= new ArrayList<Language>();

        SharedPreferences prefs = c.getSharedPreferences(TEHONO, MODE_PRIVATE);
        int frapreforder = prefs.getInt("frapreforder",0);
        int tahpreforder = prefs.getInt("tahpreforder",1);
        int paupreforder = prefs.getInt("paupreforder",2);
        int marpreforder = prefs.getInt("marpreforder",3);
        int manpreforder = prefs.getInt("manpreforder",4);
        int auspreforder = prefs.getInt("auspreforder",5);
        int rapreforder = prefs.getInt("rapreforder",6);


        Language fra = new Language("Français","fra","Français / Reo Fārani","Français", ContextCompat.getDrawable(c,R.drawable.fran50),frapreforder);
        Language tah = new Language("Tahitien","tah","Tahitien / Reo Tahiti","Tahitien", ContextCompat.getDrawable(c,R.drawable.tah50),tahpreforder);
        Language pau = new Language("Pa'umotu","pau","Pa'umotu / Reo Pa'umotu","Pa'umotu", ContextCompat.getDrawable(c,R.drawable.pau50),paupreforder);
        Language mar = new Language("Marquisien","mar","Marquisien / 'Eo Enata - Enana ","Marquisien", ContextCompat.getDrawable(c,R.drawable.mar50),marpreforder);
        Language man = new Language("Mangarevien","man","Mangarevien / Reo Magareva","Mangarevien", ContextCompat.getDrawable(c,R.drawable.man50),manpreforder);
        Language aus = new Language("Australes","aus","Australes / Reo Tuha'a Pae","Australes", ContextCompat.getDrawable(c,R.drawable.aus50),auspreforder);
        Language rap = new Language("Rapa","rap","Rapa / Oparo","Rapa", ContextCompat.getDrawable(c,R.drawable.rapa50),rapreforder);


        ArrayList<Language> old = new ArrayList<Language>();
        old.add(fra);
        old.add(tah);
        old.add(pau);
        old.add(mar);
        old.add(man);
        old.add(aus);
        old.add(rap);

        Collections.sort(old, new Comparator<Language>() {
            @Override
            public int compare(Language fruit2, Language fruit1)
            {
                return   (fruit1.getOrder() < fruit2.getOrder()) ? 1 : -1 ;
            }
        });
        dataModels.clear();
        for(int i = 0; i < old.size(); i++){
            Language xx = old.get(i);
            int pos = xx.getOrder();
            dataModels.add(pos,xx);;
        }
        return  dataModels;
    }

    public Boolean setOrder(ArrayList<Language> newlist){
        /*Collections.sort(newlist, new Comparator<Language>() {
            @Override
            public int compare(Language fruit2, Language fruit1)
            {

                return  (fruit1.getOrder() < fruit2.getOrder()) ? 1 : -1;
            }
        });
        ArrayList<Language> xxx = new ArrayList<Language>();*/

        SharedPreferences prefs = c.getSharedPreferences(TEHONO, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();

        for(int i = 0; i < newlist.size(); i++){
            Language xx = newlist.get(i);
            int k = xx.getOrder();
            String id = xx.getType()+"preforder";
            editor.putInt(id,k);
            //xxx.add(k,xx);

             //Log.i("K", "setOrder "+xx.getType() +" => " +k);
        }
        editor.commit();
        this.langualist = newlist;
        return true;


    }

}
