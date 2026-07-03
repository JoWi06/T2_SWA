import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Category } from '../model/category';
import { Family } from '../model/family';
import { Laboratory } from '../model/laboratory';

@Injectable({
  providedIn: 'root'
})
export class MaintenanceService {
  private baseUrl = 'http://localhost:8080/api';

  constructor(private http: HttpClient) { }

  getCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/categories`);
  }

  getFamilies(): Observable<Family[]> {
    return this.http.get<Family[]>(`${this.baseUrl}/families`);
  }

  getLaboratories(): Observable<Laboratory[]> {
    return this.http.get<Laboratory[]>(`${this.baseUrl}/laboratories`);
  }
}