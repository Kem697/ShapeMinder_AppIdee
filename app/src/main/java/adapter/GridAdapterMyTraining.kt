import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.getString
import androidx.navigation.findNavController
import com.example.shapeminder_appidee.R
import model.data.local.model.myTraining.Bodypart
import ui.viewModel.ExercisesViewModel

/*
DE:
Dieser Code definiert einen Adapter (`GridAdapter`), der verwendet wird,
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

/*
EN:
This code defines an adapter (`GridAdapter`) that is used
to bind data to a GridView in Android. Here is an explanation of the code:

1. Class `GridAdapter`: This class implements the `BaseAdapter` interface,
which is required to create an adapter for a GridView.

2. Constructor: The constructor of the `GridAdapter` class takes three parameters:
- `dataset`: A list of `Content` objects containing the data for the GridView elements.
- `viewModel`: An object of type `HomeViewModel` that is likely to be used
to perform certain actions or data manipulations.
- `context`: The context in which the adapter is used, usually the activity or fragment containing the GridView.

3 Internal class `ViewHolder`:
This class is used to hold a cache reference of the view items in each GridView element.
This improves performance, as the system does not have to perform expensive caching every time an element is displayed,
expensive calls to `findViewById` every time an item is displayed.

4 Overridden methods `getCount`, `getItem`, `getItemId`: These methods are required,
to determine the size of the data source (`dataset`),
a single item at a specific position and the unique ID of an item.
These methods are used by the adapter class to provide the data for the GridView.

5 Method `getView`**: This method is called when an element of the GridView needs to be displayed.
If a "recyclable" view (`convertView`) exists, it is reused,
otherwise a new view is blown up. In this method, the data object is bound to the view elements of the GridView element.

- `ViewHolder` is used to hold the references of the view items in the GridView element, which improves performance.
- If `convertView` is not present, a new view is blown up and a `ViewHolder` is created for this view. Otherwise, the existing `ViewHolder` is reused.
- The data of the corresponding element from `dataset` is extracted.
- The view elements in `convertView` are updated using the data.
- Finally, the updated view is returned.

This is essentially the functionality of the `GridAdapter`. It connects the data in `dataset` with the view elements in `R.layout.grid_item_bodyparts` and ensures that they are displayed correctly in the GridView.

*/
class GridAdapterMyTraining(

    private val dataset: List<Bodypart>,
    private val viewModel: ExercisesViewModel,
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


        /*DE:
        * Navigations in der Bodyparts Liste.
        * Mit dieser When Verzweigen werden die View Elemente meines Gridlayouts angesprochen.
        * Zunächst einmal wird nur jenes Element anwählbar sein, welches den Titel Arme trägt.
        * Das Problem hierbei ist es meinen Datensatz überzugeben, dessen Eigenschaft Bodypart
        * mit dem Argument "Arme" zugewiesen ist.
        * Nachtrag: Die when Verzweigung wurde erweitert, indem eine Methode aus meinem ViewModel
        * zum filtern der Liste aufgerufen worden ist. Hierbei wird eine Parameter, welcher ein
        * Stringwert ist, meine Methode übergeben, sodass meine LiveData gefiltert bzw. nach dem
        * Filter aktualisiert wird.
        * getContentTitle dient für die Sortierung der Inhalt in diesem Screen.*/

        /*EN:
        * Navigation in the Bodyparts list.
        * With this When branching, the view elements of my grid layout are addressed.
        * First of all, only the element with the title Arms will be selectable.
        * The problem here is to pass my data set whose property Bodypart
        * is assigned with the argument "Arms".
        * Addendum: The when branch was extended by calling a method from my ViewModel
        * to filter the list. Here a parameter, which is a
        * string value, is passed to my method so that my LiveData is filtered or updated after the
        * filter is updated.
        * getContentTitle is used for sorting the content in this screen.*/


        when (viewHolder.textViewTitle.text) {
           getString(context,R.string.bpArme) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedBodypart = getString(context,R.string.bpArme)
                    viewModel.getBodypartCategoryTitle(selectedBodypart)
                    viewModel.filterExercisesByBodypart(selectedBodypart,context)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.exerciseListFragment)
                }
            }

            getString(context,R.string.bpBauch) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedBodypart = getString(context,R.string.bpBauch)
                    viewModel.getBodypartCategoryTitle(selectedBodypart)
                    viewModel.filterExercisesByBodypart(selectedBodypart,context)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.exerciseListFragment)
                }
            }

            getString(context,R.string.bpSchulter) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedBodypart = getString(context,R.string.bpSchulter)
                    viewModel.getBodypartCategoryTitle(selectedBodypart)
                    viewModel.filterExercisesByBodypart(selectedBodypart,context)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.exerciseListFragment)
                }
            }

            getString(context,R.string.bpRücken) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedBodypart = getString(context,R.string.bpRücken)
                    viewModel.getBodypartCategoryTitle(selectedBodypart)
                    viewModel.filterExercisesByBodypart(selectedBodypart,context)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.exerciseListFragment)
                }
            }

            getString(context,R.string.bpBeine) -> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedBodypart = getString(context,R.string.bpBeine)
                    viewModel.getBodypartCategoryTitle(selectedBodypart)
                    viewModel.filterExercisesByBodypart(selectedBodypart,context)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.exerciseListFragment)
                }
            }

            getString(context,R.string.bpBrust)-> {
                viewHolder.imageViewIcon.setOnClickListener {
                    val selectedBodypart = getString(context,R.string.bpBrust)
                    viewModel.getBodypartCategoryTitle(selectedBodypart)
                    viewModel.filterExercisesByBodypart(selectedBodypart,context)
                    viewHolder.imageViewIcon.findNavController().navigate(R.id.exerciseListFragment)
                }
            }
        }


        return itemView!!
    }


}
