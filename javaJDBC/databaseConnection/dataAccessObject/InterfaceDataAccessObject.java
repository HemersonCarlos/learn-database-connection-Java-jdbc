package dataAccessObject;

import java.util.List;

public interface InterfaceDataAccessObject<GenericType> {

	void insert(GenericType informedGenericType);
	List<GenericType> selectAll();
	void update(GenericType informedGenericType);
	void delete(int informedId);
	GenericType selectById(int InformedId);
}