package br.com.example.ormliteteste.modelo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by wesley on 7/19/16.
 */

@DatabaseTable(tableName = "contas")
public class Conta implements Serializable {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    private String numero;
    @DatabaseField(canBeNull = false)
    private String agencia;
    @DatabaseField
    private Double saldo;
    @DatabaseField(columnName = "conta_tipo", dataType = DataType.ENUM_STRING)
    private ContaTipo contaTipo;

    @DatabaseField(foreign = true, columnName = "pessoa_id")
    private Pessoa pessoa;

    public Conta() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getAgencia() {
        return agencia;
    }

    public void setAgencia(String agencia) {
        this.agencia = agencia;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public ContaTipo getContaTipo() {
        return contaTipo;
    }

    public void setContaTipo(ContaTipo contaTipo) {
        this.contaTipo = contaTipo;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Conta conta = (Conta) o;

        if (!id.equals(conta.id)) return false;
        if (!numero.equals(conta.numero)) return false;
        return agencia.equals(conta.agencia);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + numero.hashCode();
        result = 31 * result + agencia.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", numero='" + numero + '\'' +
                ", agencia='" + agencia + '\'' +
                ", saldo=" + saldo +
                ", contaTipo=" + contaTipo +
                ", pessoa=" + pessoa +
                '}';
    }
}
