package com.xt.interview.service.impl;

import com.xt.interview.dto.DepartmentDTO;
import com.xt.interview.dto.PageResult;
import com.xt.interview.entity.Department;
import com.xt.interview.repository.DepartmentRepository;
import com.xt.interview.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private static final Integer NOT_DELETED = 0;
    private static final Integer DELETED = 1;

    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public DepartmentDTO createDepartment(DepartmentDTO dto) {
        if (departmentRepository.existsByCodeAndDeleted(dto.getCode(), NOT_DELETED)) {
            throw new RuntimeException("部门编码已存在");
        }
        Department department = new Department();
        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());
        department.setStatus(dto.getStatus());
        department.setDeleted(NOT_DELETED);
        department = departmentRepository.save(department);
        return convertToDTO(department);
    }

    @Override
    public DepartmentDTO updateDepartment(Long id, DepartmentDTO dto) {
        Department department = departmentRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new RuntimeException("部门不存在"));

        if (!department.getCode().equals(dto.getCode()) &&
            departmentRepository.existsByCodeAndDeleted(dto.getCode(), NOT_DELETED)) {
            throw new RuntimeException("部门编码已存在");
        }

        department.setName(dto.getName());
        department.setCode(dto.getCode());
        department.setDescription(dto.getDescription());
        department.setStatus(dto.getStatus());
        department = departmentRepository.save(department);
        return convertToDTO(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new RuntimeException("部门不存在"));
        department.setDeleted(DELETED);
        departmentRepository.save(department);
    }

    @Override
    @Transactional(readOnly = true)
    public DepartmentDTO getDepartmentById(Long id) {
        Department department = departmentRepository.findByIdAndDeleted(id, NOT_DELETED)
                .orElseThrow(() -> new RuntimeException("部门不存在"));
        return convertToDTO(department);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DepartmentDTO> getAllDepartments() {
        return departmentRepository.findByDeleted(NOT_DELETED).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<DepartmentDTO> getDepartmentPage(int page, int size, String name, String code, Integer status) {
        Pageable pageable = PageRequest.of(page, size);

        Specification<Department> spec = (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            predicates.add(cb.equal(root.get("deleted"), NOT_DELETED));

            if (StringUtils.hasText(name)) {
                predicates.add(cb.like(root.get("name"), "%" + name.trim() + "%"));
            }
            if (StringUtils.hasText(code)) {
                predicates.add(cb.like(root.get("code"), "%" + code.trim() + "%"));
            }
            if (status != null) {
                predicates.add(cb.equal(root.get("status"), status));
            }
            return cb.and(predicates.toArray(new Predicate[0]));
        };

        Page<Department> deptPage = departmentRepository.findAll(spec, pageable);

        List<DepartmentDTO> dtoList = deptPage.getContent().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());

        return new PageResult<>(
                dtoList,
                deptPage.getTotalElements(),
                deptPage.getTotalPages(),
                deptPage.getNumber(),
                deptPage.getSize()
        );
    }

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO();
        dto.setId(department.getId());
        dto.setName(department.getName());
        dto.setCode(department.getCode());
        dto.setDescription(department.getDescription());
        dto.setStatus(department.getStatus());
        dto.setCreateTime(department.getCreateTime());
        dto.setUpdateTime(department.getUpdateTime());
        return dto;
    }
}
