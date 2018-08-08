package com.titan.domain

interface Command<T> {
    fun execute(): T?
}

