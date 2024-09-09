package com.avinash.zapcom.demo.model

import com.google.gson.annotations.SerializedName

data class CatalogItem(
    @SerializedName("sectionType")
    val section: String?,

    @SerializedName("items")
    val items: List<ProductItem>?
) {
    fun catalogSectionType(): SectionType {
        return if (!section.isNullOrBlank()) {
            SectionType.values().find { it.type.equals(section, ignoreCase = true) } ?: SectionType.UNKNOWN
        } else SectionType.UNKNOWN
    }
}

enum class SectionType(
    val type: String
) {
    BANNER("banner"),
    SPLIT_BANNER("splitBanner"),
    HORIZONTAL_SCROLL("horizontalFreeScroll"),
    UNKNOWN("")
}
