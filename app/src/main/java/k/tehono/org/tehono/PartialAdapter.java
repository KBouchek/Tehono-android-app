package k.tehono.org.tehono;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by K on 23/01/2017.
 */

public class PartialAdapter extends ArrayAdapter<String> {
     ArrayList<String> local ;
    public PartialAdapter(Context context, ArrayList<String> users) {

        super(context, 0, users);
        local = users;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            String user = local.get(position);
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
            }
            View view =super.getView(position, convertView, parent);

            TextView textView = (TextView) view.findViewById(android.R.id.text1);
            textView.setTextSize(17.0f);
            textView.setTextColor(Color.WHITE);
            textView.setText(user);
            int color = 0x7F052469;
            view.setBackgroundColor(color);

            /*ViewGroup.LayoutParams params = view.getLayoutParams();
            params.height = 24;
            view.setLayoutParams(params);*/

            return view;
        }


}