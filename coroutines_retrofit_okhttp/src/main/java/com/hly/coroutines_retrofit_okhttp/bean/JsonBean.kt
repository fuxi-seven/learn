import com.google.gson.annotations.SerializedName

data class JsonBean(@SerializedName("data")
                    val data: List<DataItem>?,
                    @SerializedName("errorCode")
                    val errorCode: Int = 0,
                    @SerializedName("errorMsg")
                    val errorMsg: String = "")