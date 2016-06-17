package com.belatrix.pickmeup.Persistence;

import android.provider.BaseColumns;

/**
 * Created by gzavaleta on 16/06/16.
 */
public class RouteFavoriteContract {

    public RouteFavoriteContract() {}

    public static abstract class FavoriteEntry implements BaseColumns{
        public static final String TABLE_NAME = "favoriteRoutes";
        public static final String COLUMN_NAME_DEPARTURE = "departure";
        public static final String COLUMN_NAME_DESTINE = "destine";
        public static final String COLUMN_NAME_COST = "cost";
        public static final String COLUMN_NAME_PAYMENT_TYPE = "paymentType";
        public static final String COLUMN_NAME_DEPARTURE_TIME = "departureTime";
        public static final String COLUMN_NAME_CONTACT = "contact";
        public static final String COLUMN_NAME_ADDRESS = "address";
    }
}
