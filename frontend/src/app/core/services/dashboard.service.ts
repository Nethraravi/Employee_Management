import { Injectable} from '@angular/core';
import { HttpClient} from '@angular/common/http';
import { Observable} from 'rxjs';
import { API_CONFIG} from '../config/api.config';
import {ApiResponse} from '../models/api-response';
import {Dashboard} from '../models/dashboard';

@Injectable({
  providedIn: 'root'
})
export class DashboardService {
  constructor(private http: HttpClient) {
  }

  getDashboardData(): Observable<any> {
    return this.http.get<any>(`${API_CONFIG.BASE_URL}/dashboard`);
  }

  getDashboard(): Observable<ApiResponse<Dashboard>>
  {
    return this.http.get<ApiResponse<Dashboard>>(`${API_CONFIG.BASE_URL}/dashboard`);
  }

}
