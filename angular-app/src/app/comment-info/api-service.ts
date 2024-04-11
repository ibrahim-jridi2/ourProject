import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8084';

  constructor() { }

  // Example GET request
  public getComment(page: number){
    return axios.get(`${this.apiUrl}/module-comment/comments?page=${page}`);
  }
  
  // Example POST request
  public postData(data: any) {
    return axios.post(`${this.apiUrl}/data`, data);
  }
}
