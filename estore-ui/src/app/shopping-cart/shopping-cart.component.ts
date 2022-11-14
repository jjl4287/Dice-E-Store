import { Component, OnInit } from '@angular/core';
import { NotFoundError } from 'rxjs';
import { Order } from '../order';
import { OrderService } from '../order.service';
import { Product } from '../product';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  currentUser: User | undefined;
  cart: any;
  products: Map<number, Product>;

  constructor(private userService: UserService, private orderService: OrderService) {
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.userService.getShoppingCart().subscribe(shoppingCart => this.cart = shoppingCart);
    this.products = new Map<number, Product>();

  }
  refresh(): void {
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.userService.getShoppingCart().subscribe(shoppingCart => this.cart = shoppingCart);
  }

  addProduct(product: Product): void {
    this.userService.addToCart(product).subscribe();
    this.refresh();
  }
  removeProduct(product: Product): void {
    this.userService.removeFromCart(product).subscribe();
    this.refresh();
  }

  reduceProduct(product: Product): void {
    this.userService.reduceFromCart(product).subscribe();
    this.refresh();
  }



}
