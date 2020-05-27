package com.slimani.medicofinder.model;



public class SymptomeService {
    private String symptome;
    private String service;

    public SymptomeService(String symptome, String service) {
        this.symptome = symptome;
        this.service = service;
    }

    public String getSymptome() {
        return symptome;
    }

    public void setSymptome(String symptome) {
        this.symptome = symptome;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
