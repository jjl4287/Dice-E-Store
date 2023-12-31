import { Component, OnInit } from '@angular/core';
import { map } from 'rxjs';
import { Order } from '../order';
import { OrderService } from '../order.service';
import { Product } from '../product';
import { ProductService } from '../product.service';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-check-out',
  templateUrl: './check-out.component.html',
  styleUrls: ['./check-out.component.css']
})
export class CheckOutComponent implements OnInit {
  currentUser: User | undefined;
  shoppingCart: any;
  products: Map<number, Product>;
  totalprice: number;

  constructor(private productService: ProductService, private userService: UserService, private orderService: OrderService) {
    this.totalprice = 0;
    this.products = new Map<number, Product>();
    this.userService.getCurrentUser().subscribe(user => {
      this.currentUser = user
    });
    this.userService.getShoppingCart().subscribe(shoppingCart => {
      this.shoppingCart = shoppingCart
      this.shoppingCart.forEach((product: Product) => {
        this.totalprice += product.qty * product.price;
        this.productService.getProduct(product.id).subscribe(p => this.products.set(p.id, p));
      });
    });


  }

  ngOnInit(): void {

  }

  async submitOrder(): Promise<boolean> {
    let allowed = true;
    this.shoppingCart.forEach((product: Product) => {
      let p = this.products.get(product.id);
      if (p == undefined || product.qty > p.qty) {
        allowed = false;
        window.alert(p?.name + "Out of stock")
        return false;
      }
      return true;
    });

    if (!allowed) {
      return false;
    }

    let s = new Array<Product>();
    this.shoppingCart.forEach(async (product: Product) => {
      let p = this.products.get(product.id);
      if (p != undefined) {
        this.productService.updateProduct({ id: p.id, name: p.name, price: p.price, qty: (p.qty - product.qty), url: p.url, description: p.description }).subscribe();
        s.push(product);
        const respones = await this.userService.removeFromCart(p).toPromise();
      }
    });

    if (this.currentUser != undefined) {
      await this.orderService.addOrder(s, this.currentUser).toPromise();
      window.location.replace("/order-success");
      return true;
    }
    return false;

  }

}
