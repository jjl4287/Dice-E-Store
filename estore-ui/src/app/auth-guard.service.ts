import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { User } from './user';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  user: any;

  constructor(private userService: UserService, private router: Router) {
    this.userService.getCurrentUser().subscribe(user => this.user = user);
  }

  canActivate(): boolean  {
    console.log(this.user.username)
    if (this.user.username === "Guest") { 
      return true; 
    }
    else {
      //window.location.replace("/");
      this.router.navigate(['/login']);
      return false;
    }
  }
}
