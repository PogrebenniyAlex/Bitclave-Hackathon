package com.rejoice.entity.token;

import com.rejoice.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "tokens")
@Data
//@NoArgsConstructor
@ToString
public class Token {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "expires_in")
    @JsonIgnore
    private Date expiresIn;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Token(String accessToken, String refreshToken, Date expiresIn, User user) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.expiresIn = expiresIn;
        this.user = user;
    }
}
