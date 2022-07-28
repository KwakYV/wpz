package ru.wpz.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.wpz.dto.MessageDto;
import ru.wpz.entity.Device;
import ru.wpz.entity.Message;
import ru.wpz.entity.Report;
import ru.wpz.mapper.MessageMapper;
import ru.wpz.repository.DeviceRepository;
import ru.wpz.repository.MessageRepository;
import ru.wpz.repository.ReportRepository;

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

    public void saveFromKafka(MessageDto messageDto) {
        Message message = save(messageMapper.mapMessage(messageDto));
        Device device = deviceRepository.findById(message.getDevId().getId()).orElse(null);
        if (device != null){
           createReport(device, message);
        }
    }

    private void createReport(Device device, Message message) {
        Report report = new Report();
        report.setTimeMessage(message.getCreatedDt());
        report.setStatus(message.getStatus());
        report.setDevice(device);
        report.setZoneId(device.getZoneId());
        report.setBuilding(device.getZoneId().getBuilding());
        report.setOrganizationId(device.getZoneId().getBuilding().getOrganization());
        reportRepository.save(report);
    }
}

