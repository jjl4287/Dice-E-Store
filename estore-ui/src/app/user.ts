import { provideCloudflareLoader } from "@angular/common";

export interface User {
  id: number;
  username: string;
  password: string;
  email: string;
  admin: boolean;
}