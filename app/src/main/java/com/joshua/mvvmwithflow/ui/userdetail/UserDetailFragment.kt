package com.joshua.mvvmwithflow.ui.userdetail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import com.joshua.mvvmwithflow.R
import kotlinx.android.synthetic.main.fragment_userdetail.*

class UserDetailFragment : Fragment() {

    private lateinit var userDetailViewModel: UserDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        userDetailViewModel = ViewModelProvider(this).get(UserDetailViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_userdetail, container, false)
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getString("id")
        if (id != null) {

            userDetailViewModel.getUserDetail(id).observe(viewLifecycleOwner) {

                if (it == null) {

                    name.text = "ID not found"
                } else {
                    name.text = it.name
                    email.text = it.email
                    address.text =
                        "${it.address?.suite ?: ""} ${it.address?.street ?: ""} ${it.address?.city ?: ""} ${it.address?.zipcode ?: ""} "
                    phone.text = it.phone
                }
            }
        } else {
            name.text = "ID not passed"
        }


    }

}

