package br.com.alura.agenda.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import br.com.alura.agenda.model.Aluno;

@Dao
public interface RoomAlunoDAO {

    /*
        Annotation '@Insert' informa ao Room que ele deverá implementar esse método como sendo uma
        operação de inserção na Database. Somente lembrando que o argumento passado deve ser uma
        entidade (entity).
     */
    @Insert
    void salva(Aluno aluno);

    /*
        Annotation '@Delete' informa ao Room que ele deverá implementar esse método como sendo uma
        operação de remoção de uma tupla na Database utilizando a entity passada como argumento
     */
    @Delete
    void remove(Aluno aluno);

    @Query("SELECT * FROM aluno")
    List<Aluno> todos();

}
