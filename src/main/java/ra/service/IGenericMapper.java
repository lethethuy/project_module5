package ra.service;

import ra.exception.NotFoundException;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.Orders;

public interface IGenericMapper<T,K,V> {

    // K request, T entity, V response
    T toEntity(K k) throws NotFoundException; // Nhan vao Request tra ve 1 Entity
    V toResponse(T t); // Tu Entity tra ve Response
}