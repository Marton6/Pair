package com.marton.pair.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatDialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.marton.pair.R;
import com.marton.pair.quiz.Quiz;

/**
 * Created by marton on 12/11/17.
 */

public class NewQuizDialog extends AppCompatDialogFragment {
    NewQuizDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        final View dialogView = inflater.inflate(R.layout.dialog_new_quiz, null);

        builder.setView(dialogView)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        EditText nameEditText = (EditText) dialogView.findViewById(R.id.name_text);

                        String name = nameEditText.getText().toString();
                        Quiz quiz = new Quiz(name);

                        listener.onPositiveResponse(quiz);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        NewQuizDialog.this.getDialog().cancel();
                    }
                });
        
        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (NewQuizDialogListener) context;
        } catch(ClassCastException e){
            e.printStackTrace();
        }
    }

    public interface NewQuizDialogListener{
        void onPositiveResponse(Quiz quiz);
    }
}
