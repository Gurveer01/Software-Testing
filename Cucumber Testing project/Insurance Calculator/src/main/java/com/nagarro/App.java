package com.nagarro;

import com.nagarro.enums.InsuranceType;
import com.nagarro.model.Car;
import com.nagarro.service.impl.BasicInsuranceCalculator;
import com.nagarro.service.impl.ConsoleInputService;
import com.nagarro.service.impl.ConsoleOutputService;
import com.nagarro.service.impl.PremiumInsuranceCalculator;

import java.util.ArrayList;
import java.util.List;

public class App
{
    public static void main(String[] args) {
        String choice = "";
        List<Car> carList = new ArrayList<>();
        while (true) {
            ConsoleInputService inService = new ConsoleInputService();
            inService.inputCarDetails(carList);
            choice = inService.inputUserChoice();
            if (choice.equals("n")) {
                break;
            }
        }

        for(Car objCar: carList) {
            float insurancePremium;
            InsuranceType type = InsuranceType.findByName(objCar.getInsuranceType());
            if(type.equals(InsuranceType.BASIC)) {
                insurancePremium = new BasicInsuranceCalculator()
                        .calculateInsurance(objCar);
            }
            else {
                insurancePremium = new PremiumInsuranceCalculator()
                        .calculateInsurance(objCar);
            }
            ConsoleOutputService outService = new ConsoleOutputService();
            outService.printCarDetails(objCar, insurancePremium);
        }
    }
}
