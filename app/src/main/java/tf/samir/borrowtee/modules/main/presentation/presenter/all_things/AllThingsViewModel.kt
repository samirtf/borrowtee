package tf.samir.borrowtee.modules.main.presentation.presenter.all_things

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import tf.samir.borrowtee.modules.main.domain.repositories.ThingRepository
import tf.samir.borrowtee.modules.main.infrastructure.repositories.InMemoryThingRepositoryMock
import tf.samir.borrowtee.modules.main.utils.toRecyclerItem

class AllThingsViewModel() : ViewModel() {

    private val repository: ThingRepository = InMemoryThingRepositoryMock()

    val things = liveData {
        repository.allThings.map {
                t -> t.map { it.toRecyclerItem() }
        }.collect { emit(it) }
    }

}


