package com.karthik.blissv2alpha10.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel

class AboutFragment : Fragment() {

    private val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeViewModel.setCurrent(-1)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
//        TODO: create about page
        return inflater.inflate(R.layout.fragment_about, container, false)
    }
}