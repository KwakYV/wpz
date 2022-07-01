package ru.wpz.lessons.lesson1.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.wpz.lessons.lesson1.entity.EdgeDevice;
import ru.wpz.lessons.lesson1.service.EdgeDeviceService;

import java.util.List;

/**
 * Rest controller for device
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/device")
public class EdgeDeviceController {
    private final EdgeDeviceService edgeDeviceService;


    @GetMapping
    public List<EdgeDevice> getDeviceList() {
        return edgeDeviceService.getDeviceList();
    }

    @PostMapping
    public ResponseEntity<?> handlePost(@Validated @RequestBody EdgeDevice edgeDevice) {
        edgeDeviceService.save(edgeDevice);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<?> handleUpdate(@PathVariable("deviceId") Long id,
                                          @Validated @RequestBody EdgeDevice edgeDevice) {
        edgeDevice.setId(id);
        edgeDeviceService.save(edgeDevice);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("deviceId") Long id) {
        edgeDeviceService.deleteById(id);
    }

}
