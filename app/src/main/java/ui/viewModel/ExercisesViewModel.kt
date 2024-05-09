package ui.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.shapeminder_appidee.R
import model.data.local.LocalRepository
import kotlinx.coroutines.launch
import model.data.local.model.myTraining.Exercise
import model.data.local.getTrainingDatabase
import model.data.local.model.myTraining.Bodypart

class ExercisesViewModel(application: Application) : AndroidViewModel(application) {
    private var trainingDatabase = getTrainingDatabase(application)
    private val repository = LocalRepository(trainingDatabase)
    private val allBodyparts = repository.bodyParts
    private var allExercisesByBodyparts = repository.allExercisesByBodyparts


    private var _listOfAllExercises = MutableLiveData(allExercisesByBodyparts)
    val listOfAllExercises: MutableLiveData<List<Exercise>>
        get() = _listOfAllExercises



    private var _listOfBodyparts = MutableLiveData(allBodyparts)
    val listOfBodyparts: LiveData<List<Bodypart>>
        get() = _listOfBodyparts



    /*DE:
    * Diese LiveData habe ich erstellt, um im Nach-
    * hinein auf eine gewählte Kraftrainingsübung zu
    * zugreifen.
    * */

    /*EN:
    * I ve created this livedata to fetch a specific
    * ecercise of my dataset of exercises.*/

    private var _selectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: LiveData<Exercise>
        get() = _selectedExercise



    private var _exercisesByBodyparts = MutableLiveData(allExercisesByBodyparts)

    val exercisesByBodyparts: LiveData<List<Exercise>>
        get() = _exercisesByBodyparts



    private var _savedExercises = MutableLiveData(
        mutableListOf(
            Exercise(0, 0, 0, true, false, 0, false, null, null)
        )
    )
    val savedExercises: LiveData<MutableList<Exercise>>
        get() = _savedExercises


    private var _addToSessionExercises = MutableLiveData<MutableList<Exercise>>()
    val addToSessionExercises: LiveData<MutableList<Exercise>>

        get() = _addToSessionExercises


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


    private val _selectedExerciseTitle = MutableLiveData<String>()
    val selectedExerciseTitle: LiveData<String>
        get() = _selectedExerciseTitle



    fun filterExercisesByBodypart(bodypart: String, context: Context) {
        viewModelScope.launch {
            val filteredExercises = allExercisesByBodyparts.filter { context.getString(it.bodyPart) == bodypart }
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


    fun sortExercisesByAlphabet(bodypart: String, sort: Boolean, context: Context) {
        viewModelScope.launch {
            val filteredExercises = exercisesByBodyparts.value?.filter { context.getString(it.bodyPart) == bodypart }
            val sortedExercises = if (sort) {
                filteredExercises?.sortedByDescending { it.stringRessourceText }
            } else {
                filteredExercises?.sortedBy { it.stringRessourceText }
            }
            _exercisesByBodyparts.value = sortedExercises
        }
    }

    fun filterAllExercisesByBodypart(bodypart: String,context: Context) {
        viewModelScope.launch {
            val filteredExercises = allExercisesByBodyparts.filter { context.getString(it.bodyPart) == bodypart }
            _listOfAllExercises.value = filteredExercises
        }
    }

    fun sortAllExercisesByAlphabet(sort: Boolean) {
        viewModelScope.launch {
            val filteredExercises = listOfAllExercises.value
            val sortedExercises = if (sort) {
                filteredExercises?.sortedByDescending { it.stringRessourceText }
            } else {
                filteredExercises?.sortedBy { it.stringRessourceText }
            }
            _listOfAllExercises.value = sortedExercises
        }
    }

    fun filterAllExercisesByTitle(userInput: String, context: Context) {
        viewModelScope.launch {
            val filteredExercises = _listOfAllExercises.value?.filter {
                val xmlValue = context.getString(it.stringRessourceTitle)
                xmlValue.contains(userInput, ignoreCase = true)
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _listOfAllExercises.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts()
                }
            }
        }
    }

    fun filterAllExercisesByBodyweight(context: Context) {
        viewModelScope.launch {
            val filteredExercises = _listOfAllExercises.value?.filter {
                !context.getString(it.stringRessourceTitle).contains("-")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _listOfAllExercises.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts()
                }
            }
        }
    }

    fun filterAllExercisesByLongDumbbell(context: Context) {
        viewModelScope.launch {
            val filteredExercises = _listOfAllExercises.value?.filter {
                context.getString(it.stringRessourceTitle)
                    .contains("LH") || context.getString(it.stringRessourceTitle).contains("SZ")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _listOfAllExercises.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts()
                }
            }
        }
    }

    fun filterAllExercisesByShortDumbbell(context: Context) {
        viewModelScope.launch {
            val filteredExercises = _listOfAllExercises.value?.filter {
                context.getString(it.stringRessourceTitle).contains("KH")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _listOfAllExercises.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts()
                }
            }
        }
    }


    fun filterAllExercisesByTwoSelections(
        context: Context,
        imageButton: ImageButton,
        textButton: Button,
    ) {
        val textButtonName = context.resources.getResourceEntryName(textButton.id)
        val bodyPart = when (textButtonName) {
            "sec0_armsBtn" -> context.resources.getString(R.string.bpArme)
            "sec0_absBtn" -> context.resources.getString(R.string.bpBauch)
            "sec0_legsBtn" -> context.resources.getString(R.string.bpBeine)
            "sec0_chestBtn" -> context.resources.getString(R.string.bpBrust)
            "sec0_backBtn" -> context.resources.getString(R.string.bpRücken)
            "sec0_shoulderBtn" -> context.resources.getString(R.string.bpSchulter)
            else -> return // Wenn keine Übereinstimmung gefunden wurde, die Funktion verlassen
        }

        // Filtern der Übungen basierend auf den ausgewählten Kriterien
        val filteredExercises = _listOfAllExercises.value?.filter { exercise ->
            context.getString(exercise.bodyPart) == bodyPart &&
                    when (imageButton.id) {
                        R.id.sec1_short_dumbell_Btn -> context.getString(exercise.stringRessourceTitle)
                            .contains("KH")

                        R.id.sec1_long_dumbell_Btn -> context.getString(exercise.stringRessourceTitle)
                            .contains("LH") || context.getString(exercise.stringRessourceTitle)
                            .contains("SZ")

                        R.id.sec1_own_bodyweight_Btn -> !context.getString(exercise.stringRessourceTitle)
                            .contains("-")

                        else -> true // Wenn kein Bildschaltfläche ausgewählt wurde, wird die Übung nicht gefiltert
                    }
        }


        _listOfAllExercises.value = filteredExercises
    }


    fun retrieveExercisesByBodyparts() {
        _listOfAllExercises.value = allExercisesByBodyparts
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
                    retrieveExercisesByBodyparts(bodypart,context)
                }
            }
        }
    }


    fun filterExercisesByBodyweight(bodypart: String, context: Context) {
        viewModelScope.launch {
            val filteredExercises = _exercisesByBodyparts.value?.filter {
                !context.getString(it.stringRessourceTitle).contains("-")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _exercisesByBodyparts.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts(bodypart,context)
                }
            }
        }
    }

    fun filterExercisesByLongDumbbell(bodypart: String, context: Context) {
        viewModelScope.launch {
            val filteredExercises = _exercisesByBodyparts.value?.filter {
                context.getString(it.stringRessourceTitle)
                    .contains("LH") || context.getString(it.stringRessourceTitle).contains("SZ")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _exercisesByBodyparts.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts(bodypart,context)
                }
            }
        }
    }


    fun filterExercisesByShortDumbbell(bodypart: String, context: Context) {
        viewModelScope.launch {
            val filteredExercises = _exercisesByBodyparts.value?.filter {
                context.getString(it.stringRessourceTitle).contains("KH")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _exercisesByBodyparts.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts(bodypart,context)
                }
            }
        }
    }


    fun filterExercisesByVideo(bodypart: String,context: Context) {
        viewModelScope.launch {
            val filteredExercises = _exercisesByBodyparts.value?.filter {
                it.video != null
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _exercisesByBodyparts.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts(bodypart,context)
                }
            }
        }
    }


    fun filterExercisesByNoVideo(bodypart: String,context: Context) {
        viewModelScope.launch {
            val filteredExercises = _exercisesByBodyparts.value?.filter {
                it.video == null
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _exercisesByBodyparts.value = filteredExercises
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveExercisesByBodyparts(bodypart,context)
                }
            }
        }
    }


    fun retrieveExercisesByBodyparts(bodypart: String, context: Context) {
        _exercisesByBodyparts.value = allExercisesByBodyparts.filter { context.getString(it.bodyPart) == bodypart }
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


    fun getBodypartCategoryTitle(bodypart: String) {
        _selectedExerciseTitle.value = bodypart
    }


    fun addToNewWorkout(exercise: Exercise) {
        exercise.addedToSession = true
        _addToSessionExercises.value = mutableListOf(exercise)
    }

    fun setOriginalList(exercises: List<Exercise>, bodypart: String, context: Context) {
        exercises.filter { context.getString(it.bodyPart) == bodypart }
        _exercisesByBodyparts.value = exercises
    }


    /*EN:
    *This code is used to update the save status of an exercise
    *and to manage the list of saved exercises accordingly.
    *The function expects two parameters that indicate whether the exercises should be saved
    *or not. With `val updatedExercises = _savedExercises.value?: mutableListOf()`:
    *the list of updated exercises is retrieved from the LiveData object `_savedExercises`.
    *If this LiveData object has not yet been initialized, an empty list is created.
    *In the `if (saved) { ... } else { ... }` statements are checked,
    *whether the exercise should be saved (`saved` is true) or not (false).
    *Depending on this, the exercise is either added to the list or removed from the list.
    *In the block for the case that the exercise is to be saved (`saved == true`),
    *the exercise `exercise` is added to the list of updated exercises (`updatedExercises.add(exercise)`).
    *In the block for the case that the exercise should not be saved (`saved == false`),
    *The exercise `exercise` is removed from the list of updated exercises (`updatedExercises.remove(exercise)`).
    * With `_savedExercises.value = updatedExercises`: the list of updated exercises is finally added back to the LiveData object.
    * exercises is reassigned to the LiveData object `_savedExercises` so that all observers can be informed of the changes.
    * To summarize, this function is used,
    * to update the save status of an exercise and manage the list of saved exercises,
    * creating corresponding log entries to track the process.
*/


    /*DE:
    *Dieser Code dient dazu, den Speicherstatus einer Übung
    *zu aktualisieren und die Liste der gespeicherten Übungen entsprechend zu verwalten.
    *Die Funktion erwarte zwei Parameter, die angeben, ob die Übungen gespeichert werden soll
    *oder nicht. Mit `val updatedExercises = _savedExercises.value?: mutableListOf()`:
    *wird die Liste der aktualisierten Übungen aus dem LiveData-Objekt `_savedExercises` geholt.
    *Falls dieses LiveData-Objekt noch nicht initialisiert wurde, wird eine leere Liste erstellt.
    *In den `if (saved) { ... } else { ... }` Anweisungen wird überprüft,
    *ob die Übung gespeichert werden soll (`saved` ist true) oder nicht (false).
    *Abhängig davon wird entweder die Übung zur Liste hinzugefügt oder aus der Liste entfernt.
    *Im Block für den Fall, dass die Übung gespeichert werden soll (`saved == true`),
    *wird die Übung `exercise` der Liste der aktualisierten Übungen hinzugefügt (`updatedExercises.add(exercise)`).
    *Im Block für den Fall, dass die Übung nicht gespeichert werden soll (`saved == false`),
    *wird die Übung `exercise` aus der Liste der aktualisierten Übungen entfernt (`updatedExercises.remove(exercise)`).
    * Mit `_savedExercises.value = updatedExercises`: wird schließlich wird die Liste der aktualisierten
    * Übungen wieder dem LiveData-Objekt `_savedExercises` zugewiesen, damit alle Observer
    * über die Änderungen informiert werden können.Zusammenfassend wird diese Funktion verwendet,
    * um den Speicherstatus einer Übung zu aktualisieren und die Liste der gespeicherten Übungen zu verwalten,
    * wobei entsprechende Protokolleinträge erstellt werden, um den Vorgang zu verfolgen.*/

    fun isExerciseSaved(saved: Boolean, exercise: Exercise) {
        val updatedExercises = _savedExercises.value ?: mutableListOf()

        if (saved) {
            updatedExercises.add(exercise)
            var tag = "Fehler"
            Log.e(
                tag,
                "Übung wirdd gespeichert!!:${exercise} Zustand: ${saved}. Die Liste enthält: ${updatedExercises.size}"
            )
        } else {
            updatedExercises.remove(exercise)
            var tag = "Fehler"
            Log.e(tag, "Übung wird entfernt!!:${exercise} Zustand: ${saved} ${updatedExercises}")
        }

        _savedExercises.value = updatedExercises
    }

    fun savedInWorkoutSession(addedExercise: Boolean, exercise: Exercise) {
        val updatedExercises = addToSessionExercises.value ?: mutableListOf()

        if (addedExercise) {
            updatedExercises.add(exercise)
            var tag = "Radiocheck??"
            Log.e(
                tag,
                "Übung wirdd gespeichert!!:${exercise} Zustand: ${addedExercise}. Die Liste enthält: ${updatedExercises.size}"
            )
        } else {
            updatedExercises.remove(exercise)
            var tag = "Radiocheck??"
            Log.e(
                tag,
                "Übung wird entfernt!!:${exercise} Zustand: ${addedExercise}. Die Liste enthält: ${updatedExercises.size}"
            )
        }

        _addToSessionExercises.value = updatedExercises
    }

    fun resetSavedInWorkoutSession(addedToSessionExercises: MutableList<Exercise>) {
        addedToSessionExercises.forEach { it.addedToSession = false }
        addedToSessionExercises.removeAll { it.addedToSession == false }
        var tag = "Liste zurücksetzen"
        Log.i(tag, "Liste wird zurückgesetzt ! ${addToSessionExercises.value}")

    }



    fun navigateSelectedExercises(exercise: Exercise) {
        _selectedExercise.value = exercise
    }

}












