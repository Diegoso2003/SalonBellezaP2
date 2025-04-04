import { Routes } from '@angular/router';
import { LoginComponent } from './auth/login/login.component';
import { RegistroComponent } from './auth/registro/registro.component';

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
        path: '',
        redirectTo: 'inicio',
        pathMatch: 'full'
    },
    {
        path: '**',
        redirectTo: 'inicio'
    }
];
