package com.gcu.base.network

import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor
import com.gcu.base.BulletContext

/**
 * description:
 *
 * @author Db_z
 * @Date 2022/01/22 16:28
 */
object CookieClass {

    /**Cookie*/
    private val cookiePersists = SharedPrefsCookiePersistor(BulletContext.getApplication())
    val cookieJar = PersistentCookieJar(SetCookieCache(), cookiePersists)

    /**清除Cookie*/
    fun clearCookie() = cookieJar.clear()

    /**是否有Cookie*/
    fun hasCookie() = cookiePersists.loadAll().isNotEmpty()
}