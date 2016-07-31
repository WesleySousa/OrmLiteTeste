package br.com.example.ormliteteste;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.j256.ormlite.dao.Dao;

import java.sql.SQLException;

import br.com.example.ormliteteste.dao.DataBaseHelper;
import br.com.example.ormliteteste.modelo.Pessoa;
import br.com.example.ormliteteste.util.Util;

public class PessoaCadastro extends AppCompatActivity {

    private DataBaseHelper dataBaseHelper;
    private Context mContext;
    private EditText edtNome;
    private EditText edtDataNascimento;
    private EditText edtEndereco;
    private Dao<Pessoa, Long> pDao;
    private Pessoa pessoa;
    private boolean atualizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pessoa_cadastro);
        mContext = this;

        dataBaseHelper = new DataBaseHelper(mContext);
        try {
            pDao = dataBaseHelper.getPessoaDao();
        } catch (SQLException e) {
            e.printStackTrace();
            Util.message(mContext, "Erro ao abrir banco de dados");
            finish();
        }
        edtNome = (EditText) findViewById(R.id.edt_nome);
        edtDataNascimento = (EditText) findViewById(R.id.edt_data_de_nascimento);
        edtEndereco = (EditText) findViewById(R.id.edt_endereco);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            atualizar = extras.getBoolean("atualizar");
            pessoa = (Pessoa) extras.get("pessoa");

            if (pessoa != null) {
                edtNome.setText(pessoa.getNome());
                if (pessoa.getDataNascimento() != null)
                    edtDataNascimento.setText(Util.formatDate(pessoa.getDataNascimento(), false));
                if (pessoa.getEndereco() != null)
                    edtEndereco.setText(pessoa.getEndereco());
            }
        }
    }

    public void salvar(View view) {

        String nome = edtNome.getText().toString();
        String dataNascimento = edtDataNascimento.getText().toString();
        String endereco = edtEndereco.getText().toString();

        if (nome.isEmpty()) {
            Util.message(mContext, "Preencha o nome");
            return;
        }

        if (endereco.isEmpty())
            endereco = null;

        if (!atualizar)
            pessoa = new Pessoa();

        pessoa.setNome(nome);
        pessoa.setDataNascimento(Util.formatDate(dataNascimento, false));
        pessoa.setEndereco(endereco);
        pessoa.setContas(null); // teste

        try {
            if (atualizar) {
                pDao.update(pessoa);
                Util.message(mContext, "Pessoa atualizada com sucesso.");
            } else {
                pDao.create(pessoa);
                Util.message(mContext, "Pessoa salva com sucesso.");
            }
            setResult(RESULT_OK);
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Util.message(mContext, "Erro ao salvar pessoa");
        }


    }
}
