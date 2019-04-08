package br.com.alura.agenda.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import br.com.alura.agenda.model.Aluno;

@Dao
public interface AlunoDAO {

    @Insert
    void salva(Aluno aluno);

    @Delete
    void remove(Aluno aluno);

    @Query("SELECT * FROM aluno")
    List<Aluno> todos();

    @Update
    void edita(Aluno aluno);
}
