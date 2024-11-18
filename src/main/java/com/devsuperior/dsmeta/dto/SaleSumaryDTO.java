package com.devsuperior.dsmeta.dto;

public class SaleSumaryDTO {

    private String sellerName;
    private Double total;

    public SaleSumaryDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }

}
