package com.rejoice.entity.user;


import com.rejoice.entity.converter.BooleanToStringConverter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "users")
public class User implements UserDetails {

    public interface SignInRepresentation {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    @Column(name = "id")
    private Long id;

    @Column(name = "email", nullable = false)
    @JsonView(value = {SignInRepresentation.class})
    private String email;

    @Column(name = "username", nullable = false)
    @JsonView(value = {SignInRepresentation.class})
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date date_created;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @JsonIgnore
    private Date date_updated;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    @JsonIgnore
    private List<Role> authorities;

    @Convert(converter=BooleanToStringConverter.class)
    @Column(name = "account_non_expired", nullable = false)
    @JsonIgnore
    private boolean accountNonExpired;

    @Convert(converter=BooleanToStringConverter.class)
    @Column(name = "account_non_locked", nullable = false)
    @JsonIgnore
    private boolean accountNonLocked;

    @Convert(converter=BooleanToStringConverter.class)
    @Column(name = "credentials_non_expired", nullable = false)
    @JsonIgnore
    private boolean credentialsNonExpired;

    @Convert(converter=BooleanToStringConverter.class)
    @Column(name = "enabled", nullable = false)
    @JsonIgnore
    private boolean enabled;

}
