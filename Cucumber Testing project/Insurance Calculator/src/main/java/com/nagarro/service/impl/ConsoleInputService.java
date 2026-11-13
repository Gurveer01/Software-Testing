package com.nagarro.service.impl;

import com.nagarro.enums.CarType;
import com.nagarro.enums.InsuranceType;
import com.nagarro.exception.InvalidChoiceException;
import com.nagarro.exception.InvalidTypeException;
import com.nagarro.exception.PriceLessThanZeroException;
import com.nagarro.model.Car;
import com.nagarro.service.InputService;

import java.util.List;
import java.util.Scanner;

public class ConsoleInputService implements InputService {
    Scanner sc = new Scanner(System.in);

    /* takes car details as input from user */
    public void inputCarDetails(List<Car> carList) {
        System.out.println("Enter Car details:");
        String carModel = inputCarModel();
        CarType carType = inputCarType();
        int carPrice = inputCarPrice();
        InsuranceType insuranceType = inputInsuranceType();
        Car objCar = new Car(carModel, carType, carPrice, insuranceType);
        carList.add(objCar);
    }

    /* takes car model as input from user */
    public String inputCarModel() {
        String carModel = "";
        boolean validModel = false;
        System.out.println("Enter Car Model: ");
        do {
            try {
                carModel = sc.nextLine();
                if (carModel.equals("")) {
                    throw new NullPointerException();
                } else {
                    validModel = true;
                }

            } catch (NullPointerException e) {
                System.out.println("Please enter valid car model");
            }
        } while (!validModel);

        return carModel;
    }

    /* takes car type as input from user */
    public CarType inputCarType() {
        CarType type;
        String carType="";
        boolean validType = false;
        System.out.println("Enter car type ("
                + CarType.HATCHBACK.getName() + ", "
                + CarType.SEDAN.getName() + ", "
                + CarType.SUV.getName() + "): ");
        do {
            try {
                carType = sc.nextLine();
                validType = validateCarType(carType);
            } catch (InvalidTypeException e) {
                System.out.println("Please enter valid car type ("
                        + CarType.HATCHBACK.getName() + ", "
                        + CarType.SEDAN.getName() + ", "
                        + CarType.SUV.getName() + "): ");
            }
        } while (!validType);
        type = CarType.findByName(carType);
        return type;
    }

    /* checks for a valid car type */
    public boolean validateCarType(String carType) throws InvalidTypeException {
        boolean validCarType = false;
        CarType types[] = CarType.values();
        for (CarType type : types) {
            if (type.getName().equals(carType)) {
                validCarType = true;
                break;
            }
        }
        if (validCarType == false) {
            throw new InvalidTypeException();
        }
        return validCarType;
    }

    /* takes price as input from user */
    public int inputCarPrice() {
        int carPrice = 0;
        boolean validPrice = false;
        System.out.println("Enter Car Price: ");
        do {
            try {
                String price = sc.nextLine();
                carPrice = Integer.parseInt(price);
                if(carPrice<=0) {
                    throw new PriceLessThanZeroException();
                }
                validPrice = true;
            } catch (NumberFormatException e) {
                System.out.println("Please enter valid car price: ");
            }
            catch (PriceLessThanZeroException e) {
                System.out.println("Please enter valid car price: ");
            }
        } while (!validPrice);
        return carPrice;
    }

    /* takes insurance type as input from user */
    public InsuranceType inputInsuranceType() {
        InsuranceType type;
        String insuranceType = "";
        boolean validInsuranceType = false;
        System.out.println("Enter insurance type ("
                + InsuranceType.BASIC.getName() + ", "
                + InsuranceType.PREMIUM.getName() + "): ");
        do {
            try {
                insuranceType = sc.nextLine();
                validInsuranceType = validateInsuranceType(insuranceType);
            } catch (InvalidTypeException e) {
                System.out.println("Please enter valid insurance type ("
                        + InsuranceType.BASIC.getName()+ ", "
                        + InsuranceType.PREMIUM.getName() + ") ");
            }
        } while (!validInsuranceType);
        type = InsuranceType.findByName(insuranceType);
        return type;
    }

    /* checks for a valid insurance type */
    public boolean validateInsuranceType(String insuranceType) throws InvalidTypeException {
        boolean validInsuranceType = false;
        InsuranceType types[] = InsuranceType.values();
        for (InsuranceType type : types) {
            if (type.getName().equals(insuranceType)) {
                validInsuranceType = true;
                break;
            }
        }
        if (validInsuranceType == false) {
            throw new InvalidTypeException();
        }
        return validInsuranceType;
    }

    /* takes choice as input from user */
    public String inputUserChoice() {
        String choice = "";
        boolean validChoice = false;
        System.out.println("Do you want to enter details of any other car (y/n):");
        do {
            try {
                choice = sc.nextLine();
                validChoice = validateChoice(choice);
            } catch (InvalidChoiceException e) {
                System.out.println("Please enter valid choice (y/n):");
            }
        } while (!validChoice);
        return choice;
    }

    /* checks for a valid choice */
    public boolean validateChoice(String choice) throws InvalidChoiceException {
        boolean validChoice = false;
        if (choice.equals("y") || choice.equals("n")) {
            validChoice = true;
        }
        if (!validChoice) {
            throw new InvalidChoiceException();
        }
        return validChoice;
    }
}
