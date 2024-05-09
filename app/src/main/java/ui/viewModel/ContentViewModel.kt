package ui.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import model.data.local.LocalRepository
import model.data.local.getTrainingDatabase
import model.data.local.model.myTraining.Content
import model.data.local.model.myTraining.Exercise

class ContentViewModel(application: Application) : AndroidViewModel(application) {

    private var trainingDatabase = getTrainingDatabase(application)
    private val repository = LocalRepository(trainingDatabase)
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


