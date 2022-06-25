import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { ImagenClass } from './imagen.model';

@Injectable({
	providedIn: 'root'
})
export class ReadImageService {
	
	private API_SERVER = "http://localhost:8082/api";

	constructor(private hpp: HttpClient) { }
	
	public sendRequest(data: ImagenClass): Observable<ImagenClass> {
		return this.hpp.post<ImagenClass>(this.API_SERVER, data);
	}
}