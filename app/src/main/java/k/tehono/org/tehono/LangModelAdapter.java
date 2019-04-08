package k.tehono.org.tehono;

import android.content.ClipData;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


import android.app.Activity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.DragShadowBuilder;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.jmedeisis.draglinearlayout.DragLinearLayout;

/**
 * Created by K on 25/01/2017.
 */

public class LangModelAdapter extends ArrayAdapter<LangOrderModel> {

    private ArrayList<LangOrderModel> dataSet;
    Context mContext;
    private ListView list;
    private int lastPosition = -1;
    // View lookup cache
    private static class ViewHolder {
        TextView txtVersion;
        ImageView info;
        ImageView drapeau;
    }

    public LangModelAdapter(ArrayList<LangOrderModel> data, Context context,ListView list) {
        super(context, R.layout.langrowmodel, data);
        this.dataSet = data;
        this.mContext=context;
        this.list = list;


        //list.setOnDragListener(this);

    }

    /*@Override
    public void onClick(View v) {

        int position=(Integer) v.getTag();
        Object object= getItem(position);
        LangOrderModel dataModel=(LangOrderModel)object;

        switch (v.getId())
        {
            case R.id.item_info:
                Snackbar.make(v, "Release date " +dataModel.getVersion_number(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                break;
        }
    }*/



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        LangOrderModel dataModel = getItem(position);
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View result = inflater.inflate(R.layout.langrowmodel, parent, false);
        TextView txtVersion = (TextView) result.findViewById(R.id.version_number);
        ImageView drapeau = (ImageView) result.findViewById(R.id.item_drapeau);
        txtVersion.setText(dataModel.getVersion_number());
        drapeau.setImageDrawable(dataModel.getDrapeau());
        result.setTag(position);
        //result.setOnTouchListener(this);
        return result;
        // Check if an existing view is being reused, otherwise inflate the view
        //ViewHolder viewHolder; // view lookup cache stored in tag

        /*final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.langrowmodel, parent, false);

            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
            viewHolder.drapeau = (ImageView) convertView.findViewById(R.id.item_drapeau);
            convertView.setOnTouchListener(this);
            result=convertView;
            //convertView.setOnTouchListener(new MyTouchListener());



            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result=convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        //ViewHolder convertView  = new ViewHolder();
        ViewHolder viewHolder = new ViewHolder();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.langrowmodel, parent, false);



        viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
        viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);
        viewHolder.drapeau = (ImageView) convertView.findViewById(R.id.item_drapeau);
        convertView.setOnTouchListener(this);

        lastPosition = position;

        viewHolder.txtVersion.setText(dataModel.getVersion_number());
        //viewHolder.info.setOnClickListener(this);
        viewHolder.info.setTag(position);
        //Drawable myDrawable = ResourcesCompat.getDrawable(getResources(), R.drawable.fran50, null);
        viewHolder.drapeau.setImageDrawable(dataModel.getDrapeau());
        // Return the completed view to render on screen
        //convertView = (ViewHolder)viewHolder;
        return result;*/
    }

    /*private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(
                        view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }*/
    /*@Override
    public boolean onDrag(View v, DragEvent event) {
        if (event.getAction() == DragEvent.ACTION_DROP) {
            //we want to make sure it is dropped only to left and right parent view
            View view = (View) event.getLocalState();
            Log.e("K", "droped view tag = "+view.getTag());
            //ViewGroup source = (ViewGroup) view.getParent();
            //Log.e("K", source+"droped");
          /*  if (v == list) {

                ViewGroup source = (ViewGroup) view.getParent();
                source.removeView(view);

                LinearLayout target = (LinearLayout) v;
                target.addView(view);
           }
            //make view visible as we set visibility to invisible while starting drag
            view.setVisibility(View.VISIBLE);

        }
        return true;
    }*/

   /* @Override
    public boolean onTouch(View view, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(null, shadowBuilder, view, 0);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
        return false;
    }*/
}