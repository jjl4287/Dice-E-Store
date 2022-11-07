import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/product.service';
import { Product } from 'src/app/product';
import { Router } from '@angular/router';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {
  title: any;
  products: any;

  constructor(private router: Router, private productService: ProductService) {
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
  Save(product: Product): void {
    console.log(product);
    if (!product) { return; }
    this.productService.addProduct(product)
      .subscribe(product => {
        this.products.push(product);
      });
    setTimeout(() => {
      window.location.replace('/admin/products')
    }, 100)
  }

  // using service to delete products
  delete(product: Product): void {
    this.products = this.products.filter((h: Product) => h !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }
}
