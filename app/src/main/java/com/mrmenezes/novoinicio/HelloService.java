package com.mrmenezes.novoinicio;

/**
 * Created by Mr Menezes on 01/06/2015.
 */
import android.app.Service;
import android.graphics.Typeface;
import android.os.IBinder;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import org.andengine.engine.Engine;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTextureRegionFactory;
import org.andengine.extension.collisions.opengl.texture.region.PixelPerfectTiledTextureRegion;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.texture.TextureManager;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.util.color.Color;

public class HelloService  extends Service {

    private BitmapTextureAtlas mBitmapTextureAtlas1,mBitmapTextureAtlas2;
    private PixelPerfectTiledTextureRegion mFaceTextureRegion1,mFaceTextureRegion2;
    private Engine mEngine;
    private TextureManager getTextureManager;


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
    }
}