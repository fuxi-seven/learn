class MainRepository {

    suspend fun getDataFromServer(): JsonBean {
        return RetrofitRequest.instance.retrofitService.json()
    }
}