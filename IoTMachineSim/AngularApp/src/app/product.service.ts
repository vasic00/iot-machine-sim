import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ProductService {

  productServiceBackendURL: string = "http://localhost:8082/api/products/plates/";

  constructor(private httpClient: HttpClient) { }

  getAllPlates(): Observable<any> {
    return this.httpClient.get(this.productServiceBackendURL);
  }

  getPlateById(plateId: number): Observable<any> {
    return this.httpClient.get(this.productServiceBackendURL + plateId);
  }

}
