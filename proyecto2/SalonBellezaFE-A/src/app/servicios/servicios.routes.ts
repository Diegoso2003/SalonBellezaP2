import { Routes } from "@angular/router";

export const SERVICIOS_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./navbar-servicios/navbar-servicios.component').then(m => m.NavbarServiciosComponent),
        children: [
            {
                path: 'crear_servicio',
                title: 'Crear Servicio',
                loadComponent: () => import('./form-servicio/form-servicio.component').then(m => m.FormServicioComponent)
            },
            {
                path: 'servicios_mas_reservados',
                title: 'Servicios mas reservados',
                loadComponent: () => import('./vista-reporte-servicio/vista-reporte-servicio.component').then(m => m.VistaReporteServicioComponent),
                data: { servicioMasReservado: true }
            },
            {
                path: 'servicios_menos_reservados',
                title: 'Servicios menos reservados',
                loadComponent: () => import('./vista-reporte-servicio/vista-reporte-servicio.component').then(m => m.VistaReporteServicioComponent),
                data: { servicioMasReservado: false }
            },
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'crear_servicio'
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'crear_servicio'
    }
];