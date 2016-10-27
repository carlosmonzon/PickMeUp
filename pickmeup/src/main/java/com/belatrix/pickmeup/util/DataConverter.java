package com.belatrix.pickmeup.util;

import com.belatrix.pickmeup.model.MyRoute;
import com.belatrix.pickmeup.model.MyUser;
import com.belatrix.pickmeup.model.RouteDto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import retrofit2.Response;

/**
 * Created by angel on 23/10/16.
 */

public class DataConverter {

    public static MyRoute convertRouteData(String routeId, RouteDto routeDto) {
        MyRoute route = new MyRoute(routeDto);
        route.setId(routeId);

        if(routeDto.getMembers() != null){
            List<MyUser> passangers = new ArrayList<>();
            Map<String, MyUser> mapMembers = routeDto.getMembers();
            for (Map.Entry<String, MyUser> entryMember : mapMembers.entrySet())
            {
                MyUser user = entryMember.getValue();
                user.setId(entryMember.getKey());
                passangers.add(user);
            }
            route.setPassengers(passangers);
        }
        return route;
    }
}
