package com.google.sample.cast.refplayer.settings


import android.content.SharedPreferences
import android.preference.PreferenceActivity
import android.os.Bundle
import com.google.sample.cast.refplayer.R
import android.preference.EditTextPreference
import android.preference.PreferenceManager
import com.google.sample.cast.refplayer.browser.VideoBrowserFragment


class CastPreference : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.application_preference)

        // Register the preference change listener
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        // Set the current custom_catalog_url as the summary for the EditTextPreference
        val customCatalogUrlPref = findPreference("custom_catalog_url") as EditTextPreference
        val customCatalogUrl = sharedPreferences.getString("custom_catalog_url", "")
        customCatalogUrlPref.summary = customCatalogUrl

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "custom_catalog_url") {
            val customCatalogUrlPref = findPreference(key) as EditTextPreference
            customCatalogUrlPref.summary = customCatalogUrlPref.text

            // Update the CATALOG_URL in VideoBrowserFragment
            val newUrl = customCatalogUrlPref.text.trim()
            if (newUrl.isNotEmpty()) {
                val videoBrowserFragment = fragmentManager.findFragmentByTag("VideoBrowserFragment") as VideoBrowserFragment?
                videoBrowserFragment?.updateCatalogUrl(newUrl)
            }
        }
    }
}