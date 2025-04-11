import { Routes } from '@angular/router';

export const ADMIN_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./navbar-admin/navbar-admin.component').then(m => m.NavbarAdminComponent),
        children: [
            {
                path: 'registro_personal',
                title: 'Registro Personal',
                loadComponent: () => import('../auth/registro/registro.component').then(m => m.RegistroComponent),
                data: {cliente: false}
            },
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'registro_personal'
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'registro_personal'
    },
];