package com.rejoice.entity.userData;

import com.rejoice.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Table(name = "geo")
@Data
@ToString
public class Geo {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    private Double x;
    private Double y;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    public Geo(Double y, Double x) {
        this.x = x;
        this.y = y;
    }

    public Geo(Double x) {
        this.x = x;
    }

    @Override
    public String toString() {
        return "Geo{" +
                "id=" + id +
                ", x=" + x +
                ", y=" + y +
                ", user=" + user +
                '}';
    }
}
