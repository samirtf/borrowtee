package tf.samir.borrowtee.features.main.view.dashboard

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.FragmentDashboardBinding
import tf.samir.borrowtee.features.main.presentation.presenter.dashboard.DashboardViewModel

@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private val dashboardViewModel: DashboardViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDashboardBinding.bind(view)
        _binding = binding

        dashboardViewModel.text.observe(viewLifecycleOwner, {
            binding.textDashboard.text = it
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}