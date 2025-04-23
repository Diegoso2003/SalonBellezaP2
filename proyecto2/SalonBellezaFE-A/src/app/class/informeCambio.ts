export class InformeCambio {
    private cambio: boolean = false;
    private fecha: string = '';

    public getCambio(): boolean {
        return this.cambio;
    }

    public setCambio(cambio: boolean): void {
        this.cambio = cambio;
    }

    public getFecha(): string {
        return this.fecha;
    }

    public setFecha(fecha: string): void {
        this.fecha = fecha;
    }
}