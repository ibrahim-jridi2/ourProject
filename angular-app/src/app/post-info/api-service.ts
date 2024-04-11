import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://localhost:8089';

  constructor() { }

  // Example GET request
  public getPosts(page: number){
    return axios.get(`${this.apiUrl}/module-post/posts?page=${page}`);
  }
  
  // Example POST request
  public postData(data: any) {
    return axios.post(`${this.apiUrl}/data`, data);
  }
}
