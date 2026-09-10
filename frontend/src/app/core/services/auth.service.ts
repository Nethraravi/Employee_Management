import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LoginRequest } from '../models/login-request';
import { AuthenticationResponse } from '../models/authentication-response';
import { API_CONFIG} from '../config/api.config';

@Injectable({
  providedIn: 'root'
})
export class AuthService
{
  constructor(private http: HttpClient) {
  }

  login(request: LoginRequest): Observable<AuthenticationResponse>
  {
    return this.http.post<AuthenticationResponse>(
      `${API_CONFIG.BASE_URL}/auth/login`, request
    );
  }

  saveToken(token: string): void
  {
    localStorage.setItem('jwt', token);
  }

  getToken(): string | null
  {
    return localStorage.getItem('jwt');
  }

  getRole(): string | null
  {
    const token = this.getToken();
    if(!token)
    {
      return null;
    }

    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.role;
    }
    catch (error)
    {
      return null;
    }
  }

  isAdmin(): boolean
  {
    return this.getRole() === 'ROLE_ADMIN';
  }

  logout(): void {
    localStorage.removeItem('jwt');
  }

  changePassword(currentPassword: string,newPassword: string): Observable<any> {

    return this.http.post(
        `${API_CONFIG.BASE_URL}/auth/change-password`,
        {
          currentPassword,
          newPassword
        }
    );
  }

}
