package com.fate.scheduleonwheels.common

/**
 * Used as a wrapper for timeSheetsSummaryData that is exposed via a LiveData that represents an event.
 * With an Event wrapper, you can add multiple observers to a single-use event
 * You need to specify the intention by using [getContentIfNotHandled] or [peekContent]
 * This method models the events as part of the state: they’re now simply a message that has been consumed or not.
 */
open class Event<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
}