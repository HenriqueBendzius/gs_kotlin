package henriquebendzius.com.github.henrique_rm87348


import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import henriquebendzius.com.github.henrique_rm87348.adapter.DicaAdapter
import henriquebendzius.com.github.henrique_rm87348.database.DatabaseHelper
import henriquebendzius.com.github.henrique_rm87348.model.Dica

class MainActivity : ComponentActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var dicaAdapter: DicaAdapter
    private lateinit var dbHelper: DatabaseHelper
    private lateinit var dicasList: MutableList<Dica>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializando o RecyclerView e o SearchView
        recyclerView = findViewById(R.id.recyclerView)
        val searchView: SearchView = findViewById(R.id.searchView)

        // Inicializando o DatabaseHelper para manipular o banco de dados
        dbHelper = DatabaseHelper(this)

        // Inicializando a lista de dicas a partir do banco de dados
        dicasList = dbHelper.getAllDicas()

        // Configurando o RecyclerView para exibir as dicas
        dicaAdapter = DicaAdapter(this, dicasList)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dicaAdapter

        // Configurando a funcionalidade de busca
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val filteredList = filterDicas(newText)
                dicaAdapter.updateList(filteredList)
                return true
            }
        })
    }

    // Função para filtrar a lista de dicas com base no texto digitado
    private fun filterDicas(query: String?): List<Dica> {
        return if (query.isNullOrEmpty()) {
            dicasList
        } else {
            dicasList.filter {
                it.titulo.contains(query, ignoreCase = true) || it.descricao.contains(query, ignoreCase = true)
            }
        }
    }
}

