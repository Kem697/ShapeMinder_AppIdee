package ui

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



    /*Diese Methode dient dazu, um meinen Datensatz im Nachhinein mit
    * eine Eigenschaft zu überprüfen und sie nach dem dem übergebenen Argument zu filtern.
    * Die Methode wird anschließend in meinem GridAdapter aufgerufen.*/
    fun filterExercisesByBodypart(bodypart: String) {
        viewModelScope.launch {
            val filteredExercises = allExercisesByBodyparts.filter { it.bodyPart == bodypart }
            _exercisesByBodyparts.value = filteredExercises
        }
    }






    fun navigateDetailView(content: Content) {
        _selectedContent.value = content
    }


    fun navigateToExerciseList(selectedExercise: Content) {
        _selectedExercisesByBodypart.value = selectedExercise
    }

}