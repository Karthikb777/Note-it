package com.karthik.blissv2alpha10.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.karthik.blissv2alpha10.R
import com.karthik.blissv2alpha10.SearchBarFragment
import com.karthik.blissv2alpha10.ui.adapters.TodoHomeAdapter
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.TodoViewModel
import kotlinx.android.synthetic.main.fragment_todo_home_layout.*
import kotlinx.android.synthetic.main.fragment_todo_home_layout.view.*

class TodoHomeLayout : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel : TodoViewModel by activityViewModels()

        tRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = TodoHomeAdapter(requireContext(), viewModel)
        tRecyclerView.adapter = adapter

        viewModel.allTodos.observe(viewLifecycleOwner, {
            adapter.updateList(it)
        })

        viewModel.gotTodo.observe(viewLifecycleOwner, {
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

        val homeViewModel: HomeViewModel by activityViewModels()

        homeViewModel.getCurrent().observe(viewLifecycleOwner, Observer {
            when(it) {
                0 -> NavHostFragment.findNavController(this).navigate(R.id.action_todoHomeLayout_to_noteHomeLayout)
                1 -> NavHostFragment.findNavController(this).navigate(R.id.action_todoHomeLayout_to_reminderHomeLayout)
            }
        })

        return inflater.inflate(R.layout.fragment_todo_home_layout, container, false)
    }

}