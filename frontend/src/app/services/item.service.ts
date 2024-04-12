import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

const ITEM_API = 'http://localhost:8080/api/items';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  constructor(private http: HttpClient) {}

  getAllItems(): Observable<any> {
    return this.http.get(ITEM_API + '', { responseType: 'json' });
  }
}
