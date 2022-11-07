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

  // use the user service to login and subscribe to current user, then reload page
  async login(username: string, password: string): Promise<void> {
    const response = await this.userService.login(username, password).toPromise();
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    console.log(this.currentUser);
    window.location.replace("/");
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
      const response = await this.userService.login(username, password).toPromise();
      this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
      console.log(this.currentUser);
      window.location.replace("/");
    }


  // using service to add users
  async add(username: string, password: string): Promise<void> {
    username = username.trim();
    if (!username) { return; }
    if (!password) { return; }
    this.userService.addUser({ username, password } as User)
      .subscribe(user => {
        this.users.push(user);
      });
    const response = await this.userService.login(username, password).toPromise();
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    console.log(this.currentUser);
    window.location.replace('/')
  }

  // Form Validables
  registerForm: any = FormGroup;
  submitted = false;
  //Add user form actions
  get f() { return this.registerForm.controls; }

}
