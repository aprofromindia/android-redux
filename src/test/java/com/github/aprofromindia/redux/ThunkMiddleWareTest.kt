package com.github.aprofromindia.redux

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.inOrder
import com.nhaarman.mockitokotlin2.spy
import com.nhaarman.mockitokotlin2.times
import org.junit.Test

class ThunkMiddleWareTest {
    private val sut = ThunkMiddleWare()

    @Test
    fun whenThunkMiddlewareIsAdded_andAThunkIsDispatched_thenItsResolved() {
        // given
        val store = spy(object : Store(emptyMap()) {})

        // when
        store.addMiddleware(sut)
        store.dispatch(object : Thunk {
            override fun invoke(store: Store) {
                store.dispatch(object : Action {})
                store.dispatch(object : Action {})
            }
        })

        // then
        inOrder(store) {
            verify(store).dispatch(any<Thunk>())
            verify(store, times(2)).dispatch(any())
        }
    }
}