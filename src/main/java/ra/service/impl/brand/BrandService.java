package ra.service.impl.brand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.dto.request.BrandRequest;
import ra.model.dto.response.BrandResponse;
import ra.model.entity.Brand;
import ra.repository.IBrandRepository;
import ra.service.mapper.BrandMapper;


import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Service
public class BrandService implements IBrandService{
    @Autowired
    private IBrandRepository brandRepository;
    @Autowired
    private BrandMapper brandMapper;


    @Override
    public List<BrandResponse> findAll() {
        List<Brand> brands = brandRepository.findAll();
        return brands.stream()
                .map(brand -> brandMapper.toResponse(brand))
                .collect(Collectors.toList());

    }

    @Override
    public BrandResponse findById(Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if(brandOptional.isPresent()){
            return brandMapper.toResponse(brandOptional.get());
        }
        return null;
    }

    @Override
    public BrandResponse save(BrandRequest brandRequest) {

        Brand brand = brandRepository.save(brandMapper.toEntity(brandRequest));
        return brandMapper.toResponse(brand);
    }

    @Override
    public BrandResponse update(BrandRequest brandRequest, Long id) {
        Brand brand = brandMapper.toEntity(brandRequest);
        brand.setId(id);
        return brandMapper.toResponse(brandRepository.save(brand));
    }

    @Override
    public BrandResponse deleteById(Long id) {
        Optional<Brand> brandOptional = brandRepository.findById(id);
        if(brandOptional.isPresent()){
            brandRepository.deleteById(id);
            return brandMapper.toResponse(brandOptional.get());
        }
        return null;
    }
}
