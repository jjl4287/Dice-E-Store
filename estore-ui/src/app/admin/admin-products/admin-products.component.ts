import { Component, OnInit } from '@angular/core';
import { Product } from 'src/app/product';
import { ProductService } from 'src/app/product.service';

@Component({
  selector: 'app-admin-products',
  templateUrl: './admin-products.component.html',
  styleUrls: ['./admin-products.component.css']
})
export class AdminProductsComponent implements OnInit {
  products: any;

  constructor(private productService: ProductService) {
    this.products = productService.getProducts();
  }

  ngOnInit(): void {
    this.getProducts();
  }

  // using the service from the backend to properly grab products
  getProducts(): void {
    this.productService.getProducts()
      .subscribe(products => this.products = products);
  }

  // using service to delete products
  delete(product: Product): void {
    this.products = this.products.filter((h: Product) => h !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }
}
