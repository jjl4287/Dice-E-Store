import { Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, Validators, FormBuilder } from '@angular/forms';
import { UserService } from '../user.service';
import { User } from '../user'
import { Route, Router } from '@angular/router';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  users: any;
  router: Router;

    ngOnInit() {
      //Add User form validations
      this.registerForm = this.formBuilder.group({
        email: ['', [Validators.required, Validators.email]],
        password: ['', [Validators.required]]
      });
    }
  
    // using the service from the backend to properly grab products
    getUsers(): void {
      this.userService.getUsers()
        .subscribe(users => this.users = users);
    }
  
    login(username: string, password: string): void {
      this.userService.login(username, password).subscribe()
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
        this.userService.login(username, password)
    }
  
    // using service to delete products
    delete(user: User): void {
      this.users = this.users.filter((h: User) => h !== user);
      this.userService.deleteUser(user.id).subscribe();
    }

    getCurrentUser(){
      this.users = this.userService.getCurrentUser();
    }

  //Form Validables
  registerForm: any = FormGroup;
  submitted = false;
  constructor(private formBuilder: FormBuilder, private userService: UserService, router: Router) {
    //this.users = userService.getUsers();
    this.router = router;
  }
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
      this.getCurrentUser();
      this.router.navigateByUrl("http://localhost:4200/")
    }

  }

}
