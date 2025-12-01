package util

import java.net.URL

object InputUtil {
    private val classLoader: ClassLoader
        get() = Thread.currentThread().contextClassLoader

    fun getInput (day: Int, sample: Boolean): String {
        val path = "day$day/input${if (sample) ".sample" else ""}";
        val url = getPath (path);
        return url.readText(Charsets.UTF_8);
    }

    fun getPath (path: String): URL = classLoader.getResource (path)!!
}

fun <T> withInput (day: Int, sample: Boolean, func: (String) -> T?): T? {
    val input = InputUtil.getInput(day, sample)
    return func (input)
}

// EOF