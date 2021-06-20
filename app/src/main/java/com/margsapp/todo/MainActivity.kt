package com.margsapp.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.margsapp.todo.edittask.EditFragment
import com.margsapp.todo.listtasks.ListFragment
import com.margsapp.todo.settings.SettingsActivity
import java.util.*

class MainActivity : AppCompatActivity(), ListFragment.Callbacks {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sharedPreferences = getSharedPreferences("themeID", 0)

        val Theme = sharedPreferences.getInt(SettingsActivity.THEME, 0)

        if (Theme == 2) {


            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
        if (Theme == 1) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
        if (Theme == 0) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        }


        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)

        if(currentFragment == null){
            val fragment = ListFragment()
            supportFragmentManager
                .beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit()
        }

    }

    override fun onTaskSelect(view: View?, taskId: UUID) {
        val fragment = EditFragment.newInstance(taskId)
        supportFragmentManager
            .beginTransaction()
            .addSharedElement(view!!, taskId.toString())
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onResume() {
        super.onResume()
        val sharedPreferences = getSharedPreferences("themeID", 0)

        val Theme = sharedPreferences.getInt(SettingsActivity.THEME, 0)

        if (Theme == 2) {


            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        }
        if (Theme == 1) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        }
        if (Theme == 0) {

            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)

        }
    }
}
