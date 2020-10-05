package tf.samir.borrowtee.modules.main.presentation.presenter.all_things

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tf.samir.borrowtee.modules.main.domain.entities.Thing

class AllThingsViewModel : ViewModel() {

    private val things = MutableLiveData<Thing>()
    private val _text = MutableLiveData<String>().apply {
        value = "This is All Things Fragment"
    }
    val text: LiveData<String> = _text

    init {
        loadData()
    }

    private fun loadData() {
        throw NotImplementedError("fetch data from repository")
    }
}