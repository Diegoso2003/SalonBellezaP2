export class InformeCambio {
    private cambio: boolean = false;
    private fecha: string = '';
    private fechaFin?: string;
    private fechaInicio?: string;
    private campo?: string;
    private suma: number = 0;

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

    public getCampo(): string {
        return this.campo || '';
    }

    public setCampo(campo: string): void {
        this.campo = campo;
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

    public esCampoValido(): boolean {
        return this.campo !== undefined && this.campo !== '';
    }

    public getSuma(): number {
        return this.suma;
    }
    
    public setSuma(suma: number): void {
        this.suma = suma;
    }

    public vaciarFechas(): void {
        this.fechaInicio = undefined;
        this.fechaFin = undefined;
        this.campo = undefined;
        this.suma = 0;
    }
}