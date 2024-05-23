import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { MachinesComponent } from './machines/machines.component';
import { ProductsComponent } from './products/products.component';
import { HomeComponent } from './home/home.component';


const routes: Routes = [
  {
    path: 'machines',
    component: MachinesComponent
  },
  {
    path: 'products',
    component: ProductsComponent
  },
  {
    path: '',
    component: HomeComponent
  },
  {
    path: '**',
    redirectTo: '/',
    pathMatch: 'full',
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
