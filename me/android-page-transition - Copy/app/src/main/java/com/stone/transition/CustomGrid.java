package com.stone.transition;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ryan on 1/21/17.
 */

public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private final String[] names;
    private final int[] Imageid;

    public CustomGrid(Context c, String[] names, int[] Imageid) {
        mContext = c;
        this.Imageid = Imageid;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (view == null) {
            // grid = new View(mContext);
            grid = inflater.inflate(R.layout.grid_single, null);

            TextView textView = (TextView)grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView)grid.findViewById(R.id.grid_image);

            textView.setText(names[position]);
            imageView.setImageResource(Imageid[position]);
        } else {
            grid = (View)view;
        }

        return grid;
    }
}
