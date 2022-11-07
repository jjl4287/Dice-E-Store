import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../user'
import { Route, Router } from '@angular/router';
import { Observable } from 'rxjs';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  users: any;
  currentUser!: User;
  router: Router;
  constructor(private formBuilder: FormBuilder, private userService: UserService, router: Router) {
    this.users = userService.getUsers();
    this.router = router;
  }

    ngOnInit() {
      this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
      console.log(this.currentUser);
    }
  
    // using the service from the backend to properly grab products
    getUsers(): void {
      this.userService.getUsers()
        .subscribe(users => this.users = users);
    }
  
    async login(username: string, password: string): Promise<void> {
      this.userService.login(username, password).subscribe();
      this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
      console.log(this.currentUser);
      setTimeout(() => {
        window.location.replace('/')
      }, 100)
    }

    // using service to add users
    add(username: string, password: string): void {
      username = username.trim();
      if (!username) { return; }
      if (!password) { return; }
      this.userService.addUser({ username, password } as User)
        .subscribe(user => {
          this.users.push(user);
        });
      this.userService.login(username, password).subscribe();
      this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
      console.log(this.currentUser);
      setTimeout(() => {
        window.location.replace('/')
      }, 100)
    }

  //Form Validables
  registerForm: any = FormGroup;
  submitted = false;
  //Add user form actions
  get f() { return this.registerForm.controls; }
  onSubmit() {

    this.submitted = true;
    // stop here if form is invalid
    if (this.registerForm.invalid) {
      return;
    }
    //True if all the fields are filled
    if (this.submitted) {
      this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
      console.log(this.currentUser);
      // this.router.navigate(['/'])
    }

  }

}
