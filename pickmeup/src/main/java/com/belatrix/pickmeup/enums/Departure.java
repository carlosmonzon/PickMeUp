package com.belatrix.pickmeup.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzavaleta on 09/05/16.
 */
public enum Departure {
    BELATRIX_SAN_ISIDRO("San Isidro"), BELATRIX_LA_MOLINA("La Molina");

    private String stringValue;

    Departure(String toString) {
        stringValue = toString;
    }

    public static Departure getValue(String value) {
        switch (value) {
            case "San Isidro":
                return BELATRIX_SAN_ISIDRO;
            case "La Molina":
                return BELATRIX_LA_MOLINA;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static List<String> getList() {
        List<String> listEnum = new ArrayList<>();
        Departure[] values = Departure.values();
        for (int i = 0; i < values.length; i++) {
            listEnum.add(values[i].toString());
        }
        return listEnum;
    }

    @Override
    public String toString() {
        return stringValue;
    }
}
