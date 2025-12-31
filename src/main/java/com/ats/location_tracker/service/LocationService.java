package com.ats.location_tracker.service;

import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ats.location_tracker.entity.DeviceLocation;
import com.ats.location_tracker.repository.DeviceLocationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LocationService {

    private final DeviceLocationRepository repo;
    private final SimpMessagingTemplate messagingTemplate;

    public void saveAndBroadcast(DeviceLocation location) {
        repo.save(location);
        messagingTemplate.convertAndSend(
            "/topic/location/" + location.getDeviceId(),
            location
        );
    }
    public Optional<DeviceLocation> getLatest(String deviceId) {
        return repo.findTopByDeviceIdOrderByCreatedAtDesc(deviceId);
    }
}
