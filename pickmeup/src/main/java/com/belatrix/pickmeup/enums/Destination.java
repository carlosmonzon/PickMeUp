package com.belatrix.pickmeup.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzavaleta on 09/05/16.
 */
public enum Destination {
    SAN_ISIDRO("San Isidro"), LINCE("Lince"), CALLAO("Callao"), CHORRILLOS("Chorrillos"), SAN_BORJA(
            "San Borja"), CERCADO_DE_LIMA("Cercado de Lima");

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
            case "San Isidro":
                return SAN_ISIDRO;
            case "Lince":
                return LINCE;
            case "Callao":
                return CALLAO;
            case "Chorrillos":
                return CHORRILLOS;
            case "San Borja":
                return SAN_BORJA;
            case "Cercado de Lima":
                return CERCADO_DE_LIMA;
            default:
                throw new IllegalArgumentException();
        }
    }

    public static List<String> getList(){
        List<String> listEnum = new ArrayList<>();
        Destination[] values = Destination.values();
        for (int i = 0; i < values.length; i++) {
            listEnum.add(values[i].toString());
        }
        return listEnum;
    }

}
