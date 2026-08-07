import { Routes } from '@angular/router';
import { Dashboard } from './features/dashboard/dashboard';
import { Employees } from './features/employees/employees';
import { Departments } from './features/departments/departments';
import { Login } from './features/auth/login/login';
import { authGuard} from './core/guards/auth.guard';
import { AuthLayout} from './layouts/auth-layout/auth-layout';
import { MainLayout} from './layouts/main-layout/main-layout';
import { AddEmployee} from './features/add-employee/add-employee';
import { AddDepartment} from './features/add-department/add-department';

export const routes: Routes = [
  {
    path: '',
    component: AuthLayout,
    children: [
      {
        path: '',
        component: Login
      }
    ]
  },
  {
    path: '',
    component: MainLayout,
    canActivate: [authGuard],
    children: [
      {
        path: 'dashboard',
        component: Dashboard
      },
      {
        path: 'employees',
        component: Employees
      },
      {
        path: 'add-employee',
        component: AddEmployee
      },
      {
        path: 'edit-employee/:id',
        component: AddEmployee
      },
      {
        path: 'departments',
        component: Departments
      },
      {
        path: 'add-department',
        component: AddDepartment
      },
      {
        path: 'edit-department/:id',
        component: AddDepartment
      },
    ]
  }
];
