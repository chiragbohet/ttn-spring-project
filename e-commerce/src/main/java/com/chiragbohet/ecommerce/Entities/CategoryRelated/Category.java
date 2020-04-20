package com.chiragbohet.ecommerce.Entities.CategoryRelated;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "CATEGORY")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME")
    String name;

    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    Category category;

    @OneToMany
    Set<CategoryMetadataField> categoryMetadataFieldSet;

}
