import { Component, OnInit } from '@angular/core';
import { NotFoundError } from 'rxjs';
import { Product } from '../product';

@Component({
  selector: 'app-shopping-cart',
  templateUrl: './shopping-cart.component.html',
  styleUrls: ['./shopping-cart.component.css']
})
export class ShoppingCartComponent {
  cart: any;

  addProduct(product: Product):boolean{
    if(this.cart==undefined){
      console.log("hi");
      this.cart=new Array<Product>();
    }
    console.log(this.cart);

    let found = false;
    for (let p of this.cart){
      if(p.id == product.id){
        p.qty+=1;
        found = true;
        length+=1;
      }
    };
    if(!found){
      let copy = {id:product.id, name:product.name, price:product.price, qty:product.qty}
      copy.qty=1;
      this.cart.push(copy);
      console.log(this.cart);
      length+=1;
      return true;
    }
    return found;
  }
  removeProduct(product: Product):boolean{
    let found = false;
    for (let i =0; i<this.cart.length;i++){
      if(this.cart[i].id == product.id){
        this.cart.splice(i, 1); 
      }
    };
    return found;
  }

  reduceProduct(product: Product):boolean{
    let found = false;
    for (let i =0; i<this.cart.length;i++){
      if(this.cart[i].id == product.id){
        this.cart[i].qty-=1; 
        if(this.cart[i].qty==0){
          this.cart.splice(i, 1); 
        }
      }
    };
    return found;
  }

  checkOut():void{
    return;
  }



}
