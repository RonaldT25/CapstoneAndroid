package com.hfad.capstone.helper

import android.content.Context
import android.content.SharedPreferences
import com.hfad.capstone.R

class SessionManager (context: Context) {
    private var prefs: SharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE)

    companion object {
        const val USER_TOKEN = "user_token"
        const val PRODUCT_ID = "product_id"
        const val role = "role"
    }
    fun saveRole(role: String) {
        val editor = prefs.edit()
        editor.putString(role, role)
        editor.apply()
    }

    fun fetchRole(): String? {
        return prefs.getString(role, null)
    }

    fun saveAuthToken(token: String) {
        val editor = prefs.edit()
        editor.putString(USER_TOKEN, token)
        editor.apply()
    }

    fun fetchAuthToken(): String? {
        return prefs.getString(USER_TOKEN, null)
    }
    fun saveProductId(id: String) {
        val editor = prefs.edit()
        editor.putString(PRODUCT_ID, id)
        editor.apply()
    }

    fun fetchProductId(): String? {
        return prefs.getString(PRODUCT_ID, null)
    }
}