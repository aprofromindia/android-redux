package com.github.aprofromindia.redux

interface Thunk : (Store) -> Unit, Action {
    override fun invoke(store: Store)
}

class ThunkMiddleWare : Middleware {
    override fun apply(store: Store, action: Action) {
        if (action is Thunk) action.invoke(store)
    }
}