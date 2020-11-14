package tf.samir.borrowtee.modules.main.view.all_things

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tf.samir.borrowtee.databinding.FragmentAllThingsBinding
import tf.samir.borrowtee.modules.main.presentation.presenter.all_things.AllThingsViewModel
import timber.log.Timber

@AndroidEntryPoint
class AllThingsFragment : Fragment() {

    companion object {
        const val TAG = "AllThingsFragment"
    }
    private var _binding: FragmentAllThingsBinding? = null
    private val binding get() = _binding!!

    private val allThingsViewModel by viewModels<AllThingsViewModel>()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        _binding =  FragmentAllThingsBinding.inflate(inflater, container, false)
            .also {
                it.viewModel = allThingsViewModel
                it.lifecycleOwner = viewLifecycleOwner
            }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        allThingsViewModel.event.observe(viewLifecycleOwner, Observer {
            when (it) {
                AllThingsViewModel.Companion.Action.Idle -> Timber.tag(TAG).i("Idle")
                AllThingsViewModel.Companion.Action.NavigateToCreateBorrowing -> {
                    Timber.tag(TAG).i("NavigateToCreateBorrowing")
                    allThingsViewModel.onActionComplete()
                    navigateToCreateBorrowing()
                }
            }
        })
    }

    private fun navigateToCreateBorrowing() {
        Toast.makeText(requireContext(), "navigating to create borrowing",
            Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}