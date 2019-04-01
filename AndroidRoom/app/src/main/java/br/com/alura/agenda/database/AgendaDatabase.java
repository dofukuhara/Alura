package br.com.alura.agenda.database;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

import br.com.alura.agenda.database.dao.AlunoDAO;
import br.com.alura.agenda.model.Aluno;

@Database(entities = {Aluno.class}, version = 3, exportSchema = false)
public abstract class AgendaDatabase extends RoomDatabase {

    private static final String NOME_BRANCO_DE_DADOS = "agenda.db";

    public abstract AlunoDAO getRoomAlunoDAO();

    public static AgendaDatabase getInstance(Context context) {
        return Room
                .databaseBuilder(context, AgendaDatabase.class, NOME_BRANCO_DE_DADOS)
                .allowMainThreadQueries()
                .addMigrations(new Migration(1, 2) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        database.execSQL("ALTER TABLE aluno ADD COLUMN sobrenome TEXT");
                    }
                }, new Migration(2, 3) {
                    @Override
                    public void migrate(@NonNull SupportSQLiteDatabase database) {
                        /*
                            A principio, poderíamos tratar a remoção de uma coluna na DB utilizando o comando:
                                "ALTER TABLE aluno DROP COLUMN sobrenome"
                            Porém, a implementação do SQLite não possui a instrução 'DROP COLUMN'.
                            Por isso, para contornarmos essa limitação devemos realizar os 4 passos abaixo:
                                1) Criar nova tabela com as informações desejadas
                                2) Copiar dados da tabela antiga para a nova
                                3) Remover a tabela antiga
                                4) Renomear a tabela nova com o nome da tabela antiga
                         */

                        
                    }
                })
                .build();
    }
}
