package com.karthik.blissv2alpha10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.karthik.blissv2alpha10.ui.NoteHomeLayout
import com.karthik.blissv2alpha10.ui.ReminderHomeLayout
import com.karthik.blissv2alpha10.ui.TodoHomeLayout
import com.karthik.blissv2alpha10.ui.viewModels.HomeViewModel

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager.beginTransaction()
                .replace(R.id.searchBarHolder, SearchBarFragment(), null)
                .commit()

        val homeViewModel : HomeViewModel by viewModels()

        homeViewModel.getCurrent().observe(this, Observer {
//            TODO: use this to remove the search bar fragment while displaying the view fragments
        })
    }
}