package tf.samir.coreexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import tf.samir.core.base.AndroidHyperActivity
import tf.samir.coreexample.databinding.ActivityExampleBinding


class ExampleAndroidActivity : AndroidHyperActivity<ExampleViewState, ExampleViewEffect, ExampleViewEvent, ExampleViewModel>(){

    private lateinit var binding: ActivityExampleBinding
    override val viewModel: ExampleViewModel by viewModels()

    private val nameRvAdapter by lazy {
        NameRvAdapter {
            viewModel.handle(ExampleViewEvent.ExampleItemClicked(it.tag as String))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityExampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvNewsHome.adapter = nameRvAdapter

        binding.srlNewsHome.setOnRefreshListener {
            viewModel.handle(ExampleViewEvent.OnSwipeRefresh)
        }

        binding.fabStar.setOnClickListener {
            viewModel.handle(ExampleViewEvent.FabClicked)
        }
    }

    override fun renderViewState(viewState: ExampleViewState) {

        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                binding.srlNewsHome.isRefreshing = false
            }
            is FetchStatus.NotFetched -> {
                viewModel.handle(ExampleViewEvent.FetchExampleItems)
                binding.srlNewsHome.isRefreshing = false
            }
            is FetchStatus.Fetching -> {
                binding.srlNewsHome.isRefreshing = true
            }
        }
        nameRvAdapter.submitList(viewState.itemsList)
    }

    override fun renderViewEffect(viewEffect: ExampleViewEffect) {
        when (viewEffect) {
            is ExampleViewEffect.ShowSnackbar -> {
                Snackbar.make(binding.coordinatorLayoutRoot, viewEffect.message, Snackbar.LENGTH_SHORT).show()
            }
            is ExampleViewEffect.ShowToast -> {
                toast(viewEffect.message, Toast.LENGTH_SHORT)
            }
        }
    }


}
