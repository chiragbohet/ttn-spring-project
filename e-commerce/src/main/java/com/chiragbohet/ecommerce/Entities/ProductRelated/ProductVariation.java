package com.chiragbohet.ecommerce.Entities.ProductRelated;

import com.chiragbohet.ecommerce.Entities.CategoryRelated.Category;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataField;
import com.chiragbohet.ecommerce.Entities.CategoryRelated.CategoryMetadataFieldValues;
import com.chiragbohet.ecommerce.Exceptions.GenericUserValidationFailedException;
import com.chiragbohet.ecommerce.Utilities.MetadataConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_VARIATION")
public class ProductVariation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "QUANTITY_AVAILABLE")
    private Long quantityAvailable;

    @Column(name = "PRICE")
    private BigDecimal price;

    @Column(name = "PRIMARY_IMAGE_NAME")
    private String primaryImageName;

    @Column(name = "IS_ACTIVE")
    Boolean isActive;

    @Setter(AccessLevel.NONE)
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Setter(AccessLevel.NONE)
    @Column(name = "METADATA")
    @Convert(converter = MetadataConverter.class)
    private Map<String, String> metadata;


    // product must be set before this or will throw a null pointer exception
    public void setMetadata(Map<String, String> userInputMetadata)
    {
        Set<CategoryMetadataFieldValues> categoryFieldnamesAndValues = this.product.category.getFieldValuesSet();

        Map<String,Set<String>> fieldNamesAndTheirValuesMap = new HashMap<>();

        // using a tree map so that all the product belonging to same category have a similar metadata structure
        this.metadata = new TreeMap<>();

        for(CategoryMetadataFieldValues fieldValue : categoryFieldnamesAndValues)
        {
            String fieldName = fieldValue.getCategoryMetadataField().getName();
            Set<String> possibleValues = new HashSet<>(Arrays.asList(fieldValue.getValues().split(",")));
            fieldNamesAndTheirValuesMap.put(fieldName, possibleValues);
            this.metadata.put(fieldName, null); // Initializing the map with all the fields and null
        }


        for(Map.Entry<String,String> entry : userInputMetadata.entrySet())
        {
            String userInputField = entry.getKey();
            String userInputValue = entry.getValue();

            if(fieldNamesAndTheirValuesMap.containsKey(userInputField))
            {
                if(fieldNamesAndTheirValuesMap.get(userInputField).contains(userInputValue))
                    this.metadata.put(userInputField,userInputValue);
                else
                    throw new GenericUserValidationFailedException("No value as : " + userInputValue + ", for the field : "+ userInputField + ". Please choose from one of these values : " + fieldNamesAndTheirValuesMap.get(userInputField).toString());
            }
            else
                throw new GenericUserValidationFailedException("No field with name : " + userInputField + ", associated with the category : " + this.product.getCategory().getName()+". Please choose fields within : " + fieldNamesAndTheirValuesMap.keySet().toString());

        }

    }

    public void setProduct(Product product){
       if(product != null)
       {
           if(this.product == product)  // to prevent endless loop
               return;
           else if(this.product == null)
           {
               this.product = product;
               product.addProductVariation(this);
           }

       }
    }

}
