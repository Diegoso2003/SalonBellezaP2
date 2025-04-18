import { Routes } from '@angular/router';

export const routes: Routes = [
    {
        path: 'inicio',
        title: 'Inicio',
        loadComponent: () => import('./auth/login/login.component').then(m => m.LoginComponent)
    },
    {
        path: 'registro',
        title: 'Registro',
        loadComponent: () => import('./auth/registro/registro.component').then(m => m.RegistroComponent)
    },
    {
        path: 'detalles',
        title: 'Detalles',
        loadComponent: () => import('./auth/detalles/detalles.component').then(m => m.DetallesComponent)
    },
    {
        path: 'admin',
        loadChildren: () => import('./admin/admin.routes').then(m => m.ADMIN_ROUTES)
    },
    {
        path: 'servicios',
        loadChildren: () => import('./servicios/servicios.routes').then(m => m.SERVICIOS_ROUTES)
    },
    {
        path: 'marketing',
        loadChildren: () => import('./marketing/marketing.routes').then(m => m.MARKETING_ROUTES)
    },
    {
        path: 'cliente',
        loadChildren: () => import('./cliente/cliente.routes').then(m => m.CLIENTE_ROUTES)
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
