package com.mrmenezes.novoinicio;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr Menezes on 14/06/2015.
 */
public class DataBaseHelper extends SQLiteOpenHelper {
    private static final String databaseName = "player.db";
    private static final int databaseVersion = 1;

    public DataBaseHelper(Context context) {
        super(context, databaseName, null, databaseVersion);
    }

    @Override
    public void close() {
        super.close();
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateTableJog = "CREATE TABLE jogador(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "pontos INTEGER" +
                ")";
        sqLiteDatabase.execSQL(sqlCreateTableJog);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i2) {
        String sqlDropTableJog = "DROP TABLE jogador";
        sqLiteDatabase.execSQL(sqlDropTableJog);
        onCreate(sqLiteDatabase);

    }

    public void insertJogador(Jogador jogador) {
        SQLiteDatabase db = getWritableDatabase();
        String sqlInsertJog = "INSERT INTO jogador (nome,pontos) values(\"" +
                jogador.getNome() + "\"," +
                jogador.getPontos() + ")";
        db.execSQL(sqlInsertJog);
        db.close();
    }

    public List<Jogador> selectAllJogador() {
        List<Jogador> lista = new ArrayList<Jogador>();

        SQLiteDatabase db = getReadableDatabase();

        String selectAll = "SELECT * FROM jogador ORDER BY pontos DESC;";

        Cursor c = db.rawQuery(selectAll, null);

        if (c.moveToFirst()) {
            do {
                Jogador jog = new Jogador();
                jog.setNome(c.getString(1));
                jog.setPontos(c.getInt(2));
                jog.setId(c.getInt(0));
                lista.add(jog);

            } while (c.moveToNext());


        }
        db.close();

        return lista;
    }

}
