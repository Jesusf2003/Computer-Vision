import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ImagenClass } from 'src/app/service/imagen.model';
import { ReadImageService } from 'src/app/service/read-image.service';

@Component({
	selector: 'app-formulary',
	templateUrl: './formulary.component.html',
	styleUrls: ['./formulary.component.css']
})
export class FormularyComponent implements OnInit {

	public dto: ImagenClass = new ImagenClass();
	public response: ImagenClass = new ImagenClass();

	constructor(
		private urlService: ReadImageService
	) { }

	ngOnInit(): void {
	}
	
	sendRequest(): void {
		const data: ImagenClass = {
			img_url: this.dto.img_url
		}
		console.log(this.dto);
		this.urlService.sendRequest(data).subscribe(
			date => {
				this.response = date;
			},
			(err: HttpErrorResponse) => {
				console.log(err.message);
			}
		);
	}
}