package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.dto.DeviceFullInfoDto;
import ru.wpz.dto.MessageDto;
import ru.wpz.entity.Message;
import ru.wpz.entity.Report;
import ru.wpz.mapper.MessageMapper;
import ru.wpz.repository.*;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;
    private final MessageMapper messageMapper;
    private final DeviceRepository deviceRepository;
    private final ReportRepository reportRepository;

    public List<Message> findAll(long devId) {
        return messageRepository.findAll(devId);
    }

    public Optional<Message> get(long id) {
        return messageRepository.findById(id);
    }

    @Transactional
    public Message save(Message message) {
       return messageRepository.save(message);
    }

    public void delete(Long id) {
        messageRepository.deleteById(id);
    }

    @Transactional
    public void saveFromKafka(MessageDto messageDto) {
        Message message = save(messageMapper.mapMessage(messageDto));
        createReport(message);

    }

    private void createReport(Message message) {
        DeviceFullInfoDto deviceFullInfoDto = deviceRepository.getDeviceFullInfo(message.getDevId());
        Report report =  Report.builder()
                .messageDt(message.getCreatedDt())
                .status(message.getStatus())
                .deviceId(deviceFullInfoDto.getDeviceId())
                .deviceNumber(deviceFullInfoDto.getDeviceNumber())
                .zoneNumber(deviceFullInfoDto.getZoneNumber())
                .buildingId(deviceFullInfoDto.getBuildingId())
                .buildingName(deviceFullInfoDto.getBuildingName())
                .organizationId(deviceFullInfoDto.getOrganizationId())
                .organizationName(deviceFullInfoDto.getOrganizationName())
                .build();
        reportRepository.save(report);
    }
}

