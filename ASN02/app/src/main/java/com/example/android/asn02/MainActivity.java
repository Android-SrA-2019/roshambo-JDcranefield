/*
 * Submitted By: Jordan Trenholm
 * Assignment: 2
 * Date: 2019/02/28
 */

package com.example.android.asn02;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.asn02.models.Rochambo;

public class MainActivity extends AppCompatActivity {

    private Rochambo rochambo;
    private AnimatorSet animations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ObjectAnimator animatorPlayer = ObjectAnimator.ofFloat(findViewById(R.id.user_move_image), "rotationX", 0f, 360f).setDuration(500);
        ObjectAnimator animatorGame = ObjectAnimator.ofFloat(findViewById(R.id.cpu_move_image),"rotationY", 0f, 360f).setDuration(500);

        animations = new AnimatorSet();
        animations.playTogether(animatorGame, animatorPlayer);
        animations.setInterpolator(new AnticipateOvershootInterpolator());

        if(savedInstanceState != null){
            rochambo = (Rochambo)savedInstanceState.getSerializable("rochambo");
            updateView();
        }else {
            rochambo = new Rochambo();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("rochambo", rochambo);
    }

    public void playRochambo(View view) {
        int id = view.getId();

        rochambo.playerMakesMove(
            id == R.id.rock_button ? Rochambo.ROCK :
            id == R.id.paper_button ? Rochambo.PAPER :
            id == R.id.scissor_button ? Rochambo.SCISSOR :
            Rochambo.NONE
        );

        updateView();

        animations.end();
        animations.start();
    }

    public void updateView(){
        TextView result = findViewById(R.id.result_text);
        ImageView playerMove = findViewById(R.id.user_move_image);
        ImageView gameMove = findViewById(R.id.cpu_move_image);

        result.setText(rochambo.winLoseOrDraw());
        playerMove.setImageResource(getRoshamboImage(rochambo.getPlayerMove()));
        gameMove.setImageResource(getRoshamboImage(rochambo.getGameMove()));
    }

    public int getRoshamboImage(int move){
        int image;
        switch (move){
            case Rochambo.ROCK:
                image = R.mipmap.ic_rock;
                break;
            case Rochambo.PAPER:
                image = R.mipmap.ic_paper;
                break;
            case Rochambo.SCISSOR:
                image = R.mipmap.ic_scissors;
                break;
            default:
                image = R.mipmap.ic_none;
                break;
        }
        return image;
    }
}
