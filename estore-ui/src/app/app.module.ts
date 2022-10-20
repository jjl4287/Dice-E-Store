import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { DataTableModule } from 'angular-4-data-table';
import { BsNavbarComponent } from './bs-navbar/bs-navbar.component';
import { HomeComponent } from './home/home.component';
import { ProductsComponent } from './products/products.component';
import { ShoppingCartComponent } from './shopping-cart/shopping-cart.component';
import { CheckOutComponent } from './check-out/check-out.component';
import { OrderSuccessComponent } from './order-success/order-success.component';
import { MyOrdersComponent } from './my-orders/my-orders.component';
import { AdminProductsComponent } from './admin/admin-products/admin-products.component';
import { AdminOrdersComponent } from './admin/admin-orders/admin-orders.component';
import { LoginComponent } from './login/login.component';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { AdminComponent } from './admin/admin.component';
import { MessageService } from './message.service';
import { ProductService } from './product.service';
import { UserService } from './user.service';
import { HttpClient, HttpClientModule } from '@angular/common/http';
import { ProductFormComponent } from './admin/product-form/product-form.component';

@NgModule({
  declarations: [
    AppComponent,
    BsNavbarComponent,
    HomeComponent,
    ProductsComponent,
    ShoppingCartComponent,
    CheckOutComponent,
    OrderSuccessComponent,
    MyOrdersComponent,
    AdminProductsComponent,
    AdminOrdersComponent,
    LoginComponent,
    AdminComponent,
    ProductFormComponent

  ],
  imports: [
    FormsModule,
    DataTableModule,
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent},
      { path: 'products', component: ProductsComponent},
      { path: 'shopping-cart', component: ShoppingCartComponent},
      { path: 'check-out', component: CheckOutComponent},
      { path: 'order-success', component: OrderSuccessComponent},
      { path: 'my/orders', component: MyOrdersComponent},
      { path: 'login', component: LoginComponent},
      { path: 'admin/products', component: AdminProductsComponent},
      { path: 'admin/products/new', component: ProductFormComponent},
      { path: 'admin/orders', component: AdminOrdersComponent}
    ]),
    NgbModule
  ],
  providers: [
    MessageService,
    ProductService,
    UserService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
