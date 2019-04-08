package k.tehono.org.tehono;

/**
 * Created by K on 28/01/2017.
 */

public class Definition {
    String  nid,title, type,fra,tah, pau, mar, man, rap, aus ;

public Definition(){
        }
public Definition(String id, String name){
        this.setNid(id);
        this.setTitle(name);
        }
public void setTrad(String t,String trad) {
         if(t.equals("tah")) setTah(trad);
         if(t.equals("fra")) setFra(trad);
         if(t.equals("mar")) setMar(trad);
        if(t.equals("man")) setMan(trad);
        if(t.equals("pau")) setPau(trad);
        if(t.equals("rap")) setRap(trad);
        if(t.equals("aus")) setAus(trad);
        }
public String getTitle() {
        return title;
        }
public void setTitle(String t) {this.title = t;}

public String getType() {
        return type;
        }
public void setType(String t) {this.type = t;}

public String getNid() {
        return nid;
        }


public String getFra() {
        return fra;
        }
public void setFra(String t) {this.fra = t;}

public String getTah() {
        return tah;
        }
public void setTah(String t) {this.tah = t;}

public String getPau() {
        return pau;
        }
public void setPau(String t) {this.pau = t;}

public String getMar() {
        return mar;
        }
public void setMar(String t) {this.mar = t;}

public String getMan() {
        return man;
        }
public void setMan(String t) {this.man = t;}

public String getAus() {
        return aus;
        }
public void setAus(String t) {this.aus = t;}

public String getRap() {
        return rap;
        }
public void setRap(String t) {this.rap = t;}


public void setNid(String t) {
        nid = t;
        }


public String toString() {

        String k =  "title "+title+ "-" +" tah "+tah+ "-" +" fra "+fra+ "-" +" pau "+pau+ "-" +" aus "+aus+ "-" +" mar "+mar+ "-" +" man "+man+ "-" +" rap "+rap;

        return k;
        }
}
