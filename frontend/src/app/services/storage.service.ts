import { Injectable } from '@angular/core';

const USER_KEY = 'auth-user';

@Injectable({
  providedIn: 'root',
})
export class StorageService {
  private storage: Storage | { [key: string]: string } = {};

  constructor() {
    try {
      if (typeof window !== 'undefined' && window.localStorage) {
        this.storage = window.localStorage;
      }
    } catch (error) {
      console.error('Falling back to in-memory storage', error);
    }
  }

  setItem(key: string, value: string): void {
    this.storage[key] = value;
  }

  getItem(key: string): string | null {
    return this.storage[key] || null;
  }

  removeItem(key: string): void {
    delete this.storage[key];
  }

  clear(): void {
    if (this.storage instanceof Storage) {
      localStorage.clear();
      sessionStorage.clear();
      window.localStorage.clear();

      this.storage.clear();
    } else {
      this.storage = {};
    }
  }

  clean(): void {
    this.clear(); // Use the clear method that already checks for storage type.
  }

  public saveUser(user: any): void {
    // console.log('saveUser', user);

    this.removeItem(USER_KEY); // Use the removeItem method to ensure consistent behavior.
    this.setItem(USER_KEY, JSON.stringify(user)); // Use the setItem method for abstraction.
  }

  public getUser(): any {
    const userJson = this.getItem(USER_KEY); // Use the getItem method for abstraction.
    return userJson ? JSON.parse(userJson) : {};
  }

  public isLoggedIn(): boolean {
    const user = this.getUser();
    return !!user && Object.keys(user).length > 0;
  }
}
