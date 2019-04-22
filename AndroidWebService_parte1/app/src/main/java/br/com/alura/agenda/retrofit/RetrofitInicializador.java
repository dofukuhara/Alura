package br.com.alura.agenda.retrofit;

import br.com.alura.agenda.BuildConfig;
import br.com.alura.agenda.services.AlunoService;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class RetrofitInicializador {

    private final Retrofit retrofit;

    public RetrofitInicializador() {

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.LOCAL_WEBSERVER_URL)
                .addConverterFactory(JacksonConverterFactory.create()).build();
    }

    public AlunoService getAlunoService() {
        return retrofit.create(AlunoService.class);
    }
}
