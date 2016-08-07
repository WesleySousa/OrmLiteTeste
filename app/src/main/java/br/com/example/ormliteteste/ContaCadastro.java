package br.com.example.ormliteteste;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;

import com.j256.ormlite.dao.Dao;

import br.com.example.ormliteteste.dao.DataBaseHelper;
import br.com.example.ormliteteste.modelo.Conta;
import br.com.example.ormliteteste.modelo.Pessoa;

public class ContaCadastro extends AppCompatActivity {

    private Context mContext;
    private DataBaseHelper dataBaseHelper;
    private Dao<Pessoa, Long> pDao;
    private Dao<Conta, Long> cDao;

    private EditText edtNumero;
    private EditText edtAgencia;
    private EditText edtSaldo;
    private RadioButton rbCorrente;
    private RadioButton rbPoupanca;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conta_cadastro);
    }

    public void salvar(View view) {
    }

    public void rbClick(View view) {
    }
}
