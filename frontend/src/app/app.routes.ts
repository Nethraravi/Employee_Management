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
import { ChangePassword } from './features/auth/change-password/change-password';
import {ApplyLeave} from './features/leaves/apply-leave/apply-leave';
import { MyLeaves } from './features/leaves/my-leaves/my-leaves';
import { LeaveManagement } from './features/leaves/leave-management/leave-management';

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
        path: 'change-password',
        component: ChangePassword
      },
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
      {
        path: 'apply-leave',
        component: ApplyLeave
      },
      {
        path: 'my-leaves',
        component: MyLeaves
      },
      {
        path: 'leave-management',
        component: LeaveManagement
      }
    ]
  }
];
