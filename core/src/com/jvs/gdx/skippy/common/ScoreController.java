package com.jvs.gdx.skippy.common;

public class ScoreController {
    private int score;

    public ScoreController(){

    }

    public void incrementScore(){
        score++;
    }

    public void resetScore(){
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public String getScoreString(){
        return Integer.toString(score);
    }
}
