import { Component, OnInit } from '@angular/core';
import { ApiService } from './api-service';

@Component({
  selector: 'app-feedback-info',
  templateUrl: './feedback-info.component.html',
  styleUrls: ['./feedback-info.component.css']
})
export class FeedbackInfoComponent implements OnInit {
  responseData: any;

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.apiService.getFeedbacks()
      .then(response => {
        this.responseData = response.data;
      })
      .catch(error => {
        console.error('Error fetching data:', error);
      });
  }
}
 