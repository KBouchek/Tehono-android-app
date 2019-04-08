package k.tehono.org.tehono;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by k on 02/10/2016.
 */
public class SpinnerAdapter extends ArrayAdapter<Language> {
    int groupid;
    Activity context;
    ArrayList<Language> list;
    LayoutInflater inflater;
    public SpinnerAdapter(Activity context, int groupid, int id, ArrayList<Language>
            list){
        super(context,id,list);
        this.list=list;
        inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groupid=groupid;
    }

    public View getView(int position, View convertView, ViewGroup parent ){
        View itemView=inflater.inflate(groupid,parent,false);
        ImageView imageView=(ImageView)itemView.findViewById(R.id.img);
        Language l = list.get(position);
        imageView.setImageDrawable(l.getDrapeau());
        //imageView.setImageResource(l.getDrapeau());
        TextView textView=(TextView)itemView.findViewById(R.id.txt);
        textView.setText(l.getLabel());
        itemView.setTag(position);
        return itemView;
    }

    public View getDropDownView(int position, View convertView, ViewGroup
            parent){
        return getView(position,convertView,parent);

    }

    public void replaceAll(ArrayList<Language> items) {
        this.list = items;
        notifyDataSetChanged();
    }
}