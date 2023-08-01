package com.example.deezer.data.model.response

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.deezer.data.model.response.TableConstants.DURATION
import com.example.deezer.data.model.response.TableConstants.ID_FIELD
import com.example.deezer.data.model.response.TableConstants.IS_LIKED
import com.example.deezer.data.model.response.TableConstants.MD5_IMAGE
import com.example.deezer.data.model.response.TableConstants.PREVIEW
import com.example.deezer.data.model.response.TableConstants.TABLE_NAME
import com.example.deezer.data.model.response.TableConstants.TITLE
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = TABLE_NAME)
data class TrackModel(
    @PrimaryKey
    @ColumnInfo(name=ID_FIELD)
    val id: Long?,
    @ColumnInfo(name = TITLE)
    val title: String?,
    @ColumnInfo(name = DURATION)
    val duration: String?,
    @ColumnInfo(name = PREVIEW)
    val preview: String?,
    @ColumnInfo(name = MD5_IMAGE)
    val md5_image: String?,
    @ColumnInfo(name= IS_LIKED)
    val is_liked: Boolean?
): Parcelable

object TableConstants{
    const val ID_FIELD="id"
    const val TABLE_NAME="liked_tracks"
    const val TITLE="title_"
    const val DURATION="duration_"
    const val PREVIEW="preview_"
    const val MD5_IMAGE="md5_image_"
    const val IS_LIKED="is_liked_"
}