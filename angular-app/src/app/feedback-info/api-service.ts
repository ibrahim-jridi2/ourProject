import { Injectable } from '@angular/core';
import axios from 'axios';

@Injectable({
  providedIn: 'root'
})
export class ApiService {
  private apiUrl = 'http://127.0.0.1:8000';

  constructor() { }

  // Example GET request
  public getFeedbacks() {
    return axios.get(`${this.apiUrl}/getAllFeedback`);
  }

  // Example POST request
  public postData(data: any) {
    return axios.post(`${this.apiUrl}/data`, data);
  }
}
