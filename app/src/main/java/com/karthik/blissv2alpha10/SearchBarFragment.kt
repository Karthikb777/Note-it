package com.karthik.blissv2alpha10

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.MenuRes
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel
import com.karthik.blissv2alpha10.ui.viewModels.NoteViewModel
import com.karthik.blissv2alpha10.ui.viewModels.ReminderViewModel
import com.karthik.blissv2alpha10.ui.viewModels.TodoViewModel
import kotlinx.android.synthetic.main.fragment_search_bar.*


class SearchBarFragment : Fragment() {

    val noteViewModel : NoteViewModel by activityViewModels()
    val reminderViewModel : ReminderViewModel by activityViewModels()
    val todoViewModel : TodoViewModel by activityViewModels()
    val homeViewModel : HomeViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        hamburger.setOnClickListener {
            showMenu(it, R.menu.menu_home)
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                Log.d("searchInput", s.toString())
                search(s)
// TODO : add a way to abort search operation, by changing search icon to x and adding on click listener to it
            }

            override fun afterTextChanged(s: Editable?) {

            }

        })
    }

    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            val homeViewModel : HomeViewModel by activityViewModels()
            when(menuItem.title.toString()) {
                "Notes" -> homeViewModel.setCurrent(0)
                "Reminders" -> homeViewModel.setCurrent(1)
                "Todos" -> homeViewModel.setCurrent(2)
//                "About" -> homeViewModel.currentHome.value = "about"
            }
            true
        }
        popup.setOnDismissListener {
            // Respond to popup being dismissed.
        }
        // Show the popup menu.
        popup.show()
    }

    private fun search(query: CharSequence?) {
       when(homeViewModel.getCurrent().value) {
           0 -> {
               noteViewModel.getNoteByTitle(query.toString())
           }
           1 -> {
               reminderViewModel.getNoteByTitle(query.toString())
           }
           2 -> {
               todoViewModel.getTodoByTitle(query.toString())
           }
       }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search_bar, container, false)
    }

}