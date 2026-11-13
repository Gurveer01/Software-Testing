package com.nagarro.service.impl;

import com.nagarro.model.Car;
import com.nagarro.service.OutputService;

public class ConsoleOutputService implements OutputService {
    public void printCarDetails(Car car, float insurancePremium) {

        System.out.println(car.toString()+", Insurance Premium = "
                + insurancePremium);
    }
}
