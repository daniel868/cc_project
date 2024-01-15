import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor() { }

  public getToken(): string | null {
    return localStorage.getItem('jwt');
  }

  public isAuthenticated(): boolean {
    const token = this.getToken();
    return token != null;
  }
}
