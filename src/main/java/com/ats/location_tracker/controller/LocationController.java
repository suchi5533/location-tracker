package com.ats.location_tracker.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ats.location_tracker.entity.DeviceLocation;
import com.ats.location_tracker.repository.DeviceLocationRepository;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    private final DeviceLocationRepository repository;

    public LocationController(DeviceLocationRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/update")
    public ResponseEntity<?> update(@RequestBody DeviceLocation location) {
        try {
            repository.save(location);
            return ResponseEntity.ok("Location saved");
        } catch (Exception e) {
            e.printStackTrace(); // 👈 IMPORTANT
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }
        
        @GetMapping("/latest/{deviceId}")
        public DeviceLocation latest(@PathVariable String deviceId) {
            return repository
                .findTopByDeviceIdOrderByCreatedAtDesc(deviceId)
                .orElseThrow(() -> new RuntimeException("No data"));
        }
        
        @GetMapping("/health")
        public String health() {
            return "Location Tracker is running";
        }

    

}
