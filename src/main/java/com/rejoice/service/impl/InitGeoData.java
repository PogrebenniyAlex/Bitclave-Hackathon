package com.rejoice.service.impl;

import com.rejoice.dao.userData.GeoDao;
import com.rejoice.dao.userData.TargetDataDao;
import com.rejoice.entity.enums.*;
import com.rejoice.entity.user.User;
import com.rejoice.entity.userData.Geo;
import com.rejoice.entity.userData.TargetData;
import com.rejoice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

import java.util.List;
import java.util.Random;

//@Service
public class InitGeoData implements ApplicationRunner{

    @Autowired
    private UserService userService;

    @Autowired
    private TargetDataDao targetDataDao;

    @Autowired
    private GeoDao geoDao;

    private GeoPoint[][] positions = {
            {       new GeoPoint(50.449256, 30.511848),
                    new GeoPoint(50.448144, 30.521898),
                    new GeoPoint(50.443957, 30.511734),
                    new GeoPoint(50.443957, 30.521065)
            },
            {       new GeoPoint(50.460076, 30.522901),
                    new GeoPoint(50.461129, 30.526427),
                    new GeoPoint(50.458057, 30.525698),
                    new GeoPoint(50.458671, 30.527431)
            },
            {       new GeoPoint(50.466253, 30.512914),
                    new GeoPoint(50.467389, 30.514482),
                    new GeoPoint(50.464645, 30.515600),
                    new GeoPoint(50.465708, 30.517414)
            },
            {       new GeoPoint(50.571817, 30.308865),
                    new GeoPoint(50.586292, 30.665981),
                    new GeoPoint(50.357738, 30.397116),
                    new GeoPoint(50.388168, 30.702949)
            }
    };

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {

        List<TargetData> byCity = targetDataDao.findByCity(City.Kyiv);

        for (TargetData targetData : byCity) {
            int range = randomNumberInRange(10, 30);

            int i = randomNumberInRange(0, 2);
            User user = targetData.getUser();

            for(int j =0; j < range; j++){
                GeoPoint geoPoint = generateDots(i);
                Geo geo = new Geo(geoPoint.y, geoPoint.x);

                geo.setUser(user);

                geoDao.saveAndFlush(geo);
            }
        }
    }

    private GeoPoint generateDots(){
        int i = randomNumberInRange(0, 2);

        return generateDots(i);
    }

    private GeoPoint generateDots(int i){

        GeoPoint[] points = positions[i];

        double yMid;
        double xMid;

//        double leftSide;
//        double rightSide;
//        double topSide;
//        double bottomSide;

//        leftSide = points[0].x < points[3].x ? points[0].x : points[3].x;
//        rightSide = points[1].y > points[2].y ? points[1].y : points[2].y;
//        topSide = points[0].y > points[1].y ? points[0].y : points[1].y;
//        bottomSide = points[2].y < points[3].y ? points[2].y : points[3].y;
//
//        boolean b;
//
//        do{
//            double top = randomDouble(points[0].x, points[1].x);
//            double right = randomDouble(points[2].y, points[1].y);
//            double bottom = randomDouble(points[2].x, points[3].x);
//            double left = randomDouble(points[3].y, points[0].y);
//
//            yMid = (top + bottom) / 2;
//            xMid = (right + left) / 2;
//
//            b = ((yMid < topSide) && (yMid > bottomSide)) && ((xMid < rightSide) && (xMid > leftSide));
//        }while (!b);

        double top = randomDouble(points[0].x, points[1].x);
        double right = randomDouble(points[2].y, points[1].y);
        double bottom = randomDouble(points[2].x, points[3].x);
        double left = randomDouble(points[3].y, points[0].y);

        yMid = (top + bottom) / 2;
        xMid = (right + left) / 2;

        System.out.println("i = " + i);
        return new GeoPoint(yMid, xMid);
    }

    private int randomNumberInRange(int min, int max) {
        Random random = new Random();
        return random.nextInt((max - min) + 1) + min;
    }

    double randomDouble(double min, double max) {
        if(min > max){
            return 0;
        }

        Random r = new Random();
        return min + (max - min) * r.nextDouble();
    }

    @Data
    @AllArgsConstructor
    @ToString
    private class GeoPoint{
        private Double y;
        private Double x;
    }
}

