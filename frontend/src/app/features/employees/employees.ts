import { Component, OnInit} from '@angular/core';
import { EmployeeService} from '../../core/services/employee.service';
import { Employee} from '../../core/models/employee';
import { Router} from '@angular/router';
import { FormsModule} from '@angular/forms';
import { Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged} from 'rxjs/operators';
import { CommonModule} from '@angular/common';
import { AuthService} from '../../core/services/auth.service';

@Component({
  standalone: true,
  selector: 'app-employees',
  imports: [CommonModule, FormsModule],
  templateUrl: './employees.html',
  styleUrl: './employees.css',
})
export class Employees implements OnInit{
  constructor(private employeeService: EmployeeService, private router: Router, public authService: AuthService) {
    console.log('Employees constructor');
    console.log("Employees component instance:",this);
  }

  employees: Employee[] = [];
  currentPage=0;
  pageSize=5;
  totalPages=0;
  totalEmployees = 0;
  sortField = 'id';
  sortDirection = 'asc';
  search='';
  searchBy = 'name';
  isLoading = false;

  ngOnInit() {
    console.log('Employees ngOnInit');
    this.loadEmployees();
    this.searchSubject.pipe(debounceTime(500),distinctUntilChanged()).subscribe(() => {
      this.currentPage=0;
      this.loadEmployees();
    });
  }

  loadEmployees(): void
  {
    this.isLoading = true;
    this.employeeService.getAllEmployees(this.currentPage,this.pageSize,this.sortField,this.sortDirection,this.search,this.searchBy).subscribe({
      next: (response) => {
        this.employees=response.data.content;
        this.totalPages=response.data.totalPages;
        this.totalEmployees=response.data.totalElements;
        // Promise.resolve().then(() => {
        //   this.employees=response.data;
        // });
        this.isLoading=false;
        console.log("Length:",this.employees.length);
      },
      error: (error) => {
        console.error(error);
        this.isLoading=false;
      }
    });
  }

  private searchSubject = new Subject<string>();

  getEmployeeCount(): number {
    console.log("Template invoked. Length = ",this.employees.length);
    return this.employees.length;
  }

  editEmployee(id: number): void {
    console.log('Edit clicked:',id);
    this.router.navigate(['/edit-employee', id]);
  }

  addEmployee(): void
  {
    this.router.navigate(['/add-employee']);
  }

  deleteEmployee(id: number): void
  {
    const confirmed = confirm("Are you sure you want to delete this employee?");
    if(!confirmed)
    {
      return;
    }
    console.log("Deleting Employee:",id);
    this.employeeService.deleteEmployee(id).subscribe({
      next: (response) => {
        alert(response.message);
        this.loadEmployees();
      },
      error: (error) => {
        console.error(error);
        alert(error.error);
      }
    });
  }

  nextPage(): void
  {
    if(this.currentPage < this.totalPages -1)
    {
      this.currentPage++;
      this.loadEmployees();
    }
  }

  previousPage(): void
  {
    if(this.currentPage > 0)
    {
      this.currentPage--;
      this.loadEmployees();
    }
  }

  getPages(): number[]
  {
    return Array.from({length: this.totalPages},(_, i) => i);
  }

  goToPage(page: number): void
  {
    this.currentPage=page;
    this.loadEmployees();
  }

  onSortChange(): void
  {
    this.currentPage=0;
    this.loadEmployees();
  }

  onSearch(): void
  {
    this.searchSubject.next(this.search);
  }

  clearSearch(): void
  {
    this.search='';
    this.searchBy='name';
    this.currentPage=0;
    this.loadEmployees();
  }

}
