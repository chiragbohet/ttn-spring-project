package com.chiragbohet.ecommerce.entities;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(CategoryMetadataFieldValues.class)
public abstract class CategoryMetadataFieldValues_ extends com.chiragbohet.ecommerce.utilities.Auditable_ {

	public static volatile SingularAttribute<CategoryMetadataFieldValues, String> values;
	public static volatile SingularAttribute<CategoryMetadataFieldValues, CategoryMetadataFieldValuesId> id;
	public static volatile SingularAttribute<CategoryMetadataFieldValues, Category> category;
	public static volatile SingularAttribute<CategoryMetadataFieldValues, CategoryMetadataField> categoryMetadataField;

	public static final String VALUES = "values";
	public static final String ID = "id";
	public static final String CATEGORY = "category";
	public static final String CATEGORY_METADATA_FIELD = "categoryMetadataField";

}

