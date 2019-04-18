package br.com.alura.agenda.database.dao;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import br.com.alura.agenda.model.Telefone;

@Dao
public interface TelefoneDAO {

//    @Query("SELECT t.* FROM Telefone t " +
//            "JOIN Aluno a " +
//            "ON t.alunoId = a.id " +
//            "WHERE t.alunoId = :alunoId LIMIT 1")
    @Query("SELECT * FROM Telefone " +
            "WHERE alunoId = :alunoId LIMIT 1")
    Telefone buscaPrimeiroTelefoneDoAluno(int alunoId);

    @Insert
    void salva(Telefone... telefones);

    @Query("SELECT * FROM Telefone " +
            "WHERE alunoId = :alunoId")
    List<Telefone> buscaTodosTelefonesDoAluno(int alunoId);

    /*
        Aqui poderíamos utilizar apenas a annotation 'Update', porém, como fizemos uma mudança na
        estrutura da tabela (antes havia apenas 1 número de telefone na tabela do Aluno, mas agora
        temos uma tabela específica para Telefone e com mais opções de tipo de telefone), os registros
        anteriores podem não possuir o novo tipo de telefone (o tipo Celular, já que no script do
        Migration definimos que o telefone do Aluno seria convertido para o tipo Fixo apenas).
        Dessa forma, como o Update só atualiza registros já existentes, quando atualizamos um Aluno e
        informamos um número de Celular, essa atualização não é salva na tabela.
        Para isso, podemos utilizar a annotation 'Insert' e utilizando o 'onConflict' REPLACE. Dessa
        forma, caso o ID não exista, um novo registro será criado e caso exista, a nova entrada irá
        substituir a existente.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void atualiza(Telefone... telefones);
}
