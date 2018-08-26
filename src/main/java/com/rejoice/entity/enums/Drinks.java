package com.rejoice.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Drinks {
    Beer, Wine, Vodka;

    private static final List<Drinks> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Drinks randomDrinks()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
