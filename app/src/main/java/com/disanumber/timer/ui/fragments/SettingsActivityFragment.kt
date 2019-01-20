package com.disanumber.timer.ui.fragments

import android.os.Bundle
import com.disanumber.timer.R
import com.takisoft.fix.support.v7.preference.PreferenceFragmentCompat

class SettingsActivityFragment: PreferenceFragmentCompat(){
    override fun onCreatePreferencesFix(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}