package com.amir.manammiam.infrastructure;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.amir.manammiam.R;
import com.amir.manammiam.infrastructure.customView.TextViewFont;

import java.util.List;

public class NumberPlateAdapter extends BaseAdapter{
    private String[] alphabet = {"الف", "ب", "پ", "ت", "ج", "د", "س", "ص", "ط", "ع", "ق", "ک", "ل", "م", "ن", "و", "ه", "ی", "ژ", "معلولین"};
    private LayoutInflater inflater;

    public NumberPlateAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return alphabet.length;
    }

    @Override
    public String getItem(int position) {
        return alphabet[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NumberPlateViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_number_plate_letter, parent, false);

            viewHolder = new NumberPlateViewHolder();
            viewHolder.textView = (TextViewFont) convertView.findViewById(R.id.item_number_plate_letter_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (NumberPlateViewHolder) convertView.getTag();
        }

        viewHolder.textView.setText(alphabet[position]);

        return convertView;
    }

    public String[] getAlphabet() {
        return alphabet;
    }

    private class NumberPlateViewHolder {
        TextViewFont textView;
    }
}
