import { Routes } from "@angular/router";

export const EMPLEADO_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./navbar-empleado/navbar-empleado.component').then(m => m.NavbarEmpleadoComponent),
        children: [
            {
                path: 'agenda',
                title: 'Agenda',
                loadComponent: () => import('./vista-citas/vista-citas.component').then(m => m.VistaCitasComponent)
            },
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'agenda'
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'agenda'
    }
];