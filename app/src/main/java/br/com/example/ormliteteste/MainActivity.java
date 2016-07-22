package br.com.example.ormliteteste;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.List;

import br.com.example.ormliteteste.dao.ContaDao;
import br.com.example.ormliteteste.dao.DataBaseHelper;
import br.com.example.ormliteteste.dao.PessoaDao;
import br.com.example.ormliteteste.modelo.Conta;
import br.com.example.ormliteteste.modelo.Pessoa;

public class MainActivity extends AppCompatActivity {

    private static final String SCRIPT = "Script";
    private DataBaseHelper dataBaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataBaseHelper = new DataBaseHelper(this);

        try {
            PessoaDao pDao = new PessoaDao(dataBaseHelper.getConnectionSource());
            /*Pessoa p = new Pessoa();
            p.setNome("Wesley Sousa");
            p.setEndereco("Travessa timbiras II");
            Calendar calendar = Calendar.getInstance();
            calendar.set(1995, Calendar.APRIL, 27);
            p.setDataNascimento(calendar.getTime());
            int i = pDao.create(p);
            Log.d(SCRIPT, "Pessoa cadastrada");
            if (i != 1)
                return;*/
            //pDao.delete(pDao.queryForId(1L));
            //Log.d(SCRIPT, pDao.queryForId(1L).toString());
            List<Pessoa> pessoas = pDao.queryForAll();
            Log.d(SCRIPT, "");
            Log.d(SCRIPT, "Pessoas cadastradas");
            for(Pessoa p : pessoas) {
                Log.d(SCRIPT, p.toString());
                Log.d(SCRIPT, "");
                Log.d(SCRIPT, "Contas cadastradas");
                for(Conta c : p.getContas()) {
                    Log.d(SCRIPT, c.toString());
                }
                Log.d(SCRIPT, "");
            }
            Log.d(SCRIPT, "");

            ContaDao cDao = new ContaDao(dataBaseHelper.getConnectionSource());

            List<Conta> contas = cDao.queryForAll();
            Log.d(SCRIPT, "");
            Log.d(SCRIPT, "Contas cadastradas");
            for(Conta c : contas) {
                Log.d(SCRIPT, c.toString());
                Log.d(SCRIPT, c.getPessoa().toString());
            }
            Log.d(SCRIPT, "");

            Dao<Pessoa, Long> pessoaDao = dataBaseHelper.getPessoaDao();
            List<Pessoa> pessoas1 = pessoaDao.queryForAll();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null && dataBaseHelper.isOpen())
            dataBaseHelper.close();
    }

    public void backupDB(View view) {
        Log.d(SCRIPT, "Fazendo backup.");
        exportDB();
    }

    private void exportDB() {
        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//" + getPackageName()
                        + "//databases//" + DataBaseHelper.DATABASE_NAME;
                String backupDBPath = DataBaseHelper.DATABASE_NAME;
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getApplicationContext(), "Backup com sucesso!",
                        Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Backup Falhou!", Toast.LENGTH_SHORT)
                    .show();
        }
    }
}
