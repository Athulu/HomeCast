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
package com.google.sample.cast.refplayer.browser

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.sample.cast.refplayer.R
import com.google.sample.cast.refplayer.browser.VideoListAdapter.ItemClickListener
import com.google.sample.cast.refplayer.mediaplayer.LocalPlayerActivity
import com.google.sample.cast.refplayer.utils.MediaItem

/**
 * A fragment to host a list view of the video catalog.
 */
class VideoBrowserFragment : Fragment(), ItemClickListener, LoaderManager.LoaderCallbacks<List<MediaItem>?> {
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: VideoListAdapter? = null
    private var mEmptyView: View? = null
    private var mLoadingView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        //
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val serverUrl = sharedPreferences.getString("server_url", "")
        val serverPort = sharedPreferences.getString("server_port", "")
        CATALOG_URL = if (serverUrl.isNullOrEmpty() || serverPort.isNullOrEmpty()) {
            DEFAULT_CATALOG_URL
        } else {
            "http://${serverUrl.trim()}:${serverPort.trim()}/videos"
        }
        return inflater.inflate(R.layout.video_browser_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mRecyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        mEmptyView = view.findViewById(R.id.empty_view)
        mLoadingView = view.findViewById(R.id.progress_indicator)
        val layoutManager = LinearLayoutManager(activity)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        mRecyclerView!!.layoutManager = layoutManager
        mAdapter = VideoListAdapter(this, this.requireContext())
        mRecyclerView!!.adapter = mAdapter
        LoaderManager.getInstance(this).initLoader(0, null, this)

        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)
        swipeRefreshLayout.setOnRefreshListener {
            refreshData()
            swipeRefreshLayout.isRefreshing = false
        }
    }


    override fun itemClicked(v: View?, item: MediaItem?, position: Int) {
        val transitionName = getString(R.string.transition_image)
        val viewHolder = mRecyclerView!!.findViewHolderForLayoutPosition(position) as VideoListAdapter.ViewHolder?
        val imagePair = Pair
                .create(viewHolder!!.imageView as View, transitionName)
        val options = ActivityOptionsCompat
                .makeSceneTransitionAnimation(requireActivity(), imagePair)
        val intent = Intent(activity, LocalPlayerActivity::class.java)
        intent.putExtra("media", item!!.toBundle())
        intent.putExtra("shouldStart", false)
        ActivityCompat.startActivity(requireActivity(), intent, options.toBundle())
    }

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<MediaItem>?> {
        return VideoItemLoader(activity, CATALOG_URL)
    }

    override fun onLoadFinished(loader: Loader<List<MediaItem>?>, data: List<MediaItem>?) {
        mAdapter!!.setData(data)
        mLoadingView!!.visibility = View.GONE
        mEmptyView!!.visibility = if (null == data || data.isEmpty()) View.VISIBLE else View.GONE
    }

    override fun onLoaderReset(loader: Loader<List<MediaItem>?>) {
        mAdapter!!.setData(null)
    }

    companion object {
        private const val TAG = "VideoBrowserFragment"
        private const val DEFAULT_CATALOG_URL = "http://192.168.1.110:8080/videos"
        private var CATALOG_URL = DEFAULT_CATALOG_URL;
    }

    fun refreshData() {
        mLoadingView!!.visibility = View.VISIBLE
        clearData()
        updateUrl()

        val newMediaList = VideoProvider.buildMedia(CATALOG_URL) //
        mAdapter!!.setData(newMediaList)

        mLoadingView!!.visibility = View.GONE
        mEmptyView!!.visibility = if (newMediaList.isNullOrEmpty()) View.VISIBLE else View.GONE
        mRecyclerView!!.visibility = if (newMediaList.isNullOrEmpty()) View.GONE else View.VISIBLE
    }
    fun clearData() {
        mAdapter!!.setData(null)
        mLoadingView!!.visibility = View.GONE
        mEmptyView!!.visibility = View.GONE
        mRecyclerView!!.visibility = View.GONE
    }

    fun updateUrl(){
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())
        val serverUrl = sharedPreferences.getString("server_url", "")
        val serverPort = sharedPreferences.getString("server_port", "")
        CATALOG_URL = if (serverUrl.isNullOrEmpty() || serverPort.isNullOrEmpty()) {
            DEFAULT_CATALOG_URL
        } else {
            "http://${serverUrl.trim()}:${serverPort.trim()}/videos"
        }
    }

}