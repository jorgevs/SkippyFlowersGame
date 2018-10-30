package com.jvs.gdx.skippy.common;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.jvs.gdx.skippy.SkippyFlowersGame;

public class ScoreController {

    private static final String HIGH_SCORE_KEY = "highScore";

    private final Preferences preferences;
    private int score;
    private int highScore;

    public ScoreController() {
        preferences = Gdx.app.getPreferences(SkippyFlowersGame.class.getSimpleName());
        highScore = preferences.getInteger(HIGH_SCORE_KEY, 0);
    }

    public void incrementScore() {
        score++;
    }

    public void resetScore() {
        score = 0;
    }

    public int getScore() {
        return score;
    }

    public String getScoreString() {
        return Integer.toString(score);
    }

    public String getHighScoreString() {
        return Integer.toString(highScore);
    }

    public void updateHighScore() {
        if (score > highScore) {
            highScore = score;
            preferences.putInteger(HIGH_SCORE_KEY, highScore);
            preferences.flush();
        }
    }
}
