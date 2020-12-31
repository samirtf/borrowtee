package tf.samir.borrowtee.features.main.view.notifications

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.FragmentNotificationsBinding
import tf.samir.borrowtee.features.main.presentation.presenter.notifications.NotificationsViewModel

class NotificationsFragment : Fragment(R.layout.fragment_notifications) {

    private val notificationsViewModel: NotificationsViewModel by viewModels()

    private var _binding: FragmentNotificationsBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNotificationsBinding.bind(view)
        _binding = binding

        notificationsViewModel.text.observe(viewLifecycleOwner, {
            binding.textNotifications.text = it
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}