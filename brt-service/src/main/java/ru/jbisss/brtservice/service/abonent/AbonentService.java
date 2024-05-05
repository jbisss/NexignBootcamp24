package ru.jbisss.brtservice.service.abonent;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.dto.TariffDto;
import ru.jbisss.brtservice.dto.AbonentDto;
import ru.jbisss.brtservice.entity.AbonentEntity;
import ru.jbisss.brtservice.repository.AbonentRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AbonentService implements IAbonentService {

    private final AbonentRepository abonentRepository;

    @Override
    @Transactional
    public AbonentDto addNewAbonent(AbonentDto abonentDto) {
        AbonentEntity abonentEntity = AbonentEntity.builder()
                .phoneNumber(abonentDto.getPhoneNumber())
                .tariffId(abonentDto.getTariffId())
                .connectionDate(LocalDateTime.now())
                .balance(abonentDto.getBalance())
                .build();
        AbonentEntity createdAbonentEntity = abonentRepository.save(abonentEntity);
        abonentDto.setAbonentId(createdAbonentEntity.getAbonentId());
        return abonentDto;
    }

    @Override
    @Transactional
    public AbonentDto changeAbonentTariff(TariffDto tariffDto, String msisdn) {
        AbonentEntity abonentEntity = abonentRepository.findByPhoneNumber(msisdn);
        abonentEntity.setTariffId(tariffDto.getTariffId());
        AbonentEntity abonentEntityWithChangedTariff = abonentRepository.save(abonentEntity);
        return buildAbonentDtoByAbonentEntity(abonentEntityWithChangedTariff);
    }

    @Override
    @Transactional
    public AbonentDto topUpAbonentBalance(int topUp, String msisdn) {
        AbonentEntity abonentEntity = abonentRepository.findByPhoneNumber(msisdn);
        abonentEntity.setBalance(abonentEntity.getBalance() + topUp);
        AbonentEntity abonentEntityWithToppedUpBalance = abonentRepository.save(abonentEntity);
        return buildAbonentDtoByAbonentEntity(abonentEntityWithToppedUpBalance);
    }

    private AbonentDto buildAbonentDtoByAbonentEntity(AbonentEntity abonentEntity) {
        return AbonentDto.builder()
                .abonentId(abonentEntity.getAbonentId())
                .tariffId(abonentEntity.getTariffId())
                .phoneNumber(abonentEntity.getPhoneNumber())
                .balance(abonentEntity.getBalance())
                .connectionDate(abonentEntity.getConnectionDate())
                .build();
    }
}
