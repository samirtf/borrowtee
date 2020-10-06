package tf.samir.borrowtee.modules.main.view.all_things

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tf.samir.borrowtee.R
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
        val view = binding.root
        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}