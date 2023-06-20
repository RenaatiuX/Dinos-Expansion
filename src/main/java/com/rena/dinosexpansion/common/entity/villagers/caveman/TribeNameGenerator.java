package com.rena.dinosexpansion.common.entity.villagers.caveman;

import java.util.Random;

public class TribeNameGenerator {
    private static final String[] ADJECTIVES = {"Brave", "Cunning", "Fierce", "Gentle", "Noble", "Proud", "Spirited", "Swift", "Wise", "Agile", "Courageous", "Daring", "Fearless", "Mighty", "Resolute", "Savage", "Stalwart", "Tenacious", "Valiant"};

    private static final String[] NOUNS = {"Bear", "Eagle", "Elk", "Fox", "Hawk", "Lion", "Owl", "Panther", "Raven", "Wolf", "Buffalo", "Coyote", "Deer", "Moose", "Mustang", "Turtle", "Salmon", "Heron", "Crane", "Snake"};

    private static final String[] TRIBE_SUFFIXES = {"Tribe", "Clan", "Nation", "People"};

    private static final Random RANDOM = new Random();

    public static String generateTribeName() {
        String adjective = ADJECTIVES[RANDOM.nextInt(ADJECTIVES.length)];
        String noun = NOUNS[RANDOM.nextInt(NOUNS.length)];
        String tribeSuffix = TRIBE_SUFFIXES[RANDOM.nextInt(TRIBE_SUFFIXES.length)];

        return adjective + " " + noun + " " + tribeSuffix;
    }
}
