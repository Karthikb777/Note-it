package com.karthik.blissv2alpha10.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.SearchBarFragment
import com.karthik.blissv2alpha10.ui.adapters.ReminderHomeAdapter
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import com.karthik.blissv2alpha10.ui.viewModels.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_reminder_home_layout.*

class ReminderHomeLayout : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val reminderViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(ReminderViewModel::class.java)
        val reminderViewModel : ReminderViewModel by activityViewModels()
        val homeViewModel : HomeViewModel by activityViewModels()

        rRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val adapter = ReminderHomeAdapter(requireContext(), this, homeViewModel)
        rRecyclerView.adapter = adapter

        reminderViewModel.allReminders.observe(viewLifecycleOwner, {
            adapter.updateList(it)
        })

        reminderViewModel.gotReminder.observe(viewLifecycleOwner, {
            adapter.updateList(it.toList())
        })

        homeViewModel.getSearch().observe(viewLifecycleOwner, {
            if (it == 0) {
                adapter.updateList(reminderViewModel.allReminders.value)
            }
        })



    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        childFragmentManager.beginTransaction()
                .replace(R.id.searchBarHolder, SearchBarFragment(), null)
                .commit()

        val homeViewModel: HomeViewModel by activityViewModels()

        homeViewModel.getCurrent().observe(viewLifecycleOwner, Observer {
            when(it) {
                0 -> NavHostFragment.findNavController(this).navigate(R.id.action_reminderHomeLayout_to_noteHomeLayout)
                2 -> NavHostFragment.findNavController(this).navigate(R.id.action_reminderHomeLayout_to_todoHomeLayout)
            }
        })

        return inflater.inflate(R.layout.fragment_reminder_home_layout, container, false)
    }

}