package com.example.xcargomobile.cargo;

import java.util.ArrayList;
import java.util.List;

public class CargoTracking {

    List<Cargo> Cargo;

    public CargoTracking() {
        this.Cargo = new ArrayList();
    }

    public CargoTracking(List<Cargo> Cargo) {
        this.Cargo = Cargo;
    }

    public List<Cargo> getCargo() {
        return Cargo;
    }

    public void setKargo(List<Cargo> Cargo) {
        this.Cargo = Cargo;
    }

}
