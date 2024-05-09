package ru.jbisss.hrsservice.service.tariff.validator;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import ru.jbisss.hrsservice.domain.CdrWithTariff.CdrRow;
import ru.jbisss.hrsservice.entity.DebtEntity;
import ru.jbisss.hrsservice.entity.RemainsEntity;
import ru.jbisss.hrsservice.entity.ServiceInTariffEntity;
import ru.jbisss.hrsservice.entity.TariffEntity;
import ru.jbisss.hrsservice.repository.*;
import ru.jbisss.hrsservice.service.callDuration.ICallDurationService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class MonthlyTariffValidator extends AbstractValidator {

    private final DebtRepository debtRepository;

    private final ApplicationContext applicationContext;

    private String currentMonth = "2012-01";

    public MonthlyTariffValidator(ICallDurationService callDurationService, TariffRepository tariffRepository, ServiceRepository serviceRepository, ServiceInTariffRepository serviceInTariffRepository, DebtRepository debtRepository, RemainsRepository remainsRepository, ApplicationContext applicationContext) {
        super(callDurationService, tariffRepository, serviceRepository, serviceInTariffRepository, remainsRepository);
        this.debtRepository = debtRepository;
        this.applicationContext = applicationContext;
    }

    private TariffValidator getTariffValidatorByCode(int tariffCode)  {
        Map<Integer, TariffValidator> tariffValidators = new HashMap<>();
        applicationContext.getBeansOfType(TariffValidator.class).values()
                .forEach(value -> {
                    TariffEntity tariff = value.getCorrespondingTariff();
                    tariffValidators.put(tariff.getTariffId(), value);
                });
        return tariffValidators.get(tariffCode);
    }

    @Override
    public TariffEntity getCorrespondingTariff() {
        return tariffRepository.findById(12).get();
    }

    @Override
    public double countDebt(CdrRow cdrRow, long minutes) {
        double result = 0;

        TariffEntity monthlyTariff = getCorrespondingTariff();
        ServiceInTariffEntity serviceInTariff;
        if (cdrRow.getCallType().equals(CdrRow.CallType.IN)) {
            serviceInTariff = getServiceByNameInTariff(monthlyTariff, "IN_ANY");
        } else {
            serviceInTariff = getServiceByNameInTariff(monthlyTariff, "OUT_ANY");
        }
        RemainsEntity remains = remainsRepository
                .findByAbonentPhoneNumberAndServiceInTariff(cdrRow.getCallerPhoneNumber(), serviceInTariff)
                .get();
        if (remains.getRemains() > 0) {
            LocalDateTime callPreviousMonthWithYear = cdrRow.getPreviousCallMonthWithYear();
            Optional<DebtEntity> debtPreviousMonth = debtRepository.findByAbonentPhoneNumberAndDebtDate(cdrRow.getCallerPhoneNumber(), callPreviousMonthWithYear);
            if (isFirstCdrOfNewMonth(cdrRow) && debtPreviousMonth.isPresent()) {
                result = monthlyTariff.getAbonentPayment().doubleValue();
                if (minutes > remains.getRemains()) {
                    long minutesToCountByExtraTariff = minutes - remains.getRemains();
                    Integer tariffExtra = getTariffValidatorByCode(monthlyTariff.getTariffExtra()).getCorrespondingTariff().getTariffExtra();
                    result += getTariffValidatorByCode(tariffExtra).countDebt(cdrRow, minutesToCountByExtraTariff);
                    remains.setRemains(0);
                } else {
                    remains.setRemains((int) (remains.getRemains() - minutes));
                }
                remainsRepository.save(remains);
            }
        } else {
            result = getTariffValidatorByCode(monthlyTariff.getTariffExtra()).countDebt(cdrRow, minutes);
        }
        return result;
    }

    private boolean isFirstCdrOfNewMonth(CdrRow cdrRow) {
        String currentCdrMonth = cdrRow.getCallMonthWithYearAsString();
        if (currentCdrMonth.equals(currentMonth)) {
            return false;
        }
        setZeroDebt(cdrRow);
        currentMonth = currentCdrMonth;
        return true;
    }

    private void setZeroDebt(CdrRow cdrRow) {
        DebtEntity debtEntity = new DebtEntity();
        debtEntity.setDebtAmount(0);
        debtEntity.setDebtDate(cdrRow.getCallMonthWithYear());
        debtEntity.setAbonentPhoneNumber(cdrRow.getCallerPhoneNumber());
        debtRepository.save(debtEntity);
    }
}
