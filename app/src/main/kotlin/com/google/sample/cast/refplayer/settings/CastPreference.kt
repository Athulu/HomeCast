package com.google.sample.cast.refplayer.settings


import android.content.SharedPreferences
import android.preference.PreferenceActivity
import android.os.Bundle
import com.google.sample.cast.refplayer.R
import android.preference.EditTextPreference
import android.preference.PreferenceManager
import com.google.sample.cast.refplayer.browser.VideoBrowserFragment
import com.google.sample.cast.refplayer.utils.Utils.getAppVersionName


class CastPreference : PreferenceActivity(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.application_preference)
        preferenceScreen.sharedPreferences.registerOnSharedPreferenceChangeListener(this)
        val versionPref = findPreference("app_version") as EditTextPreference
        versionPref.title = getString(R.string.version, getAppVersionName(this))

        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)

        val customCatalogUrlPref = findPreference("custom_catalog_url") as EditTextPreference
        val customCatalogUrl = sharedPreferences.getString("custom_catalog_url", "")
        customCatalogUrlPref.summary = customCatalogUrl
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "custom_catalog_url") {
            var customCatalogUrlPref = findPreference(key) as EditTextPreference
            customCatalogUrlPref.summary = customCatalogUrlPref.text

            var newUrl = customCatalogUrlPref.text.trim()
            VideoBrowserFragment.updateCatalogUrl(newUrl)
        }
    }
}