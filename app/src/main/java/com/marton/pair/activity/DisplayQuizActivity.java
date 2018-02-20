package com.marton.pair.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.app.IntentService;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.marton.pair.R;
import com.marton.pair.quiz.Pair;
import com.marton.pair.quiz.Quiz;

import org.w3c.dom.Text;

import java.util.Random;

public class DisplayQuizActivity extends AppCompatActivity {
    public static final int COLOR_CORRECT = Color.parseColor("#FF009900");
    public static final int COLOR_WRONG = Color.parseColor("#FFFF0000");

    private Quiz quiz;
    private boolean answered, first;
    private Pair crtQ;
    private Button answerButton;

    private TextView questionTextView;
    private TextView scoreTextView;
    private EditText answerEditText;

    private Random random;
    private ActionBar actionBar;

    private int score;
    private int count;
    private int crtEditTextColor = Color.WHITE;
    private int crtActionBarColor = Color.parseColor("#FF303F9F");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_quiz);
        this.answered = false;
        this.first = false;
        this.random = new Random();

        this.actionBar = getSupportActionBar();

        int qnr = (int) getIntent().getExtras().get("quiz");

        this.quiz = QuizListActivity.getQuiz(qnr);
        actionBar.setTitle(quiz.getName());
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FF303F9F")));


        scoreTextView = findViewById(R.id.scoreTextView);
        questionTextView = findViewById(R.id.textViewQuestion);
        answerEditText = findViewById(R.id.editTextAnswer);

        scoreTextView.setText("Score:");
        answerButton = findViewById(R.id.buttonAnswer);
        answerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answered){
                    answered = false;
                    nextQuestion();
                }else{
                    answer();
                    answered = true;
                }
            }
        });
        nextQuestion();
    }

    private void nextQuestion() {
        answerButton.setText("Answer");
        answerEditText.setText("");
        changeColorTo(Color.WHITE, null);
        int crtq = random.nextInt(quiz.getPairs().size());
        crtQ = quiz.getPairs().get(crtq);
        first = random.nextBoolean();
        if(first){
            questionTextView.setText(crtQ.getFirst());
        }
        else {
            questionTextView.setText(crtQ.getSecond());
        }
    }

    private void answer(){
        answerButton.setText("Next Question");
        String correctAns, ans;

        if(first) correctAns = crtQ.getSecond();
        else correctAns = crtQ.getFirst();

        ans = answerEditText.getText().toString();

        if(ans.toLowerCase().trim().equals(correctAns.toLowerCase().trim())){
            changeColorTo(COLOR_CORRECT, COLOR_CORRECT);
            score++;
        }
        else{
            answerEditText.setText(correctAns);
            changeColorTo(COLOR_WRONG, COLOR_WRONG);
        }
        count++;
        updateScoreDisplay();
    }

    private void updateScoreDisplay() {
        scoreTextView.setText("Score: "+(float)score/(float)count*100+"%");
    }

    private void changeColorTo(Integer editTextColor1, Integer actionBarColor1){
        int editTextColor = crtEditTextColor;

        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), editTextColor, editTextColor1);
        colorAnimation.setDuration(250); // milliseconds
        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int val = (int)animator.getAnimatedValue();
                answerEditText.setBackgroundColor(val);
                crtEditTextColor = val;
            }

        });
        colorAnimation.start();

        if(actionBarColor1 == null)return;
        int actionBarColor = crtActionBarColor;

        ValueAnimator colorAnimationActionBar = ValueAnimator.ofObject(new ArgbEvaluator(), actionBarColor, actionBarColor1);
        colorAnimationActionBar.setDuration(250); // milliseconds
        colorAnimationActionBar.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int val = (int)animator.getAnimatedValue();
                actionBar.setBackgroundDrawable(new ColorDrawable(val));
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Window window = getWindow();
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(val);
                }

                crtActionBarColor = val;
            }

        });
        colorAnimationActionBar.start();

    }
}
