package com.mrmenezes.novoinicio;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import org.andengine.audio.music.Music;
import org.andengine.audio.music.MusicFactory;
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
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegionFactory;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.color.Color;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
    private int time, score;
    private Text textTime;
    private boolean perdeu = false, primeira = true;
    private String nome;
    private Music myMusic2, myMusic;
    private DataBaseHelper db;
    private List<Jogador> collectiontBestJogadores = new ArrayList<Jogador>();

    private void iniBd() {
        db = new DataBaseHelper(this);
        collectiontBestJogadores = db.selectAllJogador();
    }

    @Override
    public EngineOptions onCreateEngineOptions() {
        //Criando a Visão do Jogo
        mCamera = new Camera(WIDTH / 2, 0, WIDTH, HEIGHT);
        EngineOptions engineOptions = new EngineOptions(true,
                ScreenOrientation.PORTRAIT_FIXED, new
                FillResolutionPolicy(),
                mCamera);
        engineOptions.getAudioOptions().setNeedsMusic(true);
        engineOptions.getAudioOptions().setNeedsSound(true);

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

    //Criando Splash
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

    //Carregamento dos Sprites
    protected void loadResources() {
        iniBd();
        try {
            myMusic = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "sfx/tetris.ogg");
            myMusic.setLooping(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            myMusic2 = MusicFactory.createMusicFromAsset(mEngine.getMusicManager(), this, "sfx/palmas.ogg");
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    //Fase1
    protected void createGame() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());

        mScene = new Scene();
        mScene.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(420, 90, mFont, "TetrisGram", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);
        mScene.attachChild(textTetrisGram);
        TailRandomize tR = new TailRandomize();
        Tail t = tR.getRMatriz(TailRandomize.DIFICULDADE_NOOB);
        int[][] matriz = t.getTabuleiro();
        int[] list = tR.getRList(t, TailRandomize.DIFICULDADE_NOOB);

        this.textTime = new Text(300, 45, mFont, "60", getVertexBufferObjectManager());
        textTime.setScale(0.6f);
        mScene.attachChild(textTime);
        time = 3600;
        this.map = new Map(mFont, Sprits.NORMAL, mScene, list, matriz, this.mFaceTextureRegion1, this.mFaceTextureRegion2, getVertexBufferObjectManager());
        mScene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                time = time - 1;
                if (time % 60 == 0)
                    textTime.setText(Integer.toString(time / 60));
                if (map.getGanhou()) {
                    score = time;
                    time = 0;
                    createGame2();

                    mEngine.setScene(mScene);

                }
                if (time < 0) {
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

    //Fase2
    protected void createGame2() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());

        mScene = new Scene();
        mScene.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(420, 90, mFont, "TetrisGram", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);

        mScene.attachChild(textTetrisGram);
        TailRandomize tR = new TailRandomize();
        Tail t = tR.getRMatriz(TailRandomize.DIFICULDADE_NORMAL);
        int[][] matriz = t.getTabuleiro();
        int[] list = tR.getRList(t, TailRandomize.DIFICULDADE_NORMAL);

        this.textTime = new Text(300, 45, mFont, "60", getVertexBufferObjectManager());
        textTime.setScale(0.6f);
        mScene.attachChild(textTime);
        time = 3600;
        this.map = new Map(mFont, Sprits.NORMAL, mScene, list, matriz, this.mFaceTextureRegion1, this.mFaceTextureRegion2, getVertexBufferObjectManager());
        mScene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                time = time - 1;
                if (time % 60 == 0)
                    textTime.setText(Integer.toString(time / 60));
                if (map.getGanhou()) {
                    score += time;
                    time = 0;
                    createGame3();

                    mEngine.setScene(mScene);
                }
                if (time < 0) {
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

    //Fase3
    protected void createGame3() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());

        mScene = new Scene();
        mScene.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(420, 90, mFont, "TetrisGram", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);

        mScene.attachChild(textTetrisGram);
        TailRandomize tR = new TailRandomize();
        Tail t = tR.getRMatriz(TailRandomize.DIFICULDADE_NORMAL);
        int[][] matriz = t.getTabuleiro();
        int[] list = tR.getRList(t, TailRandomize.DIFICULDADE_NORMAL);

        this.textTime = new Text(300, 45, mFont, "45", getVertexBufferObjectManager());
        textTime.setScale(0.6f);
        mScene.attachChild(textTime);
        time = 2700;
        this.map = new Map(mFont, Sprits.NORMAL, mScene, list, matriz, this.mFaceTextureRegion1, this.mFaceTextureRegion2, getVertexBufferObjectManager());
        mScene.registerUpdateHandler(new IUpdateHandler() {
            @Override
            public void onUpdate(float pSecondsElapsed) {
                time = time - 1;
                if (time % 60 == 0)
                    textTime.setText(Integer.toString(time / 60));
                if (map.getGanhou()) {
                    perdeu = false;
                    time = 0;
                    createMenu();
                    mEngine.setScene(menumScene);


                }
                if (time < 0) {
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

    //Dialog de cadasttro
    protected void showInputDialog(Text lose) {
        final Text loser = lose;
        myMusic2.play();
        myMusic2.setLooping(false);
        this.runOnUiThread(new Runnable() {
            public void run() {
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View promptView = layoutInflater.inflate(R.layout.input_dialog, null);
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setView(promptView);

                final EditText editText = (EditText) promptView.findViewById(R.id.edittext);
                // setup a dialog window
                alertDialogBuilder.setCancelable(false)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                nome = editText.getText().toString();
                                loser.setText(nome + ": " + Integer.toString(165 - (score / 60)));
                                menumScene.attachChild(loser);
                                Jogador temp = new Jogador();
                                temp.setNome(nome);
                                temp.setPontos(165 - (score / 60));

                                db.insertJogador(temp);
                                collectiontBestJogadores = db.selectAllJogador();
                                myMusic2.play();

                            }
                        })
                        .setNegativeButton("Cancel",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });

                // create an alert dialog
                AlertDialog alert = alertDialogBuilder.create();
                alert.show();
            }

        });

    }

    //Criado o Menu
    public void createMenu() {
        //Criando Scena do Menu
        menumScene = new Scene();
        menumScene.setBackground(new Background(0f, 0f, 0f));
        myMusic.play();
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

        Text textRecordes = new Text(440, (HEIGHT / 3) + 224, mFont, "Recordes", getVertexBufferObjectManager()) {
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

                createRecordes();
                mEngine.setScene(mSceneRecordes);
                return true;
            }
        };
        menumScene.registerTouchArea(textRecordes);
        menumScene.attachChild(textRecordes);

        Text textSound = new Text(440, (HEIGHT / 3) + 300, mFont, "Sons| On", getVertexBufferObjectManager()) {
            public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {
                if (this.getText().equals("Sons| On")) {
                    myMusic.setVolume(0);
                    myMusic2.setVolume(0);
                    myMusic.pause();
                    this.setText("Sons|Off");
                } else {
                    myMusic.play();
                    myMusic.setVolume(0.5f);
                    myMusic2.setVolume(0.5f);
                    this.setText("Sons| On");
                }
                return true;
            }
        };
        menumScene.registerTouchArea(textSound);
        menumScene.attachChild(textSound);
        Text textTetrisGram = new Text(420, 90, mFont, "TetrisGram", getVertexBufferObjectManager());
        menumScene.attachChild(textTetrisGram);
        Text loser = new Text(440, HEIGHT - 150, mFont, "Você Perdeu", getVertexBufferObjectManager());

        textTetrisGram.setScale(2.f);
        textTetrisGram.setScale(2.f);
        //Verificando Se  o Jogador Ganhou ou Perdeu
        if (perdeu && !primeira)

        {
            loser.setText("Perdeu");
            menumScene.attachChild(loser);

        } else

        {
            if (!primeira && map.getGanhou()) {
                showInputDialog(loser);


            }
        }

        primeira = false;
        perdeu = false;


    }

    //Criada Scena do Sobre

    protected void createSobre() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());

        mSceneSobre = new Scene();
        mSceneSobre.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(460, 90, mFont, "Sobre", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);
        mSceneSobre.attachChild(textTetrisGram);
        Text textCriado = new Text(260, 460, mFont, "Criado por:\n\t Erick Menezes\n\t sr.tama@outlook.com", getVertexBufferObjectManager());
        mSceneSobre.attachChild(textCriado);

    }

    //Criada  Scena de Recordes
    protected void createRecordes() {
        //Carregando Secena do jogo
        mEngine.registerUpdateHandler(new FPSLogger());
        mSceneRecordes = new Scene();
        mSceneRecordes.setBackground(new Background(0f, 0f, 0f));
        Text textTetrisGram = new Text(440, 90, mFont, "Recordes", getVertexBufferObjectManager());
        textTetrisGram.setScale(2.f);
        mSceneRecordes.attachChild(textTetrisGram);
        Text textName = new Text(340, 186, mFont, "Jogador", getVertexBufferObjectManager());
        textTetrisGram.setScale(1.2f);
        Text textPont = new Text(540, 186, mFont, "Tempo", getVertexBufferObjectManager());
        textTetrisGram.setScale(1.2f);
        mSceneRecordes.attachChild(textName);
        mSceneRecordes.attachChild(textPont);
        int cont = 1;
        if (!(collectiontBestJogadores.size() == 0)) {
            Collections.sort(collectiontBestJogadores);
            for (Jogador jogador : collectiontBestJogadores) {

                Text textNamej = new Text(340, 186 + (cont * 90), mFont, jogador.getNome().toString(), getVertexBufferObjectManager());
                Text textPontj = new Text(540, 186 + (cont * 90), mFont, Integer.toString(jogador.getPontos()), getVertexBufferObjectManager());
                mSceneRecordes.attachChild(textNamej);
                mSceneRecordes.attachChild(textPontj);
                cont++;
                if (cont > 7) {
                    break;
                }
            }
        }


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
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            myMusic.stop();
                            finish();
                        }

                    })
                    .setNegativeButton("Não", null)
                    .show();


        }
    }

    @Override
    protected void onPause() {

        myMusic.pause();
        super.onPause();
    }

    @Override
    protected void onResume() {

        super.onResume();
        System.gc();


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
        if (this.isGameLoaded()) {

            myMusic.stop();
            finish();
            System.exit(0);
        }
    }

}