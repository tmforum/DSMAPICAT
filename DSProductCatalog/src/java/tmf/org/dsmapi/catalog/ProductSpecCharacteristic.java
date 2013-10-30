/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tmf.org.dsmapi.catalog;

import java.io.Serializable;
import java.util.Arrays;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 *     "productSpecCharacteristics": [
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

 *
 * @author pierregauthier
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ProductSpecCharacteristic implements Serializable {
    String name;
    
    String description;
    
    String valueType;
    
    Boolean configurable;
    
    ProductSpecCharacteristicValue[] productSpecCharacteristicValues;

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

    public String getValueType() {
        return valueType;
    }

    public void setValueType(String valueType) {
        this.valueType = valueType;
    }

    public Boolean getConfigurable() {
        return configurable;
    }

    public void setConfigurable(Boolean configurable) {
        this.configurable = configurable;
    }

    public ProductSpecCharacteristicValue[] getProductSpecCharacteristicValues() {
        return productSpecCharacteristicValues;
    }

    public void setProductSpecCharacteristicValues(ProductSpecCharacteristicValue[] productSpecCharacteristicValues) {
        this.productSpecCharacteristicValues = productSpecCharacteristicValues;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + (this.name != null ? this.name.hashCode() : 0);
        hash = 71 * hash + (this.description != null ? this.description.hashCode() : 0);
        hash = 71 * hash + (this.valueType != null ? this.valueType.hashCode() : 0);
        hash = 71 * hash + (this.configurable != null ? this.configurable.hashCode() : 0);
        hash = 71 * hash + Arrays.deepHashCode(this.productSpecCharacteristicValues);
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
        final ProductSpecCharacteristic other = (ProductSpecCharacteristic) obj;
        if ((this.name == null) ? (other.name != null) : !this.name.equals(other.name)) {
            return false;
        }
        if ((this.description == null) ? (other.description != null) : !this.description.equals(other.description)) {
            return false;
        }
        if ((this.valueType == null) ? (other.valueType != null) : !this.valueType.equals(other.valueType)) {
            return false;
        }
        if (this.configurable != other.configurable && (this.configurable == null || !this.configurable.equals(other.configurable))) {
            return false;
        }
        if (!Arrays.deepEquals(this.productSpecCharacteristicValues, other.productSpecCharacteristicValues)) {
            return false;
        }
        return true;
    }
    
    
}
