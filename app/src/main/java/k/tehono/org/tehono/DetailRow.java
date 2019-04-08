package k.tehono.org.tehono;

import android.graphics.drawable.Drawable;

/**
 * Created by K on 28/01/2017.
 */

public class DetailRow {
    String type;
    String label;
    Drawable drapeau;
    String trad;
    int order;
    String valid;

    public DetailRow(){
    }

    public Drawable getDrapeau() {
        return drapeau;
    }
    public String getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public String getTrad() {
        return trad;
    }

    public String getValid() {
        return valid;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setTrad(String trad) {
        this.trad = trad;
    }

    public void setDrapeau(Drawable drapeau) {
        this.drapeau = drapeau;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    public void setValid(String valid) {
        this.valid = valid;
    }
}
