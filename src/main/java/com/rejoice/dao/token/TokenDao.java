package com.rejoice.dao.token;


import com.rejoice.entity.token.Token;
import com.rejoice.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface TokenDao extends JpaRepository<Token, Long> {

    Token findTokenByAccessToken(String accessToken);
    Token findTokenByRefreshToken(String refreshToken);
    Token findTokenByUser(User user);
    void deleteTokenByAccessToken(String accessToken);

    List<Token> findTokensByUser(User user);

    List<Token> findAllByExpiresInIsLessThan(Date date);
}
