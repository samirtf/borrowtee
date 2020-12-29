package tf.samir.borrowtee.features.main.view.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.FragmentDashboardBinding
import tf.samir.borrowtee.features.main.presentation.presenter.dashboard.DashboardViewModel

class DashboardFragment : Fragment(R.layout.fragment_dashboard) {

    private var _binding: FragmentDashboardBinding? = null
    private lateinit var dashboardViewModel: DashboardViewModel

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