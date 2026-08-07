import {HttpErrorResponse, HttpInterceptorFn} from '@angular/common/http';
import { AuthService} from '../services/auth.service';
import { inject} from '@angular/core';
import {Router} from '@angular/router';
import {catchError, throwError} from 'rxjs';

export const jwtInterceptor: HttpInterceptorFn = (req, next) => {
  const authService = inject(AuthService);
  const router = inject(Router);
  const token = authService.getToken();
  if(token)
  {
    const modifiedRequest = token
    ? req.clone({
        setHeaders: {
          Authorization: `Bearer ${token}`
        }
      })
    : req;
    return next(modifiedRequest).pipe(
      catchError((error: HttpErrorResponse) => {
        if(error.status === 401 || error.status === 403) {
          authService.logout();
          router.navigate(['/']);
        }
        return throwError(() => error);
      })
    );
  };
  return next(req);
};
