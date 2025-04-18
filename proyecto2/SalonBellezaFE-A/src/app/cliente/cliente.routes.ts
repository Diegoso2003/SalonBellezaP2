import { Routes } from "@angular/router";

export const CLIENTE_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./navbar-cliente/navbar-cliente.component').then(m => m.NavbarClienteComponent),
        children: [
            {
                path: 'servicios_disponibles',
                title: 'Servicios disponibles',
                loadComponent: () => import('./servicios-disponibles/servicios-disponibles.component').then(m => m.ServiciosDisponiblesComponent)
            },
            {
                path: 'agendar_cita/:idServicio',
                title: 'Agendar_cita',
                loadComponent: () => import('./form-cita/form-cita.component').then(m => m.FormCitaComponent)
            },
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'servicios_disponibles'
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'servicios_disponibles'
    }
];