package com.marton.pair.quiz_list;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.marton.pair.R;
import com.marton.pair.quiz.Pair;
import com.marton.pair.quiz.Quiz;

import java.util.List;

/**
 * Created by marton on 12/13/17.
 */

public class PairListAdapter extends ArrayAdapter<Pair>{

    public PairListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public PairListAdapter(@NonNull Context context, int resource, @NonNull List<Pair> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.pair_list_element, null);
        }

        Pair pair = getItem(position);

        if(pair !=null){
            TextView textViewLeft = (TextView) v.findViewById(R.id.textViewLeft);
            TextView textViewRight = (TextView) v.findViewById(R.id.textViewRight);

            textViewLeft.setText(pair.getFirst());
            textViewRight.setText(pair.getSecond());
        }

        return v;
    }
}
