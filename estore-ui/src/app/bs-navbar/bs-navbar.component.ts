import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/product';
import { ProductService } from 'src/app/product.service';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { UserService } from '../user.service';
import { User } from '../user';

@Component({
  selector: 'bs-navbar',
  templateUrl: './bs-navbar.component.html',
  styleUrls: ['./bs-navbar.component.css']
})
export class BsNavbarComponent implements OnInit {
  products: any;
  currentUser: User | undefined;
  products$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();

  constructor(private productService: ProductService, public userService: UserService) {
    this.products = productService.getProducts();
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    console.log(this.currentUser?.username)
  }



  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.productService.getProducts()
      .subscribe(products => this.products = products);
    this.products$ = this.searchTerms.pipe(
      // wait 300ms after each keystroke before considering the term
      debounceTime(200),

      // ignore new term if same as previous term
      distinctUntilChanged(),

      // switch to new search observable each time the term changes
      switchMap((term: string) => this.productService.searchProducts(term)),
      );
  }
  
  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }
}
