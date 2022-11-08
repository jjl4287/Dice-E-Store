import { provideCloudflareLoader } from "@angular/common";

export interface User {
    id: number;
    username: string;
    password: string;
    admin: boolean;
  }