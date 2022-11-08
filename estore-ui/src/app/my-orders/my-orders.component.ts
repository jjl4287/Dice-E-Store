import { Component, OnInit } from '@angular/core';
import { Order } from '../order';
import { OrderService } from '../order.service';
import { Product } from '../product';
import { User } from '../user';
import { UserService } from '../user.service';

@Component({
  selector: 'app-my-orders',
  templateUrl: './my-orders.component.html',
  styleUrls: ['./my-orders.component.css'],
  
})
export class MyOrdersComponent implements OnInit {
  currentUser: User | undefined;
  orders: any;
  
  constructor(private userService: UserService, private orderService: OrderService) { 
    this.userService.getCurrentUser().subscribe(user => {
      this.currentUser = user
      this.orderService.searchOrders(this.currentUser).subscribe(orders => {this.orders=orders});
    });
    
  }

  ngOnInit(): void {
    this.userService.getCurrentUser().subscribe(user => {
      this.currentUser = user
      this.orderService.searchOrders(this.currentUser).subscribe(orders => this.orders=orders);
    });
    
  }
  toggleRow(element:Element):void{
    console.log("hi");
    element.getElementsByClassName('expanded-row-content')[0].classList.toggle('hide-row');
    }

}
