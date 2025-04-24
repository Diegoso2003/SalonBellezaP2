export class InformeCambio {
    private cambio: boolean = false;
    private fecha: string = '';
    private fechaFin?: string;
    private fechaInicio?: string;

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

    public getFechaFin(): string {
        return this.fechaFin || '';
    }

    public setFechaFin(fechaFin: string): void {
        this.fechaFin = fechaFin;
    }

    public getFechaInicio(): string {
        return this.fechaInicio || '';
    }

    public setFechaInicio(fechaInicio: string): void {
        this.fechaInicio = fechaInicio;
    }

    public esFechaInicioValida(): boolean {
        return this.fechaInicio !== undefined && this.fechaInicio !== '';
    }

    public esFechaFinValida(): boolean {
        return this.fechaFin !== undefined && this.fechaFin !== '';
    }

    public sonAmbasValidas(): boolean {
        return this.esFechaInicioValida() && this.esFechaFinValida();
    }

    public vaciarFechas(): void {
        this.fechaInicio = undefined;
        this.fechaFin = undefined;
    }
}