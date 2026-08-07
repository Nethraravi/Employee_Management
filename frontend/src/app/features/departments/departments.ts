import {Component, OnInit} from '@angular/core';
import {DepartmentService} from '../../core/services/department.service';
import { Department} from '../../core/models/department';
import { CommonModule} from '@angular/common';
import { Router} from '@angular/router';
import {AuthService} from '../../core/services/auth.service';
import {FormsModule} from '@angular/forms';
import {Subject} from 'rxjs';
import {debounceTime, distinctUntilChanged} from 'rxjs/operators';

@Component({
  standalone: true,
  selector: 'app-departments',
  imports: [CommonModule, FormsModule],
  templateUrl: './departments.html',
  styleUrl: './departments.css',
})
export class Departments implements OnInit{
  constructor(private departmentService: DepartmentService, private router: Router, public authService: AuthService) {
  }

  departments: Department[] = [];
  currentPage=0;
  pageSize =5;
  totalPages=0;
  totalDepartments=0;
  sortField = 'id';
  sortDirection = 'asc';
  search = '';
  isLoading = false;

  private searchSubject = new Subject<string>();

  ngOnInit() {
    this.loadDepartments();

    this.searchSubject.pipe(debounceTime(500),distinctUntilChanged()).subscribe(() => {
      this.currentPage=0;
      this.loadDepartments();
    });
  }

  loadDepartments(): void
  {
    this.isLoading=true;
    this.departmentService.getAllDepartments(this.currentPage, this.pageSize, this.sortField, this.sortDirection, this.search).subscribe({
      next: response => {
        this.departments=response.data.content;
        this.totalPages=response.data.totalPages;
        this.totalDepartments=response.data.totalElements;
      },
      error: error => {
        console.error(error);
      }
    });
  }

  addDepartment(): void
  {
    this.router.navigate(['/add-department']);
  }

  editDepartment(id:number): void
  {
    console.log("Edit Department",id);
    this.router.navigate(['/edit-department',id]);
  }

  deleteDepartment(id: number): void
  {
    console.log("Delete Department:",id);
    if (!confirm("Are you sure, You want to Delete this department?"))
    {
      return;
    }

    this.departmentService.deleteDepartment(id).subscribe({
      next: (response) => {
        alert(response.message);
        this.loadDepartments();
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
      this.loadDepartments();
    }
  }

  previousPage(): void
  {
    if(this.currentPage>0)
    {
      this.currentPage--;
      this.loadDepartments();
    }
  }

  getPages(): number[]
  {
    return Array.from({ length: this.totalPages},(_, i) => i);
  }

  goToPage(page: number): void
  {
    if(page >= 0 && page < this.totalPages)
    {
      this.currentPage=page;
      this.loadDepartments();
    }
  }

  onSortChange(): void
  {
    this.currentPage=0;
    this.loadDepartments();
  }

  onSearch(): void
  {
    this.searchSubject.next(this.search);
  }

  clearSearch(): void
  {
    this.search='';
    this.currentPage=0;
    this.loadDepartments();
  }
}
