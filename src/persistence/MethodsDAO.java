package persistence;

import java.util.List;

public interface MethodsDAO <T>{

    void insert(T objDAO);

    void update(T objDAO);

    void delete(T objDAO);

     T searchById(long id);

     List<T> searchAll();

}
