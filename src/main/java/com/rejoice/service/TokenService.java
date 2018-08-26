package com.rejoice.service;

import com.rejoice.entity.token.Token;
import com.rejoice.entity.user.User;

import java.util.List;

public interface TokenService {

   void authenticationByRequest(String access_token);
   Token createToken(User user);
   Token refreshToken(String refreshToken);
   void deleteToken(String accessToken);
   void deleteToken(User user);

   List<Token> getTokensByUser(User user);
   Token getTokenByUser(User user);

   User getUserByToken(String accessToken);

    //TODO: method for delete expired tokens
}
