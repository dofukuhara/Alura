package br.com.alura.agenda.database.converter;

import android.arch.persistence.room.TypeConverter;

import java.util.Calendar;

public class ConversorCalendar {

    /*
        Classe conversora, que terá a lógica de como converter um Calendar para Long (que será guardado
        no DB) e de Long para Calendar.
        Lembrando que temos que utilizar a annotation "@TypeConverter" para informar ao Room que esses
        são os métodos conversores.
     */

    @TypeConverter
    public Long paraLong(Calendar valor) {
        return valor.getTimeInMillis();
    }

    @TypeConverter
    public Calendar paraCalendar(Long valor) {
        Calendar momentoAtual = Calendar.getInstance();

        if (valor != null) {
            momentoAtual.setTimeInMillis(valor);
        }

        return momentoAtual;
    }

}
