package br.com.example.ormliteteste;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.example.ormliteteste.adapter.PessoaAdapter;
import br.com.example.ormliteteste.dao.ContaDao;
import br.com.example.ormliteteste.dao.DataBaseHelper;
import br.com.example.ormliteteste.dao.PessoaDao;
import br.com.example.ormliteteste.modelo.Conta;
import br.com.example.ormliteteste.modelo.ContaTipo;
import br.com.example.ormliteteste.modelo.Pessoa;
import br.com.example.ormliteteste.util.Util;

public class MainActivity extends AppCompatActivity {

    private static final String SCRIPT = "Script";
    private static final int COD_PESSOA = 71;
    private static final int COD_CONTA = 72;
    private Context mContext;
    private DataBaseHelper dataBaseHelper;
    private Dao<Pessoa, Long> pDao;
    private Dao<Conta, Long> cDao;
    private ListView listPessoas;
    private List<Pessoa> pessoas;
    private PessoaAdapter pessoaAdapter;
    private TextView txtListaVazia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        dataBaseHelper = new DataBaseHelper(mContext);
        txtListaVazia = (TextView) findViewById(R.id.txt_lista_vazia);
        try {
            pDao = dataBaseHelper.getPessoaDao();
            cDao = dataBaseHelper.getContaDao();
            pessoas = pDao.queryForAll();

            listPessoas = (ListView) findViewById(R.id.list_pessoas);
            pessoaAdapter = new PessoaAdapter(mContext, R.layout.pessoa_row, pessoas);
            listPessoas.setAdapter(pessoaAdapter);

        } catch (SQLException e) {
            e.printStackTrace();
            Util.message(mContext, "Erro ao abrir banco de dados");
            //finish();
            return;
        }

        verificaListaVazia();

        listPessoas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Pessoa pessoa = pessoas.get(i);
                Intent intent = new Intent(mContext, PessoaCadastro.class);
                intent.putExtra("pessoa", pessoa);
                intent.putExtra("atualizar", true);
                startActivityForResult(intent, COD_PESSOA);
            }
        });

        listPessoas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int position, long l) {
                final Pessoa pessoa = pessoas.get(position);


                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                builder.setTitle("Ações");
                builder.setItems(new CharSequence[]{"Add conta", "Del conta"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (i == 0) {
                            Conta c = new Conta();
                            c.setSaldo(Math.random());
                            c.setContaTipo(ContaTipo.CORRENTE);
                            c.setPessoa(pessoa);
                            c.setNumero(""+new Date().getTime());
                            c.setAgencia("0021");
                            try {
                                cDao.create(c);
                                Util.message(mContext, "Conta salva");
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                pDao.delete(pessoa);
                                Util.message(mContext, "Pessoa removida");
                                pessoas.remove(position);
                                pessoaAdapter.notifyDataSetChanged();
                            } catch (SQLException e) {
                                e.printStackTrace();
                                Util.message(mContext, e.getSQLState());
                            }
                        }
                    }
                });
                builder.show();

                return true;
            }
        });





    }

    private void verificaListaVazia() {
        if (pessoas != null && pessoas.isEmpty()) {
            txtListaVazia.setVisibility(View.VISIBLE);

        } else {
            txtListaVazia.setVisibility(View.GONE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dataBaseHelper != null && dataBaseHelper.isOpen())
            dataBaseHelper.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mnu_novo :
                Intent intent = new Intent(mContext, PessoaCadastro.class);
                startActivityForResult(intent, COD_PESSOA);
                break;
            case R.id.mnu_nova_conta :
                try {


                    List<Conta> contas = cDao.queryForAll();
                    StringBuilder te = new StringBuilder();
                    for (Conta c : contas) {
                        pDao.refresh(c.getPessoa());
                        te.append(c.toString());
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle("Contas");
                    builder.setMessage(te.toString());
                    builder.show();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.mnu_backup :
                if (Util.exportDB(getPackageName()))
                    Util.message(mContext, "Backup feito com sucesso na pasta Downloads");
                else
                    Util.message(mContext, "Erro ao fazer backup");

                break;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == COD_PESSOA && resultCode == RESULT_OK) {
            try {
                pessoas.clear();
                pessoas.addAll(pDao.queryForAll());
                pessoaAdapter.notifyDataSetChanged();

                verificaListaVazia();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
