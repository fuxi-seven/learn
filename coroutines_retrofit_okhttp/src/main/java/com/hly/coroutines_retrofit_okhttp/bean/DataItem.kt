import com.google.gson.annotations.SerializedName

data class DataItem(@SerializedName("imagePath")
                    val imagePath: String = "",
                    @SerializedName("id")
                    val id: Int = 0,
                    @SerializedName("isVisible")
                    val isVisible: Int = 0,
                    @SerializedName("title")
                    val title: String = "",
                    @SerializedName("type")
                    val type: Int = 0,
                    @SerializedName("url")
                    val url: String = "",
                    @SerializedName("desc")
                    val desc: String = "",
                    @SerializedName("order")
                    val order: Int = 0)