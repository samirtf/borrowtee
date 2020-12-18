package tf.samir.borrowtee.features.main.view.all_things

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_all_things.*
import tf.samir.borrowtee.R
import tf.samir.borrowtee.databinding.FragmentAllThingsBinding
import tf.samir.borrowtee.features.main.presentation.presenter.all_things.*
import tf.samir.borrowtee.features.main.utils.*
import tf.samir.borrowtee.viewbase.alert
import tf.samir.core.base.HyperFragment
import timber.log.Timber

@AndroidEntryPoint
class AllThingsFragment :
    HyperFragment<AllThingsViewState, AllThingsViewEffect, AllThingsViewEvent, AllThingsViewModel>() {

    companion object {
        const val TAG = "AllThingsFragment"
    }

    private var _binding: FragmentAllThingsBinding? = null
    private val binding: FragmentAllThingsBinding
        get() = _binding!!

    override val viewModel: AllThingsViewModel by viewModels()

    private var dialog: AlertDialog? = null


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
    ): FragmentAllThingsBinding {
        return FragmentAllThingsBinding.inflate(inflater, container, attachToRoot)
            .also {
                it.viewModel = viewModel
                it.lifecycleOwner = viewLifecycleOwner
                it.fab.setOnClickListener {
                    viewModel.handle(AllThingsViewEvent.FabClicked)
                }
                object : ItemSwipeHelper(requireContext(), it.recyclerView, 200) {
                    override fun instantiateItemButton(
                        viewHolder: RecyclerView.ViewHolder,
                        buffer: MutableList<ButtonItem>
                    ) {
                        buffer.add(createTextAndIconButtonItem())
                        buffer.add(createIconButtonItem())
                        buffer.add(createTextButtonItem())
                    }
                }
            }
    }

    private fun createTextButtonItem() = TextButtonItem(
        "Delete",
        30,
        Color.parseColor("#ff0000"),
        object : ItemClickListener {
            override fun onClick(position: Int) {
                Timber.tag("STF").e("TOAST")
                showDeletionConfirmationDialog(position)
            }
        }
    )

    private fun createIconButtonItem() = IconButtonItem(
        requireContext(),
        R.drawable.ic_baseline_create_24,
        Color.parseColor("#ff7400"),
        object : ItemClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    requireContext(),
                    "EDIT ID$position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

    private fun createTextAndIconButtonItem() = TextAndIconButtonItem(
        requireContext(),
        "Edit",
        30,
        R.drawable.ic_baseline_create_24,
        Color.parseColor("#007FFF"),
        object : ItemClickListener {
            override fun onClick(position: Int) {
                Toast.makeText(
                    requireContext(),
                    "EDIT ID$position",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    )

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

    private fun deleteThingItem(id: Int) {
        viewModel.handle(AllThingsViewEvent.DeleteThingClicked(id))
    }

    private fun showDeletionConfirmationDialog(thingPosition: Int) {
        dialog = null
        dialog = alert("Deleting confirmation!", "Are you sure you want to delete this entry?" ) {
            positiveButton("Delete") { deleteThingItem(thingPosition) }
            negativeButton("Cancel") { Timber.tag(TAG).d("Cancelling deletion.") }
            cancelable = false
        }
        dialog?.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        dialog?.dismiss()
        dialog = null
    }
}