package k.tehono.org.tehono;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;



/**
 * Created by K on 26/01/2017.
 */

public class LanguagesPref {

    private static LanguagesPref languages;
    private SharedPreferences sharedPreferences;
    public  Context c;
    public static LanguagesPref getInstance(Context context) {
        if (languages == null) {
            languages = new LanguagesPref(context);
        }
        return languages;
    }

    private LanguagesPref(Context context) {
        sharedPreferences = context.getSharedPreferences("Tehono",Context.MODE_PRIVATE);
        c = context;
    }

    public ArrayList<Language> getdefaults(){
        ArrayList<Language> dataModels= new ArrayList<Language>();

        Preferences yourPrefrence = Preferences.getInstance(c);

        int tahpreforder = yourPrefrence.getIntData("tahpreforder");

    if(tahpreforder < 0) {
        Log.i("KK tahpreforder", "tahpreforder reset: ");
        yourPrefrence.saveIntData("tahpreforder",0);
        yourPrefrence.saveIntData("frapreforder",1);
        yourPrefrence.saveIntData("paupreforder",2);
        yourPrefrence.saveIntData("marpreforder",3);
        yourPrefrence.saveIntData("manpreforder",4);
        yourPrefrence.saveIntData("auspreforder",5);
        yourPrefrence.saveIntData("rappreforder",6);
    }
        tahpreforder = yourPrefrence.getIntData("tahpreforder");
        int frapreforder = yourPrefrence.getIntData("frapreforder");
        int paupreforder = yourPrefrence.getIntData("paupreforder");
        int marpreforder = yourPrefrence.getIntData("marpreforder");
        int manpreforder = yourPrefrence.getIntData("manpreforder");
        int auspreforder = yourPrefrence.getIntData("auspreforder");
        int rapreforder = yourPrefrence.getIntData("rappreforder");

        /*Language fra = new Language("Français","fra","Français / Reo Frani","Français", ContextCompat.getDrawable(c,R.drawable.fran50),frapreforder);
        Language tah = new Language("Tahitien","tah","Tahitien / Reo Tahiti","Tahitien", ContextCompat.getDrawable(c,R.drawable.tah50),tahpreforder);
        Language pau = new Language("Pa'umotu","pau","Pa'umotu / Reo Pa'umotu","Pa'umotu", ContextCompat.getDrawable(c,R.drawable.pau50),paupreforder);
        Language mar = new Language("Marquisien","mar","Marquisien / 'Eo Enata - Enana ","Marquisien", ContextCompat.getDrawable(c,R.drawable.mar50),marpreforder);
        Language man = new Language("Mangarevien","man","Mangarevien / Reo Magareva","Mangarevien", ContextCompat.getDrawable(c,R.drawable.man50),manpreforder);
        Language aus = new Language("Australes","aus","Australes / Reo Tuha'a Paei","Australes", ContextCompat.getDrawable(c,R.drawable.aus50),auspreforder);
        Language rap = new Language("Rapa","rap","Rapa / Oparo ","Rapa", ContextCompat.getDrawable(c,R.drawable.rapa50),rapreforder);
*/
        Language fra = new Language("Français","fra","Français / Reo Fārani","Français", ContextCompat.getDrawable(c,R.drawable.fran50),frapreforder);
        Language tah = new Language("Tahitien","tah","Tahitien / Reo Tahiti","Tahitien", ContextCompat.getDrawable(c,R.drawable.tah50),tahpreforder);
        Language pau = new Language("Pa'umotu","pau","Pa'umotu / Reo Pa'umotu","Pa'umotu", ContextCompat.getDrawable(c,R.drawable.pau50),paupreforder);
        Language mar = new Language("Marquisien","mar","Marquisien / 'Eo Enata - Enana ","Marquisien", ContextCompat.getDrawable(c,R.drawable.mar50),marpreforder);
        Language man = new Language("Mangarevien","man","Mangarevien / Reo Magareva","Mangarevien", ContextCompat.getDrawable(c,R.drawable.man50),manpreforder);
        Language aus = new Language("Australes","aus","Australes / Reo Tuha'a Pae","Australes", ContextCompat.getDrawable(c,R.drawable.aus50),auspreforder);
        Language rap = new Language("Rapa","rap","Rapa / Oparo","Rapa", ContextCompat.getDrawable(c,R.drawable.rapa50),rapreforder);


        ArrayList<Language> old = new ArrayList<>();
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
                return   fruit2.getOrder() - fruit1.getOrder();
            }
        });
        dataModels.clear();
        dataModels = new ArrayList<>(7);
        //Language[] array= new Language[7];
        /*for(int i = 0; i < old.size(); i++){
            Language xx = old.get(i);
            int pos = xx.getOrder();
            array[pos]= xx;
        }

        dataModels = Arrays.asList(array);*/



        for(int i = 0; i < old.size(); i++){
            Language xx = old.get(i);
            int pos = xx.getOrder();
            // Log.i("KK LanguagesPref", "adding: "+xx.getType()+" => "+pos);
            dataModels.add(i,xx);
            //Log.i("KK LanguagesPref", "dataModels: "+xx.getType()+" => "+pos);
        }
        for(int i = 0; i < dataModels.size(); i++){
            Language xx = dataModels.get(i);

             //Log.i("KK LanguagesPref", "dataModels: "+xx.getType()+" => "+xx.getOrder());
        }

        return  dataModels;
    }
}
