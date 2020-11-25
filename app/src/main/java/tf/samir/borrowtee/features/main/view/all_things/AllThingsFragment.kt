package tf.samir.borrowtee.features.main.view.all_things

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_things.*
import tf.samir.borrowtee.databinding.FragmentAllThingsBinding
import tf.samir.borrowtee.features.main.presentation.presenter.all_things.*
import tf.samir.core.base.HyperFragment
import timber.log.Timber

@AndroidEntryPoint
class AllThingsFragment :
    HyperFragment<AllThingsViewState, AllThingsViewEffect, AllThingsViewEvent, AllThingsViewModel>() {

    companion object {
        const val TAG = "AllThingsFragment"
    }

    private var _binding: ViewDataBinding? = null
    private val binding: ViewDataBinding
        get() = _binding!!

    override val viewModel: AllThingsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = inflateViewDataBinding(inflater, container)
        return binding.root
    }

    private fun inflateViewDataBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean = false
    ): ViewDataBinding {
        return FragmentAllThingsBinding.inflate(inflater, container, attachToRoot)
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
                it.fab.setOnClickListener {
                    viewModel.handle(AllThingsViewEvent.FabClicked)
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    override fun renderViewState(viewState: AllThingsViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                progressBar.visibility = View.INVISIBLE
            }
            is FetchStatus.NotFetched -> {
                viewModel.handle(AllThingsViewEvent.FetchAllThings)
                progressBar.visibility = View.INVISIBLE
            }
            is FetchStatus.Fetching -> {
                progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun renderViewEffect(viewEffect: AllThingsViewEffect) {
        when (viewEffect) {
            is AllThingsViewEffect.ShowToast -> {
                Timber.tag(TAG).i("ShowToast")
                Toast.makeText(context, viewEffect.message, Toast.LENGTH_SHORT).show()
            }
            AllThingsViewEffect.NavigateToCreateBorrowingPage -> {
                Timber.tag(TAG).i("NavigateToCreateBorrowing")
                navigateToCreateBorrowing()
            }
        }
    }


    private fun navigateToCreateBorrowing() {
        this.findNavController().navigate(
            AllThingsFragmentDirections
                .actionNavigationHomeToCreateBorrowingActivity()
        )
    }

}