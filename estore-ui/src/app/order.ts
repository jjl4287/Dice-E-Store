import { Product } from "./product";
import { User } from "./user";

export interface Order {
    UUID: String;
    user: User;
    products: Array<Product>;
  }