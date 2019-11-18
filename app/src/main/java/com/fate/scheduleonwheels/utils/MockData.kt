package com.fate.scheduleonwheels.utils

import com.fate.data.di.entities.Engineer


class MockData {

    companion object {

        var mockedEngineersList =
            listOf(
                Engineer(0, "Bogdan"),
                Engineer(1, "Nic"),
                Engineer(2, "Tung"),
                Engineer(3, "Gautam"),
                Engineer(4, "Bala"),
                Engineer(5, "Nazih"),
                Engineer(6, "Huteri"),
                Engineer(7, "Aldy"),
                Engineer(8, "Ankur"),
                Engineer(9, "Chinh")
            )

    }
}