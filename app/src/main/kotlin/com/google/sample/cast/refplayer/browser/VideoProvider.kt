package com.google.sample.cast.refplayer.browser

import android.os.AsyncTask
import android.util.Log
import com.google.sample.cast.refplayer.utils.MediaItem
import org.json.JSONException
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL


class VideoProvider : AsyncTask<String?, Void?, String>(){
    override fun doInBackground(vararg params: String?): String {
        try {
            var connection = URL(params.get(0)).openConnection() as HttpURLConnection
            return connection.inputStream.bufferedReader().readText()
        } catch (e: Exception){
            Log.d(TAG, "troche lipa z pobraniem jsona", e)
            e.printStackTrace()
        }
        return "{}"
    }
    override fun onPostExecute(message: String) {
        Log.d(TAG, "pobrano jsona")
    }
    protected fun parseUrl(urlString: String?): JSONObject? {
        val result = execute(urlString).get()
        return JSONObject(result)
    }

    companion object {
        private const val TAG = "VideoProvider"
        private const val TAG_VIDEOS = "videos"
        private const val TAG_MP4 = "mp4"
        private const val TAG_IMAGES = "images"
        private const val TAG_VIDEO_TYPE = "type"
        private const val TAG_VIDEO_URL = "url"
        private const val TAG_VIDEO_MIME = "mime"
        private const val TAG_EPISODE = "episode"
        private const val TAG_SOURCES = "sources"
        private const val TAG_SUBTITLE = "subtitle"
        private const val TAG_DURATION = "duration"
        private const val TAG_THUMB = "image480x270"
        private const val TAG_IMG_780_1200 = "image780x1200"
        private const val TAG_TITLE = "title"
        const val KEY_DESCRIPTION = "description"
        private const val TARGET_FORMAT = TAG_MP4
        private var mediaList: MutableList<MediaItem>? = null

        @JvmStatic
        @Throws(JSONException::class)
        fun buildMedia(url: String?): List<MediaItem>? {
            val urlPrefixMap: MutableMap<String, String> = HashMap()
            mediaList = ArrayList()
            var jsonObj = VideoProvider().parseUrl(url!!)
            print(jsonObj.toString())
            if(jsonObj.toString().equals("{}")) jsonObj = null

            if (null != jsonObj) {
                urlPrefixMap[TAG_MP4] = jsonObj.getString(TAG_MP4)
                urlPrefixMap[TAG_IMAGES] = jsonObj.getString(TAG_IMAGES)
                val videos = jsonObj.getJSONArray(TAG_VIDEOS)
                for (j in 0 until videos.length()) {
                    var videoUrl: String? = null
                    var mimeType: String? = null
                    val video = videos.getJSONObject(j)
                    print(video)
                    val subTitle = video.getString(TAG_SUBTITLE)
                    val videoSpecs = video.getJSONArray(TAG_SOURCES)
                    if (videoSpecs.length() == 0) {
                        continue
                    }
                    for (k in 0 until videoSpecs.length()) {
                        val videoSpec = videoSpecs.getJSONObject(k)
                        if (TARGET_FORMAT == videoSpec.getString(TAG_VIDEO_TYPE)) {
                            videoUrl = urlPrefixMap[TARGET_FORMAT].toString() + videoSpec
                                    .getString(TAG_VIDEO_URL)
                            mimeType = videoSpec.getString(TAG_VIDEO_MIME)
                        }
                    }
                    if (videoUrl == null) {
                        continue
                    }
                    val imageUrl =
                            urlPrefixMap[TAG_IMAGES].toString() + video.getString(TAG_THUMB)
                    val bigImageUrl = urlPrefixMap[TAG_IMAGES].toString() + video
                            .getString(TAG_IMG_780_1200)
                    val title = video.getString(TAG_TITLE)
                    val studio = video.getString(TAG_EPISODE)
                    val duration = video.getInt(TAG_DURATION)
                    mediaList!!.add(
                            buildMediaInfo(
                                    title, studio, subTitle, duration, videoUrl,
                                    mimeType, imageUrl, bigImageUrl
                            )
                    )
                }
            }
            return mediaList
        }

        private fun buildMediaInfo(title: String, studio: String, subTitle: String,
                                   duration: Int, url: String, mimeType: String?, imgUrl: String, bigImageUrl: String): MediaItem {
            val media = MediaItem()
            media.url = url
            media.title = title
            media.subTitle = subTitle
            media.episode = studio
            media.addImage(imgUrl)
            media.addImage(bigImageUrl)
            media.contentType = mimeType
            media.duration = duration
            return media
        }
    }
}