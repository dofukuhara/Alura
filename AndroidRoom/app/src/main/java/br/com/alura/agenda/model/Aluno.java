package br.com.alura.agenda.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@Entity
public class Aluno implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id = 0;
    private String nome;
    private String telefone;
    private String email;

    /*
        Na maioria das situações em que utilizamos classes que não representam tipos primitivos, o
        Room pode não conseguir converter o tipo automaticamente.
        Nesse página da documentação é mostrado os tipos e afinidades que o SQLite possui:
        https://sqlite.org/datatype3.html#affinity_name_examples

        Verificar nas classes AgendaDatabase e ConversorCalendar como deve ser feito a conversão
        de dados não primitivos.

        PS: Existem casos em que náo queremos que o Room persista um atributo. Para isso, podemos
            utilizar a annotation "@Ignore". Dessa forma, o Room não irá tentar criar uma coluna
            na tabela para esse atributo da classe.
     */
    private Calendar momentoDeCadastro = Calendar.getInstance();

    @Ignore
    public Aluno(String nome, String telefone, String email) {
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
    }

    public Aluno() {

    }

    public Calendar getMomentoDeCadastro() {
        return momentoDeCadastro;
    }

    public void setMomentoDeCadastro(Calendar momentoDeCadastro) {
        this.momentoDeCadastro = momentoDeCadastro;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }

    @NonNull
    @Override
    public String toString() {
        return nome + " - " + telefone;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public boolean temIdValido() {
        return id > 0;
    }

    public String dataFormatada() {
        SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");
        return formatador.format(momentoDeCadastro.getTime());
    }
}
