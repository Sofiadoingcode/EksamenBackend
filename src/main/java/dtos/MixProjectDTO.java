package dtos;

import java.util.Objects;

public class MixProjectDTO {
    double totalHours;

    double totalPrice;

    public MixProjectDTO(double totalHours, double totalPrice) {
        this.totalHours = totalHours;
        this.totalPrice = totalPrice;
    }


    public double getTotalHours() {
        return totalHours;
    }

    public void setTotalHours(double totalHours) {
        this.totalHours = totalHours;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MixProjectDTO entity = (MixProjectDTO) o;
        return Objects.equals(this.totalHours, entity.totalHours) &&
                Objects.equals(this.totalPrice, entity.totalPrice);
    }

}
