import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { FormularyComponent } from './component/formulary/formulary.component';

const routes: Routes = [
	{ path: '', redirectTo: 'formulario', pathMatch: 'full' },
	{ path: 'formulario', component: FormularyComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }