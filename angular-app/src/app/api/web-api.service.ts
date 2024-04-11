import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class WebApiService {

  constructor(private http: HttpClient) { }

  public getUserInfo(): Observable<any> {
    return this.http.get<any>(`${environment.apiUrl}/userInfo1`);
  }

}
