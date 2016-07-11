package edu.ejemplo.demo.value_object;

import edu.ejemplo.demo.model.Coche;

import java.util.List;

/**
 * Created by admin on 7/11/2016.
 */
public class CarFilterResult {
    List<Coche> carList;
    private Long totalUnfiltered;
    private Long totalFiltered;

    public List<Coche> getCarList() {
        return carList;
    }

    public void setCarList(List<Coche> carList) {
        this.carList = carList;
    }

    public Long getTotalUnfiltered() {
        return totalUnfiltered;
    }

    public void setTotalUnfiltered(Long totalUnfiltered) {
        this.totalUnfiltered = totalUnfiltered;
    }

    public Long getTotalFiltered() {
        return totalFiltered;
    }

    public void setTotalFiltered(Long totalFiltered) {
        this.totalFiltered = totalFiltered;
    }
}
