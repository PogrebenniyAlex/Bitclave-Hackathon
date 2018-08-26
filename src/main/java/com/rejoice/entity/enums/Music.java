package com.rejoice.entity.enums;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Music {
    Rock, Jazz, Rap, Pop;

    private static final List<Music> VALUES =
            Collections.unmodifiableList(Arrays.asList(values()));
    private static final int SIZE = VALUES.size();
    private static final Random RANDOM = new Random();

    public static Music randomMusic()  {
        return VALUES.get(RANDOM.nextInt(SIZE));
    }
}
