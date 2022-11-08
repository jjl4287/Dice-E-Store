import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { Observable } from 'rxjs';
import { User } from './user';
import { UserService } from './user.service';

@Injectable({
  providedIn: 'root'
})
export class AdminAuthGuard implements CanActivate {
  user: User | undefined;
  bool: boolean;

  constructor(private userService: UserService, private router: Router) {
    this.bool = false;
  }

  async canActivate(): Promise<boolean> {
    this.user = await this.userService.getCurrentUser().toPromise();
    if (this.user?.admin == true) {
      this.bool = true;
    }
    else {
      this.router.navigate(['/']);
      this.bool = false;
    }
    return this.bool;
  }
}
