import { Component, OnInit } from '@angular/core';
import { ApiService } from './api-service';

@Component({
  selector: 'app-comment-info',
  templateUrl: './comment-info.component.html',
  styleUrls: ['./comment-info.component.css']
})
export class CommentInfoComponent implements OnInit {

  comments: any[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.fetchComments();
  }

  fetchComments() {
    this.apiService.getComment(1)
      .then(response => {
        this.comments = response.data;
      })
      .catch(error => {
        console.error('Error fetching posts:', error);
      });
  }
}
