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
import com.marton.pair.quiz.Quiz;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by marton on 12/13/17.
 */

public class QuizListAdapter extends ArrayAdapter<Quiz>{

    public QuizListAdapter(@NonNull Context context, int resource) {
        super(context, resource);
    }

    public QuizListAdapter(@NonNull Context context, int resource, @NonNull List<Quiz> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = convertView;

        if(v == null){
            v = LayoutInflater.from(getContext()).inflate(R.layout.quiz_list_element, null);
        }

        Quiz quiz = getItem(position);

        if(quiz !=null){
            TextView nameText = (TextView) v.findViewById(R.id.name_text);
            nameText.setText(quiz.getName());
        }

        return v;
    }
}
