package tf.samir.borrowtee.modules.main.presentation.presenter.all_things

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tf.samir.borrowtee.modules.main.domain.repositories.ThingRepository
import tf.samir.borrowtee.modules.main.infrastructure.repositories.InMemoryThingRepositoryMock
import tf.samir.borrowtee.modules.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem

class AllThingsViewModel() : ViewModel() {

    private val repository: ThingRepository = InMemoryThingRepositoryMock()

    var things = MutableLiveData<List<RecyclerItem>>(emptyList())
        private set

    // Called from Fragment's onStart()
    fun loadData() {
        things.value = repository.getThingsAtHome()
            .map { it.toRecyclerItem() }
    }
}
