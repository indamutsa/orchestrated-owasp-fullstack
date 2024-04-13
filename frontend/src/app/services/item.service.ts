import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, BehaviorSubject, tap } from 'rxjs';

const ITEM_API = 'http://localhost:8080/api/items';

@Injectable({
  providedIn: 'root',
})
export class ItemService {
  private itemSubject = new BehaviorSubject<any[]>([]);
  public items$ = this.itemSubject.asObservable();

  constructor(private http: HttpClient) {}

  fetchAllItemsByUser(userId: string): void {
    this.http
      .get<any[]>(ITEM_API + `/user?userId=${userId}`, {
        responseType: 'json',
      })
      .subscribe({
        next: (data) => {
          this.itemSubject.next(data);
        },
        error: (err) => {
          console.log(err);
        },
      });
  }

  addItem(item: any, userId: string): Observable<any> {
    return this.http
      .post<any>(ITEM_API + `/${userId}`, item, {
        responseType: 'json',
      })
      .pipe(
        tap((addedItem) => {
          const currentItems = this.itemSubject.value;
          this.itemSubject.next([...currentItems, addedItem]);
        })
      );
  }

  updateItem(userId: string, item: any): Observable<any> {
    // console.log('Updating item:', item);

    return this.http
      .put(ITEM_API + `/${userId}`, item, {
        responseType: 'json',
      })
      .pipe(
        tap((updatedItem) => {
          const currentItems = this.itemSubject.value;
          const index = currentItems.findIndex((i) => i.id === item.id);

          if (index !== -1) {
            currentItems[index] = updatedItem;
            this.itemSubject.next([...currentItems]); // Update items in the current list
          }
        })
      );
  }

  deleteItem(itemId: string, userId: string): Observable<any> {
    return this.http.delete(ITEM_API + `/${itemId}?userId=${userId}`).pipe(
      tap(() => {
        const currentItems = this.itemSubject.value;
        const updatedItems = currentItems.filter((item) => item.id !== itemId);
        this.itemSubject.next(updatedItems);
      })
    );
  }

  getItemById(itemId: string): any {
    const currentItems = this.itemSubject.value;
    return currentItems.find((item) => item.id === itemId);
  }
}
