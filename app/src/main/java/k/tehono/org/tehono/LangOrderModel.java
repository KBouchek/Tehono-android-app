package k.tehono.org.tehono;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by K on 25/01/2017.
 */

public class LangOrderModel {

    String version_number;

    Drawable drapeau;

    public LangOrderModel(String version_number,Drawable drapeaux ) {
        this.version_number=version_number;
        this.drapeau = drapeaux;
    }


    public String getVersion_number() {
        return version_number;
    }

    public Drawable getDrapeau() {
        return drapeau;
    }

}
