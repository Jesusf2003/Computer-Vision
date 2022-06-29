import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ImagenClass, ImagenLocalClass, ImageUrlResponse, ImageLocalResponse } from 'src/app/service/imagen.model';
import { ReadImageService } from 'src/app/service/read-image.service';

@Component({
	selector: 'app-formulary',
	templateUrl: './formulary.component.html',
	styleUrls: ['./formulary.component.css']
})
export class FormularyComponent implements OnInit {

	public dto: ImagenClass = new ImagenClass();
	public dtoLocal: ImagenLocalClass = new ImagenLocalClass();

	public response: ImageUrlResponse = new ImageUrlResponse();
	public responseLocal: ImageLocalResponse = new ImageLocalResponse();

	constructor(
		private urlService: ReadImageService
	) { }

	ngOnInit(): void {
	}
	
	sendRequestUrl(): void {
		const dataUrl: ImagenClass = {
			img_url: this.dto.img_url
		}
		this.urlService.sendRequestUrl(dataUrl).subscribe(
			date => {
				this.response = date;
				console.log(this.response);
			},
			(err: HttpErrorResponse) => {
				console.log(err.message);
			}
		);
	}
	
	sendRequestLocal(): void {
		const dataLocal: ImagenLocalClass = {
			img_url: this.dtoLocal.img_url
		}
		this.urlService.sendRequestLocal(dataLocal).subscribe(
			date => {
				this.responseLocal = date;
				console.log(this.responseLocal);
			},
			(err: HttpErrorResponse) => {
				console.log(err.message);
			}
		);
	}
}