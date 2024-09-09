package com.avinash.zapcom.demo.model

import com.google.gson.annotations.SerializedName

data class ProductItem(
    @SerializedName("title")
    val name: String?,

    @SerializedName("image")
    val imageURL: String?
)
