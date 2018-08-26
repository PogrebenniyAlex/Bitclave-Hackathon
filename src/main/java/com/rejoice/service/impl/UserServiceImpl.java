package com.rejoice.service.impl;

import com.rejoice.dao.user.*;
import com.rejoice.dao.userData.GeoDao;
import com.rejoice.dao.userData.TargetDataDao;
import com.rejoice.entity.enums.*;
import com.rejoice.entity.token.Token;
import com.rejoice.entity.user.*;
import com.rejoice.entity.userData.Area;
import com.rejoice.entity.userData.Geo;
import com.rejoice.entity.userData.TargetData;
import com.rejoice.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
public class UserServiceImpl implements UserService {

    private Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SecurityService securityService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private TargetDataDao targetDataDao;

    @Autowired
    private GeoDao geoDao;


    @Override
    public User createNewUser(User user) {
        Role role = findRoleByName(Roles.USER.name());
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setAuthorities(roles);

        user.setUsername(user.getEmail());
        user.setAccountNonExpired(true);
        user.setEnabled(false);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setDate_created(new Date());
        user.setDate_updated(new Date());

        User savedUser = saveUser(user);

        securityService.autoLoginUser(savedUser);

        return savedUser;
    }

    @Override
    public User createNewUserVendor(User user) {
        Role role = findRoleByName(Roles.VENDOR.name());
        List<Role> roles = new ArrayList<>();
        roles.add(role);

        user.setAuthorities(roles);

        user.setUsername(user.getEmail());
        user.setAccountNonExpired(true);
        user.setEnabled(false);
        user.setCredentialsNonExpired(true);
        user.setAccountNonLocked(true);

        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));

        user.setDate_created(new Date());
        user.setDate_updated(new Date());

        User savedUser = saveUser(user);

        securityService.autoLoginUser(savedUser);

        return savedUser;
    }

    @Override
    public User saveUser(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    public User findUsersByEmail(String email) {
        return userDao.findUsersByEmail(email);
    }

    @Override
    public Role findRoleByName(String name) {
        return roleDao.findRoleByAuthority(name);
    }

    @Override
    public Token createUser(User user) {

        User newUser = createNewUser(user);
        return tokenService.createToken(newUser);
    }

    @Override
    public Map<String, Object> search(Map<String, Object> request) {
        String category = (String)request.get("category");
        String city = (String)request.get("city");

        System.out.println("category : "  + category);
        System.out.println("city : "  + city);

        Map<String, Boolean> ages = (Map<String, Boolean>) request.get("age");

        for (Map.Entry entry : ages.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        Map<String, Object> interests = (Map<String, Object>) request.get("interests");
        Map<String, Boolean> drinks = (Map<String, Boolean>)interests.get("drinks");

        for (Map.Entry entry : drinks.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        Map<String, Boolean> music = (Map<String, Boolean>)interests.get("music");

        for (Map.Entry entry : music.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        Map<String, Boolean> food = (Map<String, Boolean>)interests.get("food");

        for (Map.Entry entry : food.entrySet()) {
            System.out.println(entry.getKey() + ", " + entry.getValue());
        }

        String salary = (String)request.get("salary");

        System.out.println(salary);

        Map<String, Boolean> sex = (Map<String, Boolean>)request.get("sex");

        Boolean female = sex.get("female");
        Boolean male = sex.get("male");

        System.out.println("female : " + female);
        System.out.println("male : " + male);

        String waste = (String)request.get("waste");

        System.out.println("waste : "  + waste);

        City cityEnum = City.valueOf(city);
        List<TargetData> byCity = targetDataDao.findByCity(cityEnum);

        System.out.println("------------First :  " + byCity.size());

        Stream<TargetData> targetDataStream = byCity.stream();

//        switch (salary){
//            case "300-" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()<300);
//                break;
//            case "300-600" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()>=300&&targetData.getSalary()<600);
//                break;
//            case "600-800" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()>=600&&targetData.getSalary()<800);
//                break;
//            case "800-1000" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()>=800&&targetData.getSalary()<1000);
//                break;
//            case "1000+" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()>=1000);
//                break;
//        }

//        System.out.println("-------------------------------");
//        System.out.println("-------------------------------");
//
//        switch (waste){
//            case "50-" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()<50);
//                break;
//            case "50-100" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()>=50&&targetData.getSalary()<100);
//                break;
//            case "100-150" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()>=100&&targetData.getSalary()<150);
//                break;
//            case "150+" :
//                targetDataStream = targetDataStream.filter(targetData -> targetData.getSalary()>=150);
//                break;
//        }

        if(!female){
            targetDataStream = targetDataStream.filter(targetData -> targetData.getGender().name().equals(Gender.Male.name()));
        }

        if(!male){
            targetDataStream = targetDataStream.filter(targetData -> targetData.getGender().name().equals(Gender.Female.name()));
        }

        targetDataStream = targetDataStream.filter(targetData -> checkAge(targetData.getAge(), ages));
//        targetDataStream = targetDataStream.filter(targetData -> checkDrinks(targetData.getDrinks(), drinks));
//        targetDataStream = targetDataStream.filter(targetData -> checkMusic(targetData.getMusic(), music));
//        targetDataStream = targetDataStream.filter(targetData -> checkFood(targetData.getFood(), food));

//        targetDataStream.forEach(System.out::println);

        List<TargetData> targetDataList = targetDataStream.collect(Collectors.toList());
        targetDataList.forEach(System.out::println);

        System.out.println("Size : " + targetDataList.size());

        List<Area> areas = devideAreas();

        for (TargetData targetData : targetDataList) {
            User user = targetData.getUser();

            List<Geo> byUser = geoDao.findByUser(user);

            for (Geo geo : byUser) {

                double temp = geo.getY();
                geo.setY(geo.getX());
                geo.setX(temp);

                System.out.print("X: " + geo.getX());
                System.out.println(" Y: " + geo.getY());

                for (Area area : areas) {
                    boolean owner = area.isOwner(geo);
                    if(owner) break;
                }
            }
        }

        areas.forEach(System.out::println);

        return getResult(areas);
    }

    private boolean checkAge(int age, Map<String, Boolean> ages){
        Boolean aB = ages.get("18-24");
        Boolean aB1 = ages.get("25-34");
        Boolean aB2 = ages.get("35-44");
        Boolean aB3 = ages.get("45-54");
        Boolean aB4 = ages.get("55-64");
        Boolean aB5 = ages.get("65+");

        boolean b = age >= 18 && age <= 24;
        boolean b1 = age >= 25 && age <= 34;
        boolean b2 = age >= 35 && age <= 44;
        boolean b3 = age >= 45 && age <= 54;
        boolean b4 = age >= 55 && age <= 64;
        boolean b5 = age >= 65;

        return (b && aB) || (b1 && aB1) || (b2 && aB2) || (b3 && aB3) || (b4 && aB4) || (b5 && aB5);
    }

    private boolean checkDrinks(Drinks drinks, Map<String, Boolean> drinksMap){
        boolean beer = drinks.name().equals(Drinks.Beer.name());
        boolean vodka = drinks.name().equals(Drinks.Vodka.name());
        boolean wine = drinks.name().equals(Drinks.Wine.name());

        Boolean aB = drinksMap.get("Beer");
        Boolean aB1 = drinksMap.get("Vodka");
        Boolean aB2 = drinksMap.get("Wine");

        return (beer && aB) || (vodka && aB1) || (wine && aB2);
    }

    private boolean checkMusic(Music music, Map<String, Boolean> musicMap){
        boolean jazz = music.name().equals(Music.Jazz.name());
        boolean pop = music.name().equals(Music.Pop.name());
        boolean rap = music.name().equals(Music.Rap.name());
        boolean rock = music.name().equals(Music.Rock.name());

        Boolean aB = musicMap.get("Jazz");
        Boolean aB1 = musicMap.get("Pop");
        Boolean aB2 = musicMap.get("Rap");
        Boolean aB3 = musicMap.get("Rock");

        return (jazz && aB) || (pop && aB1) || (rap && aB2) || (rock && aB3);
    }

    private boolean checkFood(Food food, Map<String, Boolean> foodMap){
        boolean burgers = food.name().equals(Food.Burgers.name());
        boolean pizza = food.name().equals(Food.Pizza.name());
        boolean sushi = food.name().equals(Food.Sushi.name());

        Boolean aB = foodMap.get("Burgers");
        Boolean aB1 = foodMap.get("Pizza");
        Boolean aB2 = foodMap.get("Sushi");

        return (burgers && aB) || (pizza && aB1) || (sushi && aB2);
    }

    private Map<String, Object> getResult(List<Area> areaList){

        List<HashMap<String, Object>> areas = new ArrayList<>();

        areaList = areaList.stream().filter(area -> area.count()!=0).collect(Collectors.toList());

        int max = 0;

        for (Area areaL : areaList) {
            HashMap<String, Object> area = new HashMap<>();

            int count = areaL.count();
            if(count > max){
                max = count;
            }

            area.put("peoples", count);
            area.put("points", getPositions(areaL.toArray()));

            areas.add(area);
        }

        HashMap<String, Object> response = new HashMap<>();
        response.put("areas", areas);
        response.put("max", max);

        return response;
    }

    Object getPositions(Geo[] position){
        LinkedList<Double> list = new LinkedList<>();
        LinkedList<Double> list1 = new LinkedList<>();
        LinkedList<Double> list2 = new LinkedList<>();
        LinkedList<Double> list3 = new LinkedList<>();

        list.add(position[0].getY());
        list.add(position[0].getX());

        list1.add(position[1].getY());
        list1.add(position[1].getX());

        list2.add(position[2].getY());
        list2.add(position[2].getX());

        list3.add(position[3].getY());
        list3.add(position[3].getX());

        List<Object> result = new LinkedList<>();

        result.add(list);
        result.add(list1);
        result.add(list2);
        result.add(list3);

        return result;
    }

    List<Area> devideAreas(){
        int devider = 10;

        Geo lTop = new Geo(50.467639, 30.494597);
        Geo rTop = new Geo(50.467639, 30.524777);
        Geo lBottom = new Geo(50.441512, 30.494597);
        Geo rBottom = new Geo(50.441512, 30.524777);

        double xDev = (rTop.getX() - lTop.getX()) / devider;
        double yDev = (rTop.getY() - rBottom.getY()) / devider;

        List<Area> areas = new ArrayList<>();

        for(int i =0; i <= 10; i++){
            for(int j =0; j <= 10; j++){
                double yBottom = lBottom.getY() + yDev * i;
                double yTop = lBottom.getY() + yDev * (i+1);
                double xLeft = lBottom.getX() + xDev * j;
                double xRight = lBottom.getX() + xDev * (j+1);

                Geo lBottomA = new Geo(yBottom, xLeft);
                Geo rBottomA = new Geo(yBottom, xRight);
                Geo lTopA = new Geo(yTop, xLeft);
                Geo rTopA = new Geo(yTop, xRight);

                Area area = Area.builder()
                        .rTgeo(rTopA)
                        .lTgeo(lTopA)
                        .lBgeo(lBottomA)
                        .rBgeo(rBottomA)
                        .build();

                areas.add(area);
            }
        }

        return areas;
    }
}
