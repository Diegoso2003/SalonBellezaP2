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
                path: 'cliente_mas_gasto',
                title: 'Reporte  del cliente que mas gasto',
                loadComponent: () => import('./vista-reporte-cliente-gastos/vista-reporte-cliente-gastos.component').then(m => m.VistaReporteClienteGastosComponent),
                data: { clienteMasGasto: true }
            },
            {
                path: 'cliente_menos_gasto',
                title: 'Reporte del cliente que menos gasto',
                loadComponent: () => import('./vista-reporte-cliente-gastos/vista-reporte-cliente-gastos.component').then(m => m.VistaReporteClienteGastosComponent),
                data: { clienteMasGasto: false }
            },
            {
                path: 'anuncios_mas_vistos',
                title: 'Reporte de los anuncios mas vistos',
                loadComponent: () => import('../marketing/vista-reporte-anuncio-vista/vista-reporte-anuncio-vista.component').then(m => m.VistaReporteAnuncioVistaComponent),
                data: { anunciosMasVistos: true }
            },
            {
                path: 'clientes_mas_citas',
                title: 'Reporte de los clientes con mas citas',
                loadComponent: () => import('./vista-reporte-cliente-citas/vista-reporte-cliente-citas.component').then(m => m.VistaReporteClienteCitasComponent),
                data: { clienteMasCitas: true }
            },
            {
                path: 'clientes_menos_citas',
                title: 'Reporte de los clientes con menos citas',
                loadComponent: () => import('./vista-reporte-cliente-citas/vista-reporte-cliente-citas.component').then(m => m.VistaReporteClienteCitasComponent),
                data: { clienteMasCitas: false }
            },
            {
                path: 'ganancias_servicios',
                title: 'Reporte de ganancias por servicios',
                loadComponent: () => import('./vista-reporte-ganancias-servicio/vista-reporte-ganancias-servicio.component').then(m => m.VistaReporteGananciasServicioComponent)
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