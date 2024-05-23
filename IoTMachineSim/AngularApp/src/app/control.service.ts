import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ControlService {


  controlServiceBackendURL: string = "http://localhost:8081/api/control/machines/";

  constructor(private httpClient: HttpClient) { }

  getAllStatus(): Observable<any> {
    return this.httpClient.get(this.controlServiceBackendURL + "status");
  }

  changeStatus(machineId: number, status: string): Observable<any> {
    return this.httpClient.put(this.controlServiceBackendURL + "status/" + machineId + "/" + status, {});
  }
}
