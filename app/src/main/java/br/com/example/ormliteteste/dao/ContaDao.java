package br.com.example.ormliteteste.dao;

import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;

import br.com.example.ormliteteste.modelo.Conta;

/**
 * Created by wesley on 7/22/16.
 */
public class ContaDao extends BaseDaoImpl<Conta, Long> {
    public ContaDao(ConnectionSource connectionSource) throws SQLException {
        super(Conta.class);
        setConnectionSource(connectionSource);
        initialize();
    }
}
