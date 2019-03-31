package com.bapspatil.scaredpreferences

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

/*
** Created by Bapusaheb Patil {@link https://bapspatil.com}
*/

class ScaredPreferences constructor(private val sharedPrefs: SharedPreferences) {

    class Builder {
        private var sharedPrefs: SharedPreferences? = null

        fun withSharedPreferences(sharedPreferences: SharedPreferences) = apply {
            this.sharedPrefs = sharedPreferences
        }

        fun withDefaultSharedPreferences(context: Context) = apply {
            this.sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context)
        }

        fun build(): ScaredPreferences {
            val prefs = sharedPrefs
            return prefs?.let { ScaredPreferences(it) }!!
        }
    }

    fun <T> get(key: String, defaultVal: T, kclass: KClass<*>): T {
        return when (kclass) {
            Boolean::class -> sharedPrefs.getBoolean(key, defaultVal as? Boolean ?: false) as T
            Int::class -> sharedPrefs.getInt(key, defaultVal as? Int ?: -1) as T
            Long::class -> sharedPrefs.getLong(key, defaultVal as? Long ?: -1) as T
            Float::class -> sharedPrefs.getFloat(key, defaultVal as? Float ?: -1f) as T
            String::class -> sharedPrefs.getString(key, defaultVal as? String) as T
            else -> throw Exception("Unable to get value stored for $key. Please check the data type.")
        }
    }

    fun getAll() : MutableMap<String, *>? {
        return sharedPrefs.all
    }

    fun set(key: String, value: Any?) {
        when (value) {
            is String? -> edit { putString(key, value) }
            is Int -> edit { putInt(key, value) }
            is Boolean -> edit { putBoolean(key, value) }
            is Float -> edit { putFloat(key, value) }
            is Long -> edit { putLong(key, value) }
            else -> throw Exception("Unable to set value for $key. Please check the data type.")
        }
    }

    inline fun <reified T> get(key: String, defaultValue: T? = null): T? {
        return this.get(key, defaultValue, T::class)
    }

    inline fun <reified T> delegate(key: String? = null, defaultValue: T): ReadWriteProperty<Any, T> {
        return object : ReadWriteProperty<Any, T> {
            override fun getValue(thisRef: Any, property: KProperty<*>) = this@ScaredPreferences.get(key ?: property.name, defaultValue, T::class)

            override fun setValue(thisRef: Any, property: KProperty<*>, value: T) = this@ScaredPreferences.set(key ?: property.name, value)
        }
    }

    private inline fun edit(sharedPrefEditorMethod: SharedPreferences.Editor.() -> Unit) {
        val editor = sharedPrefs.edit()
        sharedPrefEditorMethod(editor)
        editor.apply()
    }

    fun remove(key: String) {
        edit { remove(key) }
    }

    fun clear() {
        edit { clear() }
    }

    fun contains(key: String): Boolean {
        return sharedPrefs.contains(key)
    }
}