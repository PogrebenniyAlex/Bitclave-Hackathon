package com.rejoice.entity.userData;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Area {

    private Geo lTgeo;
    private Geo rTgeo;
    private Geo lBgeo;
    private Geo rBgeo;

    private List<Long> users;

    public boolean isOwner(Geo point){

        if(users == null){
            users = new ArrayList<>();
        }

        Double y = point.getY();
        Double x = point.getX();

        if((y < lTgeo.getY() && x > lTgeo.getX()) && (y > lBgeo.getY() && x < rBgeo.getX())){
            boolean contains = users.contains(point.getUser().getId());
            if(!contains){
                users.add(point.getUser().getId());
            }
            return true;
        }
        return false;
    }

    public int count(){return users.size();}

    public Geo[] toArray(){
        Geo[] geos = new Geo[4];
        geos[0] = lTgeo;
        geos[1] = rTgeo;
        geos[2] = rBgeo;
        geos[3] = lBgeo;

        return geos;
    }

    @Override
    public String toString() {
        return "Area{" +
                "lTgeo=" + lTgeo +
                ", rTgeo=" + rTgeo +
                ", lBgeo=" + lBgeo +
                ", rBgeo=" + rBgeo +
                '}';
    }
}
