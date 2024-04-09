import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { User } from '../models/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http: HttpClient) { }
  baseUrl = environment.endpoint;

  getUsers(): Observable<any> {
    
    return this.http.get<User[]>(this.baseUrl + '/all');
  }
}
