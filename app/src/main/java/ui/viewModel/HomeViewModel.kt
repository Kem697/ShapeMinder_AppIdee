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

    /*Ich habe hier eine neue LiveData erstellt, um die Liste
    * von Krafttrainingsübungen durch meine UI beobachten zu lassen.*/

    private var _exercises = MutableLiveData(allExercises)
    val exercises: LiveData<List<Content>>
        get() = _exercises

    /*Diese LiveData habe ich erstellt, um im Nach-
    * hinein auf eine gewählte Kraftrainingsübung zu
    * zugreifen.
    * */

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

    /*
    *    Ich habe die _selectedContentTitle LiveData- Variabel erstellt,
    *    um den Wert des contentTitle zu speichern:
    *    Auf diese Weise wird der Wert des contentTitle im ViewModel gesetzt,
    *    und das Fragment ExerciseListFragment kann darauf zugreifen, um die
    *    Übungen entsprechend zu sortieren, wenn der Benutzer auf den Sortieren-Button klickt.*/


    private val _selectedContentTitle = MutableLiveData<String>()
    val selectedContentTitle: LiveData<String>
        get() = _selectedContentTitle

    /*Diese Methode dient dazu, um meinen Datensatz im Nachhinein mit
    * eine Eigenschaft zu überprüfen und sie nach dem dem übergebenen Argument zu filtern.
    * Die Methode wird anschließend in meinem GridAdapter aufgerufen.*/
    fun filterExercisesByBodypart(bodypart: String) {
        viewModelScope.launch {
            val filteredExercises = allExercisesByBodyparts.filter { it.bodyPart == bodypart }
            _exercisesByBodyparts.value = filteredExercises
        }
    }

    /*
    * Aktueller Stand: Sortierfunktion funktioniert.
    * Mit dieser Methode wird die Sortierfunktion nach alphabetischer Reihenfolge
    * zur Ilustration absteigend sortiert. Das erste Problem war, dass bei der Sortierung wieder nicht nach
    * entsprechend der Körperpartieren gefiltert worden ist. Mit der Methode getContentTitle wird
    * die Liste nochmals nach Körperpartie gefiltert, sobald der Nutzer den View mit dem jeweiligen
    * Namen z.B. Arme anklickt. */


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

    /*Diese beiden Funktionen sollten zur Filterung der Übungen abhängig von
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


    /* Um die entsprechende TextView Id aus einer anderen Fragment Klasse herauszuholen,
     *  habe ich die Methode getContentTitle im ViewModel erstellt, um den Wert des contentTitles zu setzen.
     *  Dadurch, dass diese Methode im viewModel Fragment ist, kann ich sie in meinen anderen Fragmentklassen
     *  aufrufen. (ViewModel hilft, um Fragment übergreifend zu kommunizieren.). Die Methode wird dann
     *  in den when Verzweigungen aufgerufen die Bezeichnung der Körperpartie als Parameter übergeben.
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
//        var savedExercise: MutableList<Content> = mutableListOf()
        for (exercise in _exercisesByBodyparts.value!!) {
            exercise.isSaved = saved
//            if (exercise.isSaved){
//                savedExercise.add(exercise)
//            }
        }
//        _exercises.value = savedExercise
    }
}