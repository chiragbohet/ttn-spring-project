package com.chiragbohet.ecommerce.Entities.ProductRelated;

import com.chiragbohet.ecommerce.Utilities.MetadataConverter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Map;

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

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @Column(name = "METADATA")
    @Convert(converter = MetadataConverter.class)
    private Map<String, String> metadata;


}
