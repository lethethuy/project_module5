package ra.service.impl.category;

import jdk.jshell.spi.ExecutionControl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ra.model.dto.request.CategoryRequest;
import ra.model.dto.response.CategoryResponse;
import ra.model.entity.Category;
import ra.repository.ICategoryRepository;
import ra.service.IGenericService;
import ra.service.mapper.CategoryMapper;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService implements ICategoryService{
    @Autowired
    private ICategoryRepository categoryRepository;
    @Autowired
    private CategoryMapper categoryMapper;


    @Override
    public List<CategoryResponse> findAll() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(category -> categoryMapper.toResponse(category))
                .collect(Collectors.toList());

    }

    @Override
    public CategoryResponse findById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent()){
            return categoryMapper.toResponse(categoryOptional.get());
        }
        return null;
    }

    @Override
    public CategoryResponse save(CategoryRequest categoryRequest) {

        Category category = categoryRepository.save(categoryMapper.toEntity(categoryRequest));
        return categoryMapper.toResponse(category);
    }

    @Override
    public CategoryResponse update(CategoryRequest categoryRequest, Long id) {
        Category customer = categoryMapper.toEntity(categoryRequest);
        customer.setId(id);
        return categoryMapper.toResponse(categoryRepository.save(customer));
    }

    @Override
    public CategoryResponse deleteById(Long id) {
        Optional<Category> categoryOptional = categoryRepository.findById(id);
        if(categoryOptional.isPresent()){
            categoryRepository.deleteById(id);
            return categoryMapper.toResponse(categoryOptional.get());
        }
        return null;
    }
}

