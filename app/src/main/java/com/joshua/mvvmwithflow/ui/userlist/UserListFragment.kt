package com.joshua.mvvmwithflow.ui.userlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.joshua.mvvmwithflow.R
import com.joshua.mvvmwithflow.data.model.User
import kotlinx.android.synthetic.main.fragment_userlist.*
import kotlinx.android.synthetic.main.item_userlist.view.*
import timber.log.Timber

class UserListFragment : Fragment() {

    private lateinit var userListViewModel: UserListViewModel
    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userListViewModel = ViewModelProvider(this).get(UserListViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_userlist, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userListAdapter = UserListAdapter(userListViewModel, viewLifecycleOwner)
        recycler_watchlist.adapter = userListAdapter

        userListViewModel.getUserList().observe(viewLifecycleOwner) {
            if (it != null)
                userListAdapter.submitList(it)
        }

        userListViewModel.spinner.observe(viewLifecycleOwner) {
            Timber.d("spinner $it")
            if (it)
                spinner.visibility = View.VISIBLE
            else
                spinner.visibility = View.GONE
        }

        userListViewModel.getNavigationRequest().observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandled()?.let {
                // https://developer.android.com/guide/navigation/navigation-pass-data
                // https://medium.com/@vepetruskova/using-safe-args-plugin-current-state-of-affairs-41b1f01e7de8
//                val actionDetail = UserListFragmentDirections.actionUserDetail(it)
//                findNavController().navigate(actionDetail)
                val bundle = bundleOf("id" to it)
                findNavController().navigate(R.id.action_user_detail, bundle)
            }
        }
    }

}

class UserListAdapter(val viewModel: UserListViewModel, val viewLifecycleOwner: LifecycleOwner) :
    ListAdapter<User, WatchlistViewHolder>(
        DIFF_CALLBACK
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WatchlistViewHolder {
        return WatchlistViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_userlist, parent, false),
            viewModel, viewLifecycleOwner
        )
    }

    override fun onBindViewHolder(holder: WatchlistViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean =
                oldItem == newItem
        }
    }
}

class WatchlistViewHolder(
    private val rootView: View,
    private val viewModel: UserListViewModel,
    private val viewLifecycleOwner: LifecycleOwner
) : RecyclerView.ViewHolder(rootView) {

    fun bind(item: User) {
        rootView.userlist_name.text = item.name

        // We have to keep on receiving, so we need to use Flow
        // https://medium.com/androiddevelopers/livedata-with-coroutines-and-flow-part-iii-livedata-and-coroutines-patterns-592485a4a85a
        // https://codelabs.developers.google.com/codelabs/advanced-kotlin-coroutines/#0
        if (item.id != null) {
            viewModel.getNumberOfLikes(item.id).observe(viewLifecycleOwner) {
                if (it != null) {
                    rootView.userlist_like.text = "Number of likes: $it"
                } else
                    rootView.userlist_like.text = "Number of likes: 0"
            }

        }

        rootView.userlist_parent.setOnClickListener {
            if (item.id != null)
                viewModel.setNavigationRequest(item.id)
        }


    }


}