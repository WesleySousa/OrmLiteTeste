package br.com.example.ormliteteste.modelo;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by wesley on 7/18/16.
 */
@DatabaseTable(tableName = "pessoas")
public class Pessoa implements Serializable {

    @DatabaseField(generatedId = true)
    private Long id;
    @DatabaseField(canBeNull = false)
    private String nome;
    @DatabaseField(canBeNull = true, columnName = "data_nascimento", dataType = DataType.DATE)
    private Date dataNascimento;
    @DatabaseField(canBeNull = true)
    private String endereco;

    @ForeignCollectionField
    private Collection<Conta> contas;

    public Pessoa() {
        contas = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pessoa pessoa = (Pessoa) o;

        return id.equals(pessoa.id);

    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public Collection<Conta> getContas() {
        return contas;
    }

    public void setContas(Collection<Conta> contas) {
        this.contas = contas;
    }

    @Override
    public String toString() {
        return "Pessoa{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", endereco='" + endereco + '\'' +
                ", contas=" + contas +
                '}';
    }
}
