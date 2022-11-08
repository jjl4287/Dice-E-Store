import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { ReactiveFormsModule } from '@angular/forms';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
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
import { AboutComponent } from './about/about.component';
import { ContactUsComponent } from './contact-us/contact-us.component';
import { AuthGuard } from './auth-guard.service';
import { AdminAuthGuard } from './admin-auth-guard.service';

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
    ProductFormComponent,
    AboutComponent,
    ContactUsComponent

  ],
  imports: [
    FormsModule,
    BrowserModule,
    ReactiveFormsModule,
    HttpClientModule,
    RouterModule.forRoot([
      { path: '', component: HomeComponent},
      { path: 'contact-us', component: ContactUsComponent},
      { path: 'about', component: AboutComponent},
      { path: 'products', component: ProductsComponent},
      { path: 'login', component: LoginComponent},
      
      { path: 'shopping-cart', component: ShoppingCartComponent,canActivate: [AuthGuard]},
      { path: 'check-out', component: CheckOutComponent, canActivate: [AuthGuard]},
      { path: 'order-success', component: OrderSuccessComponent, canActivate: [AuthGuard]},
      { path: 'my/orders', component: MyOrdersComponent, canActivate: [AuthGuard]},

      { path: 'admin/products', component: AdminProductsComponent, canActivate: [AdminAuthGuard]},
      { path: 'admin/products/new', component: ProductFormComponent, canActivate: [AdminAuthGuard]},
      { path: 'admin/products/new/:id', component: ProductFormComponent, canActivate: [AdminAuthGuard]},
      { path: 'admin/orders', component: AdminOrdersComponent, canActivate: [AdminAuthGuard]}
    ], { onSameUrlNavigation: 'reload' }),
    NgbModule
  ],
  providers: [
    MessageService,
    ProductService,
    UserService,
    AuthGuard,
    AdminAuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
