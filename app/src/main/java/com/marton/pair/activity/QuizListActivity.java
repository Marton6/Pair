package com.marton.pair.activity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatDialogFragment;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.marton.pair.dialog.NewQuizDialog;
import com.marton.pair.R;
import com.marton.pair.quiz.Quiz;
import com.marton.pair.quiz_list.QuizListAdapter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class QuizListActivity extends AppCompatActivity implements NewQuizDialog.NewQuizDialogListener {

    static List<Quiz> quizzes;
    QuizListAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);
        Quiz.context = getApplicationContext();

        quizzes = new ArrayList<>();
        readQuizzes();

        FloatingActionButton newQuizFab = (FloatingActionButton) findViewById(R.id.new_quiz_fab);
        newQuizFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppCompatDialogFragment dialogFragment = new NewQuizDialog();
                dialogFragment.show(getSupportFragmentManager(), "new_quiz_dial");
            }
        });

        ListView listView = (ListView) findViewById(R.id.quiz_list_view);
        adapter = new QuizListAdapter(this, R.layout.quiz_list_element, quizzes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), DisplayQuizActivity.class);
                intent.putExtra("quiz", position);
                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), EditQuizActivity.class);
                intent.putExtra("quiz", position);
                startActivity(intent);
                return true;
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetInvalidated();
    }

    private void readQuizzes() {
        InputStream inputStream = null;
        File file1 = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "quiz");
        file1.mkdirs();

        File file = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DOCUMENTS), "quiz/quiz_list.dat");

        try {
            inputStream = new FileInputStream(file);

            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                Quiz q = null;
                try {
                    q = Quiz.load(line);
                    quizzes.add(q);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
            inputStream.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveQuizzes() {
        FileOutputStream outputStream;
        try {
            outputStream = new FileOutputStream(new File(Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DOCUMENTS), "quiz/quiz_list.dat"));
            for(Quiz q:quizzes){
                outputStream.write(q.getName().getBytes());
                outputStream.write("\n".getBytes());
            }
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPositiveResponse(Quiz quiz) {
        adapter.add(quiz);
        try {
            Quiz.save(quiz);
        } catch (IOException e) {
            e.printStackTrace();
        }
        saveQuizzes();
        Intent intent = new Intent(getBaseContext(), EditQuizActivity.class);
        intent.putExtra("quiz", quizzes.size()-1);
        startActivity(intent);
    }

    public static Quiz getQuiz(int qnr) {
        return quizzes.get(qnr);
    }
}
