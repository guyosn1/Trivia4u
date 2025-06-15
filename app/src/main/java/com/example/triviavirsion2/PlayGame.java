package com.example.triviavirsion2;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.example.triviavirsion2.Question;

public class PlayGame extends AppCompatActivity {

    TextView questionTextView, timerTextView;
    Button optionA, optionB, optionC, optionD;
    Button btnFiftyFifty, btnPhoneAFriend, btnSkip;

    List<Question> questions;
    int currentQuestionIndex = 0;
    int score = 0;
    int QuestionIndex = 0;
    CountDownTimer timer;
    boolean isAnswering = false;

    boolean usedFiftyFifty = false;
    boolean usedPhoneAFriend = false;
    boolean usedSkip = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.play_game);

        // Init views
        questionTextView = findViewById(R.id.questionTextView);
        timerTextView = findViewById(R.id.timerTextView);
        optionA = findViewById(R.id.optionA);
        optionB = findViewById(R.id.optionB);
        optionC = findViewById(R.id.optionC);
        optionD = findViewById(R.id.optionD);
        btnFiftyFifty = findViewById(R.id.btnFiftyFifty);
        btnPhoneAFriend = findViewById(R.id.btnPhoneAFriend);
        btnSkip = findViewById(R.id.btnSkip);

        // Lifeline listeners
        btnFiftyFifty.setOnClickListener(v -> useFiftyFifty());
        btnPhoneAFriend.setOnClickListener(v -> usePhoneAFriend());
        btnSkip.setOnClickListener(v -> useSkip());

        questions = getSampleQuestions();

        loadQuestion();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (timer != null) {
            timer.cancel();
        }
    }

    private void loadQuestion() {
        if (currentQuestionIndex >= questions.size()) {
            goToGameOver();
            return;
        }

        isAnswering = false;

        // Reset all options to visible
        optionA.setVisibility(View.VISIBLE);
        optionB.setVisibility(View.VISIBLE);
        optionC.setVisibility(View.VISIBLE);
        optionD.setVisibility(View.VISIBLE);

        Question q = questions.get(currentQuestionIndex);
        questionTextView.setText(q.getQuestion());

        // FIXED here: convert String[] to List<String> properly
        List<String> options = new ArrayList<>(Arrays.asList(q.getOptions()));
        Collections.shuffle(options);

        optionA.setText(options.get(0));
        optionB.setText(options.get(1));
        optionC.setText(options.get(2));
        optionD.setText(options.get(3));

        View.OnClickListener answerListener = v -> {
            if (isAnswering) return;
            isAnswering = true;

            if (timer != null) timer.cancel();

            Button selected = (Button) v;
            boolean isCorrect = selected.getText().toString().equals(q.getCorrectAnswer());

            if (isCorrect) {
                QuestionIndex++;
                if (QuestionIndex<=5 )
                    score++;
                else if (QuestionIndex>5 && QuestionIndex<=9) {
                    score+=2;
                }
                else if (QuestionIndex>9 && QuestionIndex<=12) {
                    score+=3;
                }
                else if (QuestionIndex>12 && QuestionIndex<=14) {
                    score+=4;
                }
                else if (QuestionIndex==15) {
                    score+=5;
                }
            }


            Intent intent = new Intent(PlayGame.this, QResultScreen.class);
            intent.putExtra("isCorrect", isCorrect);
            intent.putExtra("score", score);
            intent.putExtra("questionIndex", QuestionIndex);
            startActivityForResult(intent, 1);
        };

        optionA.setOnClickListener(answerListener);
        optionB.setOnClickListener(answerListener);
        optionC.setOnClickListener(answerListener);
        optionD.setOnClickListener(answerListener);

        startTimer();
    }

    private void startTimer() {
        timer = new CountDownTimer(20000, 1000) {
            public void onTick(long millisUntilFinished) {
                timerTextView.setText("Time left: " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                if (!isAnswering) {
                    Toast.makeText(PlayGame.this, "Time's up!", Toast.LENGTH_SHORT).show();
                    isAnswering = true;

                    Intent intent = new Intent(PlayGame.this, QResultScreen.class);
                    intent.putExtra("isCorrect", false);
                    intent.putExtra("score", score);
                    intent.putExtra("questionIndex", currentQuestionIndex);
                    startActivityForResult(intent, 1);
                }
            }
        }.start();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && data != null) {
            boolean wasCorrect = data.getBooleanExtra("wasCorrect", false);

            if (wasCorrect) {
                currentQuestionIndex++;
                loadQuestion();
            } else {
                goToGameOver();
            }
        }
    }

    private void goToGameOver() {
        if (timer != null) timer.cancel();
        Intent intent = new Intent(PlayGame.this, GameOver.class);
        intent.putExtra("score", score);
        startActivity(intent);
        finish();
    }

    private void useFiftyFifty() {
        if (usedFiftyFifty) return;
        usedFiftyFifty = true;
        btnFiftyFifty.setEnabled(false);
        VibrationUtils.vibrate(this, 150);

        Question q = questions.get(currentQuestionIndex);
        List<Button> buttons = List.of(optionA, optionB, optionC, optionD);
        List<Button> wrongButtons = new ArrayList<>();

        for (Button b : buttons) {
            if (!b.getText().toString().equals(q.getCorrectAnswer())) {
                wrongButtons.add(b);
            }
        }

        Collections.shuffle(wrongButtons);
        if (wrongButtons.size() >= 2) {
            wrongButtons.get(0).setVisibility(View.INVISIBLE);
            wrongButtons.get(1).setVisibility(View.INVISIBLE);
        }
    }

    private void usePhoneAFriend() {
        if (usedPhoneAFriend) return;
        usedPhoneAFriend = true;
        btnPhoneAFriend.setEnabled(false);
        VibrationUtils.vibrate(this, 150);

        Question q = questions.get(currentQuestionIndex);
        List<Button> buttons = List.of(optionA, optionB, optionC, optionD);

        for (Button b : buttons) {
            if (!b.getText().toString().equals(q.getCorrectAnswer())) {
                b.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void useSkip() {
        if (usedSkip) return;
        usedSkip = true;
        btnSkip.setEnabled(false);
        VibrationUtils.vibrate(this, 150);

        if (timer != null) timer.cancel();
        currentQuestionIndex++;
        loadQuestion();
    }

    private List<Question> getSampleQuestions() {
        List<Question> finalQuestions = new ArrayList<>();

        // Add Easy questions
        finalQuestions.addAll(EasyQuestions.getRandomQuestions(5));

        // Add Medium questions
        finalQuestions.addAll(MediumQuestions.getRandomQuestions(4));

        // Add Hard questions
        finalQuestions.addAll(HardQuestions.getRandomQuestions(3));

        // Add Professional questions
        finalQuestions.addAll(ProfessionalQuestions.getRandomQuestions(2));

        // Add Expert question
        finalQuestions.addAll(ExpertQuestions.getRandomQuestions(1));

        return finalQuestions;
    }
}


/**
 *
 * questions
 *      easy
 *      medium
 *      hard
 *      professional
 *      expert
 *
 *
 */
