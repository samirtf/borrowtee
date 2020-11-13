package tf.samir.borrowtee.modules.main.presentation.presenter.all_things

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import tf.samir.borrowtee.modules.main.domain.repositories.ThingRepository
import tf.samir.borrowtee.modules.main.utils.toRecyclerItem


class AllThingsViewModel @ViewModelInject constructor(private val repository: ThingRepository) : ViewModel() {

    val things = liveData {
        repository.allThings.map { thingList -> thingList.map { it.toRecyclerItem() } }
            .collect { emit(it) }
    }

}


