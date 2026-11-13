package com.nagarro.enums;

public enum InsuranceType {
    BASIC("Basic", 0), PREMIUM("Premium", 20);
    private final String name;
    private final float percent;

    private InsuranceType(String name, float percent) {
        this.name = name;
        this.percent = percent;
    }

    public String getName() {
        return name;
    }

    public float getPercent() {
        return percent;
    }

    public static InsuranceType findByName(String name){
        for(InsuranceType ins : InsuranceType.values()){
            if( ins.getName().equals(name)){
                return ins;
            }
        }
        return null;
    }
}
