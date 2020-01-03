# Android Redux Library with Thunk support

- use the `Store class` to create your Android App store (see example below).
- it supports other Store Middleware, through the `Middleware` interface.
- it comes with a Thunk middleware for Asynchronous task.

A sample AppStore for your Android app can be created by subclassing Store and using the map to store your properties : -

```
class AppStore(state: Map<String, State>) : Store(state) {
   val mainState: MainState
       get() = state[MAIN_STATE] as MainState
   val detailState: DetailState
       get() = state[DETAIL_STATE] as DetailState

   companion object {
       val MAIN_STATE = "MAIN_STATE"
       val DETAIL_STATE = "DETAIL_STATE"
   }
}
```


For thunk please see the sample code below : -

- add Thunk middleware to App store
```
appStore.addMiddleware(ThunkMiddleware())
```

then from your ViewModels or Presenter ```dispatch``` a ```Thunk``` as shown below : -

```
class MainVM(private val store: MyAppStore) : ViewModel(), Subscriber {

    private val _state = MutableLiveData<MainState>()

    private val state: LiveData<MainState> = _state

    private val exceptionHandler =
        CoroutineExceptionHandler { _, ex -> store.dispatch(FailedData(Exception(ex.localizedMessage))) }

    fun fetchData() {
        store.dispatch(object : Thunk {
            override fun invoke(dispatch: Dispatch, store: Store) {
                dispatch(FetchData())
                viewModelScope.launch(exceptionHandler) {
                    val response = dataService.getData()
                    if (response.isSuccessful)
                        dispatch(SuccessData(response.body()?.data!!))
                    else throw Exception(response.message())
                }
            }
        })
    }

    // Subscriber interface override
    override fun update(state: State) {
            if (state is MainState && _state.value !== state) {
                _state.value = state
            }
        }
}
```