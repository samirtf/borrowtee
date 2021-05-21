package tf.samir.borrowtee.features.main.view.all_things

import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
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

    override val viewModel by viewModels<AllThingsViewModel>()

    private var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.all_things_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle menu item clicks
        when (item.itemId) {
            R.id.filterAllThings -> handleFilterByAllThings()
            R.id.filterBorrowed -> handleFilterByBorrowed()
            R.id.filterAtHome -> handleFilterByAtHome()
            R.id.sortUpward -> handleSortUpward()
            R.id.sortDownward -> handleSortDownward()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun handleFilterByAllThings() {
        Timber.tag(TAG).d("Filter All Things Clicked!")
        viewModel.handle(AllThingsViewEvent.FilterByAllThings)
    }

    private fun handleFilterByBorrowed() {
        Timber.tag(TAG).d("Filter Borrowed Clicked!")
        viewModel.handle(AllThingsViewEvent.FilterByBorrowed)
    }

    private fun handleFilterByAtHome() {
        Timber.tag(TAG).d("Filter At Home Clicked!")
        viewModel.handle(AllThingsViewEvent.FilterByAtHome)
    }

    private fun handleSortUpward() {
        Timber.tag(TAG).d("Sort Upward Clicked!")
        viewModel.handle(AllThingsViewEvent.SortUpward)
    }

    private fun handleSortDownward() {
        Timber.tag(TAG).d("Sort Downward Clicked!")
        viewModel.handle(AllThingsViewEvent.SortDownward)
    }

    override fun renderViewState(viewState: AllThingsViewState) {
        when (viewState.fetchStatus) {
            is FetchStatus.Fetched -> {
                binding.progressBar.visibility = View.INVISIBLE
            }
            is FetchStatus.NotFetched -> {
                viewModel.handle(AllThingsViewEvent.FetchAllThings)
                binding.progressBar.visibility = View.INVISIBLE
            }
            is FetchStatus.Fetching -> {
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    override fun renderViewEffect(viewEffect: AllThingsViewEffect) {
        when (viewEffect) {
            AllThingsViewEffect.NavigateToCreateBorrowingPage -> {
                Timber.tag(TAG).i("NavigateToCreateBorrowing")
                navigateToCreateBorrowing()
            }
            AllThingsViewEffect.ShowFetchThingsSuccess -> {
                Toast.makeText(context, getString(R.string.fetch_things_success), Toast.LENGTH_SHORT).show()
            }
            is AllThingsViewEffect.ShowFetchThingsFailure -> {
                Toast.makeText(context, getString(R.string.fetch_things_failure), Toast.LENGTH_SHORT).show()
            }
            AllThingsViewEffect.ShowDeleteThingSuccess -> {
                Toast.makeText(context, getString(R.string.delete_thing_success), Toast.LENGTH_SHORT).show()
            }
            is AllThingsViewEffect.ShowDeleteThingFailure -> {
                Toast.makeText(context, getString(R.string.delete_thing_failure), Toast.LENGTH_SHORT).show()
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
        dialog = alert(getString(R.string.delete_confirmation_dialog_title), getString(R.string.delete_confirmation_dialog_message) ) {
            positiveButton(getString(R.string.button_delete)) { deleteThingItem(thingPosition) }
            negativeButton(getString(R.string.button_cancel)) { Timber.tag(TAG).d("Cancelling deletion.") }
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