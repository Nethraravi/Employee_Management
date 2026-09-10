import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../../core/models/login-request';
import { AuthenticationResponse } from '../../../core/models/authentication-response';
import { Router} from '@angular/router';
import { OnInit} from '@angular/core';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-login',
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})

export class Login implements OnInit{

  username = '';
  password = '';
  errorMessage= '';

  constructor(private authService: AuthService, private router: Router) {
  }

  login(): void
  {
    this.errorMessage='';

    const request: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.authService.login(request).subscribe({
      next: (response: AuthenticationResponse) => {
        console.log("Login success");
        this.authService.saveToken(response.token);
        console.log("Navigating...");
        if(response.mustChangePassword)
        {
          this.router.navigate(['/change-password']);
        }
        else
        {
          this.router.navigate(['/dashboard']).then(result =>{
            console.log('Navigation result:',result);
          });
        }
      },
      error: (error) => {
        console.log("Login failed:", error);
        this.errorMessage = "Invalid username or password";
      }
    });
  }

  ngOnInit() {
    if(this.authService.getToken()) {
      this.router.navigate(['/dashboard']);
    }
  }
}
