package ru.jbisss.brtservice.service.debt;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.brtservice.ApplicationConstants;
import ru.jbisss.brtservice.entity.AbonentEntity;
import ru.jbisss.brtservice.repository.AbonentRepository;

@Service
@RequiredArgsConstructor
public class DebtService implements IDebtService {

    private final AbonentRepository abonentRepository;

    @Override
    public void getDebtAndTariffAbonent(String abonentDebtMessage) {
        String[] abonentDebtTokens = abonentDebtMessage.split(ApplicationConstants.COMMA_DELIMITER);
        String abonentPhoneNumber = abonentDebtTokens[0];
        double abonentDebt = Double.parseDouble(abonentDebtTokens[1]);
        AbonentEntity abonentEntity = abonentRepository.findByPhoneNumber(abonentPhoneNumber);
        abonentEntity.setBalance(abonentEntity.getBalance() - abonentDebt);
        abonentRepository.save(abonentEntity);
    }
}
