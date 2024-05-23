package de.kem697.shapeminder.ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import de.kem697.shapeminder.model.data.local.getTrainingDatabase
import de.kem697.shapeminder.model.data.local.model.myTraining.Content

class ContentViewModel(application: Application) : AndroidViewModel(application) {

    private var trainingDatabase = getTrainingDatabase(application)
    private val repository =
        de.kem697.shapeminder.model.data.local.LocalRepository(trainingDatabase)
    private val allContent = repository.content


    var index = 0



    private var _contents = MutableLiveData(allContent)
    val contents: LiveData<List<Content>>
        get() = _contents



    private var _selectedContent = MutableLiveData(allContent[index])
    val selectedExercise: LiveData<Content>
        get() = _selectedContent


    fun navigateContentDetailView(content: Content) {
        _selectedContent.value = content
    }






}


