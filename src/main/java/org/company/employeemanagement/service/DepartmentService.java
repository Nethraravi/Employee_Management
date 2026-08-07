package org.company.employeemanagement.service;

import org.company.employeemanagement.dto.DepartmentRequestDTO;
import org.company.employeemanagement.dto.DepartmentResponseDTO;
import org.company.employeemanagement.entity.Department;
import org.company.employeemanagement.exception.DepartmentDeletionException;
import org.company.employeemanagement.mapper.DepartmentMapper;
import org.company.employeemanagement.repository.DepartmentRepository;
import org.company.employeemanagement.specification.DepartmentSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final DepartmentMapper departmentMapper;

    public DepartmentService(DepartmentRepository departmentRepository,
                             DepartmentMapper departmentMapper)
    {
        this.departmentRepository=departmentRepository;
        this.departmentMapper=departmentMapper;
    }

    public DepartmentResponseDTO createDepartment(DepartmentRequestDTO dto)
    {
        if(departmentRepository.existsByNameIgnoreCase(dto.getName()))
        {
            throw new RuntimeException("Department already exists");
        }
        Department dept = departmentMapper.toEntity(dto);
        Department savedDepartment = departmentRepository.save(dept);
        return departmentMapper.toDto(savedDepartment);
    }

    public Page<DepartmentResponseDTO> getAllDepartments(String search, Pageable pageable)
    {
        Specification<Department> specification = (root, query, cb) -> cb.conjunction();
        if(search != null && !search.isBlank())
        {
            specification = specification.and(DepartmentSpecification.hasNameContaining(search));
        }

        return departmentRepository.findAll(specification, pageable).map(departmentMapper::toDto);
    }

    public DepartmentResponseDTO getDepartmentById(Long id)
    {
        return departmentMapper.toDto(getDepartmentEntity(id));
    }

    private Department getDepartmentEntity(Long id)
    {
        return departmentRepository.findById(id).orElseThrow(()->new RuntimeException("Department not found"));
    }

    public Department updateDepartment(Long id, DepartmentRequestDTO dto)
    {
        Department dept = getDepartmentEntity(id);

        if(!dept.getName().equalsIgnoreCase(dto.getName()) && departmentRepository.existsByNameIgnoreCase(dto.getName()))
        {
            throw new RuntimeException("Department already exists");
        }

        dept.setName(dto.getName());
        return departmentRepository.save(dept);
    }

    public void deleteDepartment(Long id)
    {
        Department dept = getDepartmentEntity(id);
        if(!dept.getEmployees().isEmpty())
        {
            throw new DepartmentDeletionException("Cannot delete department because employees are assigned to it!");
        }
        departmentRepository.delete(dept);
    }
}
