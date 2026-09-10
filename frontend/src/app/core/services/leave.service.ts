import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

import { LeaveRequest } from '../models/leave-request';
import { LeaveResponse } from '../models/leave-response';
import { API_CONFIG } from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class LeaveService {

  constructor(private http: HttpClient) {}

  applyLeave(request: LeaveRequest): Observable<any> {
    return this.http.post(
      `${API_CONFIG.BASE_URL}/leaves`,
      request
    );
  }

  getMyLeaves(): Observable<any> {

    return this.http.get(
      `${API_CONFIG.BASE_URL}/leaves/my`
    );
  }

  getAllLeaves(): Observable<any> {
    return this.http.get(
      `${API_CONFIG.BASE_URL}/leaves`
    );
  }

  updateLeaveStatus(
    id: number,
    status: string
  ): Observable<any> {

    return this.http.put(
      `${API_CONFIG.BASE_URL}/leaves/${id}/status?status=${status}`,
      {}
    );
  }

  getLeaveById(id: number): Observable<any> {
    return this.http.get(`${API_CONFIG.BASE_URL}/leaves/${id}`);
  }

  getLeavesByEmployee(employeeId: number): Observable<any> {
    return this.http.get(`${API_CONFIG.BASE_URL}/leaves/employee/${employeeId}`);
  }

  getLeavesByStatus(status: string): Observable<any> {
    return this.http.get(`${API_CONFIG.BASE_URL}/leaves/status/${status}`);
  }
}
