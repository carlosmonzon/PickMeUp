package com.belatrix.pickmeup.enums;

/**
 * Created by gzavaleta on 09/05/16.
 */
public enum Departure {
    BELATRIX_SAN_ISIDRO("San Isidro"), BELATRIX_LA_MOLINA("La Molina");

    private String stringValue;
    Departure(String toString) {
        stringValue = toString;
    }

    @Override
    public String toString() {
        return stringValue;
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
}
