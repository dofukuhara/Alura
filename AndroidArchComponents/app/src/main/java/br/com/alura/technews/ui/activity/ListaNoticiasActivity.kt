package br.com.alura.technews.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import br.com.alura.technews.R
import br.com.alura.technews.database.AppDatabase
import br.com.alura.technews.model.Noticia
import br.com.alura.technews.repository.NoticiaRepository
import br.com.alura.technews.ui.activity.extensions.mostraErro
import br.com.alura.technews.ui.recyclerview.adapter.ListaNoticiasAdapter
import br.com.alura.technews.ui.viewmodel.ListaNoticiasViewModel
import br.com.alura.technews.ui.viewmodel.factory.ListaNoticiasViewModelFactory
import kotlinx.android.synthetic.main.activity_lista_noticias.*

private const val TITULO_APPBAR = "Notícias"
private const val MENSAGEM_FALHA_CARREGAR_NOTICIAS = "Não foi possível carregar as novas notícias"

class ListaNoticiasActivity : AppCompatActivity() {

//    private val repository by lazy {
//        NoticiaRepository(AppDatabase.getInstance(this).noticiaDAO)
//    }
    private val adapter by lazy {
        ListaNoticiasAdapter(context = this)
    }

    private val viewModel by lazy {
        // Primeiro -> Criado o repositório, que é a dependência para criar um ViewModel para ter acesso
        // a lista de notícias
        val repository = NoticiaRepository(AppDatabase.getInstance(this).noticiaDAO)
        /* Segundo -> Criado o Factory customizado do nosso ViewModel, já que, por padrão, o construtor
            do ViewModel não aceita parâmetros
            Isso se torna necessário pois não queremos passar o contexto para o ListaNoticiasViewModel, a
            fim de deixá-lo sem nenhuma referência ao framework Android (para não ficar atrelado com o ciclo
            de vida do Android). Caso o ViewModel não precisasse do contexto, então não seria necessário o Factory
        */
        val factory = ListaNoticiasViewModelFactory(repository)
        // Terceiro -> Cria o provedor capaz de criar a instância do nosso ListaNoticiasViewModel
        val provedor = ViewModelProviders.of(this, factory)
        // Quarto -> A criação/obtenção da instância do ViewModel
        provedor.get(ListaNoticiasViewModel::class.java)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lista_noticias)
        title = TITULO_APPBAR
        configuraRecyclerView()
        configuraFabAdicionaNoticia()
        buscaNoticias()
    }

    private fun configuraFabAdicionaNoticia() {
        activity_lista_noticias_fab_salva_noticia.setOnClickListener {
            abreFormularioModoCriacao()
        }
    }

    private fun configuraRecyclerView() {
        val divisor = DividerItemDecoration(this, VERTICAL)
        activity_lista_noticias_recyclerview.addItemDecoration(divisor)
        activity_lista_noticias_recyclerview.adapter = adapter
        configuraAdapter()
    }

    private fun configuraAdapter() {
        adapter.quandoItemClicado = this::abreVisualizadorNoticia
    }

    private fun buscaNoticias() {
        viewModel.buscaTodos().observe(this, Observer {resource ->
            resource.dado?.let { adapter.atualiza(it) }
            resource.erro?.let { mostraErro(MENSAGEM_FALHA_CARREGAR_NOTICIAS) }
        })
    }

    private fun abreFormularioModoCriacao() {
        val intent = Intent(this, FormularioNoticiaActivity::class.java)
        startActivity(intent)
    }

    private fun abreVisualizadorNoticia(it: Noticia) {
        val intent = Intent(this, VisualizaNoticiaActivity::class.java)
        intent.putExtra(NOTICIA_ID_CHAVE, it.id)
        startActivity(intent)
    }

}
