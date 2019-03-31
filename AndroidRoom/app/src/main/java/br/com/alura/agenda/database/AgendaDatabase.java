package br.com.alura.agenda.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

/*
    Quando fazemos uma alteração na Entity (foi adicionado uma nova coluna 'Sobrenome'), devemos:
        - Incrementar a versão do Database
 */

@Database(entities = {Aluno.class}, version = 2, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BRANCO_DE_DADOS = "agenda.db";

    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDatabase.class, NOME_BRANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();
    }
}
