package com.nagarro.service.impl;

import com.nagarro.enums.InsuranceType;
import com.nagarro.model.Car;
import com.nagarro.service.InsuranceCalculator;

public class BasicInsuranceCalculator implements InsuranceCalculator {
    public float calculateInsurance(Car car) {

        float insurancePremium = 0;
        float carPercent = car.getPercent();
        float insurancePercent = InsuranceType.BASIC.getPercent();

        insurancePremium = car.getPrice() * (carPercent / 100);

        insurancePremium = insurancePremium
                + (insurancePremium * (insurancePercent / 100) );

        return insurancePremium;
    }
}
