package com.rejoice.entity.userData;

import com.rejoice.entity.enums.*;
import com.rejoice.entity.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "target_data")
@Data
@Builder
public class TargetData {

    @Id
    @GeneratedValue
    @JsonIgnore
    private Long id;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private City city;

    private Integer age;

    private Integer salary;

    private Integer spending;

    @Enumerated(EnumType.STRING)
    private Music music;

    @Enumerated(EnumType.STRING)
    private Drinks drinks;

    @Enumerated(EnumType.STRING)
    private Food food;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User user;

    @Override
    public String toString() {
        return "TargetData{" +
                "id=" + id +
                ", gender=" + gender +
                ", city=" + city +
                ", age=" + age +
                ", salary=" + salary +
                ", spending=" + spending +
                ", music=" + music +
                ", drinks=" + drinks +
                ", food=" + food +
                '}';
    }
}
