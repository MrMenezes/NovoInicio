package com.mrmenezes.novoinicio;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;



import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegionFactory;

import android.util.Log;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.IGameInterface;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;




public class MainActivity extends BaseGameActivity {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 800;

    private Camera mCamera;
    private Scene mScene,SplashmScene;
    private BitmapTextureAtlas mBitmapTextureAtlas1, mBitmapTextureAtlas2, mBitmapTextureAtlas3, mFontTexture;
    private Font mFont;
    private PixelPerfectTiledTextureRegion mFaceTextureRegion1, mFaceTextureRegion2;
    private TiledTextureRegion mFaceTextureRegion3;







    @Override
    public EngineOptions onCreateEngineOptions() {
        //this.startLoadPage();
        mCamera = new Camera(WIDTH / 2, 0, WIDTH, HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.PORTRAIT_FIXED, new
                FillResolutionPolicy(),
                mCamera);

        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        return engineOptions;
    }

    @Override
    public void onCreateResources(OnCreateResourcesCallback pOnCreateResourcesCallback) throws Exception {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mBitmapTextureAtlas3 = new BitmapTextureAtlas(this.getTextureManager(), 256, 2048);
        this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas3, getAssets(), "png0.png", 0, 0, 1, 8);
        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas3);

        this.mFontTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 256);
        this.mFont = new Font(this.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 96, true, Color.WHITE);
        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);
        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        initSplashScene();

        pOnCreateSceneCallback.onCreateSceneFinished(this.SplashmScene);

    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        mEngine.registerUpdateHandler(new TimerHandler(3f, new ITimerCallback() {
            public void onTimePassed(final TimerHandler pTimerHandler) {
                mEngine.unregisterUpdateHandler(pTimerHandler);
                loadResources();
                loadScenes();
                mEngine.setScene(mScene);
            }
        }));
        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    private void initSplashScene() {
        SplashmScene = new Scene();
        SplashmScene.setBackground(new Background(0f, 0f, 0f));
        AnimatedSprite pAnime = new AnimatedSprite(304,HEIGHT/3,mFaceTextureRegion3,getVertexBufferObjectManager());
        pAnime.setScale(0.5f);
        SplashmScene.attachChild(pAnime);
        AnimatedSprite pAnime2 = new AnimatedSprite(pAnime.getX()+64,HEIGHT/3,mFaceTextureRegion3,getVertexBufferObjectManager());
        pAnime2.setScale(0.5f);
        SplashmScene.attachChild(pAnime2);
        AnimatedSprite pAnime3 = new AnimatedSprite(pAnime2.getX()+64 ,HEIGHT/3,mFaceTextureRegion3,getVertexBufferObjectManager());
        pAnime3.setScale(0.5f);
        SplashmScene.attachChild(pAnime3);
        Text textCollision = new Text(240, (HEIGHT/3), mFont, "Carregando",getVertexBufferObjectManager());
        textCollision.setScale(0.5f);
        SplashmScene.attachChild(textCollision);
        Text textTetrisGram = new Text(240, 90, mFont, "TetrisGram",getVertexBufferObjectManager());

        SplashmScene.attachChild(textTetrisGram);
    }

    protected void loadResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        PixelPerfectTextureRegionFactory.setAssetBasePath("gfx/");
        this.mFontTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 256);

        this.mFont = new Font(this.getFontManager(), this.mFontTexture, Typeface.create(Typeface.DEFAULT, Typeface.BOLD), 48, true, Color.RED);

        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);

        this.mBitmapTextureAtlas1 = new BitmapTextureAtlas(this.getTextureManager(), 2048, 2048);
        this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 2048, 2048);


        this.mFaceTextureRegion1 = PixelPerfectTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas1, getAssets(), "pn00.png", 0, 0, 8, 8, 0);
        this.mFaceTextureRegion2 = PixelPerfectTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas2, getAssets(), "pn22.png", 0, 0, 8, 5, 0);


        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas2);
        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas1);


    }
    protected void loadScenes() {


        mScene = new Scene();
        mScene.setBackground(new Background(1f, 1f, 1f));
        Text textTetrisGram = new Text(356, 45, mFont, "TetrisGram",getVertexBufferObjectManager());

        mScene.attachChild(textTetrisGram);
        TailRandomize tR = new TailRandomize();
        Tail t =  tR.getRMatriz();
        int[][] matriz = t.getTabuleiro();
        int[] list = tR.getRList(t);
        Map map = new Map(mFont, Sprits.NORMAL, mScene, list, matriz, this.mFaceTextureRegion1, this.mFaceTextureRegion2, getVertexBufferObjectManager());

    }



}