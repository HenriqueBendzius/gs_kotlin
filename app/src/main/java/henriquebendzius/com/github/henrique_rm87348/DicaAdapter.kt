package henriquebendzius.com.github.henrique_rm87348.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import henriquebendzius.com.github.henrique_rm87348.model.Dica
import henriquebendzius.com.github.henrique_rm87348.R

class DicaAdapter(
    private val context: Context,
    private var dicasList: List<Dica>
) : RecyclerView.Adapter<DicaAdapter.DicaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DicaViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_dica, parent, false)
        return DicaViewHolder(view)
    }

    override fun onBindViewHolder(holder: DicaViewHolder, position: Int) {
        val dica = dicasList[position]
        holder.tituloTextView.text = dica.titulo
        holder.descricaoTextView.text = dica.descricao

        holder.itemView.setOnClickListener {
            // Exibe a descrição da dica em um Toast
            Toast.makeText(context, dica.descricao, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return dicasList.size
    }

    fun updateList(newDicasList: List<Dica>) {
        dicasList = newDicasList
        notifyDataSetChanged()
    }

    class DicaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tituloTextView: TextView = itemView.findViewById(R.id.titulo_dica)
        val descricaoTextView: TextView = itemView.findViewById(R.id.descricao_dica)
    }
}
