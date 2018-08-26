package com.rejoice.dao.userData;

import com.rejoice.entity.enums.City;
import com.rejoice.entity.userData.TargetData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TargetDataDao extends JpaRepository<TargetData, Long> {
    List<TargetData> findByCity(City city);
}
