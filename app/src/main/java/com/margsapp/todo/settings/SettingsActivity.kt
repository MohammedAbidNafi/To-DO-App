package com.margsapp.todo.settings

import android.app.ActivityOptions
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Pair
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.margsapp.todo.MainActivity
import com.margsapp.todo.R
import kotlinx.android.synthetic.main.settings_activity.*

class SettingsActivity : AppCompatActivity() {

    private var theme_id: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.settings_activity)


        val sharedPreferences = getSharedPreferences("themeID", 0)

        val Theme = sharedPreferences.getInt(SettingsActivity.THEME, 0)

        if (Theme == 2) {


            theme_txt.setText("Light")

        }
        if (Theme == 1) {


            theme_txt.setText("Dark")

        }
        if (Theme == 0) {


            theme_txt.setText("Default")

        }


        theme_card.setOnClickListener {
            showDialog()
        }



    }


    private fun showDialog() {
        val preferences = getSharedPreferences("themeID", MODE_PRIVATE)
        theme_id = preferences.getInt(THEME, 0)

        val themes = arrayOf("Default", "Dark", "Light")

        val dialog = AlertDialog.Builder(this)
        dialog.setTitle(resources.getString(R.string.Choose_Theme))


        dialog.setSingleChoiceItems(themes, theme_id) { dialog1: DialogInterface, i: Int ->
            if (i == 0) {
                setDefaultTheme()

                recreate()
            } else if (i == 1) {
                setDark()

                recreate()
            } else if (i == 2) {
                setLight()

                recreate()
            }
            dialog1.dismiss()
        }

        val alertDialog = dialog.create()
        alertDialog.show()
    }





    private fun setDefaultTheme() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        val sharedPreferences = getSharedPreferences("themeID", 0)
        val editor = sharedPreferences.edit()
        editor.putInt(THEME, 0)
        editor.apply()


    }

    private fun setDark() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        val sharedPreferences = getSharedPreferences("themeID", 0)
        val editor = sharedPreferences.edit()
        editor.putInt(THEME, 1)
        editor.apply()


    }

    private fun setLight() {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        val sharedPreferences = getSharedPreferences("themeID", 0)
        val editor = sharedPreferences.edit()
        editor.putInt(THEME, 2)
        editor.apply()



    }



    private fun restartApp() {
        val i = Intent(applicationContext, SettingsActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onBackPressed() {

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT)
        val pairs = listOf<Pair<View, String>>(
        Pair(settings_button, "text")
        )
        val options = ActivityOptions.makeSceneTransitionAnimation(this, pairs[0])
        startActivity(intent, options.toBundle())
        finish()
    }

    companion object {
        const val THEME = "0"
    }
}