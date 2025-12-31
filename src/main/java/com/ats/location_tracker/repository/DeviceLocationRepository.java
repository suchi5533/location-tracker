//package repository;
package com.ats.location_tracker.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ats.location_tracker.entity.DeviceLocation;


public interface DeviceLocationRepository
        extends JpaRepository<DeviceLocation, Long> {

    Optional<DeviceLocation> findTopByDeviceIdOrderByCreatedAtDesc(String deviceId);
}
