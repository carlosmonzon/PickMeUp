package com.belatrix.pickmeup.enums;

/**
 * Created by gzavaleta on 09/05/16.
 */
public enum Destination {
    SAN_ISIDRO("san_isidro"), LINCE("lince"), CALLAO("callao"), CHORRILLOS("chorrillos"), SAN_BORJA(
            "san_borja"), CERCADO_DE_LIMA("cercado_de_lima");

    private String stringValue;
    Destination(String toString) {
        stringValue = toString;
    }

    @Override
    public String toString() {
        return stringValue;
    }

    public static Destination getValue(String value) {
        switch (value) {
            case "san_isidro":
                return SAN_ISIDRO;
            case "lince":
                return LINCE;
            case "callao":
                return CALLAO;
            case "chorrillos":
                return CHORRILLOS;
            case "san_borja":
                return SAN_BORJA;
            case "cercado_de_lima":
                return CERCADO_DE_LIMA;
            default:
                throw new IllegalArgumentException();
        }
    }

}
