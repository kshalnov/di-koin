package ru.gb.course1.dil

import android.os.Bundle
import androidx.fragment.app.Fragment

class DiHolderFragment : Fragment() {
    lateinit var dependency: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }
}