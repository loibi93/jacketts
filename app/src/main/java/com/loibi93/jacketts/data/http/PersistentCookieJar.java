package com.loibi93.jacketts.data.http;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

public class PersistentCookieJar implements CookieJar {
    private Cookie secureCookie = null;

    private static boolean isCookieExpired(Cookie cookie) {
        return cookie.expiresAt() < System.currentTimeMillis();
    }

    @NotNull
    @Override
    public List<Cookie> loadForRequest(@NotNull HttpUrl httpUrl) {
        List<Cookie> result = new ArrayList<>();
        if (secureCookie != null && !isCookieExpired(secureCookie)) {
            result.add(secureCookie);
        } else {
            secureCookie = null;
        }
        return result;
    }

    @Override
    public void saveFromResponse(@NotNull HttpUrl httpUrl, @NotNull List<Cookie> list) {
        if (secureCookie != null) {
            return;
        }
        for (Cookie cookie : list) {
            if (cookie.name().contentEquals("Jackett")) {
                secureCookie = cookie;
            }
        }
    }

    public boolean isLoggedIn() {
        return secureCookie != null;
    }
}
