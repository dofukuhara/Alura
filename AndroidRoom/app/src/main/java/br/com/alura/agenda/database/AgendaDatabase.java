package br.com.alura.agenda.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

/*
    Para o Room, somente anotar a classe com "@Database" não é o suficiente. Além disso, a classe
    deve extender a classe "RoomDatabase".
    Além disso, o Room recomenda que a classe que for extender "RoomDatabase" também seja declarada
    como abstrata. Isso é feito para que não seja necessário implementar manualmente os métodos de
    acesso ao Database, deixando essa responsabilidade para o Room.
 */

@Database(entities = {Aluno.class}, version = 1, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BRANCO_DE_DADOS = "agenda.db";

    /*
                Método (abstrato, que o Room irá implementar) que retorna uma instância do nosso DAO.
             */
    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDatabase.class, NOME_BRANCO_DE_DADOS)
                .allowMainThreadQueries()
                .build();
    }
}
