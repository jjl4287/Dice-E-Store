import { Component, OnInit } from '@angular/core';

import { Observable, Subject } from 'rxjs';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { ShoppingCartComponent } from '../shopping-cart/shopping-cart.component';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
  products: any;
  currentUser: User | undefined;
  products$!: Observable<Product[]>;
  private searchTerms = new Subject<string>();

  constructor(private productService: ProductService, private userService: UserService) { 
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.products = productService.getProducts();
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

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.getProducts();
  }

  // using the service from the backend to properly grab products
  getProducts(): void {
    this.productService.getProducts()
    .subscribe(products => this.products = products);
  }

  // using service to add products
  add(name: string): void {
    name = name.trim();
    if (!name)  { return; }
    this.productService.addProduct({ name } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  addToCart(product: Product):void{
    this.userService.addToCart({id:product.id,name:product.name,price:product.price,qty:1,url:product.url,description:product.description}).subscribe();
  }

  // using service to delete products
  delete(product: Product): void {
    this.products = this.products.filter((h: Product) => h !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }



  // Push a search term into the observable stream.
  search(term: string): void {
    this.searchTerms.next(term);
  }
}