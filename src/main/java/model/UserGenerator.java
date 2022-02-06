package model;

import org.apache.commons.lang3.RandomStringUtils;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;

public class UserGenerator {

    public static final String EMAIL_POSTFIX = "@yandex.ru";

    /*public static String getCourierLogin() {
        return randomAlphabetic(10);
    }

    public static String getCourierPassword() {
        return randomAlphabetic(10);
    } */

    public static User getRandomUser() {
        // с помощью библиотеки RandomStringUtils генерируем имэйл
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        String email = RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String password = RandomStringUtils.randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем имя пользователя
        String name = RandomStringUtils.randomAlphabetic(10);
        User user = new User(email, password, name);
        return user;
    }

    public static String getUserEmail() {
        // с помощью библиотеки RandomStringUtils генерируем имэйл
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        return RandomStringUtils.randomAlphabetic(10) + EMAIL_POSTFIX;
    }

    public static String getUserPassword() {
        // с помощью библиотеки RandomStringUtils генерируем пароль
        return RandomStringUtils.randomAlphabetic(10);
    }

    public static String getUserName() {
        // с помощью библиотеки RandomStringUtils генерируем имя пользователя
        return RandomStringUtils.randomAlphabetic(10);
    }


/*
    public static Courier getRandomCourierWithObligatoryField() {
        // метод randomAlphabetic генерирует строку, состоящую только из букв, в качестве параметра передаём длину строки
        String courierLogin = randomAlphabetic(10);
        // с помощью библиотеки RandomStringUtils генерируем пароль
        String courierPassword = randomAlphabetic(10);
        Courier courier = new Courier(courierLogin, courierPassword);
        return courier;
    }



    public static Courier getRandomCourierWithPasswordOnly() {
        return new Courier().setPassword(randomAlphabetic(10));
    }

    public static Courier getRandomCourierWithFirstNameOnly() {
        return new Courier().setFirstName(randomAlphabetic(10));
    }*/
}