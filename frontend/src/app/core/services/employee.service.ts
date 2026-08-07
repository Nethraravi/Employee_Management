import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {Observable } from 'rxjs';
import { API_CONFIG} from '../config/api.config';
import { Employee} from '../models/employee';
import { ApiResponse} from '../models/api-response';
import { CreateEmployeeRequest} from '../models/create-employee-request';
import { Page} from '../models/page';

@Injectable({
  providedIn: 'root'
})

export class EmployeeService {

  private readonly API_URL = 'http://localhost:8080/employees';

  constructor(private http: HttpClient) {} //Dependency injection

  getAllEmployees(page: number, size: number, sortField: string, sortDirection: string, search: string, searchBy:string): Observable<ApiResponse<Page<Employee>>>
  {
    return this.http.get<ApiResponse<Page<Employee>>>(`${API_CONFIG.BASE_URL}/employees?page=${page}&size=${size}&sort=${sortField},${sortDirection}&search=${search}&searchBy=${searchBy}`);
  }

  createEmployee(request: CreateEmployeeRequest): Observable<ApiResponse<Employee>> {
    return this.http.post<ApiResponse<Employee>>(
      `${API_CONFIG.BASE_URL}/employees`, request
    );
  }

  UpdateEmployee(id: number, employee: CreateEmployeeRequest): Observable<ApiResponse<Employee>>
  {
    return this.http.put<ApiResponse<Employee>>(`${API_CONFIG.BASE_URL}/employees/${id}`,employee);
  }

  getEmployeeById(id: number): Observable<ApiResponse<Employee>>
  {
    return this.http.get<ApiResponse<Employee>>(`${API_CONFIG.BASE_URL}/employees/${id}`);
  }

  deleteEmployee(id: number): Observable<ApiResponse<string>>
  {
    return this.http.delete<ApiResponse<string>>(`${API_CONFIG.BASE_URL}/employees/${id}`);
  }
}
