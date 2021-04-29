package com.karthik.blissv2alpha10.ui

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
import com.karthik.blissv2alpha10.ui.adapters.ReminderHomeAdapter
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import kotlinx.android.synthetic.main.fragment_reminder_home_layout.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ReminderHomeLayout.newInstance] factory method to
 * create an instance of this fragment.
 */
class ReminderHomeLayout : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
//        val navController = requireView().findNavController()
//
//        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
//        homeViewModel.currentHome.observe(viewLifecycleOwner, Observer {
//            when(it) {
//                "notes" -> navController.navigate(R.id.action_reminderHomeLayout_to_noteHomeLayout);
//                "todos" -> navController.navigate(R.id.action_reminderHomeLayout_to_todoHomeLayout);
//            }
//        })

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        rRecyclerView.layoutManager = StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL)
        val adapter = ReminderHomeAdapter(requireContext())
        rRecyclerView.adapter = adapter


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment

        val homeViewModel: HomeViewModel by activityViewModels()

        homeViewModel.getCurrent().observe(viewLifecycleOwner, Observer {
            when(it) {
                0 -> NavHostFragment.findNavController(this).navigate(R.id.action_reminderHomeLayout_to_noteHomeLayout)
                2 -> NavHostFragment.findNavController(this).navigate(R.id.action_reminderHomeLayout_to_todoHomeLayout)
            }
        })

        return inflater.inflate(R.layout.fragment_reminder_home_layout, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ReminderHomeLayout.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                ReminderHomeLayout().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}