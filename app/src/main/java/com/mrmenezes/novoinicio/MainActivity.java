package com.mrmenezes.novoinicio;



import android.graphics.Typeface;



import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegionFactory;
import android.util.Log;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;


public class MainActivity extends SimpleBaseGameActivity {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 800;

    private Camera mCamera;
    private Scene mScene;
    private BitmapTextureAtlas mBitmapTextureAtlas1,mBitmapTextureAtlas2,mBitmapTextureAtlas3, mFontTexture;//o bitmap que receberá o Sprite
    private Font mFont;
    private PixelPerfectTiledTextureRegion mFaceTextureRegion1,mFaceTextureRegion2,mFaceTextureRegion3;
    private Map map;

    @Override
    protected void onCreateResources() {
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        PixelPerfectTextureRegionFactory.setAssetBasePath("gfx/");
        this.mFontTexture = new BitmapTextureAtlas( mEngine.getTextureManager(),256, 256);
//this.mFontTexture,Typeface.BOLD,48,true,Color.BLACK

        this.mFont = new Font(this.getFontManager(),this.mFontTexture,Typeface.create(Typeface.DEFAULT, Typeface.BOLD),48,true,Color.RED);

        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);
        //Firebase.setAndroidContext(this);

        this.mBitmapTextureAtlas1 = new BitmapTextureAtlas( this.getTextureManager(),2048, 2048);
        this.mBitmapTextureAtlas2 = new BitmapTextureAtlas( this.getTextureManager(),2048, 2048);


        this.mFaceTextureRegion1 = PixelPerfectTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas1,getAssets(),"pn00.png",0,0,8,8,0);
        this.mFaceTextureRegion2 = PixelPerfectTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas2,getAssets(),"pn22.png",0,0,8,5,0);

        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas2);
        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas1);



    }

    @Override
    protected Scene onCreateScene() {
        this.mEngine.registerUpdateHandler( new FPSLogger());
        mScene = new Scene();
        mScene.setBackground(new Background(1f, 1f, 1f));
        int[] list = {16,88,24,48};
        int[][] matriz = {{0,0,0,0,0,0},{1,1,1,0,0,0},{1,1,1,1,1,1},{0,0,1,1,1,1},{0,0,1,1,1,0},{0,0,0,0,0,0}};
        //int[][] matriz = {{1,1,1,1,1,1},{1,1,1,1,1,1},{1,1,1,1,1,1},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
        //int[][] matriz = {{0,0,0,0,0,0},{1,1,1,0,0,0},{1,1,1,1,1,1},{0,0,0,0,0,0},{0,0,0,0,0,0},{0,0,0,0,0,0}};
        map = new Map(mFont, Sprits.NORMAL,mScene,list,matriz,this.mFaceTextureRegion1,this.mFaceTextureRegion2, getVertexBufferObjectManager());



        return mScene;
    }




    @Override
    public EngineOptions onCreateEngineOptions() {

        // Define our mCamera object
        mCamera = new Camera( WIDTH/2, 0,WIDTH, HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.PORTRAIT_FIXED, new
                FillResolutionPolicy( ),
                mCamera);

        engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);

        return engineOptions;
    }
    }

