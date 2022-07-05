package ru.wpz.controller;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import ru.wpz.dto.ParkingDto;
import ru.wpz.mapper.ParkingMapper;
import ru.wpz.service.ParkingService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1//parking")
@AllArgsConstructor
public class ParkingController {

    private final ParkingMapper parkingMapper;
    private final ParkingService parkingService;

    @GetMapping()
    public List<ParkingDto> showAllParking(){
        return parkingService.getAllParking().stream()
                .map(parkingMapper::mapParkingDto).collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ParkingDto getParking(@PathVariable long id){
        return parkingMapper.mapParkingDto(parkingService.getParking(id).orElse(null));
    }

    @PostMapping("/add")
    @Transactional
    public ParkingDto saveNewParking(@RequestBody ParkingDto parking){
        parkingService.saveParking(parkingMapper.mapParking(parking));
        return parking;
    }

    @PostMapping("/delete/{id}")
    public void deleteParking(@PathVariable Long id) {
        parkingService.deleteParking(id);
    }
}
