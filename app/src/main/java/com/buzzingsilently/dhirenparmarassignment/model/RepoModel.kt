package com.buzzingsilently.dhirenparmarassignment.model

import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.buzzingsilently.dhirenparmarassignment.database.BuiltByTypeConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repository")
data class RepoModel(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "author")
    @SerializedName("author")
    var author: String? = null,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    var avatar: String? = null,

    @TypeConverters(BuiltByTypeConverter::class)
    @ColumnInfo(name = "builtBy")
    @SerializedName("builtBy")
    var builtBy: List<BuiltBy>? = null,

    @ColumnInfo(name = "currentPeriodStars")
    @SerializedName("currentPeriodStars")
    var currentPeriodStars: Long? = null,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = null,

    @ColumnInfo(name = "forks")
    @SerializedName("forks")
    var forks: Long? = null,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    var language: String? = null,

    @ColumnInfo(name = "languageColor")
    @SerializedName("languageColor")
    var languageColor: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "stars")
    @SerializedName("stars")
    var stars: Long? = null,

    @ColumnInfo(name = "url")
    @SerializedName("url")
    var url: String? = null,

    @Ignore var isExpanded: Boolean = false
) {
    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<RepoModel>? =
            object : DiffUtil.ItemCallback<RepoModel>() {
                override fun areItemsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: RepoModel, newItem: RepoModel): Boolean {
                    return oldItem == newItem
                }
            }
    }

    //get avtar after necessary checking
    fun getAvtar() =
        if (!builtBy.isNullOrEmpty() && !builtBy!![0].avatar.isNullOrEmpty())
            builtBy!![0].avatar!!
        else if (!avatar.isNullOrEmpty())
            avatar!!
        else
            ""

    //get author name after necessary checking
    fun getAvtarName() =
        if (!author.isNullOrEmpty())
            author!!
        else
            "NA"

    //get repo name after necessary checking
    fun getRepoName() =
        if (!name.isNullOrEmpty())
            name!!
        else
            "NA"

    //get description after necessary checking
    fun getDesc() =
        if (!description.isNullOrEmpty())
            description!!
        else
            "NA"

    //get language after necessary checking
    fun getLang() =
        if (!language.isNullOrEmpty())
            language!!
        else
            ""

    //get language color after necessary checking
    fun getLangColor() =
        if (!languageColor.isNullOrEmpty())
            languageColor!!
        else
            "#FFFFFF"

    //get start count after necessary checking
    fun getStarCount() =
        if (stars != null && stars!! > 0)
            stars
        else
            0

    //get fork count after necessary checking
    fun getForkCount() =
        if (forks != null && forks!! > 0)
            forks
        else
            0
}

@Entity(
    tableName = "builtby",
    foreignKeys = [ForeignKey(
        entity = RepoModel::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("repo_id")
    )]
)
data class BuiltBy(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null,

    @ColumnInfo(name = "repo_id")
    var repo_id: Long? = null,

    @ColumnInfo(name = "avatar")
    @SerializedName("avatar")
    var avatar: String? = null,

    @ColumnInfo(name = "href")
    @SerializedName("href")
    var href: String? = null,

    @ColumnInfo(name = "username")
    @SerializedName("username")
    var username: String? = null
)