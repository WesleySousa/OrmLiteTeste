package br.com.example.ormliteteste.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import br.com.example.ormliteteste.modelo.Pessoa;

/**
 * Created by wesley on 7/22/16.
 */
public class PessoaDao extends BaseDaoImpl<Pessoa, Long> {

    public PessoaDao(ConnectionSource connectionSource) throws SQLException {
        super(Pessoa.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
