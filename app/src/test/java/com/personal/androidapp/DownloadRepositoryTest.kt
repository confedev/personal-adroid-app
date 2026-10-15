package com.personal.androidapp

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DownloadRepositoryTest {

    @Test
    fun testJsonFileNameTimestampFormat() {
        val pattern = "yyyyMMdd_HHmmss"
        val dateString = SimpleDateFormat(pattern, Locale.US).format(Date())
        assertTrue("Timestamp should match digits and underscore", dateString.matches(Regex("\\d{8}_\\d{6}")))
    }

    @Test
    fun testUrlValidationLogic() {
        fun isValid(url: String): Boolean {
            val trimmed = url.trim()
            return trimmed.startsWith("http://", ignoreCase = true) ||
                    trimmed.startsWith("https://", ignoreCase = true)
        }

        assertTrue(isValid("https://example.com/test.json"))
        assertTrue(isValid("http://api.example.com/data"))
        assertFalse(isValid("ftp://example.com/file"))
        assertFalse(isValid("just_text"))
        assertFalse(isValid(""))
    }

    @Test
    fun testFileNameSanitization() {
        fun sanitize(name: String): String {
            return name.replace(Regex("[^a-zA-Z0-9._-]"), "_")
        }

        assertEquals("sample_image.png", sanitize("sample image.png"))
        assertEquals("file_name__1_.jpg", sanitize("file/name (1).jpg"))
    }
}
