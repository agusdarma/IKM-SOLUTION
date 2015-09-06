package com.parking.data;



public class Product {

    
    private Long id;

   
    private String shortName;

   
    private String longName;

    
    private String thumbnailFilePath;

   
    private Long priceIdr;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(final String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(final String longName) {
        this.longName = longName;
    }

    public String getThumbnailFilePath() {
        return thumbnailFilePath;
    }

    public void setThumbnailFilePath(final String thumbnailFilePath) {
        this.thumbnailFilePath = thumbnailFilePath;
    }

    public Long getPriceIdr() {
        return priceIdr;
    }

    public void setPriceIdr(final Long priceIdr) {
        this.priceIdr = priceIdr;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Product product = (Product) o;

        if (id != null ? !id.equals(product.id) : product.id != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
    
   
}
