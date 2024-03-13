import android.content.ContentValues.TAG
import android.content.Context
import android.nfc.Tag
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.findNavController
import com.example.shapeminder_appidee.R
import com.example.shapeminder_appidee.databinding.FragmentExerciseListBinding
import data.AppRepository
import model.Content
import ui.HomeViewModel

/*Dieser Code definiert einen Adapter (`GridAdapter`), der verwendet wird,
um Daten an ein GridView in Android zu binden. Hier ist eine Erklärung des Codes:

1. **Klasse `GridAdapter`**: Diese Klasse implementiert das `BaseAdapter`-Interface,
 das erforderlich ist, um einen Adapter für ein GridView zu erstellen.

2. **Konstruktor**: Der Konstruktor der Klasse `GridAdapter` nimmt drei Parameter entgegen:
   - `dataset`: Eine Liste von `Content`-Objekten, die die Daten für die GridView-Elemente enthält.
   - `viewModel`: Ein Objekt vom Typ `HomeViewModel`, das wahrscheinlich verwendet wird,
    um bestimmte Aktionen oder Datenmanipulationen durchzuführen.
   - `context`: Der Kontext, in dem der Adapter verwendet wird, normalerweise die Aktivität oder das Fragment, das das GridView enthält.

3. **Innere Klasse `ViewHolder`**:
Diese Klasse wird verwendet, um eine Cache-Referenz der Ansichtselemente in jedem GridView-Element zu halten.
 Dadurch wird die Leistung verbessert, da das System nicht jedes Mal,
  wenn ein Element angezeigt wird, teure Aufrufe von `findViewById` durchführen muss.

4. **Überschriebene Methoden `getCount`, `getItem`, `getItemId`**: Diese Methoden sind erforderlich,
 um die Größe der Datenquelle (`dataset`),
 ein einzelnes Element an einer bestimmten Position und die eindeutige ID eines Elements zurückzugeben.
 Diese Methoden werden von der Adapterklasse verwendet, um die Daten für das GridView bereitzustellen.

5. **Methode `getView`**: Diese Methode wird aufgerufen, wenn ein Element des GridViews angezeigt werden muss.
 Wenn eine "recycelbare" Ansicht (`convertView`) vorhanden ist, wird sie wiederverwendet,
  andernfalls wird eine neue Ansicht aufgeblasen. In dieser Methode wird das Datenobjekt an die Ansichtselemente des GridView-Elements gebunden.

   - `ViewHolder` wird verwendet, um die Referenzen der Ansichtselemente im GridView-Element zu halten, was die Leistung verbessert.
   - Wenn `convertView` nicht vorhanden ist, wird eine neue Ansicht aufgeblasen und ein `ViewHolder` für diese Ansicht erstellt. Ansonsten wird der vorhandene `ViewHolder` wiederverwendet.
   - Die Daten des entsprechenden Elements aus `dataset` werden extrahiert.
   - Die Ansichtselemente in `convertView` werden anhand der Daten aktualisiert.
   - Schließlich wird die aktualisierte Ansicht zurückgegeben.

Das ist im Wesentlichen die Funktionalität des `GridAdapter`. Es verbindet die Daten in `dataset` mit den Ansichtselementen in `R.layout.grid_item_bodyparts` und stellt sicher, dass sie korrekt im GridView angezeigt werden.*/



class GridAdapter(

    private val dataset: List<Content>,
    private val viewModel: HomeViewModel,
    private val context: Context,
) : BaseAdapter() {

    private inner class ViewHolder(view: View) {
        val textViewTitle: TextView = view.findViewById(R.id.contentTitle)
        val imageViewIcon: ImageView = view.findViewById(R.id.contentImage)
    }

    override fun getCount(): Int {
        return dataset.size
    }

    override fun getItem(position: Int): Any {
        return dataset[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var itemView = convertView
        val viewHolder: ViewHolder

        if (itemView == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            itemView = inflater.inflate(R.layout.grid_item_bodyparts, parent, false)
            viewHolder = ViewHolder(itemView)
            itemView.tag = viewHolder
        } else {
            viewHolder = itemView.tag as ViewHolder
        }

        val item = dataset[position]
        viewHolder.textViewTitle.setText(item.stringRessourceTitle)
        viewHolder.imageViewIcon.setImageResource(item.imageRessource)

        /*Navigations in der Bodyparts Liste.*/


        /*Mit dieser When Verzweigen werden die View Elemente meines Gridlayouts angesprochen.
        * Zunächst einmal wird nur jenes Element anwählbar sein, welches den Titel Arme trägt.
        * Das Problem hierbei ist es meinen Datensatz überzugeben, dessen Eigenschaft Bodypart
        * mit dem Argument "Arme" zugewiesen ist.*/

        when (viewHolder.textViewTitle.text) {

            "Arme" -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.exerciseListFragment)
                }
            }

            "Bauch" -> {}
            "Schulter" -> {}
            "Rücken" -> {}
            "Beine" -> {}
            "Brust" -> {}
        }





        return itemView!!
    }


}
