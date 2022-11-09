import { Component, OnInit } from '@angular/core';
import { OrderService } from 'src/app/order.service';
import { Order } from 'src/app/order'


@Component({
  selector: 'app-admin-orders',
  templateUrl: './admin-orders.component.html',
  styleUrls: ['./admin-orders.component.css']
})
export class AdminOrdersComponent implements OnInit {
  orders: any;

  constructor( private orderService: OrderService) {
    this.orderService.getOrders().subscribe(orders => { 
      console.log(orders);
      this.orders = orders 
    });
  }

  ngOnInit(): void {
    this.orderService.getOrders().subscribe(orders => this.orders = orders);

  }
  toggleRow(element: Element): void {
    element.getElementsByClassName('expanded-row-content')[0].classList.toggle('hide-row');
  }

  async fulfillOrder(order: Order): Promise<void> {
    order.fulfilled = true;
    await this.orderService.fulfillOrder(order).toPromise();
    window.location.reload();
  }

}
