package tf.samir.coreexample

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_example.*
import tf.samir.core.base.AndroidHyperActivity


class ExampleActivityAndroid : AndroidHyperActivity<ExampleViewState, ExampleViewEffect, ExampleViewEvent, ExampleViewModel>(){

    override val viewModel: ExampleViewModel by viewModels()

    private val nameRvAdapter by lazy {
        NameRvAdapter {
            viewModel.handle(ExampleViewEvent.ExampleItemClicked(it.tag as String))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_example)

        rvNewsHome.adapter = nameRvAdapter

        srlNewsHome.setOnRefreshListener {
            viewModel.handle(ExampleViewEvent.OnSwipeRefresh)
        }

        fabStar.setOnClickListener {
            viewModel.handle(ExampleViewEvent.FabClicked)
        }
    }

    override fun renderViewState(viewState: ExampleViewState) {

        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                srlNewsHome.isRefreshing = false
            }
            is FetchStatus.NotFetched -> {
                viewModel.handle(ExampleViewEvent.FetchExampleItems)
                srlNewsHome.isRefreshing = false
            }
            is FetchStatus.Fetching -> {
                srlNewsHome.isRefreshing = true
            }
        }
        nameRvAdapter.submitList(viewState.itemsList)
    }

    override fun renderViewEffect(viewEffect: ExampleViewEffect) {
        when (viewEffect) {
            is ExampleViewEffect.ShowSnackbar -> {
                Snackbar.make(coordinatorLayoutRoot, viewEffect.message, Snackbar.LENGTH_SHORT).show()
            }
            is ExampleViewEffect.ShowToast -> {
                toast(viewEffect.message, Toast.LENGTH_SHORT)
            }
        }
    }


}
