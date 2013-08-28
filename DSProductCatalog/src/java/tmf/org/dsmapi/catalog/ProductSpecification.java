/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author pierregauthier
 * {"ProductSpecification": {
    "id": "http://serverlocation:port/catalogManagement/productSpecification/22",
    "name": "iPhone 42",
    "description": "Siri works on this iPhone",
    "brand": "Apple",
    "validFor": {
        "startDateTime": "2013-04-19T16:42:23-04:00",
        "endDateTime": "2013-06-19T00:00:00-04:00"
    },
    "productSpecCharacteristics": [
        {
            "name": "Screen Size",
            "description": "la dimension de l'ecran",
            "valueType": "number",
            "configurable": "false",
            "ProductSpecCharacteristicValue": [{
                "valueType": "number",
                "default": "true",
                "value": "4.2",
                "unitOfMeasure": "inches",
                "valueFrom": "",
                "valueTo": ""
            }]
        },
        {
            "name": "Colour",
            "description": "La couleur du bidule",
            "valueType": "string",
            "configurable": "true",
            "ProductSpecCharacteristicValue": [
                {
                    "valueType": "string",
                    "default": "true",
                    "value": "Black",
                    "unitOfMeasure": "",
                    "valueFrom": "",
                    "valueTo": ""
                },
                {
                    "valueType": "string",
                    "default": "false",
                    "value": "White",
                    "unitOfMeasure": "",
                    "valueFrom": "",
                    "valueTo": ""
                }
            ]
        }
 * 
 */
@Entity
@XmlRootElement
public class ProductSpecification implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    
    private String name;
    
    private String description;
    
    private String brand;
    
   

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public TimeRange getValidFor() {
        return validFor;
    }

    public void setValidFor(TimeRange validFor) {
        this.validFor = validFor;
    }

    
    
    //@Lob
    private TimeRange validFor;
    
    
    
     ProductSpecCharacteristic[] productSpecCharacteristics;

    public ProductSpecCharacteristic[] getProductSpecCharacteristics() {
        return productSpecCharacteristics;
    }

    public void setProductSpecCharacteristics(ProductSpecCharacteristic[] productSpecCharacteristics) {
        this.productSpecCharacteristics = productSpecCharacteristics;
    }
    
    
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductSpecification)) {
            return false;
        }
        ProductSpecification other = (ProductSpecification) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "tmf.dsm.catalog.ProductSpecification[ id=" + id + " ]";
    }
    
}
