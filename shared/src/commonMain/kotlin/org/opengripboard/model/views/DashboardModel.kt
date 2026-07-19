package org.opengripboard.model.views

import org.opengripboard.model.NavigationManager

class DashboardModel(
    val navigation: NavigationManager
){
    fun onLocalAppSettingsSelected(){
        navigation.onSettingsSelected()
    }
}
