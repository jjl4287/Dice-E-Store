import { Component, OnInit } from '@angular/core';

import { Product } from '../product';
import { ProductService } from '../product.service';

@Component({
  selector: 'app-products',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.css']
})
export class ProductsComponent implements OnInit {
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

  // using service to add products
  add(name: string): void {
    name = name.trim();
    if (!name) { return; }
    this.productService.addProduct({ name } as Product)
      .subscribe(product => {
        this.products.push(product);
      });
  }

  // using service to delete products
  delete(product: Product): void {
    this.products = this.products.filter((h: Product) => h !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }

}