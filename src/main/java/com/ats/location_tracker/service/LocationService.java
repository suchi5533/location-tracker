package com.ats.location_tracker.service;

import java.util.List;
import java.util.Optional;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.ats.location_tracker.entity.DeviceLocation;
import com.ats.location_tracker.repository.DeviceLocationRepository;

import lombok.RequiredArgsConstructor;

@Service
public class LocationService {

    private final DeviceLocationRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public LocationService(DeviceLocationRepository repository,
                           SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    public DeviceLocation saveAndBroadcast(DeviceLocation location) {

        // 1. Save to DB
        DeviceLocation saved = repository.save(location);

        // 2. Broadcast real-time update
        messagingTemplate.convertAndSend(
            "/topic/location/" + saved.getDeviceId(),
            saved
        );

        return saved;
    }
    public List<DeviceLocation> getHistory(String deviceId) {
        return repository.findByDeviceIdOrderByCreatedAtAsc(deviceId);
    }
}
/*@RequiredArgsConstructor
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
}*/
