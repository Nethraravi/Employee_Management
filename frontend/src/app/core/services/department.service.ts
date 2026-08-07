import { Injectable} from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable} from 'rxjs';

import { API_CONFIG} from '../config/api.config';
import {ApiResponse} from '../models/api-response';
import {Department} from '../models/department';
import {Departments} from '../../features/departments/departments';
import {CreateEmployeeRequest} from '../models/create-employee-request';
import {CreateDepartmentRequest} from '../models/create-department-request';
import {Page} from '../models/page';

@Injectable({
  providedIn: 'root'
})
export class DepartmentService {
  constructor(private http: HttpClient) {
  }

  getAllDepartments(page: number, size: number, sortField: string, sortDirection: string, search: string): Observable<ApiResponse<Page<Department>>>
  {
    return this.http.get<ApiResponse<Page<Department>>>(`${API_CONFIG.BASE_URL}/departments?page=${page}&size=${size}&sort=${sortField},${sortDirection}&search=${search}`);
  }

  createDepartment(request: CreateDepartmentRequest): Observable<ApiResponse<Department>>
  {
    return this.http.post<ApiResponse<Department>>(`${API_CONFIG.BASE_URL}/departments`,request);
  }

  getDepartmentById(id: number): Observable<ApiResponse<Department>>
  {
    return this.http.get<ApiResponse<Department>>(`${API_CONFIG.BASE_URL}/departments/${id}`);
  }

  updateDepartment(id: number, request: CreateDepartmentRequest): Observable<ApiResponse<Department>>
  {
    return this.http.put<ApiResponse<Department>>(`${API_CONFIG.BASE_URL}/departments/${id}`,request);
  }

  deleteDepartment(id: number): Observable<ApiResponse<string>>
  {
    return this.http.delete<ApiResponse<string>>(`${API_CONFIG.BASE_URL}/departments/${id}`);
  }
}
