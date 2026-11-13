package com.nagarro.step_definitions;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import com.nagarro.model.Car;
import com.nagarro.service.InputService;
import com.nagarro.service.OutputService;
import com.nagarro.service.impl.BasicInsuranceCalculator;
import com.nagarro.service.impl.ConsoleInputService;
import com.nagarro.service.impl.ConsoleOutputService;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ConsoleInputAndOutputSteps {
	
	private InputService inputService = new ConsoleInputService();
    private OutputService outputService = new ConsoleOutputService();
    private List<Car> carList = new ArrayList<>();
    private Car car;
    private float insurancePremium;
    private final StringBuffer outputBuffer = new StringBuffer();

    @Given("the user inputs car model {string}")
    public void the_user_inputs_car_model(String model) {
        System.setIn(new java.io.ByteArrayInputStream((model + "\n").getBytes()));
        car = new Car();
        car.setModel(inputService.inputCarModel());
    }

    @Given("the user inputs car type {string}")
    public void the_user_inputs_car_type(String type) {
        System.setIn(new java.io.ByteArrayInputStream((type + "\n").getBytes()));
        car.setType(inputService.inputCarType());
    }

    @Given("the user inputs car price {int}")
    public void the_user_inputs_car_price(int price) {
        System.setIn(new java.io.ByteArrayInputStream((price + "\n").getBytes()));
        car.setPrice(inputService.inputCarPrice());
    }

    @Given("the user inputs insurance type {string}")
    public void the_user_inputs_insurance_type(String insuranceType) {
        System.setIn(new java.io.ByteArrayInputStream((insuranceType + "\n").getBytes()));
        car.setInsuranceType(inputService.inputInsuranceType());
    }

    @When("the user finishes inputting car details")
    public void the_user_finishes_inputting_car_details() {
        carList.add(car);
    }

    @Then("the car details should be saved correctly")
    public void the_car_details_should_be_saved_correctly() {
        Assert.assertNotNull(car);
        Assert.assertNotNull(car.getModel());
        Assert.assertNotNull(car.getType());
        Assert.assertNotEquals(0, car.getPrice());
        Assert.assertNotNull(car.getInsuranceType());
    }

    @Then("the calculated insurance premium should be displayed as {string}")
    public void the_calculated_insurance_premium_should_be_displayed_as(String expectedOutput) {
        System.setOut(new java.io.PrintStream(new java.io.ByteArrayOutputStream() {
            @Override
            public void write(byte[] b, int off, int len) {
                outputBuffer.append(new String(b, off, len));
            }
        }));
        insurancePremium = new BasicInsuranceCalculator().calculateInsurance(car);
        outputService.printCarDetails(car, insurancePremium);
        Assert.assertEquals(expectedOutput.trim(), outputBuffer.toString().trim());
    }
}
