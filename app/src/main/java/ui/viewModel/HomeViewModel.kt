package ui.viewModel

import adapter.ItemAdapter
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import data.AppRepository
import kotlinx.coroutines.launch
import model.Content

class HomeViewModel: ViewModel() {

    private val repository = AppRepository()
    private val allContent = repository.content
    private val allExercises = repository.exercises
    private val allBodyparts = repository.bodyParts
    private var allExercisesByBodyparts = repository.exercisesByBodyparts


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
    val selectedExercise : LiveData<Content>
        get() = _selectedExercise


    private var _selectedContent = MutableLiveData(allContent[index])
    val selectedContent : LiveData<Content>
        get() = _selectedContent



    private var _selectedExercisesByBodypart = MutableLiveData(allExercisesByBodyparts[index])
    val selectedExercisesByBodypart : LiveData<Content>
        get() = _selectedExercisesByBodypart




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
    * Aktueller Stand: Sortierfunktion funktioniert nicht.
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


//    fun filterExerciseByUserInput(userInput: String): List<Content> {
//        viewModelScope.launch {
//            val getExercises = allExercisesByBodyparts.filter { it.stringRessourceTitle.toString() == userInput }
//            val searchedExercise = if (getExercises.isNullOrEmpty()){
//                _exercisesByBodyparts.value = allExercisesByBodyparts
//            } else{
//                _exercisesByBodyparts.value = getExercises
//            }
//            _exercisesByBodyparts.value = searchedExercise
//
//        }
//
//    }






    fun filterExercisesByTitle(userInput: String): List<Content> {
        viewModelScope.launch {
            val searchedExercise = allExercisesByBodyparts.filter { it.stringRessourceTitle.toString() == userInput }
            _exercisesByBodyparts.value = searchedExercise
        }
        return _exercisesByBodyparts.value!!
    }


    fun resetFilter() {
        _exercisesByBodyparts.value = allExercisesByBodyparts
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




}