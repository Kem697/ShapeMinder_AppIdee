package de.kem697.shapeminder.ui.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import de.kem697.shapeminder.R
import kotlinx.coroutines.launch
import de.kem697.shapeminder.model.data.local.getTrainingDatabase
import de.kem697.shapeminder.model.data.local.model.myTraining.Exercise
import de.kem697.shapeminder.model.data.local.model.myTraining.Performance
import de.kem697.shapeminder.model.data.local.model.myTraining.TrainingsSession


class TrainingsessionViewModel (application: Application) : AndroidViewModel(application) {


    private var trainingDatabase = getTrainingDatabase(application)
    private val repository =
        de.kem697.shapeminder.model.data.local.LocalRepository(trainingDatabase)

    private var trainingSessions = repository.trainingSessionList



    private var _savedTrainingSessions = trainingSessions

    val savedTrainingsSessions: LiveData<List<TrainingsSession>>
        get() = _savedTrainingSessions




    private var _selectedTraininingssession = MutableLiveData<TrainingsSession>()

    val selectedTraininingssession: MutableLiveData<TrainingsSession>

        get() = _selectedTraininingssession


    /*EN:
    * These LiveData and Methods are related to the edit training function
    * of my app.*/

    /*DE:
    * Diese Funktionen und Methoden beziehen sich auf die App Funktion
    * zur Bearbeitung des Trainingsplans */

    private val _remainExercisesForAddInSession = MutableLiveData<List<Exercise>>()

    val remainExercisesForAddInSession: MutableLiveData<List<Exercise>>

        get() = _remainExercisesForAddInSession


    fun excludeExercises(trainingsSession: TrainingsSession) : MutableList<Exercise> {
        val tag = "Apply"
        val list = mutableListOf<Exercise>()
        val allExercises = repository.loadExercisesByBodypart().toMutableList() ?: mutableListOf()
        val sessionExercises = trainingsSession.trainingsSession

        val matchingExercises = allExercises.filter { exercise ->
            sessionExercises.any { it.stringRessourceTitle == exercise.stringRessourceTitle }
        }.toMutableList()


        println("$tag ${matchingExercises.size}")

        allExercises.removeAll(matchingExercises)

        list.addAll(allExercises)

        println("$tag ${sessionExercises.size}")


        println("$tag ${allExercises.size}")

        // Den Wert von remainExercisesForAddInSession aktualisieren
        _remainExercisesForAddInSession.value = list

        println("$tag ${_remainExercisesForAddInSession.value!!.size}")
        return list
    }


    fun sortRemainExercisesByAlphabet(sort: Boolean, context: Context) {
        viewModelScope.launch {
            val filteredExercises = remainExercisesForAddInSession.value
            val sortedExercises = if (sort) {
                filteredExercises?.sortedByDescending { context.getString(it.stringRessourceTitle) }
            } else {
                filteredExercises?.sortedBy { context.getString(it.stringRessourceTitle) }
            }
            _remainExercisesForAddInSession.value = sortedExercises?: mutableListOf()
        }
    }

    fun filterRemainExercisesByTitle(userInput: String, context: Context) {
        viewModelScope.launch {
            val filteredExercises = _remainExercisesForAddInSession.value?.filter {
                val xmlValue = context.getString(it.stringRessourceTitle)
                xmlValue.contains(userInput, ignoreCase = true)
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _remainExercisesForAddInSession.value = filteredExercises?: mutableListOf()
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveRemainExercisesByBodyparts()
                }
            }
        }
    }

    fun filterRemainExercisesByBodyweight(context: Context) {
        viewModelScope.launch {
            val filteredExercises = _remainExercisesForAddInSession.value?.filter {
                !context.getString(it.stringRessourceTitle).contains("-")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _remainExercisesForAddInSession.value = filteredExercises?: mutableListOf()
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveRemainExercisesByBodyparts()
                }
            }
        }
    }

    fun filterRemainExercisesByLongDumbbell(context: Context) {
        viewModelScope.launch {
            val filteredExercises = _remainExercisesForAddInSession.value?.filter {
                context.getString(it.stringRessourceTitle)
                    .contains("LH") || context.getString(it.stringRessourceTitle).contains("SZ")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _remainExercisesForAddInSession.value = filteredExercises?: mutableListOf()
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveRemainExercisesByBodyparts()
                }
            }
        }
    }

    fun filterRemainExercisesByShortDumbbell(context: Context) {
        viewModelScope.launch {
            val filteredExercises = _remainExercisesForAddInSession.value?.filter {
                context.getString(it.stringRessourceTitle).contains("KH")
            }
            if (filteredExercises != null) {
                if (filteredExercises.isNotEmpty()) {
                    _remainExercisesForAddInSession.value = filteredExercises?: mutableListOf()
                    var tag4 = "Filter in ViewModel??"
                    Log.e(tag4, "Wurde gefiltert?: $filteredExercises")
                } else {
                    retrieveRemainExercisesByBodyparts()
                }
            }
        }
    }

    fun filterRemainExercisesByTwoSelections(
        context: Context,
        imageButton: ImageButton,
        textButton: Button,
    ) {
        val textButtonName = context.resources.getResourceEntryName(textButton.id)
        val bodyPart = when (textButtonName) {
            "sec0_armsBtn" -> context.resources.getString(R.string.bpBiceps)
            "sec0_absBtn" -> context.resources.getString(R.string.bpBauch)
            "sec0_legsBtn" -> context.resources.getString(R.string.bpBeine)
            "sec0_chestBtn" -> context.resources.getString(R.string.bpBrust)
            "sec0_backBtn" -> context.resources.getString(R.string.bpRücken)
            "sec0_shoulderBtn" -> context.resources.getString(R.string.bpSchulter)
            else -> return // Wenn keine Übereinstimmung gefunden wurde, die Funktion verlassen
        }

        // Filtern der Übungen basierend auf den ausgewählten Kriterien
        val filteredExercises = _remainExercisesForAddInSession.value?.filter { exercise ->
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


        _remainExercisesForAddInSession.value = filteredExercises?: mutableListOf()
    }




    fun filterRemainExercisesByBodypart(bodypart: String,context: Context) {
        viewModelScope.launch {
            val filteredExercises = repository.loadExercisesByBodypart().filter { context.getString(it.bodyPart) == bodypart }
            _remainExercisesForAddInSession.value = filteredExercises
        }
    }




    fun retrieveRemainExercisesByBodyparts() {
        _remainExercisesForAddInSession.value = excludeExercises(selectedTraininingssession.value!!)
    }


    fun getCurrentTrainingsession(currentSession: TrainingsSession) {
        _selectedTraininingssession.value = currentSession
    }

    fun editTrainingPerformance(getElementIndexPosition: Int, context: Context, userInput: String,propertyName: Int, trainingsSession: TrainingsSession) {
        val performance = trainingsSession.performance[getElementIndexPosition]
        val propertyNameString = context.resources.getResourceEntryName(propertyName)
        when (propertyNameString) {
            "editReps"-> performance.reps = userInput
            "editSets" -> performance.sets = userInput
            "editWeight"-> performance.weight = userInput
            else -> {
                // Fügen Sie hier eine Behandlung für den Fall hinzu, dass der propertyName nicht erkannt wird
                // Möglicherweise möchten Sie eine Benachrichtigung anzeigen oder eine Standardaktion ausführen
            }
        }
        updateTrainingsession(trainingsSession)
    }



    fun deleteWorkoutInEditSession(addedExercise: Boolean, exercise: Exercise) {
        val updatedSession = selectedTraininingssession.value?.trainingsSession ?: mutableListOf()
        val updatedPerformance = selectedTraininingssession.value?.performance ?: mutableListOf()

        if (addedExercise) {
            updatedSession.add(exercise)
            updatedPerformance.add(Performance())
            var tag = "Radiocheck??"
            Log.e(
                tag,
                "Übung wirdd gespeichert!!:${exercise} Zustand: ${addedExercise}. Die Liste enthält: ${updatedSession.size}"
            )
        } else {
            val index = updatedSession.indexOf(exercise)
            if (updatedPerformance.isNotEmpty() && index != -1){
                updatedSession.remove(exercise)
                updatedPerformance.removeAt(index)
            }
            var tag = "Radiocheck??"
            Log.e(
                tag,
                "Übung wird entfernt!!:${exercise} Zustand: ${addedExercise}. Die Liste enthält: ${updatedSession.size}"
            )
        }

        _selectedTraininingssession.value?.trainingsSession = updatedSession
        _selectedTraininingssession.value?.performance = updatedPerformance
    }


    init {
        setUpDefaultTrainingsessions()
    }

    /*EN:
    * These functions are related to issues in the training session database. */

    fun setUpDefaultTrainingsessions() {
        viewModelScope.launch {
            repository.insertNewTrainingSession(
                TrainingsSession(
                    1,
                    trainingsSession = mutableListOf(), performance = mutableListOf()
                )
            )
        }
    }

    fun deleteTrainingsession(currentSession: TrainingsSession) {
        viewModelScope.launch {
            repository.deleteTrainingsession(currentSession)
        }
    }

    fun updateTrainingsession(currentSession: TrainingsSession) {
        viewModelScope.launch {
            repository.updateTrainingsession(currentSession)
        }
    }


    fun insertNewTrainingssession(newTrainingsSession: TrainingsSession) {
        viewModelScope.launch {
            repository.insertNewTrainingSession(newTrainingsSession)
        }
    }


}