package dtos;

import java.util.Objects;

public class PHMixDTO {
    double totalPH;


    public PHMixDTO(double totalPH) {
        this.totalPH = totalPH;
    }

    public double getTotalPH() {
        return totalPH;
    }

    public void setTotalPH(double totalPH) {
        this.totalPH = totalPH;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PHMixDTO entity = (PHMixDTO) o;
        return Objects.equals(this.totalPH, entity.totalPH);
    }

}
