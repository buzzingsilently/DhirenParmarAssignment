package com.buzzingsilently.dhirenparmarassignment.model


import androidx.recyclerview.widget.DiffUtil
import androidx.room.*
import com.buzzingsilently.dhirenparmarassignment.database.converter.OwnerConverter
import com.google.gson.annotations.SerializedName

@Entity(tableName = "Repository")
data class Repository(

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    var id: Int? = null,

    @ColumnInfo(name = "node_id")
    @SerializedName("node_id")
    var nodeId: String? = null,

    @ColumnInfo(name = "name")
    @SerializedName("name")
    var name: String? = null,

    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    var fullName: String? = null,

    @TypeConverters(OwnerConverter::class)
    @ColumnInfo(name = "owner")
    @SerializedName("owner")
    var owner: Owner? = null,

    @ColumnInfo(name = "private")
    @SerializedName("private")
    var isPrivate: Boolean? = null,

    @ColumnInfo(name = "description")
    @SerializedName("description")
    var description: String? = null,

    @ColumnInfo(name = "language")
    @SerializedName("language")
    var language: String? = null,

    @ColumnInfo(name = "stargazers_count")
    @SerializedName("stargazers_count")
    var stars: Long? = null,

    @ColumnInfo(name = "forks_count")
    @SerializedName("forks_count")
    var forks: Long? = null,

    @ColumnInfo(name = "is_watching")
    var isWatching: Boolean = false
) {

    companion object {
        var DIFF_CALLBACK: DiffUtil.ItemCallback<Repository>? =
            object : DiffUtil.ItemCallback<Repository>() {
                override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                    return oldItem == newItem
                }
            }
    }

    //get avtar after necessary checking
    fun getAvtar() = if (owner != null && !owner!!.avatarUrl.isNullOrEmpty()) owner!!.avatarUrl else ""

    //get owner name after necessary checking
    fun getOwnerName() =
        if (owner != null && !owner!!.login.isNullOrEmpty()) owner!!.login else "--NA--"

    //get repo name after necessary checking
    fun getRepoName() = if (!name.isNullOrEmpty()) name else "--NA--"

    //get description after necessary checking
    fun getDesc() =
        if (!description.isNullOrEmpty())
            description!!
        else
            "--NA--"

    //get language after necessary checking
    fun getLang() =
        if (!language.isNullOrEmpty())
            language!!
        else
            ""

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
    tableName = "Owner",
    foreignKeys = [ForeignKey(
        entity = Repository::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("node_id")
    )]
)
data class Owner(
    @ColumnInfo(name = "login")
    @SerializedName("login")
    var login: String? = null,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = false)
    @SerializedName("id")
    var id: Int? = null,

    @ColumnInfo(name = "node_id")
    @SerializedName("node_id")
    var nodeId: String? = null,

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    var avatarUrl: String? = null
)