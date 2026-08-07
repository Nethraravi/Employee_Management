import { inject} from '@angular/core';
import { Router, CanActivateFn} from '@angular/router';
import { AuthService} from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  console.log("Guard executing");

  const authService = inject(AuthService);
  const router = inject(Router);

  const token = authService.getToken();

  if(token)
  {
    return true;
  }

  router.navigate(['/']);
  return false;
};
