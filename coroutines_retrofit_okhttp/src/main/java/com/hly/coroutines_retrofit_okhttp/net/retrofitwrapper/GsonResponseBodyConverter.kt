import com.google.gson.Gson
import com.google.gson.TypeAdapter

import java.io.ByteArrayInputStream
import java.io.IOException
import java.io.InputStreamReader

import okhttp3.ResponseBody
import retrofit2.Converter


class GsonResponseBodyConverter<T> internal constructor(private val gson: Gson, private val adapter: TypeAdapter<T>) : Converter<ResponseBody, T> {

    @Throws(IOException::class)
    override fun convert(value: ResponseBody): T {
        val body = value.string()
        //        try {
        //            JSONObject jsonObject = new JSONObject(body);
        //            if(jsonObject.optInt("status") == 200){
        //                JSONObject jsonObject1 = new JSONObject();
        //                body = jsonObject1.toString();
        //            }
        //        } catch (JSONException e) {
        //            e.printStackTrace();
        //        }

        /**
         * 因为你只能对ResponseBody读取一次 ,
         * 如果你调用了response.body().string()两次或者response.body().charStream()两次就会出现这个异常,
         * 先调用string()再调用charStream()也不可以
         */
        val contentType = value.contentType()
        val inputStream = ByteArrayInputStream(body.toByteArray())
        val reader = InputStreamReader(inputStream, "UTF-8")
        val jsonReader = gson.newJsonReader(reader)
        value.use {
            return adapter.read(jsonReader)
        }
    }

}
