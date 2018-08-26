package com.rejoice.service.impl;

import com.rejoice.dao.userData.TargetDataDao;
import com.rejoice.entity.enums.*;
import com.rejoice.entity.user.User;
import com.rejoice.entity.userData.TargetData;
import com.rejoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.Random;

//@Service
public class InitUsersData implements ApplicationRunner{

    @Autowired
    private UserService userService;

    @Autowired
    private TargetDataDao targetDataDao;

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        int startInt = 101;

        for (int i =0; i < 900; i++){
            Gender gender = Gender.randomGender();
            City city = City.randomCity();
            Integer age = randomNumberInRange(18, 100);
            Integer salary = randomNumberInRange(200, 1500);
            Integer spending = randomNumberInRange(20, 200);
            Music music = Music.randomMusic();
            Drinks drinks = Drinks.randomDrinks();
            Food food = Food.randomFood();

            String email = "rejoice" + (startInt + i) + "@gmail.ua";
            String password = "password" + (startInt + i);

            User user = User.builder()
                    .email(email)
                    .password(password)
                    .build();

            user = userService.createNewUser(user);

            TargetData targetData = TargetData.builder()
                    .gender(gender)
                    .city(city)
                    .age(age)
                    .salary(salary)
                    .spending(spending)
                    .music(music)
                    .drinks(drinks)
                    .food(food)
                    .user(user)
                    .build();

            targetDataDao.saveAndFlush(targetData);
        }

    }

    private int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }
}

