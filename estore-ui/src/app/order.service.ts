import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

import { Observable, of } from 'rxjs';
import { catchError, map, tap } from 'rxjs/operators';

import { Order } from './order';
import { MessageService } from './message.service';


@Injectable({ providedIn: 'root' })
export class OrderService {

  private ordersUrl = 'http://localhost:8080/orders'

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(
    private http: HttpClient,
    private messageService: MessageService) { }

  /** GET orders from the server */
  getOrders(): Observable<Order[]> {
    return this.http.get<Order[]>(this.ordersUrl)
      .pipe(
        tap(_ => this.log('fetched orders')),
        catchError(this.handleError<Order[]>('getOrders', []))
      );
  }

  /** GET order by id. Return `undefined` when id not found */
  getOrderNo404<Data>(id: number): Observable<Order> {
    const url = `${this.ordersUrl}/?id=${id}`;
    return this.http.get<Order[]>(url)
      .pipe(
        map(orders => orders[0]), // returns a {0|1} element array
        tap(h => {
          const outcome = h ? 'fetched' : 'did not find';
          this.log(`${outcome} order id=${id}`);
        }),
        catchError(this.handleError<Order>(`getOrder id=${id}`))
      );
  }

  /** GET order by id. Will 404 if id not found */
  getOrder(id: number): Observable<Order> {
    const url = `${this.ordersUrl}/${id}`;
    return this.http.get<Order>(url).pipe(
      tap(_ => this.log(`fetched order id=${id}`)),
      catchError(this.handleError<Order>(`getOrder id=${id}`))
    );
  }

  /* GET orders whose name contains search term */
  searchOrders(term: string): Observable<Order[]> {
    if (!term.trim()) {
      // if not search term, return empty order array.
      return of([]);
    }
    return this.http.get<Order[]>(`${this.ordersUrl}/?name=${term}`).pipe(
      tap(x => x.length ?
         this.log(`found orders matching "${term}"`) :
         this.log(`no orders matching "${term}"`)),
      catchError(this.handleError<Order[]>('searchOrders', []))
    );
  }

  //////// Save methods //////////

  /** POST: add a new order to the server */
  addOrder(order: Order): Observable<Order> {
    return this.http.post<Order>(this.ordersUrl, order, this.httpOptions).pipe(
      tap((newOrder: Order) => this.log(`added order w/ id=${newOrder.id}`)),
      catchError(this.handleError<Order>('addOrder'))
    );
  }

  /** DELETE: delete the order from the server */
  deleteOrder(id: number): Observable<Order> {
    const url = `${this.ordersUrl}/${id}`;

    return this.http.delete<Order>(url, this.httpOptions).pipe(
      tap(_ => this.log(`deleted order id=${id}`)),
      catchError(this.handleError<Order>('deleteOrder'))
    );
  }

  /** PUT: update the order on the server */
  updateOrder(order: Order): Observable<any> {
    return this.http.put(this.ordersUrl, order, this.httpOptions).pipe(
      tap(_ => this.log(`updated order id=${order.id}`)),
      catchError(this.handleError<any>('updateOrder'))
    );
  }

  /**
   * Handle Http operation that failed.
   * Let the app continue.
   *
   * @param operation - name of the operation that failed
   * @param result - optional value to return as the observable result
   */
  private handleError<T>(operation = 'operation', result?: T) {
    return (error: any): Observable<T> => {

      // TODO: send the error to remote logging infrastructure
      console.error(error); // log to console instead

      // TODO: better job of transforming error for user consumption
      this.log(`${operation} failed: ${error.message}`);

      // Let the app keep running by returning an empty result.
      return of(result as T);
    };
  }

  /** Log a OrderService message with the MessageService */
  private log(message: string) {
    this.messageService.add(`OrderService: ${message}`);
  }
}