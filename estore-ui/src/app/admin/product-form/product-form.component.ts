import { Component, OnInit } from '@angular/core';
import { ProductService } from 'src/app/product.service';
import { Product } from 'src/app/product';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-product-form',
  templateUrl: './product-form.component.html',
  styleUrls: ['./product-form.component.css']
})
export class ProductFormComponent implements OnInit {
  title: any;
  products: any;
  id: number;
  currentProduct: any;

  constructor(private route: ActivatedRoute, private router: Router, private productService: ProductService) {
    this.products = productService.getProducts();
    if(this.route.snapshot.paramMap.get('id') != null) {
      this.id = Number(this.route.snapshot.paramMap.get('id'));
      productService.getProduct(this.id).subscribe(currentProduct => this.currentProduct = currentProduct);
    } else {
      this.id = 0;
      this.currentProduct = {
        id: 0,
        name: null,
        qty: null,
        price: null,
        description: null,
        url: null
      };
    }
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
  async Save(product: Product): Promise<void> {
    if (this.id === 0) {
      if (!product) { return; }
      this.productService.addProduct(product).subscribe(product => {
          this.products.push(product);
      });
    } else {
      if (!product) { return; }
      product.id = this.id;
      const response = await this.productService.updateProduct(product).toPromise();
      this.getProducts();
    }
    this.router.navigate(['/admin/products'])
    
  }

  // using service to delete products
  delete(product: Product): void {
    this.products = this.products.filter((h: Product) => h !== product);
    this.productService.deleteProduct(product.id).subscribe();
  }
}
