package tf.samir.borrowtee.modules.main.presentation.presenter.all_things

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AllThingsViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is All Things Fragment"
    }
    val text: LiveData<String> = _text
}