import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const API_AUTH = 'http://localhost:8080/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
};

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(private http: HttpClient) {}

  login(username: string, password: string): Observable<any> {
    return this.http.post(
      API_AUTH + 'login',
      {
        username,
        password,
      },
      httpOptions
    );
  }

  register(username: string, email: string, password: string): Observable<any> {
    return this.http.post(
      API_AUTH + 'register',
      {
        username,
        email,
        password,
      },
      httpOptions
    );
  }

  refreshToken(refreshToken: string): Observable<any> {
    if (!refreshToken) {
      throw new Error('Refresh token is required');
    }
    const httpOptions = {
      withCredentials: true,
    };
    return this.http.post(
      API_AUTH + 'refreshtoken',
      {
        refreshToken,
      },
      httpOptions
    );
  }

  logout(): Observable<any> {
    return this.http.post(API_AUTH + 'logout', {}, httpOptions);
  }
}
