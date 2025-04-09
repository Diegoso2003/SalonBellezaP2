import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegistroComponent } from './auth/registro/registro.component';
import { DetallesComponent } from './auth/detalles/detalles.component';
import { NavbarAdminComponent } from './admin/navbar-admin/navbar-admin.component';
import { NavbarClienteComponent } from './cliente/navbar-cliente/navbar-cliente.component';

export const routes: Routes = [
    {
        path: 'inicio',
        title: 'Inicio',
        component: LoginComponent
    },
    {
        path: 'registro',
        title: 'Registro',
        component: RegistroComponent
    },
    {
        path: 'detalles',
        title: 'Detalles',
        component: DetallesComponent
    },
    {
        path: 'admin',
        title: 'Admin',
        component: NavbarAdminComponent
    },
    {
        path: 'cliente',
        title: 'Cliente',
        component: NavbarClienteComponent
    },
    {
        path: '',
        redirectTo: 'inicio',
        pathMatch: 'full'
    },
    {
        path: '**',
        redirectTo: 'inicio'
    }
];
