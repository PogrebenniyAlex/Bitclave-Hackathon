package com.rejoice.dao.userData;

import com.rejoice.entity.user.User;
import com.rejoice.entity.userData.Geo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GeoDao extends JpaRepository<Geo, Long> {
    List<Geo> findByUser(User user);
}
