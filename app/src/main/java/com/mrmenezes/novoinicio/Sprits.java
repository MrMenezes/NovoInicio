package com.mrmenezes.novoinicio;

import android.view.MotionEvent;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.extension.collisions.entity.sprite.PixelPerfectAnimatedSprite;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;
import org.andengine.input.touch.TouchEvent;
import org.andengine.input.touch.detector.ContinuousHoldDetector;
import org.andengine.input.touch.detector.HoldDetector;
import org.andengine.input.touch.detector.HoldDetector.IHoldDetectorListener;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import java.util.Vector;


/**
 * Created by Mrmenezes on 09/04/2015.
 */
public class Sprits extends PixelPerfectAnimatedSprite implements IHoldDetectorListener, IOnSceneTouchListener {
    private int indexAnimate;
    private int iAnimate;

    public static int NORMAL = 1;
    public static int HARDCORE = 2;
    public static int INSANO = 3;

    private boolean flipped = true;
    private float mDownX;
    private float mDownY;
    private final float SCROLL_THRESHOLD = 10;
    private boolean isOnClick;
    private Scene mScene;
    private boolean testeflip = false;
    private HoldDetector mHoldDetector;
    private ContinuousHoldDetector continuousHoldDetector;
    private Map map;
    private int dificuldade;
    private int colidMax;
    public boolean colidTotal = false;
    private boolean movendo = false;
    private Vector<PixelPerfectAnimatedSprite> nSpriteTail;

    public Sprits(Vector<PixelPerfectAnimatedSprite> nSpriteTail_, int dificuldade_, Map mapa, int indexAnimate_, Scene mScene_, float pX, float pY, PixelPerfectTiledTextureRegion pTiledTextureRegion, VertexBufferObjectManager pVertexBufferObjectManager) {
        super(pX, pY, pTiledTextureRegion, pVertexBufferObjectManager);

        this.nSpriteTail = nSpriteTail_;
        this.mHoldDetector = new HoldDetector(this);
        this.mHoldDetector.setEnabled(true);
        this.indexAnimate = indexAnimate_;
        this.mHoldDetector.setTriggerHoldMinimumMilliseconds(2000);
        this.continuousHoldDetector = new ContinuousHoldDetector(this);
        this.sortChildren();
        this.mScene = mScene_;
        registerUpdateHandler(continuousHoldDetector);
        this.mScene.setOnSceneTouchListener(this);
        this.mScene.registerTouchArea(this);
        this.mScene.attachChild(this);
        if (indexAnimate_ > 63) {
            this.setCurrentTileIndex(indexAnimate_ - 64);
            this.iAnimate = indexAnimate_ - 64;
        } else {
            this.setCurrentTileIndex(indexAnimate_);
            this.iAnimate = indexAnimate_;
        }

        switch (indexAnimate_) {

            case 8:
                this.colidMax = 2;
                break;
            case 16:
                this.colidMax = 3;
                break;
            case 24:
                this.colidMax = 3;
                break;
            case 32:
                this.colidMax = 4;
                break;
            case 40:
                this.colidMax = 5;
                break;
            case 48:
                this.colidMax = 5;
                break;
            case 56:
                this.colidMax = 6;
                break;
            case 64:
                this.colidMax = 4;
                break;
            case 72:
                this.colidMax = 4;
                break;
            case 80:
                this.colidMax = 4;
                break;
            case 88:
                this.colidMax = 5;
                break;
            case 96:
                this.colidMax = 4;
                break;
            default:
                this.colidMax = 0;
                break;
        }


        this.map = mapa;

        this.dificuldade = dificuldade_;
        this.setFlippedVertical(flipped);


    }

    public boolean colidindo() {
        int cont = 0;
        colidTotal = false;
        for (int a = 0; a < nSpriteTail.indexOf(nSpriteTail.lastElement()) + 1; a++) {
            if (nSpriteTail.get(a).collidesWith(this)) {
                cont++;

            }
        }
        if (colidMax == cont) return colidTotal = true;
        else return colidTotal = false;
    }

    public void MovendoOutros(boolean movendo) {
        if (movendo) {
            mScene.getTouchAreas().remove(this);
            this.setAlpha(0.7f);
            //mScene.detachChild(this);
        } else {
            mScene.getTouchAreas().add(this);
            this.setAlpha(1f);
            //mScene.attachChild(this);
        }
    }


    public boolean onAreaTouched(final TouchEvent pSceneTouchEvent, final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

        switch (pSceneTouchEvent.getAction() & MotionEvent.ACTION_MASK) {

            case TouchEvent.ACTION_DOWN:

                if (!movendo) {
                    this.map.Movendo(this, true);
                    this.movendo = true;
                }
                mDownX = pSceneTouchEvent.getX();
                mDownY = pSceneTouchEvent.getY();
                isOnClick = true;
                break;
            case TouchEvent.ACTION_CANCEL:
                if (movendo) {
                    this.map.Movendo(this, false);
                    this.movendo = false;
                }
            case TouchEvent.ACTION_UP:
                this.colidindo();
                this.map.upClick();
                if (movendo) {
                    this.map.Movendo(this, false);
                    this.movendo = false;
                }
                int aumento = 6;
                int zero = 4;
                if (flipped) {
                    aumento = 2;
                    zero = 0;
                }

                if (isOnClick) {
                    if (this.iAnimate + aumento < this.getCurrentTileIndex()) {
                        this.setCurrentTileIndex(this.iAnimate + zero);

                    } else {
                        this.setCurrentTileIndex(this.getCurrentTileIndex() + 1);

                    }
                }
                break;
            case TouchEvent.ACTION_MOVE:
                if (!movendo) {
                    this.map.Movendo(this, true);
                    this.movendo = true;
                }
                switch (dificuldade) {
                    case 1:
                        this.setPosition((Math.round((pSceneTouchEvent.getX() - this.getWidth() / 2) / 32) * 32), Math.round((pSceneTouchEvent.getY() - this.getHeight() / 2) / 32) * 32);
                        break;
                    case 2:
                        this.setPosition((Math.round((pSceneTouchEvent.getX() - this.getWidth() / 2) / 16) * 16), Math.round((pSceneTouchEvent.getY() - this.getHeight() / 2) / 16) * 16);
                        break;
                    default:
                        this.setPosition((Math.round((pSceneTouchEvent.getX() - this.getWidth() / 2) / 8) * 8), Math.round((pSceneTouchEvent.getY() - this.getHeight() / 2) / 8) * 8);
                        break;
                }


                if ((isOnClick) && (Math.abs(mDownX - pSceneTouchEvent.getX()) > SCROLL_THRESHOLD || Math.abs(mDownY - pSceneTouchEvent.getY()) > SCROLL_THRESHOLD)) {

                    isOnClick = false;
                }

                break;

            default:

                if (movendo) {
                    this.map.Movendo(this, false);
                    this.movendo = false;
                }

                break;
        }
        continuousHoldDetector.onTouchEvent(pSceneTouchEvent);
        return true;
    }

    @Override
    public void onHoldStarted(HoldDetector pHoldDetector, int pPointerID, float pHoldX, float pHoldY) {
        isOnClick = false;
        if (flipped) this.setCurrentTileIndex(this.getCurrentTileIndex() + 4);
        else this.setCurrentTileIndex(this.getCurrentTileIndex() - 4);
        flipped = !flipped;


    }

    @Override
    public void onHold(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {

    }

    @Override
    public void onHoldFinished(HoldDetector pHoldDetector, long pHoldTimeMilliseconds, int pPointerID, float pHoldX, float pHoldY) {
        if (movendo) {
            this.map.Movendo(this, false);
            this.movendo = false;
        }

    }

    @Override
    public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {

        continuousHoldDetector.onTouchEvent(pSceneTouchEvent);
        return true;
    }


}
