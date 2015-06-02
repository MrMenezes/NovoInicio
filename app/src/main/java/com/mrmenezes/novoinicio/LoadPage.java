package com.mrmenezes.novoinicio;

import android.graphics.Typeface;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.Font;

import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;
/**
 * Created by Mr Menezes on 01/06/2015.
 */
public class LoadPage extends SimpleBaseGameActivity {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 800;
    private Camera mCamera;
    private Scene mScene;
    private BitmapTextureAtlas mBitmapTextureAtlas1, mFontTexture;
    private Font mFont;
    private TiledTextureRegion mFaceTextureRegion1;

    public void killMe(){
        this.finish();
    }

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mBitmapTextureAtlas1 = new BitmapTextureAtlas( this.getTextureManager(),256, 2048);
        this.mFaceTextureRegion1 =  BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas1,getAssets(),"png0.png",0,0,1,8);
        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas1);

        this.mFontTexture = new BitmapTextureAtlas( mEngine.getTextureManager(),256, 256);
        this.mFont = new Font(this.getFontManager(),this.mFontTexture,Typeface.create(Typeface.DEFAULT, Typeface.BOLD),48,true,Color.WHITE);
        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);


    }

    @Override
    protected Scene onCreateScene() {
        mScene = new Scene();
        mScene.setBackground(new Background(0f, 0f, 0f));
        AnimatedSprite pAnime = new AnimatedSprite((WIDTH/4)*3,HEIGHT/2,mFaceTextureRegion1,getVertexBufferObjectManager());
        pAnime.setScale(0.5f);
        mScene.attachChild(pAnime);
        pAnime.animate(300);
        Text textCollision = new Text(WIDTH/4, (HEIGHT/2)+90, mFont, "Carregando",getVertexBufferObjectManager());
        mScene.attachChild(textCollision);
        return mScene;
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        mCamera = new Camera( WIDTH/4, HEIGHT/8,WIDTH, HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.PORTRAIT_FIXED, new
                FillResolutionPolicy( ),
                mCamera);

        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        return engineOptions;
    }
}
