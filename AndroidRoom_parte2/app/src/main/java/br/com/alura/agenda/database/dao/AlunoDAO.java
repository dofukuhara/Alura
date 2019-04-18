package br.com.alura.agenda.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Aluno;

@Dao
public interface AlunoDAO {
    /*
        Para recuperar o ID da tupla criada, basta mudar o tipo do retorno do insert para long.
        No caso, podemos utilizar tanto o tipo primitivo 'long', quanto o seu wrapper 'Long'.
        Aqui, decidimos utilizar o wrapper, pois no obj Telefone, precisamos do ID do tipo int, então
        utilizando o wrapper 'Long', podemos fazer o cast utilizando o método 'intValue()' do wrapper.
     */
    @Insert
    Long salva(Aluno aluno);

    @Query("SELECT * FROM aluno")
    List<Aluno> todos();

    @Delete
    void remove(Aluno aluno);

    @Update
    void edita(Aluno aluno);
}
