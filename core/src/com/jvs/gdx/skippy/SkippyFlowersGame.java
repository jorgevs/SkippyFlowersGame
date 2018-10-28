package com.jvs.gdx.skippy;

import com.badlogic.gdx.Game;
import com.jvs.gdx.skippy.screen.GameScreen;

public class SkippyFlowersGame extends Game {

    @Override
    public void create() {
        setScreen(new GameScreen());
    }
}
