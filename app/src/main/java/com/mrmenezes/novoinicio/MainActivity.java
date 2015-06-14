package com.mrmenezes.novoinicio;


import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.util.FPSLogger;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;

import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegionFactory;

import android.app.AlertDialog;
import android.content.DialogInterface;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;


public class MainActivity extends BaseGameActivity {
    private static final int WIDTH = 480;
    private static final int HEIGHT = 800;

    private Camera mCamera;
    private Scene mScene, SplashmScene, menumScene, mSceneSobre, mSceneRecordes;
    private BitmapTextureAtlas mBitmapTextureAtlas1, mBitmapTextureAtlas2, mBitmapTextureAtlas3, mFontTexture;
    private Font mFont;
    private PixelPerfectTiledTextureRegion mFaceTextureRegion1, mFaceTextureRegion2;
    private TiledTextureRegion mFaceTextureRegion3;
    private Map map;
    private int time;
    private Text textTime;
    private boolean perdeu = false, primeira = true;

    @Override
    public EngineOptions onCreateEngineOptions() {
        //Criando a Visão do Jogo
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
        //Carregando Resources Básicos Para Splash
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        this.mBitmapTextureAtlas3 = new BitmapTextureAtlas(this.getTextureManager(), 256, 2048);
        this.mFaceTextureRegion3 = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas3, getAssets(), "png0.png", 0, 0, 1, 6);
        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas3);

        FontFactory.setAssetBasePath("fnt/");
        this.mFontTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 256);
        this.mFont = FontFactory.createFromAsset(this.getFontManager(), mFontTexture, this.getAssets(), "Cartographer.ttf", 96, true, Color.WHITE_ABGR_PACKED_INT);
        this.mFont.load();
        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);

        pOnCreateResourcesCallback.onCreateResourcesFinished();
    }

    @Override
    public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback) throws Exception {
        //Iniciando o Splash de carregamento
        createSplashScene();
        pOnCreateSceneCallback.onCreateSceneFinished(this.SplashmScene);

    }

    @Override
    public void onPopulateScene(Scene pScene, OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
        //Criando Thread para carregar Resources do jogo e carregar e exibir o menu quando acabar
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //Carregando Ressources do jogo
                loadResources();
                //Carregando e exibindo Menu
                createMenu();
                mEngine.setScene(menumScene);
            }


        });


        pOnPopulateSceneCallback.onPopulateSceneFinished();
    }

    private void createSplashScene() {
        //Criando a Scene do Splash
        SplashmScene = new Scene();
        SplashmScene.setBackground(new Background(0f, 0f, 0f));
        AnimatedSprite pAnime = new AnimatedSprite(304, (HEIGHT / 3) + 90, mFaceTextureRegion3, getVertexBufferObjectManager());
        pAnime.setScale(0.5f);
        SplashmScene.attachChild(pAnime);
        pAnime.setCurrentTileIndex(1);
        pAnime.animate(new long[]{300, 300, 300, 300, 300, 300}, 0, 5, true);
        AnimatedSprite pAnime2 = new AnimatedSprite(pAnime.getX() + 64, pAnime.getY(), mFaceTextureRegion3, getVertexBufferObjectManager());
        pAnime2.setScale(0.5f);
        SplashmScene.attachChild(pAnime2);
        pAnime2.setCurrentTileIndex(1);
        pAnime2.animate(new long[]{300, 300, 300, 300, 300}, 1, 5, true);
        AnimatedSprite pAnime3 = new AnimatedSprite(pAnime2.getX() + 64, pAnime.getY(), mFaceTextureRegion3, getVertexBufferObjectManager());
        pAnime3.setScale(0.5f);
        pAnime3.setCurrentTileIndex(2);
        SplashmScene.attachChild(pAnime3);
        pAnime3.animate(new long[]{300, 300, 300, 300, 300}, 0, 4, true);
        Text textCollision = new Text(220, pAnime.getY() + 90, mFont, "Carregando", getVertexBufferObjectManager());
        textCollision.setScale(0.5f);
        SplashmScene.attachChild(textCollision);
        Text textTetrisGram = new Text(350, 180, mFont, "TetrisGram", getVertexBufferObjectManager());

        textTetrisGram.setScale(1.5f);
        SplashmScene.attachChild(textTetrisGram);
    }

    protected void loadResources() {
        //Carregando Resources do Jogo
        BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
        PixelPerfectTextureRegionFactory.setAssetBasePath("gfx/");
        this.mFontTexture = new BitmapTextureAtlas(mEngine.getTextureManager(), 256, 256);

        this.mFont = FontFactory.createFromAsset(this.getFontManager(), mFontTexture, this.getAssets(), "Cartographer.ttf", 48, true, Color.WHITE_ABGR_PACKED_INT);

        this.mFont.load();
        this.mEngine.getTextureManager().loadTexture(this.mFontTexture);
        this.mEngine.getFontManager().loadFont(this.mFont);

        this.mBitmapTextureAtlas1 = new BitmapTextureAtlas(this.getTextureManager(), 2048, 2048);
        this.mBitmapTextureAtlas2 = new BitmapTextureAtlas(this.getTextureManager(), 2048, 2048);


        this.mFaceTextureRegion1 = PixelPerfectTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas1, getAssets(), "pn00.png", 0, 0, 8, 8, 0);
        this.mFaceTextureRegion2 = PixelPerfectTextureRegionFactory.createTiledFromAsset(this.mBitmapTextureAtlas2, getAssets(), "pn22.png", 0, 0, 8, 5, 0);


        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas2);
        this.mEngine.getTextureManager().loadTexture(this.mBitmapTextureAtlas1);


    }

    protected void createGame() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());

        mScene = new Scene();
        mScene.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(420, 90, mFont, "TetrisGram", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);

        mScene.attachChild(textTetrisGram);
        TailRandomize tR = new TailRandomize();
        Tail t = tR.getRMatriz();
        int[][] matriz = t.getTabuleiro();
        int[] list = tR.getRList(t);
        this.textTime = new Text(216, 0, mFont, "0", getVertexBufferObjectManager());
        textTime.setScale(0.6f);
        mScene.attachChild(textTime);
        this.map = new Map(mFont, Sprits.NORMAL, mScene, list, matriz, this.mFaceTextureRegion1, this.mFaceTextureRegion2, getVertexBufferObjectManager());
        mScene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                time++;
                if (map.ganhando) {
                    createMenu();
                    mEngine.setScene(menumScene);
                    time = 0;

                }
                if (time > 3600) {
                    perdeu = true;
                    createMenu();
                    mEngine.setScene(menumScene);
                    time = 0;


                }
            }

            @Override
            public void reset() {

            }
        });
    }

    public void createMenu() {
        //Criando Scena do Menu
        menumScene = new Scene();
        menumScene.setBackground(new Background(0f, 0f, 0f));

        AnimatedSprite pAnime2 = new AnimatedSprite(360, (HEIGHT / 3) - 128, mFaceTextureRegion3, getVertexBufferObjectManager());

        AnimatedSprite pAnime = new AnimatedSprite(360, pAnime2.getY() + 128, mFaceTextureRegion3, getVertexBufferObjectManager());
        menumScene.attachChild(pAnime);
        AnimatedSprite pAnime3 = new AnimatedSprite(360, pAnime.getY() + 128, mFaceTextureRegion3, getVertexBufferObjectManager());
        menumScene.attachChild(pAnime3);

        pAnime.setCurrentTileIndex(2);
        pAnime.setColor(Color.PINK);
        pAnime.setRotation(90f);

        pAnime3.setCurrentTileIndex(2);
        pAnime3.setColor(Color.PINK);
        pAnime3.setRotation(90f);

        pAnime2.setCurrentTileIndex(3);
        pAnime2.setColor(Color.PINK);
        pAnime2.setRotation(90f);
        menumScene.attachChild(pAnime2);

        //Criando botão Iniciar e chamando A tela do jogo se clicado
        Text textInicial = new Text(445, (HEIGHT / 3) - 32, mFont, "Iniciar", getVertexBufferObjectManager()) {
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                createGame();
                mEngine.setScene(mScene);
                return true;
            }
        };

        menumScene.attachChild(textInicial);
        menumScene.registerTouchArea(textInicial);
        Text textSobre = new Text(455, (HEIGHT / 3) + 96, mFont, "Sobre", getVertexBufferObjectManager()) {
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                createSobre();
                mEngine.setScene(mSceneSobre);
                return true;
            }
        };
        menumScene.registerTouchArea(textSobre);
        menumScene.attachChild(textSobre);

        Text textRecordes = new Text(440, (HEIGHT / 3) + 224, mFont, "Recordes", getVertexBufferObjectManager()){
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                createRecordes();
                mEngine.setScene(mSceneRecordes);
                return true;
            }
        };
        menumScene.registerTouchArea(textRecordes);
        menumScene.attachChild(textRecordes);
        Text textTetrisGram = new Text(420, 90, mFont, "TetrisGram", getVertexBufferObjectManager());
        menumScene.attachChild(textTetrisGram);
        Text loser = new Text(440, HEIGHT - 150, mFont, "Você Perdeu", getVertexBufferObjectManager());

        textTetrisGram.setScale(2.f);
        textTetrisGram.setScale(2.f);
        //Verificando Se  o Jogador Ganhou ou Perdeu
        if (perdeu && !primeira) {
            loser.setText("Perdeu");
            menumScene.attachChild(loser);

        } else {
            if (!primeira && map.ganhando) {
                loser.setText("WIN!!" + time / 60);
                menumScene.attachChild(loser);

            }
        }
        primeira = false;
        perdeu = false;


    }

    protected void createSobre() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());

        mSceneSobre = new Scene();
        mSceneSobre.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(460, 90, mFont, "Sobre", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);

        mSceneSobre.attachChild(textTetrisGram);

    }

    protected void createRecordes() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());

        mSceneRecordes = new Scene();
        mSceneRecordes.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(440, 90, mFont, "Recordes", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);

        mSceneRecordes.attachChild(textTetrisGram);

    }

    public void onBackPressed() {
        //Retornando pra tela de menu do jogo
        if (mEngine.getScene().equals(mScene)) {
            createMenu();
            mEngine.setScene(menumScene);
            time = 0;
            perdeu = true;

        }//Retornando para tela
        if (mEngine.getScene().equals(mSceneSobre) || mEngine.getScene().equals(mSceneRecordes)) {
            mEngine.setScene(menumScene);

        }
        //Saindo do Jogo
        if (mEngine.getScene().equals(menumScene)) {
            new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Sair do TetrisGram")
                    .setMessage("Deseja realmente sair?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();

        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected synchronized void onResume() {
        super.onResume();
        System.gc();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (this.isGameLoaded()) {
            finish();
            System.exit(0);
        }
    }

}