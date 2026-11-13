package com.nagarro.model;

import com.nagarro.enums.CarType;
import com.nagarro.enums.InsuranceType;

public class Car {
    private String model;
    private CarType type;
    private int price;
    private InsuranceType insuranceType;

    

    public Car() {
    	
	}

	public Car(String model, CarType type, int price, InsuranceType insuranceType) {
        this.model = model;
        this.type = type;
        this.price = price;
        this.insuranceType = insuranceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type.getName();
    }

    public float getPercent() {
        return type.getPercent();
    }

    public void setType(CarType type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getInsuranceType() {
        return insuranceType.getName();
    }

    public void setInsuranceType(InsuranceType insuranceType) {
        this.insuranceType = insuranceType;
    }

    @Override
    public String toString() {
        return "Model = " + model + ", Type = " + type.getName()
                + ", Price = " + price + ", Insurance Type = "
                + insuranceType.getName();
    }
}
