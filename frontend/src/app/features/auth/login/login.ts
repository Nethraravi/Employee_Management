import { Component } from '@angular/core';
import { AuthService } from '../../../core/services/auth.service';
import { FormsModule } from '@angular/forms';
import { LoginRequest } from '../../../core/models/login-request';
import { AuthenticationResponse } from '../../../core/models/authentication-response';
import { Router} from '@angular/router';
import { OnInit} from '@angular/core';

@Component({
  selector: 'app-login',
  imports: [FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})

export class Login implements OnInit{

  username = '';
  password = '';

  constructor(private authService: AuthService, private router: Router) {
  }

  login(): void
  {
    const request: LoginRequest = {
      username: this.username,
      password: this.password
    };

    this.authService.login(request).subscribe({
      next: (response: AuthenticationResponse) => {
        console.log("Login success");
        this.authService.saveToken(response.token);
        console.log("Navigating...");
        this.router.navigate(['/dashboard']).then(result =>{
          console.log('Navigation result:',result);
        });
      }
    });
  }

  ngOnInit() {
    if(this.authService.getToken()) {
      this.router.navigate(['/dashboard']);
    }
  }
}
