package com.codezcook.archiplanner.data

import android.view.View

data class PlanResponseItem(
    val created_at: String = "",
    val facing: String = "",
    val id: String = "",
    val images: String? = "",
    val name: String = "",
    val room: String = "",
    val status: String = "",
    val tags: String? = "",
    val updated_at: String = ""
)
data class PlanShareData(
    val planResponseItem: PlanResponseItem,
    val view : View
)
data class ElevationShareData(
    val elevationResponseItem: ElevationResponseItem,
    val view : View
)
data class PlanFavtShareData(
    val planResponseItem: SavePlanResponseItemItem,
    val view : View
)