import { Component, OnInit } from '@angular/core';
import { ApiService } from './api-service';

@Component({
  selector: 'app-post-info',
  templateUrl: './post-info.component.html',
  styleUrls: ['./post-info.component.css']
})
export class PostInfoComponent implements OnInit {

  posts: any[] = [];

  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    this.fetchPosts();
  }

  fetchPosts() {
    this.apiService.getPosts(1)
      .then(response => {
        this.posts = response.data;
      })
      .catch(error => {
        console.error('Error fetching posts:', error);
      });
  }
}
