package nvl.com.mvvm

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
       var test = nvl.com.mvvm.ui.main.Test()
        test.list.add("")
        intArrayOf(1,3)
        assertEquals(test.list.size, 3)
    }
}

fun showTast()="Tastk"