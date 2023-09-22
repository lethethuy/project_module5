package ra.service.mapper;

import org.springframework.stereotype.Component;
import ra.exception.NotFoundException;
import ra.model.dto.request.BrandRequest;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.request.OrderRequest;
import ra.model.dto.response.BrandResponse;
import ra.model.dto.response.CategoryResponse;
import ra.model.dto.response.OrderResponse;
import ra.model.entity.Brand;
import ra.model.entity.Category;
import ra.model.entity.Orders;
import ra.service.IGenericMapper;

@Component
public class BrandMapper implements IGenericMapper<Brand, BrandRequest, BrandResponse> {


    @Override
    public Brand toEntity(BrandRequest brandRequest) {
        return Brand.builder().name(brandRequest.getName())
                .build();
    }

    @Override
    public BrandResponse toResponse(Brand brand) {
        return BrandResponse.builder().id(brand.getId())
                .name(brand.getName())
                .build();
    }
}
