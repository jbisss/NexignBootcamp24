package ru.jbisss.hrsservice.service.tariff.definer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.DebtEntity;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.DebtRepository;
import ru.jbisss.hrsservice.repository.TariffRepository;
import ru.jbisss.hrsservice.service.callDuration.ICallDurationService;
import ru.jbisss.hrsservice.service.tariff.validator.TariffValidator;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CodeTariffDefiner implements TariffDefiner {

    private final DebtRepository debtRepository;
    private final TariffRepository tariffRepository;
    private final ICallDurationService callDurationService;

    private final Set<TariffValidator> tariffValidatorSet;

    @Override
    public void defineTariffAndCountDebt(CdrRow cdrRow) {
        TariffValidator rightTariffValidator = tariffValidatorSet.stream()
                .filter(validator -> validator.getCorrespondingTariff().getTariffId() == cdrRow.getTariff())
                .findFirst()
                .orElseThrow(() -> new RuntimeException(""));
        long minutes = callDurationService
                .countCallDuration(cdrRow.getStartCallDate(), cdrRow.getEndCallDate())
                .getMinutes();
        double callCost = rightTariffValidator.countDebt(cdrRow, minutes);
        writeDebtToDb(cdrRow, callCost);
    }

    @Override
    public TariffEntity defineTariff(int cdrTariffCode) {
        return tariffRepository.findById(cdrTariffCode).get();
    }

    private void writeDebtToDb(CdrRow cdrRow, double debtAmount) {
        if (debtAmount == 0) {
            return;
        }
        LocalDateTime currentDate = cdrRow.getCallMonthWithYear();
        DebtEntity currentDebt = debtRepository.findByAbonentPhoneNumberAndDebtDate(cdrRow.getCallerPhoneNumber(), currentDate)
                .orElse(null);
        if (Objects.isNull(currentDebt)) {
            currentDebt = DebtEntity.builder()
                    .abonentPhoneNumber(cdrRow.getCallerPhoneNumber())
                    .debtDate(currentDate)
                    .debtAmount(debtAmount)
                    .build();
        } else {
            currentDebt.setDebtAmount(currentDebt.getDebtAmount() + debtAmount);
        }
        debtRepository.save(currentDebt);
    }
}
