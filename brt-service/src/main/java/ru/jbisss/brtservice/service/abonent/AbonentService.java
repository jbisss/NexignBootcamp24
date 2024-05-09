package ru.jbisss.brtservice.service.abonent;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.ApplicationConstants;
import ru.jbisss.brtservice.dto.TariffDto;
import ru.jbisss.brtservice.dto.AbonentDto;
import ru.jbisss.brtservice.entity.AbonentEntity;
import ru.jbisss.brtservice.repository.AbonentRepository;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AbonentService implements IAbonentService<AbonentDto> {

    private final AbonentRepository abonentRepository;

    @Override
    @Transactional
    public ResponseEntity<AbonentDto> addNewAbonent(AbonentDto abonentDto) {
        ResponseEntity<AbonentDto> response = handleAddingNewAbonentErrorRequest(abonentDto);
        if (Objects.nonNull(response)) {
            return response;
        }
        AbonentEntity abonentEntity = AbonentEntity.builder()
                .phoneNumber(abonentDto.getMsisdn())
                .tariffId(abonentDto.getTariffId())
                .connectionDate(AbonentDto.getLocalDateTimeConnectionDate(abonentDto))
                .balance(abonentDto.getMoney())
                .build();
        AbonentEntity createdAbonentEntity = abonentRepository.save(abonentEntity);
        abonentDto.setAbonentId(createdAbonentEntity.getAbonentId());
        return ResponseEntity.ok(abonentDto);
    }

    private ResponseEntity<AbonentDto> handleAddingNewAbonentErrorRequest(AbonentDto abonentDto) {
        ResponseEntity<AbonentDto> handleExistence = handleExistence(abonentDto);
        if (Objects.nonNull(handleExistence)) {
            return handleExistence;
        }
        ResponseEntity<AbonentDto> handleDate = handleDate(abonentDto);
        if (Objects.nonNull(handleDate)) {
            return handleDate;
        }
        return null;
    }

    private ResponseEntity<AbonentDto> handleExistence(AbonentDto abonentDto) {
        Optional<AbonentEntity> abonent = abonentRepository.findByPhoneNumber(abonentDto.getMsisdn());
        if (abonent.isPresent()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorMessage", "Such abonent already exist!");
            return new ResponseEntity<>(headers, HttpStatus.METHOD_NOT_ALLOWED);
        }
        return null;
    }

    private ResponseEntity<AbonentDto> handleDate(AbonentDto abonentDto) {
        String[] splitTokens = abonentDto.getConnectionDate().split(ApplicationConstants.DASH);
        try {
            LocalDateTime.of(Integer.parseInt(splitTokens[0]),
                    Integer.parseInt(splitTokens[1]),
                    Integer.parseInt(splitTokens[2]), 0, 0);
        } catch (Exception e) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorMessage", "Invalid date!");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        return null;
    }

    @Override
    @Transactional
    public ResponseEntity<AbonentDto> changeAbonentTariff(TariffDto tariffDto, String msisdn) {
        AbonentEntity abonentEntity = abonentRepository.findByPhoneNumber(msisdn).get();
        abonentEntity.setTariffId(tariffDto.getTariffId());
        AbonentEntity abonentEntityWithChangedTariff = abonentRepository.save(abonentEntity);
        return ResponseEntity.ok(AbonentDto.buildDtoByEntity(abonentEntityWithChangedTariff));
    }

    @Override
    @Transactional
    public ResponseEntity<AbonentDto> topUpAbonentBalance(int topUp, String msisdn) {
        Optional<AbonentEntity> abonentEntityOptional = abonentRepository.findByPhoneNumber(msisdn);

        ResponseEntity<AbonentDto> handleErrorRequest = handleErrorRequest(topUp, abonentEntityOptional);
        if (Objects.nonNull(handleErrorRequest)) {
            return handleErrorRequest;
        }

        AbonentEntity abonentEntity = abonentEntityOptional.get();
        abonentEntity.setBalance(abonentEntity.getBalance() + topUp);
        AbonentEntity abonentEntityWithToppedUpBalance = abonentRepository.save(abonentEntity);
        return ResponseEntity.ok(AbonentDto.buildDtoByEntity(abonentEntityWithToppedUpBalance));
    }

    private ResponseEntity<AbonentDto> handleErrorRequest(int topUp, Optional<AbonentEntity> abonentEntityOptional) {
        ResponseEntity<AbonentDto> handleNotFound = handleNotFound(abonentEntityOptional);
        if (Objects.nonNull(handleNotFound)) {
            return handleNotFound;
        }
        ResponseEntity<AbonentDto> handleBadRequest = handleBadRequest(topUp);
        if (Objects.nonNull(handleBadRequest)) {
            return handleBadRequest;
        }
        return null;
    }

    private ResponseEntity<AbonentDto> handleNotFound(Optional<AbonentEntity> abonentEntityOptional) {
        if (abonentEntityOptional.isEmpty()) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("errorMessage", "Such abonent doesn't exist!");
            return new ResponseEntity<>(headers, HttpStatus.NOT_FOUND);
        }
        return null;
    }

    private ResponseEntity<AbonentDto> handleBadRequest(int topUp) {
        HttpHeaders headers = new HttpHeaders();
        if (topUp == 0) {
            headers.add("errorMessage", "Top up is 0!");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        } else if (topUp < 0) {
            headers.add("errorMessage", "Top up is negative!");
            return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
        }
        return null;
    }
}
