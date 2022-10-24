import { provideCloudflareLoader } from "@angular/common";

export interface Product {
    id: number;
    name: string;
    price: GLfloat;
    qty: number;
  }