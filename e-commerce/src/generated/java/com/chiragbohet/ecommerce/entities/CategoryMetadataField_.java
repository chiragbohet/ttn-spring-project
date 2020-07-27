package com.chiragbohet.ecommerce.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SetAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CategoryMetadataField.class)
public abstract class CategoryMetadataField_ extends com.chiragbohet.ecommerce.utilities.Auditable_ {

	public static volatile SingularAttribute<CategoryMetadataField, String> name;
	public static volatile SingularAttribute<CategoryMetadataField, Long> id;
	public static volatile SetAttribute<CategoryMetadataField, CategoryMetadataFieldValues> fieldValuesSet;

	public static final String NAME = "name";
	public static final String ID = "id";
	public static final String FIELD_VALUES_SET = "fieldValuesSet";

}

