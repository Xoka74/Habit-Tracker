package com.example.hometask3.utils

import android.R.layout.simple_spinner_dropdown_item
import android.R.layout.simple_spinner_item
import android.content.Context
import android.widget.ArrayAdapter
import android.widget.Spinner

object AdapterUtils {
    fun <T> createSpinnerAdapter(spinner: Spinner, array: Array<T>, context : Context): ArrayAdapter<T> {
        val prioritySpinnerAdapter: ArrayAdapter<T> = ArrayAdapter(
            context, simple_spinner_item, array
        ).apply {
            setDropDownViewResource(simple_spinner_dropdown_item)
        }

        spinner.adapter = prioritySpinnerAdapter
        return prioritySpinnerAdapter
    }
}