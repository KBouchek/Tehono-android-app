package k.tehono.org.tehono;

/**
 * Created by K on 23/01/2017.
 */

public class Partialresult {
    String title;
    String nid;
    String type;
    public Partialresult(String title, String nid,String type){
        this.title=title;
        this.nid=nid;
        this.type=type;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String t) {
        this.title=t;
    }

    public String getNid(){
        return nid;
    }
    public void setNid(String xnid) {
        this.nid=xnid;
    }

    public String getType(){
        return type;
    }
    public void setType(String t) {
        this.type=t;
    }
}