import { Product } from "./product";
import { User } from "./user";

export interface Order {
    id: String;
    user: User;
    products: Set<Product>;
  }