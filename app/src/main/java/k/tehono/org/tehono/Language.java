package k.tehono.org.tehono;

import android.graphics.drawable.Drawable;

/**
 * Created by K on 25/01/2017.
 */

public class Language {
    String title, type, label,shortlabel;
    Drawable drapeau;
    int order;

    public Language(String title, String type, String label, String shortlabel, Drawable drapeau,int order ) {
        this.title = title;
        this.type = type;
        this.label = label;
        this.shortlabel = shortlabel;
        this.drapeau = drapeau;
        this.order = order;
    }

    public String getTitle() {
        return title;
    }
    public String getType() {
        return type;
    }
    public String getLabel() {
        return label;
    }
    public String getShortlabel() {
        return shortlabel;
    }

    public Drawable getDrapeau() {
        return drapeau;
    }
    public int getOrder() {
        return order;
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setShortlabel(String shortlabel) {
        this.shortlabel = shortlabel;
    }

    public void setDrapeau(Drawable drapeau) {
        this.drapeau = drapeau;
    }

    public void setOrder(int order) {
        this.order = order;
    }
}

