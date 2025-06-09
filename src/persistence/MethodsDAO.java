// Package persistence;
package persistence;

// Imports;
import java.util.List;

// Interface MethodsDAO, with a generic type that will be defined on the class;
public interface MethodsDAO< T>{

    // Insert method to add a register on the Database;
    public void insert(T ObjectDAO);

    // Update method to alter a register on the Database; 
    public void update(T ObjectDAO);

    // Delete method to delete a register on the Database;
    public void delete(T ObjectDAO);

    // Search method to select a register on the Database by the id;
    public T searchById(long id);

    // Search all method to select all the possibilities on the Database;
    public List<T> searchAll();

}
