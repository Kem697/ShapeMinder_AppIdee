package ui.viewModel

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat.getString
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shapeminder_appidee.R
import model.data.AppRepository
import kotlinx.coroutines.launch
import model.Content
import model.Food

class HomeViewModel : ViewModel() {
    private val repository = AppRepository()
    private val allContent = repository.content
    private val allExercises = repository.exercises
    private val allBodyparts = repository.bodyParts
    private var allExercisesByBodyparts = repository.exercisesByBodyparts
    private var groceryCategories = repository.groceryCategories

    var index = 0

    private var _contents = MutableLiveData(allContent)
    val contents: LiveData<List<Content>>
        get() = _contents


    private var _bodyparts = MutableLiveData(allBodyparts)
    val bodyparts: LiveData<List<Content>>
        get() = _bodyparts

    /*DE:
    *Ich habe hier eine neue LiveData erstellt, um die Liste
    * von Krafttrainingsübungen durch meine UI beobachten zu lassen.*/

    /*EN:
    * I ve created a livedata, which poses my exercises. This step is important
    * to allow my ui to observe the aimed data for the recyclerview initialization.
    * */


    private var _exercises = MutableLiveData(allExercises)
    val exercises: LiveData<List<Content>>
        get() = _exercises

    /*DE:
    * Diese LiveData habe ich erstellt, um im Nach-
    * hinein auf eine gewählte Kraftrainingsübung zu
    * zugreifen.
    * */

    /*EN:
    * I ve created this livedata to fetch a specific
    * ecercise of my dataset of exercises.*/

    private var _selectedExercise = MutableLiveData(allExercises[index])
    val selectedExercise: LiveData<Content>
        get() = _selectedExercise


    private var _selectedContent = MutableLiveData(allContent[index])
    val selectedContent: LiveData<Content>
        get() = _selectedContent


    private var _selectedExercisesByBodypart = MutableLiveData(allExercisesByBodyparts[index])
    val selectedExercisesByBodypart: LiveData<Content>
        get() = _selectedExercisesByBodypart


    private var _foodCategories = MutableLiveData(groceryCategories)
    val foodCategories: LiveData<List<Food>>
        get() = _foodCategories


    private var _exercisesByBodyparts = MutableLiveData(allExercisesByBodyparts)

    val exercisesByBodyparts: LiveData<List<Content>>
        get() = _exercisesByBodyparts



    private var _savedExercises = MutableLiveData<List<Content>>()
    val savedExercises: LiveData<List<Content>>

        get() = _savedExercises



    /*DE:
    * Ich habe die _selectedContentTitle LiveData- Variabel erstellt,
    * um den Wert des contentTitle zu speichern:
    * Auf diese Weise wird der Wert des contentTitle im ViewModel gesetzt,
    * und das Fragment ExerciseListFragment kann darauf zugreifen, um die
    * Übungen entsprechend zu sortieren, wenn der Benutzer auf den Sortieren-Button klickt.
    * */

    /*EN:
    * I created the _selectedContentTitle LiveData variable,
    * to store the value of the contentTitle:
    * This way the value of the contentTitle is set in the ViewModel,
    * and the ExerciseListFragment fragment can access it to sort the
    * exercises accordingly when the user clicks the sort button.
    * */


    private val _selectedContentTitle = MutableLiveData<String>()
    val selectedContentTitle: LiveData<String>
        get() = _selectedContentTitle

    /*DE:
    *Diese Methode dient dazu, um meinen Datensatz im Nachhinein mit
    * eine Eigenschaft zu überprüfen und sie nach dem dem übergebenen Argument zu filtern.
    * Die Methode wird anschließend in meinem GridAdapter aufgerufen.*/

    /*EN:
*This method is used to check my data set afterwards with
* to check a property and filter it according to the passed argument.
* The method is then called in my GridAdapter */




    fun filterExercisesByBodypart(bodypart: String) {
        viewModelScope.launch {
            val filteredExercises = allExercisesByBodyparts.filter { it.bodyPart == bodypart }
            _exercisesByBodyparts.value = filteredExercises
        }
    }

    /*DE:
    * Aktueller Stand: Sortierfunktion funktioniert.
    * Mit dieser Methode wird die Sortierfunktion nach alphabetischer Reihenfolge
    * zur Ilustration absteigend sortiert. Das erste Problem war, dass bei der Sortierung wieder nicht nach
    * entsprechend der Körperpartieren gefiltert worden ist. Mit der Methode getContentTitle wird
    * die Liste nochmals nach Körperpartie gefiltert, sobald der Nutzer den View mit dem jeweiligen
    * Namen z.B. Arme anklickt. */

    /*EN:
    * Current status: Sort function works.
    * With this method, the sort function is sorted in alphabetical order
    * sorted in descending order for illustration. The first problem was that the sorting was again not filtered by
    * according to the body parts. With the method getContentTitle
    * the list is filtered again by body part as soon as the user clicks on the view with the respective
    * name, e.g. arms. */


    fun sortExercisesByAlphabet(bodypart: String, sort: Boolean) {
        viewModelScope.launch {
            val filteredExercises = allExercisesByBodyparts.filter { it.bodyPart == bodypart }
            val sortedExercises = if (sort) {
                filteredExercises.sortedByDescending { it.stringRessourceText }
            } else {
                filteredExercises.sortedBy { it.stringRessourceText }
            }
            _exercisesByBodyparts.value = sortedExercises
        }
    }

    /*DE:
    *Diese beiden Funktionen sollten zur Filterung der Übungen abhängig von
    * der Nutzereingabe dienen. Leider funktionieren diese beiden Funktionen
    * nicht, wenn ich sie im Fragment aufrufe. Stand jetzt führt die Nutzereingabe
    * nicht zum Absturz. Nachtrag: Die Liste aus allen Übungen wird nicht mehr angezeigt.
    * Ich habe nämlich die resetFilter() Funtkionen um einen Parameter, welcher die Körperpartie
    * enthält erweitert. Dadurch werden die Übungen abhängig von der Körperpartie wiedergegeben.
    *
    * Zur Erklärung:
    * Zunächst einmal sollen die Übungen nach Körpterteil gefiltert werden.
    * Dadurch sollen nur die Übungen abhängig von der Körperpartie angezeigt werden.
    * Anschließend werden die Übungen nach Nutzereingabe gefiltert, sodass nicht alle Übungen.
    *
    */

    /*EN:
    *These two functions should be used to filter the exercises depending on
    * of the user input. Unfortunately, these two functions
    * do not work when I call them in the fragment. As of now, the user input
    * does not lead to a crash. Addendum: The list of all exercises is no longer displayed.
    * I have added a parameter to the resetFilter() functions which contains the body part.
    * has been added. As a result, the exercises are displayed depending on the body part.
    *
    * Explanation:
    * First of all, the exercises should be filtered by body part.
    * This means that only the exercises depending on the body part will be displayed.
    * Then the exercises are filtered according to user input so that not all exercises are displayed.
    *
    */


    fun filterExercisesByTitle(userInput: String, bodypart: String, context: Context) {
        viewModelScope.launch {
            val filteredExercises = _exercisesByBodyparts.value?.filter {
                val xmlValue = context.getString(it.stringRessourceTitle)
                xmlValue.contains(userInput, ignoreCase = true)
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _exercisesByBodyparts.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    resetFilter(bodypart)
                }
            }
        }
    }

    fun resetFilter(bodypart: String) {
        _exercisesByBodyparts.value = allExercisesByBodyparts.filter { it.bodyPart == bodypart }
    }


    /*DE:
     *Um die entsprechende TextView Id aus einer anderen Fragment Klasse herauszuholen,
     *  habe ich die Methode getContentTitle im ViewModel erstellt, um den Wert des contentTitles zu setzen.
     *  Dadurch, dass diese Methode im viewModel Fragment ist, kann ich sie in meinen anderen Fragmentklassen
     *  aufrufen. (ViewModel hilft, um Fragment übergreifend zu kommunizieren.). Die Methode wird dann
     *  in den when Verzweigungen aufgerufen die Bezeichnung der Körperpartie als Parameter übergeben.
     *
    */

    /*EN:
     *To get the corresponding TextView Id from another fragment class,
     * I have created the method getContentTitle in the ViewModel to set the value of the contentTitle.
     * Because this method is in the viewModel fragment, I can call it in my other fragment classes.
     * in my other fragment classes. (ViewModel helps to communicate across fragments). The method is then called
     * The method is then called in the when branches and the name of the body part is passed as a parameter.
     *
    */

    fun getContentTitle(bodypart: String) {
        _selectedContentTitle.value = bodypart
    }

    fun navigateDetailView(content: Content) {
        _selectedContent.value = content
    }

    fun setOriginalList(exercises: List<Content>, bodypart: String) {
        exercises.filter { it.bodyPart == bodypart }
        _exercisesByBodyparts.value = exercises
    }


    fun isSaved(saved: Boolean) {
        var savedExercises: MutableList<Content> = mutableListOf()
        for (exercise in _exercisesByBodyparts.value!!) {
            exercise.isSaved = saved
            savedExercises.add(exercise)
        }
        _savedExercises.value= savedExercises
    }


//    fun isSaved(saved: Boolean, exercise: Content) {
//        val updatedExercises = _savedExercises.value?.toMutableList() ?: mutableListOf()
//
//        val index = updatedExercises.indexOfFirst { it == exercise }
//        if (index != -1) {
//            // Wenn die Übung in der Liste vorhanden ist, aktualisiere den Zustand
//            updatedExercises[index].isSaved = saved
//        } else {
//            // Ansonsten füge die Übung zur Liste hinzu
//            updatedExercises.add(exercise.copy(isSaved = saved))
//        }
//
//        _savedExercises.value = updatedExercises
//    }


}