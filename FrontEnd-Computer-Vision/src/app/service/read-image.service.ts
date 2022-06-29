import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ImagenClass, ImagenLocalClass, ImageUrlResponse, ImageLocalResponse } from './imagen.model';

@Injectable({
	providedIn: 'root'
})
export class ReadImageService {
	
	private API_SERVER = "http://localhost:8082/api";

	constructor(private hpp: HttpClient) { }
	
	public sendRequestUrl(data: ImagenClass): Observable<ImageUrlResponse> {
		return this.hpp.post<ImageUrlResponse>(this.API_SERVER + '/url', data);
	}

	public sendRequestLocal(data: ImagenLocalClass): Observable<ImageLocalResponse> {
		return this.hpp.post<ImageLocalResponse>(this.API_SERVER + '/local', data);
	}
}