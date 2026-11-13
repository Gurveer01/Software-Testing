package com.nagarro.service;

import com.nagarro.enums.CarType;
import com.nagarro.enums.InsuranceType;
import com.nagarro.exception.InvalidChoiceException;
import com.nagarro.exception.InvalidTypeException;
import com.nagarro.model.Car;

import java.util.List;

public interface InputService {
    public void inputCarDetails(List<Car> carList);

    public String inputCarModel();

    public CarType inputCarType();

    public boolean validateCarType(String carType) throws InvalidTypeException;

    public int inputCarPrice();

    public InsuranceType inputInsuranceType();

    public boolean validateInsuranceType(String insuranceType) throws InvalidTypeException;

    public String inputUserChoice();

    public boolean validateChoice(String choice) throws InvalidChoiceException;
}
