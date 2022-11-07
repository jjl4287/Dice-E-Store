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
  products: Map<number,Product>;
  
  constructor(private productService: ProductService, private userService: UserService, private orderService: OrderService) { 
    this.userService.getCurrentUser().subscribe(user => this.currentUser = user);
    this.userService.getShoppingCart().subscribe(shoppingCart => this.shoppingCart = shoppingCart);
    this.products= new Map<number,Product>();
    this.shoppingCart.forEach(product => {
      this.productService.getProduct(product.id).subscribe(p => this.products.set(p.id,p));
    }); 
    
  }

  ngOnInit(): void {
    
  }

  submitOrder():boolean{
    
    this.shoppingCart.forEach(product => {
      let p = this.products.get(product.id);
        if(p==undefined || product.qty>p.qty){
          return false;
        }
    });
    let s = new Set<Product>();
    this.shoppingCart.forEach(product => {
      let p = this.products.get(product.id);
      if(p!=undefined){
        this.productService.updateProduct(new product(p.id,p.name,p.price,p.qty-product.qty))
        s.add(product);
      }
    });
    if(this.currentUser!=undefined){
      this.orderService.addOrder(s,this.currentUser);
      return true;
    }
    return false;

  }

}
