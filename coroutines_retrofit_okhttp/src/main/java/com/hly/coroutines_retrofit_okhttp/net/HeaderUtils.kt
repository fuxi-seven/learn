import java.util.Random


object HeaderUtils {

    /**
     * 获取唯一序列号,格式 产品ID+UID+UNIX时间戳+6位随机数 产品id = KHKJARD
     * @return
     */
    val seqnum: String = ""

    /**
     * 获取Header头信息
     * @return
     */
    fun generalHeaders(): Map<String, String>? {
        return null
    }

    /**
     * 获取num位随机数
     * @param num
     * @return
     */
    fun getRandom(num: Int): String {
        val sb = StringBuilder()
        val random = Random()
        for (i in 0 until num) {
            sb.append(random.nextInt(9))
        }
        return sb.toString()
    }
}
