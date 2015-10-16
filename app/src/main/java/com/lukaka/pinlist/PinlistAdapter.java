package com.lukaka.pinlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lujiawang on 10/16/15.
 */
public class PinlistAdapter extends ArrayAdapter<String> {
    private ArrayList<String> pins;

    public PinlistAdapter(Context context, int textViewResourceId, ArrayList<String> pins) {
        super(context, textViewResourceId, pins);
        this.pins = pins;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(R.layout.list_row, null);
        }

        String pin = pins.get(position);
        if (pin != null) {
            TextView tvTitle = (TextView) v.findViewById(R.id.lr_title);
            tvTitle.setText(pin);
        }

        return v;
    }
}
