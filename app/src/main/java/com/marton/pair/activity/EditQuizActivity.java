package com.marton.pair.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.marton.pair.R;
import com.marton.pair.quiz.Pair;
import com.marton.pair.quiz.Quiz;
import com.marton.pair.quiz_list.PairListAdapter;

import java.io.IOException;

public class EditQuizActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_quiz);

        int qnr = (int) getIntent().getExtras().get("quiz");

        final Quiz quiz = QuizListActivity.getQuiz(qnr);

        EditText titleEditText = findViewById(R.id.titleTextView);
        titleEditText.setText(quiz.getName());
        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                quiz.setName(s.toString());
                try {
                    Quiz.save(quiz);
                    QuizListActivity.saveQuizzes();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        final EditText leftEditText = findViewById(R.id.leftEditText);
        final EditText rightEditText = findViewById(R.id.rightEditText);

        ListView queryList = findViewById(R.id.queryListView);
        final PairListAdapter adapter = new PairListAdapter(this, R.layout.pair_list_element, quiz.getPairs());
        queryList.setAdapter(adapter);

        Button newPairButton = findViewById(R.id.newQueryButton);
        newPairButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pair p = new Pair(leftEditText.getText().toString(), rightEditText.getText().toString());
                adapter.add(p);
                try {
                    Quiz.save(quiz);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                leftEditText.setText("");
                rightEditText.setText("");
            }
        });

        queryList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Pair p = adapter.getItem(position);
                leftEditText.setText(p.getFirst());
                rightEditText.setText(p.getSecond());
                adapter.remove(p);
                return true;
            }
        });
    }
}
