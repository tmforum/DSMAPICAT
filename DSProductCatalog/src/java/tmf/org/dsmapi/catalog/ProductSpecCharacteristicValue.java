/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.logging.Logger;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *            "ProductSpecCharacteristicValue": [{
                "valueType": "number",
                "default": "true",
                "value": "4.2",
                "unitOfMeasure": "inches",
                "valueFrom": "",
                "valueTo": ""
            }]

 * @author pierregauthier
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductSpecCharacteristicValue implements Serializable{
    
    private static final Logger LOG = Logger.getLogger(ProductSpecCharacteristicValue.class.getName());
    
    
    
    String valueType;
    
    Boolean defaultValue;
    
    String value;
    
    String unitOfMeasure;
    
    TimeRange timeRange;
    
    String valueFrom;
    
    String valueTo;

    public String getValueFrom() {
        return valueFrom;
    }

    public void setValueFrom(String valueFrom) {
        this.valueFrom = valueFrom;
    }

    public String getValueTo() {
        return valueTo;
    }

    public void setValueTo(String valueTo) {
        this.valueTo = valueTo;
    }

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Boolean getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public void setUnitOfMeasure(String unitOfMeasure) {
        this.unitOfMeasure = unitOfMeasure;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.valueType != null ? this.valueType.hashCode() : 0);
        hash = 19 * hash + (this.defaultValue != null ? this.defaultValue.hashCode() : 0);
        hash = 19 * hash + (this.value != null ? this.value.hashCode() : 0);
        hash = 19 * hash + (this.unitOfMeasure != null ? this.unitOfMeasure.hashCode() : 0);
        hash = 19 * hash + (this.timeRange != null ? this.timeRange.hashCode() : 0);
        hash = 19 * hash + (this.valueFrom != null ? this.valueFrom.hashCode() : 0);
        hash = 19 * hash + (this.valueTo != null ? this.valueTo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductSpecCharacteristicValue other = (ProductSpecCharacteristicValue) obj;
        if ((this.valueType == null) ? (other.valueType != null) : !this.valueType.equals(other.valueType)) {
            return false;
        }
        if (this.defaultValue != other.defaultValue && (this.defaultValue == null || !this.defaultValue.equals(other.defaultValue))) {
            return false;
        }
        if ((this.value == null) ? (other.value != null) : !this.value.equals(other.value)) {
            return false;
        }
        if ((this.unitOfMeasure == null) ? (other.unitOfMeasure != null) : !this.unitOfMeasure.equals(other.unitOfMeasure)) {
            return false;
        }
        if (this.timeRange != other.timeRange && (this.timeRange == null || !this.timeRange.equals(other.timeRange))) {
            return false;
        }
        if ((this.valueFrom == null) ? (other.valueFrom != null) : !this.valueFrom.equals(other.valueFrom)) {
            return false;
        }
        if ((this.valueTo == null) ? (other.valueTo != null) : !this.valueTo.equals(other.valueTo)) {
            return false;
        }
        return true;
    }
   
    

    
   
  
}
