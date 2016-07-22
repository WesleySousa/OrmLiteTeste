package br.com.example.ormliteteste.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

import br.com.example.ormliteteste.modelo.Conta;
import br.com.example.ormliteteste.modelo.Pessoa;

/**
 * Created by wesley on 7/22/16.
 */
public class DataBaseHelper extends OrmLiteSqliteOpenHelper {
    public static final String DATABASE_NAME = "testeorm.db";
    private static final int DATABASE_VERSION = 3;

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, Conta.class);
            TableUtils.createTable(connectionSource, Pessoa.class);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.dropTable(connectionSource, Conta.class, true);
            TableUtils.dropTable(connectionSource, Pessoa.class, true);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Dao<Pessoa, Long> getPessoaDao() throws SQLException {
        return DaoManager.createDao(getConnectionSource(), Pessoa.class);
    }

    public Dao<Conta, Long> getContaDao() throws SQLException {
        return DaoManager.createDao(getConnectionSource(), Conta.class);
    }

    @Override
    public void close() {
        super.close();
    }
}
