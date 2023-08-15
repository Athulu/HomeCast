package com.google.sample.cast.refplayer.expandedcontrols

import android.view.View
import android.widget.ImageView
import com.google.android.gms.cast.framework.media.uicontroller.UIController

class MyCustomUIController(private val mView: View) : UIController() {

    override fun onMediaStatusUpdated() {
        mView.isEnabled = true
    }

    fun updateButtonImage(imageResId: Int) {
        if (mView is ImageView) {
            mView.setImageResource(imageResId)
        }
    }
}
