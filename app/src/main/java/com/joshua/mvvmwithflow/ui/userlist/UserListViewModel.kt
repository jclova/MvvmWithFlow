package com.joshua.mvvmwithflow.ui.userlist

import androidx.lifecycle.*
import com.joshua.mvvmwithflow.BaseApplication
import com.joshua.mvvmwithflow.common.Event
import com.joshua.mvvmwithflow.data.model.User
import com.joshua.mvvmwithflow.di.ServiceLocator
import kotlinx.coroutines.flow.*
import timber.log.Timber

class UserListViewModel : ViewModel() {

    private val userRepo = ServiceLocator.provideUserRepository(BaseApplication.appContext)

    // Don't use MutableLiveData. Use Event wrapper instead.
    // https://medium.com/androiddevelopers/livedata-with-snackbar-navigation-and-other-events-the-singleliveevent-case-ac2622673150
    private val _navigationLiveData = MutableLiveData<Event<String>>()

    private val _spinner = MutableLiveData<Boolean>(false)

    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        // use debounce so that if cache data is found immediately, it does not show Spinner at all
        get() = _spinner.asFlow().debounce(100).asLiveData()


    fun getUserList(): LiveData<ArrayList<User>?> = userRepo.getUsers()
        .onStart {  _spinner.value = true }
        .onEach {  _spinner.value = false }
        .catch { e -> Timber.e(e) }
        .asLiveData()



    fun getNumberOfLikes(id: String): LiveData<Int?> =
        userRepo.getNumberOfLikes(id).asLiveData()

    fun setNavigationRequest(id: String) {
        _navigationLiveData.value = Event(id)
    }

    fun getNavigationRequest() = _navigationLiveData

}