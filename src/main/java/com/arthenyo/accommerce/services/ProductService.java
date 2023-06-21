package com.arthenyo.accommerce.services;

import com.arthenyo.accommerce.DTO.ProductDTO;
import com.arthenyo.accommerce.DTO.ProductMinDTO;
import com.arthenyo.accommerce.entities.Product;
import com.arthenyo.accommerce.repositories.ProductRepository;
import com.arthenyo.accommerce.services.excptions.DataBaseExcption;
import com.arthenyo.accommerce.services.excptions.ResouceNotFoundExcption;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResouceNotFoundExcption("Recurso não encontrado"));
        return new ProductDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductMinDTO> findAll(String name, Pageable pageable){
        Page<Product> product = productRepository.searchByName(name,pageable);
        return product.map(x -> new ProductMinDTO(x));
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto){
        Product entity =new Product();
        copyDtoToEntity(dto, entity);
        entity = productRepository.save(entity);
        return new ProductDTO(entity);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto){
        try {
            Product entity = productRepository.getReferenceById(id);
            copyDtoToEntity(dto, entity);
            entity = productRepository.save(entity);
            return new ProductDTO(entity);
        }catch (EntityNotFoundException e){
            throw new ResouceNotFoundExcption("Recurso não encontrado");
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id){
        try {
            productRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            throw new ResouceNotFoundExcption("Recurso não encontrado");
        }catch (DataIntegrityViolationException e){
            throw new DataBaseExcption("Falha de integridade referencial");
        }

    }

    private void copyDtoToEntity(ProductDTO dto, Product entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
    }
}
