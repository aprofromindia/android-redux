package com.github.aprofromindia.redux

interface Action

interface Middleware {
    fun apply(store: Store, action: Action)
}

interface Subscriber {
    fun update(state: State)
}

interface State {
    fun reducer(action: Action): State
}

abstract class Store(protected var state: Map<String, State>) {
    private val subscribers = mutableSetOf<Subscriber>()
    private val middlewares = mutableSetOf<Middleware>()

    fun dispatch(action: Action) {
        middlewares.forEach { it.apply(this, action) }
        state = state.mapValues { (_, v) -> v.reducer(action) }
        subscribers.forEach { sub -> state.values.forEach { sub.update(it) } }
    }

    fun addMiddleware(middleware: Middleware) {
        middlewares.add(middleware)
    }

    fun subscribe(subscriber: Subscriber) {
        subscribers.add(subscriber)
    }

    fun unsubscribe(subscriber: Subscriber) {
        subscribers.remove(subscriber)
    }
}

