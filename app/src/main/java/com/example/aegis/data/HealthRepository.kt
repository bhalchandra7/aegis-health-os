package com.example.aegis.data

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.example.aegis.model.EmergencyProfile
import org.json.JSONArray
import org.json.JSONObject

class SecureHealthPreferences(context: Context) {
    private val masterKey = MasterKey.Builder(context)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    private val prefs: SharedPreferences = EncryptedSharedPreferences.create(
        "secure_health_prefs",
        masterKey,
        context,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun saveProfile(profile: EmergencyProfile) {
        val json = JSONObject().apply {
            put("name", profile.name)
            put("bloodType", profile.bloodType)
            put("allergies", JSONArray(profile.allergies))
            put("currentMedications", JSONArray(profile.currentMedications))
            put("conditions", JSONArray(profile.conditions))
            put("physician", profile.physician)
            put("directives", profile.directives)
            put("implantedDevices", JSONArray(profile.implantedDevices))
        }
        prefs.edit().putString(KEY_PROFILE, json.toString()).apply()
    }

    fun loadProfile(): EmergencyProfile? {
        val raw = prefs.getString(KEY_PROFILE, null) ?: return null
        return try {
            val json = JSONObject(raw)
            EmergencyProfile(
                name = json.optString("name", "Unknown"),
                bloodType = json.optString("bloodType", "Unknown"),
                allergies = jsonArrayToStringList(json.optJSONArray("allergies")),
                currentMedications = jsonArrayToStringList(json.optJSONArray("currentMedications")),
                conditions = jsonArrayToStringList(json.optJSONArray("conditions")),
                implantedDevices = jsonArrayToStringList(json.optJSONArray("implantedDevices")),
                physician = json.optString("physician", "Unknown"),
                directives = json.optString("directives", "")
            )
        } catch (_: Exception) {
            null
        }
    }

    fun clearProfile() {
        prefs.edit().remove(KEY_PROFILE).apply()
    }

    private fun jsonArrayToStringList(array: JSONArray?): List<String> {
        if (array == null) return emptyList()
        return List(array.length()) { index -> array.getString(index) }
    }

    companion object {
        private const val KEY_PROFILE = "encrypted_profile"
    }
}
