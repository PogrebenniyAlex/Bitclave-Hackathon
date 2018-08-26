package com.rejoice.service.impl;

import com.rejoice.dao.token.TokenDao;
import com.rejoice.entity.token.Token;
import com.rejoice.entity.user.User;
import com.rejoice.exceptions.ForbiddenException;
import com.rejoice.service.TokenService;
import com.rejoice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.keygen.KeyGenerators;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
@Transactional
public class TokenServiceImpl implements TokenService {
    private int EXPIRE_HOUR = 24;
    private int REFRESH_EXPIRE_HOUR = 30;

    @Autowired
    private UserService userService;
    @Autowired
    private TokenDao tokenDao;

    @Override
    public void authenticationByRequest(String access_token){
        Token tokenByAccessToken = tokenDao.findTokenByAccessToken(access_token);
        if(tokenByAccessToken != null){
            if(tokenByAccessToken.getExpiresIn().after(new Date())){
                User user = tokenByAccessToken.getUser();

                Date date = new Date();
                Calendar calendar = Calendar.getInstance();

                calendar.setTime(date);
                calendar.add(Calendar.HOUR, EXPIRE_HOUR);

                Date expires_in = calendar.getTime();

                tokenByAccessToken.setExpiresIn(expires_in);
                tokenDao.saveAndFlush(tokenByAccessToken);

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(user, user.getPassword(), user.getAuthorities());

                if (authenticationToken.isAuthenticated()) {
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }

            }else {
                throw new ForbiddenException("Token expired");
            }
        }else {
            throw new ForbiddenException("Token not found");
        }


    }

    @Override
    public Token createToken(User user) {

        String access_token;

        do{
            access_token = KeyGenerators.string().generateKey();
        }while (tokenDao.findTokenByAccessToken(access_token) != null);

        String refresh_token;

        do{
            refresh_token = KeyGenerators.string().generateKey();
        }while (tokenDao.findTokenByRefreshToken(refresh_token) != null);

        Date date = new Date();
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.add(Calendar.HOUR, EXPIRE_HOUR);

        Date expires_in = calendar.getTime();

//        Token tokenByUser = tokenDao.findTokenByUser(user);
//
//        if(tokenByUser != null){
//            tokenByUser.setAccessToken(access_token);
//            tokenByUser.setExpiresIn(expires_in);
//            tokenByUser.setRefreshToken(refresh_token);
//
//            return tokenDao.saveAndFlush(tokenByUser);
//        }else {
//        }
        Token token = new Token(access_token, refresh_token, expires_in, user);
        return tokenDao.saveAndFlush(token);
    }

    @Override
    public Token refreshToken(String refreshToken) {

        Token tokenByRefreshToken = tokenDao.findTokenByRefreshToken(refreshToken);
        tokenDao.delete(tokenByRefreshToken);

        return createToken(tokenByRefreshToken.getUser());
    }

    @Override
    public void deleteToken(String accessToken) {

        tokenDao.deleteTokenByAccessToken(accessToken);

    }

    @Override
    public void deleteToken(User user) {
        Token tokenByUser = tokenDao.findTokenByUser(user);
        if(tokenByUser != null)
            tokenDao.delete(tokenByUser);
    }

    @Override
    public List<Token> getTokensByUser(User user) {
        return tokenDao.findTokensByUser(user);
    }

    @Override
    public Token getTokenByUser(User user) {
        List<Token> tokensByUser = getTokensByUser(user);
        Token tokenByUser = null;

        for (Token token : tokensByUser) {
            if(token.getExpiresIn().after(new Date())){
                tokenByUser = token;
            }
        }

        return tokenByUser;
    }

    @Override
    public User getUserByToken(String accessToken) {
        return tokenDao.findTokenByAccessToken(accessToken).getUser();
    }
}
