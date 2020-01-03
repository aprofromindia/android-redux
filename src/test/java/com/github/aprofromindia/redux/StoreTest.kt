package com.github.aprofromindia.redux

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.verifyNoMoreInteractions
import org.junit.Test

class StoreTest {
    private val sut = object : Store(mapOf("key" to object : State {
        override fun reducer(action: Action): State {
            return when {
                else -> this
            }
        }
    })) {}

    @Test
    fun whenMiddlewareAdded_thenMiddlewareShouldBeApplied() {
        //given
        val middleware = mock<Middleware>()
        val action = mock<Action>()

        //when
        sut.addMiddleware(middleware)
        sut.dispatch(action)

        //then
        verify(middleware).apply(sut, action)
    }

    @Test
    fun whenSubscriberAdded_thenSubscriberShouldBeNotified() {
        //given
        val subscriber = mock<Subscriber>()
        val action = mock<Action>()

        //when
        sut.subscribe(subscriber)
        sut.dispatch(action)

        //then
        verify(subscriber).update(any())
    }

    @Test
    fun whenSubscriberRemoved_thenSubscriberShouldNotBeNotified() {
        //given
        val subscriber = mock<Subscriber>()
        val action = mock<Action>()

        //when
        sut.subscribe(subscriber)
        sut.dispatch(action)

        //then
        verify(subscriber).update(any())

        // and then
        sut.unsubscribe(subscriber)
        verifyNoMoreInteractions(subscriber)
    }
}