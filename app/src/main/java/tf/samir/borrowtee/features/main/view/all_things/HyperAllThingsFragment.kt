//package tf.samir.borrowtee.features.main.view.all_things
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.databinding.ViewDataBinding
//import androidx.fragment.app.viewModels
//import androidx.lifecycle.Observer
//import androidx.navigation.fragment.findNavController
//import dagger.hilt.android.AndroidEntryPoint
//import tf.samir.borrowtee.databinding.FragmentAllThingsBinding
//import tf.samir.borrowtee.features.main.presentation.presenter.all_things.AllThingsViewEffect
//import tf.samir.borrowtee.features.main.presentation.presenter.all_things.AllThingsViewEvent
//import tf.samir.borrowtee.features.main.presentation.presenter.all_things.AllThingsViewModel
//import tf.samir.borrowtee.features.main.presentation.presenter.all_things.AllThingsViewState
//import tf.samir.core.base.HyperFragment
//import timber.log.Timber
//
//@AndroidEntryPoint
//class HyperAllThingsFragment : HyperFragment<AllThingsViewState, AllThingsViewEffect, AllThingsViewEvent, AllThingsViewModel>() {
//
//    companion object {
//        const val TAG = "AllThingsFragment"
//    }
//    private var _binding: ViewDataBinding? = null
//    private val binding: ViewDataBinding
//        get() = _binding!!
//
//    private val allThingsViewModel by viewModels<AllThingsViewModel>()
//
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//
//        _binding = inflateViewDataBinding(inflater, container, false)
//        return binding.root
//    }
//
//    private fun inflateViewDataBinding(inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean = false): ViewDataBinding {
//        return FragmentAllThingsBinding.inflate(inflater, container, attachToRoot)
//            .also {
//                it.viewModel = allThingsViewModel
//                it.lifecycleOwner = viewLifecycleOwner
//            }
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        allThingsViewModel.event.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                AllThingsViewModel.Companion.Action.Idle -> Timber.tag(TAG).i("Idle")
//                AllThingsViewModel.Companion.Action.NavigateToCreateBorrowing -> {
//                    Timber.tag(TAG).i("NavigateToCreateBorrowing")
//                    allThingsViewModel.onActionComplete()
//                    navigateToCreateBorrowing()
//                }
//                else -> {}
//            }
//        })
//    }
//
//    private fun navigateToCreateBorrowing() {
//        this.findNavController().navigate(AllThingsFragmentDirections
//            .actionNavigationHomeToCreateBorrowingActivity())
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//}