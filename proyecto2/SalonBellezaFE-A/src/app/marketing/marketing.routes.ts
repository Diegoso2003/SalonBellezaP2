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