package com.nagarro.enums;

public enum CarType {
    HATCHBACK("Hatchback", 5), SEDAN("Sedan", 8), SUV("SUV", 10);
    private final String name;
    private final float percent;

    private CarType(String name, float percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public float getPercent() {
        return percent;
    }

    public static CarType findByName(String name){
        for(CarType c : values()){
            if( c.getName().equals(name)){
                return c;
            }
        }
        return null;
    }
}
