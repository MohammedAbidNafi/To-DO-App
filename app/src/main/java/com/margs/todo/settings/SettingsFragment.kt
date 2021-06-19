package com.margs.todo.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.margs.todo.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }

}