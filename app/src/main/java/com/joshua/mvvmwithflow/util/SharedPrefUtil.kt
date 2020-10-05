package com.joshua.mvvmwithflow.util

import android.content.Context
import android.content.SharedPreferences

class SharedPrefUtil(context: Context) {

    private val TAG = "SharedPrefUtil"

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("GeneralPreferences", Context.MODE_PRIVATE)

    fun saveString(key: String, value: String?) {
        val editor = sharedPreferences.edit()
        if (value == null) {
            editor?.remove(key)
        } else {
            editor?.putString(key, value)
        }
        editor?.apply()
    }

    fun saveLong(key: String, value: Long?) {
        val editor = sharedPreferences.edit()
        if (value == null) {
            editor?.remove(key)
        } else {
            editor?.putLong(key, value)
        }
        editor?.apply()
    }

    fun saveInt(key: String, value: Int?) {
        val editor = sharedPreferences.edit()
        if (value == null) {
            editor?.remove(key)
        } else {
            editor?.putInt(key, value)
        }
        editor?.apply()
    }

    fun saveFloat(key: String, value: Float?) {
        val editor = sharedPreferences.edit()
        if (value == null) {
            editor?.remove(key)
        } else {
            editor?.putFloat(key, value)
        }
        editor?.apply()
    }

    fun saveBoolean(key: String, value: Boolean?) {
        val editor = sharedPreferences.edit()
        if (value == null) {
            editor?.remove(key)
        } else {
            editor?.putBoolean(key, value)
        }
        editor?.apply()
    }


    fun loadString(key: String?, defaultValue: String?): String? {
        return if (key == null) {
            null
        } else {
            val value = sharedPreferences.getString(key, defaultValue)
            value
        }
    }

    fun loadLong(key: String?, defaultValue: Long): Long {
        return if (key == null) {
            defaultValue
        } else {
            val value = sharedPreferences.getLong(key, defaultValue)
            return value
        }
    }

    fun loadInt(key: String?, defaultValue: Int): Int {
        return if (key == null) {
            defaultValue
        } else {
            val value = sharedPreferences.getInt(key, defaultValue)
            value
        }
    }

    fun loadFloat(key: String?, defaultValue: Float): Float {
        return if (key == null) {
            defaultValue
        } else {
            val value = sharedPreferences.getFloat(key, defaultValue)
            value
        }
    }

    fun loadBoolean(key: String?, defaultValue: Boolean): Boolean {
        return if (key == null) {
            defaultValue
        } else {
            val value = sharedPreferences.getBoolean(key, defaultValue)
            value
        }
    }

}