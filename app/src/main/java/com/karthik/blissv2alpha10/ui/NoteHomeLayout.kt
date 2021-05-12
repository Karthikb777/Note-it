package com.karthik.blissv2alpha10.ui

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.SearchBarFragment
import com.karthik.blissv2alpha10.database.AppDatabase
import com.karthik.blissv2alpha10.database.ThisApplication
import com.karthik.blissv2alpha10.ui.adapters.NoteHomeAdapter
import com.karthik.blissv2alpha10.ui.adapters.TodoHomeAdapter
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note_home_layout.*
import kotlinx.android.synthetic.main.fragment_todo_home_layout.*


class NoteHomeLayout : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val db = AppDatabase.getDatabase(requireContext())

//        val noteViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(Application())).get(NoteViewModel::class.java)
        val noteViewModel : NoteViewModel by activityViewModels()
        val homeViewModel : HomeViewModel by activityViewModels()

        recyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val adapter = NoteHomeAdapter(requireContext(), this, homeViewModel)
        recyclerView.adapter = adapter

        noteViewModel.allNotes.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })

        noteViewModel.gotNote.observe(viewLifecycleOwner, {
            adapter.updateList(it.toList())
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        childFragmentManager.beginTransaction()
                .replace(R.id.searchBarHolder, SearchBarFragment(), null)
                .commit()

        val homeViewModel : HomeViewModel by activityViewModels()

        homeViewModel.getCurrent().observe(viewLifecycleOwner, Observer {
            when (it) {
                1 -> {
                    NavHostFragment.findNavController(this).navigate(R.id.action_noteHomeLayout_to_reminderHomeLayout)
                }
                2 -> {
                    NavHostFragment.findNavController(this).navigate(R.id.action_noteHomeLayout_to_todoHomeLayout)
                }
            }
        })

        return inflater.inflate(R.layout.fragment_note_home_layout, container, false)
    }

}