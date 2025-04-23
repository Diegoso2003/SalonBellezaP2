import { Routes } from "@angular/router";

export const MARKETING_ROUTES: Routes = [
    {
        path: '',
        loadComponent: () => import('./navbar-marketing/navbar-marketing.component').then(m => m.NavbarMarketingComponent),
        children: [
            {
                path: 'cargar_anuncio',
                title: 'Crear anuncio',
                loadComponent: () => import('./form-anuncio/form-anuncio.component').then(m => m.FormAnuncioComponent)
            },
            {
                path: 'anuncios_mas_vistos',
                title: 'Anuncios mas vistos',
                loadComponent: () => import('./form-anuncios-vistos/form-anuncios-vistos.component').then(m => m.FormAnunciosVistosComponent),
                data: { anunciosMasVistos: true }
            },
            {
                path: 'anuncios_menos_vistos',
                title: 'Anuncios menos vistos',
                loadComponent: () => import('./form-anuncios-vistos/form-anuncios-vistos.component').then(m => m.FormAnunciosVistosComponent),
                data: { anunciosMasVistos: false }
            },
            {
                path: '',
                pathMatch: 'full',
                redirectTo: 'cargar_anuncio'
            }
        ]
    },
    {
        path: '**',
        redirectTo: 'cargar_anuncio'
    }
];