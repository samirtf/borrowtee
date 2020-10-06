package tf.samir.borrowtee.modules.main.presentation.presenter.all_things

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import tf.samir.borrowtee.modules.main.domain.repositories.ThingsRepository
import tf.samir.borrowtee.modules.main.infrastructure.repositories.InMemoryThingsRepositoryMock
import tf.samir.borrowtee.modules.main.utils.toRecyclerItem
import tf.samir.borrowtee.viewbase.RecyclerItem

class AllThingsViewModel() : ViewModel() {

    private val repository: ThingsRepository = InMemoryThingsRepositoryMock()

    var things = MutableLiveData<List<RecyclerItem>>(emptyList())
        private set

    init {
        loadData()
    }

    private fun loadData() {
        things.value = repository.getThingsAtHome().map { it.toRecyclerItem() }
        things.value
    }
}