package com.otitan.domain

interface Command<T> {
    fun execute(): T?
}

