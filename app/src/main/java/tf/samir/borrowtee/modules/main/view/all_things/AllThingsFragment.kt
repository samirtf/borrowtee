package tf.samir.borrowtee.modules.main.view.all_things

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tf.samir.borrowtee.databinding.FragmentAllThingsBinding
import tf.samir.borrowtee.modules.main.presentation.presenter.all_things.AllThingsViewModel

class AllThingsFragment : Fragment() {

    private var _binding: FragmentAllThingsBinding? = null
    private val binding get() = _binding!!
    private val allThingsViewModel: AllThingsViewModel by viewModels()

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

    override fun onStart() {
        super.onStart()
        allThingsViewModel.loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}