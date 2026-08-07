import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import {AuthService} from '../../../core/services/auth.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-header',
  standalone: true,
  imports: [MatToolbarModule],
  templateUrl: './header.html',
  styleUrl: './header.css',
})
export class Header {
  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  logout(): void{
    this.authService.logout();
    this.router.navigate(['/']);
  }
}
