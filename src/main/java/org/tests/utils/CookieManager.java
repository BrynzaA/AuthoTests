package org.tests.utils;

import org.openqa.selenium.Cookie;

import java.io.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

public class CookieManager {

    private static final String COOKIES_FILE_PATH = "target/cookies/cookies.txt";


    public static void saveCookiesToFile(Set<Cookie> cookies) {
        try {
            File cookiesDir = new File("target/cookies/");
            if (!cookiesDir.exists()) {
                cookiesDir.mkdirs();
            }

            FileWriter fileWriter = new FileWriter(COOKIES_FILE_PATH);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            for (Cookie cookie : cookies) {
                bufferedWriter.write(cookieToString(cookie));
                bufferedWriter.newLine();
            }

            bufferedWriter.close();
            fileWriter.close();

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении куков: " + e.getMessage());
        }
    }


    public static Set<Cookie> loadCookiesFromFile() {
        Set<Cookie> cookies = new HashSet<>();

        try {
            File cookiesFile = new File(COOKIES_FILE_PATH);

            if (!cookiesFile.exists()) {
                return cookies;
            }

            FileReader fileReader = new FileReader(COOKIES_FILE_PATH);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                Cookie cookie = stringToCookie(line);
                if (cookie != null) {
                    cookies.add(cookie);
                }
            }

            bufferedReader.close();
            fileReader.close();


        } catch (IOException e) {
            throw new RuntimeException("Ошибка при загрузке куков из файла: " + e.getMessage());
        }

        return cookies;
    }


    public static void clearCookiesFile() {
        try {
            File cookiesFile = new File(COOKIES_FILE_PATH);

            if (cookiesFile.exists()) {
                FileWriter fileWriter = new FileWriter(COOKIES_FILE_PATH, false);
                fileWriter.write("");
                fileWriter.close();
            }

        } catch (IOException e) {
            throw new RuntimeException("Ошибка при очистке файла с куками: " + e.getMessage());
        }
    }


    private static String cookieToString(Cookie cookie) {
        StringBuilder sb = new StringBuilder();
        sb.append(cookie.getName()).append(";")
                .append(cookie.getValue()).append(";")
                .append(cookie.getDomain()).append(";")
                .append(cookie.getPath()).append(";")
                .append(cookie.getExpiry() != null ? cookie.getExpiry().getTime() : "null").append(";")
                .append(cookie.isSecure()).append(";")
                .append(cookie.isHttpOnly());
        return sb.toString();
    }


    private static Cookie stringToCookie(String cookieString) {
        try {
            StringTokenizer tokenizer = new StringTokenizer(cookieString, ";");

            if (tokenizer.countTokens() < 7) {
                return null;
            }

            String name = tokenizer.nextToken();
            String value = tokenizer.nextToken();
            String domain = tokenizer.nextToken();
            String path = tokenizer.nextToken();
            String expiryStr = tokenizer.nextToken();
            String secureStr = tokenizer.nextToken();
            String httpOnlyStr = tokenizer.nextToken();

            Date expiry = null;
            if (!"null".equals(expiryStr)) {
                try {
                    long expiryTime = Long.parseLong(expiryStr);
                    expiry = new Date(expiryTime);
                } catch (NumberFormatException e) {
                    throw new RuntimeException("Ошибка парсинга expiry: " + expiryStr);
                }
            }

            boolean isSecure = Boolean.parseBoolean(secureStr);
            boolean isHttpOnly = Boolean.parseBoolean(httpOnlyStr);

            return new Cookie.Builder(name, value)
                    .domain(domain)
                    .path(path)
                    .expiresOn(expiry)
                    .isSecure(isSecure)
                    .isHttpOnly(isHttpOnly)
                    .build();

        } catch (Exception e) {
            return null;
        }
    }
}