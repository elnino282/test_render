package org.example.AgentManagementBE.Service;

import org.example.AgentManagementBE.Model.Product;
import org.example.AgentManagementBE.Model.Parameter;
import org.example.AgentManagementBE.Repository.ProductRepository;
import org.example.AgentManagementBE.Repository.ParameterRepository;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ParameterService {
    private final ParameterRepository parameterRepository;
    private final ProductRepository productRepository;
    
    @Autowired
    public ParameterService(ParameterRepository parameterRepository, ProductRepository productRepository) {
        this.parameterRepository = parameterRepository;
        this.productRepository = productRepository;
    }

    public boolean addParameter(Parameter parameter) {
        try {
            Parameter existingParameter = parameterRepository.getParameterByName(parameter.getParameterName());
            if (existingParameter != null) {
                return false;
            }
            parameterRepository.save(parameter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int getParameter(String parameterName) {
        try {
            Parameter parameter = parameterRepository.getParameterByName(parameterName);
            if (parameter == null) {
                return -1;
            }
            return parameter.getParameterValue();
        } catch (Exception e) {
            return -1;
        }
    }

    public Iterable<Parameter> getAllParameter() {
        return parameterRepository.findAll();
    }

    @Transactional
    public boolean updateParameter(@NotNull Parameter parameter) {
        try {
            Parameter existingParameter = parameterRepository.getParameterByName(parameter.getParameterName());
            if (existingParameter == null) {
                return false;
            }

            if (parameter.getParameterName().equals("Số đại lý tối đa trong một quận")) {
                if (parameter.getParameterValue() < existingParameter.getParameterValue()) {
                    return false;
                }
            } else if (parameter.getParameterName().equals("Tỷ lệ đơn giá xuất")) {
                if (parameter.getParameterValue() > existingParameter.getParameterValue()) {
                    return false;
                }
                List<Product> products = productRepository.getAllProduct();
                for (Product product : products) {
                    product.setExportPrice(product.getImportPrice() * parameter.getParameterValue() / 100);
                }
                productRepository.saveAll(products);
            }
            parameterRepository.save(parameter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
