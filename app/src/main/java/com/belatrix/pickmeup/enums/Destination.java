package com.belatrix.pickmeup.enums;

/**
 * Created by gzavaleta on 09/05/16.
 */
public enum Destination {
    SAN_ISIDRO("sanisidro"),LINCE("lince"),CALLAO("callao"),CHORRILLOS("chorrillos"),SAN_BORJA("sanborja"),CERCADO_DE_LIMA("cercadodelima");

    private String imgName;

    Destination(String token){
        this.imgName = token;
    }

    public String getImgName(){
        return imgName;
    }
}
