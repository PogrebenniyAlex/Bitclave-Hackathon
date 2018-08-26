package com.rejoice.controller;

import com.rejoice.entity.json.Error;
import com.rejoice.entity.token.Token;
import com.rejoice.entity.user.User;
import com.rejoice.entity.userData.Geo;
import com.rejoice.service.SecurityService;
import com.rejoice.service.TokenService;
import com.rejoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.logging.Logger;

@Controller
public class SignUpSignInController {

    Logger logger = Logger.getLogger(SignUpSignInController.class.getName());

    private final SecurityService securityService;
    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    public SignUpSignInController(SecurityService securityService, UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.securityService = securityService;
        this.userService = userService;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @GetMapping
    public String hello(){return "index";}

    @RequestMapping(value = "/signup")
    public String signup(){
        return "register";
    }

    @RequestMapping(value = "/signin")
    public String signin(){
        return "login";
    }

    @RequestMapping(value = "/signup/vendor", method = RequestMethod.POST)
    public ResponseEntity<Object> signupVendor(@RequestBody User user,
                                             @RequestParam(value = "redirect", required = false) String redirect){

        if ((user.getEmail() != null) && (user.getPassword() != null)){
            User usersByEmail = userService.findUsersByEmail(user.getEmail());
            if (usersByEmail != null){
                logger.info("Such user already exist " + user);
                return new ResponseEntity<>(new Error("Such user already exist"), HttpStatus.CONFLICT);
            }

//            user = userService.createNewUser(user);
            Token token = userService.createUser(user);

            return new ResponseEntity<>(token, HttpStatus.OK);
        }

        return new ResponseEntity<>(new Error("Error"), HttpStatus.CONFLICT);
    }

    @RequestMapping(value = "/signin/vendor", method = RequestMethod.POST)
    public ResponseEntity<Object> signinForm(@RequestBody User user){

        if ((user.getEmail() != null) && (user.getPassword() != null)){
            User usersByEmail = userService.findUsersByEmail(user.getEmail());

            if(usersByEmail != null){

                if(bCryptPasswordEncoder.matches(user.getPassword(), usersByEmail.getPassword())){

                    securityService.autoLoginUser(usersByEmail);
                    logger.info(usersByEmail.toString());

                    return new ResponseEntity<>(tokenService.createToken(usersByEmail), HttpStatus.OK);
                }else return new ResponseEntity<>(new Error("Email or password not found"), HttpStatus.CONFLICT);
            }
            else return new ResponseEntity<>(new Error("Email or password not found"), HttpStatus.CONFLICT);
        }

        return new ResponseEntity<>(new Error("Error"), HttpStatus.CONFLICT);
    }


    @PostMapping("/api/search")
    public @ResponseBody Object searchData(@RequestBody Map<String, Object> request){
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userService.search(request);

//        return getMockResult();
    }

    private Geo[][] positions = {
            {       new Geo(50.449256, 30.511848),
                    new Geo(50.448144, 30.521898),
                    new Geo(50.443957, 30.521065),
                    new Geo(50.443957, 30.511734)
            },
            {       new Geo(50.460076, 30.522901),
                    new Geo(50.461129, 30.526427),
                    new Geo(50.458671, 30.527431),
                    new Geo(50.458057, 30.525698)
            },
            {       new Geo(50.466253, 30.512914),
                    new Geo(50.467389, 30.514482),
                    new Geo(50.465708, 30.517414),
                    new Geo(50.464645, 30.515600)
            }
    };

    private Map<String, Object> getMockResult(){
        HashMap<String, Object> area = new HashMap<>();
        area.put("peoples", 228);
        area.put("points", getPositions(positions[0]));

        HashMap<String, Object> area1 = new HashMap<>();
        area1.put("peoples", 28);
        area1.put("points", getPositions(positions[1]));

        HashMap<String, Object> area2 = new HashMap<>();
        area2.put("peoples", 123);
        area2.put("points", getPositions(positions[2]));

        List<HashMap<String, Object>> areas = new ArrayList<>(3);
        areas.add(area);
        areas.add(area1);
        areas.add(area2);

        HashMap<String, Object> response = new HashMap<>();
        response.put("areas", areas);

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
}
