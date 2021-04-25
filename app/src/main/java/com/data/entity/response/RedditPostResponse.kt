package com.data.entity.response

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize
import kotlinx.android.parcel.RawValue

@Parcelize
data class RedditPostResponse (
    var kind: String = "Listing",
    var data: PostData? = null,
    var errorResponse: @RawValue ErrorResponse? = null
): Parcelable

@Parcelize
data class PostData(
    var modHash: String? = null,
    var dist: Int? = null,
    var children: List<PostItems>? = null,
    var after: String? = null,
    var before: String? = null
): Parcelable

@Parcelize
data class PostItems(
    var kind: String? = null,
    var data: RedditPostContent? = null
): Parcelable

@Parcelize
data class RedditPostContent(
    @SerializedName("author_fullname")
    var authorFullName: String? = null,
    var title: String? = null,
    var thumbnail: String? = null,
    var score: String? = null,
    var author: String? = null,
    var permalink: String? = null
): Parcelable