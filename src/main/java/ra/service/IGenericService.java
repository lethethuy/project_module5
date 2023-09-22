package ra.service;

import ra.exception.NotFoundException;
import ra.model.dto.request.OrderRequest;

import java.util.List;

public interface IGenericService<K,V> {
    // K request, V response
    List<V> findAll();
    V findById (Long id) throws NotFoundException;
    V save (K k) throws NotFoundException;
    V update (K k,Long id) throws NotFoundException;
    V deleteById (Long id) throws NotFoundException;
}