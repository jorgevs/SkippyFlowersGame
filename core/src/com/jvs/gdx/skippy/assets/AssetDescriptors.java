package com.jvs.gdx.skippy.assets;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

public class AssetDescriptors {

    public static final AssetDescriptor<BitmapFont> SCORE_FONT =
            new AssetDescriptor<BitmapFont>(AssetPaths.SCORE_FONT, BitmapFont.class);

    public static final AssetDescriptor<TextureAtlas> GAME_PLAY =
            new AssetDescriptor<TextureAtlas>(AssetPaths.GAME_PLAY, TextureAtlas.class);

    private AssetDescriptors(){
    }

}
