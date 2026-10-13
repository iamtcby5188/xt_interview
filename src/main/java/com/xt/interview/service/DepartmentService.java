package com.xt.interview.service;

import com.xt.interview.dto.DepartmentDTO;
import com.xt.interview.dto.PageResult;

import java.util.List;

public interface DepartmentService {

    DepartmentDTO createDepartment(DepartmentDTO dto);

    DepartmentDTO updateDepartment(Long id, DepartmentDTO dto);

    void deleteDepartment(Long id);

    DepartmentDTO getDepartmentById(Long id);

    List<DepartmentDTO> getAllDepartments();

    PageResult<DepartmentDTO> getDepartmentPage(int page, int size, String name, String code, Integer status);
}
