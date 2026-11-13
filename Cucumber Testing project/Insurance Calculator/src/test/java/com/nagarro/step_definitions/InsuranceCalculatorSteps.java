package com.nagarro.step_definitions;

import org.junit.Assert;

import com.nagarro.enums.CarType;
import com.nagarro.enums.InsuranceType;
import com.nagarro.model.Car;
import com.nagarro.service.impl.BasicInsuranceCalculator;
import com.nagarro.service.impl.PremiumInsuranceCalculator;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class InsuranceCalculatorSteps {

	private Car car;
    private float insurancePremium;

    @Given("a car with model {string} and type {string} and price {int} and insurance type {string}")
    public void a_car_with_model_and_type_and_price_and_insurance_type(String model, String type, int price, String insuranceType) {
        car = new Car(model, CarType.findByName(type), price, InsuranceType.findByName(insuranceType));
    }

    @When("I calculate the insurance premium")
    public void i_calculate_the_insurance_premium() {
        if (car.getInsuranceType().equals(InsuranceType.BASIC.getName())) {
            insurancePremium = new BasicInsuranceCalculator().calculateInsurance(car);
        } else {
            insurancePremium = new PremiumInsuranceCalculator().calculateInsurance(car);
        }
    }

    @Then("the insurance premium should be {float}")
    public void the_insurance_premium_should_be(float expectedPremium) {
        Assert.assertEquals(expectedPremium, insurancePremium, 0.01);
    }
}
