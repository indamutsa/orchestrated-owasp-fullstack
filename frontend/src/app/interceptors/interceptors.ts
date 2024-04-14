import {
  HTTP_INTERCEPTORS,
  HttpErrorResponse,
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, catchError, switchMap, throwError } from 'rxjs';
import { AuthService } from '../services/auth.service';
import { StorageService } from '../services/storage.service';
import { EventBusService } from '../shared/EventBusService';
import { EventData } from '../shared/EventData';
import { Router } from '@angular/router';

@Injectable()
export class HttpRequestInterceptor implements HttpInterceptor {
  private isRefreshing = false;
  private refreshToken = '';

  constructor(
    private storageService: StorageService,
    private authService: AuthService,
    private eventBusService: EventBusService,
    private router: Router
  ) {}

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    req = req.clone({
      withCredentials: true,
    });

    return next.handle(req).pipe(
      catchError((error) => {
        if (req.url.includes('logout')) {
          this.storageService.clean();
          this.router.navigate(['/login']);
          this.eventBusService.emit(new EventData('logout', null));
        }
        if (
          error instanceof HttpErrorResponse &&
          !req.url.includes('/api/auth/login') &&
          error.status === 401
        ) {
          return this.handle401Error(req, next);
        }

        return throwError(() => error);
      })
    );
  }

  private handle401Error(request: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true;
      this.refreshToken = this.storageService.getUser().refreshToken;

      // console.log('refreshToken -- before', this.refreshToken);

      if (this.storageService.isLoggedIn()) {
        return this.authService.refreshToken(this.refreshToken).pipe(
          switchMap(() => {
            // console.log('Token refreshed');
            this.isRefreshing = false;

            return next.handle(request.clone({ withCredentials: true }));
          }),
          catchError((error) => {
            this.isRefreshing = false;
            console.log('Token refresh failed', error);
            this.storageService.clean();
            this.router.navigate(['/login']);

            // if (error.status == '403') {
            this.eventBusService.emit(new EventData('logout', null));
            // }

            return throwError(() => error);
          })
        );
      }
    }

    return next.handle(request.clone({ withCredentials: true }));
  }
}

export const httpInterceptorProviders = [
  { provide: HTTP_INTERCEPTORS, useClass: HttpRequestInterceptor, multi: true },
];
