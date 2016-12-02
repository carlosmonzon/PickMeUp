package com.belatrix.pickmeup.enums;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gzavaleta on 17/06/16.
 */
public enum PaymentType {
    CASH("Cash"), CREDIT("Credit");

    private String stringValue;
    PaymentType(String toString) {
        stringValue = toString;
    }

    public static PaymentType getValue(String value) {
        switch (value) {
            case "Cash":
                return CASH;
            case "Credit":
                return CREDIT;
            default:
                throw new IllegalArgumentException();
        }
    }
    @Override
    public String toString() {
        return stringValue;
    }

    public static List<String> getList(){
        List<String> listEnum = new ArrayList<>();
        PaymentType[] values = PaymentType.values();
        for (int i = 0; i < values.length; i++) {
            listEnum.add(values[i].toString());
        }
        return listEnum;
    }

}
