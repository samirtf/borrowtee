package tf.samir.borrowtee.modules.main.view.all_things

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import tf.samir.borrowtee.R
import tf.samir.borrowtee.modules.main.presentation.presenter.all_things.AllThingsViewModel

class AllThingsFragment : Fragment() {

    private lateinit var allThingsViewModel: AllThingsViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        allThingsViewModel =
                ViewModelProvider(this).get(AllThingsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_all_things, container, false)
        val textView: TextView = root.findViewById(R.id.text_all_things)
        allThingsViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}