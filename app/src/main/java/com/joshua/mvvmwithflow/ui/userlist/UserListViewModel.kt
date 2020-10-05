package com.joshua.mvvmwithflow.ui.userlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.joshua.mvvmwithflow.BaseApplication
import com.joshua.mvvmwithflow.common.Event
import com.joshua.mvvmwithflow.data.model.User
import com.joshua.mvvmwithflow.di.ServiceLocator

class UserListViewModel : ViewModel() {

    private val userRepo = ServiceLocator.provideUserRepository(BaseApplication.appContext)

    // Don't use MutableLiveData. Use Event wrapper instead.
    // https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
    private val _navigationLiveData = MutableLiveData<Event<String>>()

    fun getUserList(): LiveData<ArrayList<User>?> =
        userRepo.getUsers().asLiveData()


    fun getNumberOfLikes(id: String): LiveData<Int?> =
        userRepo.getNumberOfLikes(id).asLiveData()

    fun setNavigationRequest(id: String) {
        _navigationLiveData.value = Event(id)
    }

    fun getNavigationRequest() = _navigationLiveData

}