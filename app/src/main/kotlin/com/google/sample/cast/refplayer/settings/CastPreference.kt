/*
 * Copyright (C) 2022 Google LLC. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.google.sample.cast.refplayer.settings


import android.content.SharedPreferences
import android.os.Bundle
import android.preference.EditTextPreference
import android.preference.PreferenceActivity
import android.preference.PreferenceManager
import com.google.sample.cast.refplayer.R
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

        val customCatalogUrlPref = findPreference("server_url") as EditTextPreference
        val customCatalogUrl = sharedPreferences.getString("server_url", "np. 192.168.1.1")
        customCatalogUrlPref.summary = customCatalogUrl

        val serverPortPref = findPreference("server_port") as EditTextPreference
        val serverPort = sharedPreferences.getString("server_port", "np. 8080")
        serverPortPref.summary = serverPort

    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == "server_url") {
            val customCatalogUrlPref = findPreference(key) as EditTextPreference
            val newCustomCatalogUrl = sharedPreferences?.getString(key, "")
            customCatalogUrlPref.summary = newCustomCatalogUrl
        } else if (key == "server_port") {
            val serverPortPref = findPreference(key) as EditTextPreference
            val serverPort = sharedPreferences?.getString(key, "")
            serverPortPref.summary = serverPort
        }
    }
}