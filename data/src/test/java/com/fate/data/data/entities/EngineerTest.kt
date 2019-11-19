package com.fate.data.data.entities

import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class EngineerTest{

    private lateinit var engineer: Engineer

    @Before
    fun setUp() {
        engineer = Engineer(0, "Tomato")
    }

    @Test
    fun test_getName() {
        assertEquals("Tomato", engineer.name)
    }

    @Test
    fun test_getId() {
        assertEquals(0, engineer.id)
    }

}