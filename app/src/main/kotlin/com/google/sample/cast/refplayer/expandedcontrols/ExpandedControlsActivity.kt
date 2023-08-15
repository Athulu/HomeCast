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
package com.google.sample.cast.refplayer.expandedcontrols

import android.view.Menu
import com.google.android.gms.cast.framework.media.widget.ExpandedControllerActivity
import com.google.sample.cast.refplayer.R
import com.google.android.gms.cast.framework.CastButtonFactory

class ExpandedControlsActivity : ExpandedControllerActivity() {
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        super.onCreateOptionsMenu(menu)

        //przewiń do tyłu 10s
        var customButtonView = getButtonImageViewAt(0)
        var myCustomUiController = MyCustomUIController(customButtonView)
        myCustomUiController.updateButtonImage(R.drawable.cast_ic_notification_rewind10)
        uiMediaController.bindViewToUIController(customButtonView, myCustomUiController)
        uiMediaController.bindViewToRewind(customButtonView, 10000)

        //przewiń 30s do tyłu
        customButtonView = getButtonImageViewAt(1)
        myCustomUiController = MyCustomUIController(customButtonView)
        myCustomUiController.updateButtonImage(R.drawable.cast_ic_expanded_controller_rewind30)
        uiMediaController.bindViewToUIController(customButtonView, myCustomUiController)
        uiMediaController.bindViewToRewind(customButtonView, 30000)

        //przewiń 30s do przodu
        customButtonView = getButtonImageViewAt(2)
        myCustomUiController = MyCustomUIController(customButtonView)
        myCustomUiController.updateButtonImage(R.drawable.cast_ic_expanded_controller_forward30)
        uiMediaController.bindViewToUIController(customButtonView, myCustomUiController)
        uiMediaController.bindViewToForward(customButtonView, 30000)

        //przewiń do przodu 10s
        customButtonView = getButtonImageViewAt(3)
        myCustomUiController = MyCustomUIController(customButtonView)
        myCustomUiController.updateButtonImage(R.drawable.cast_ic_notification_forward10)
        uiMediaController.bindViewToUIController(customButtonView, myCustomUiController)
        uiMediaController.bindViewToForward(customButtonView, 10000)

        menuInflater.inflate(R.menu.expanded_controller, menu)
        CastButtonFactory.setUpMediaRouteButton(this, menu, R.id.media_route_menu_item)
        return true
    }
}