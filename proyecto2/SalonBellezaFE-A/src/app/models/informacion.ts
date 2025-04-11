export class Informacion {
    private hayError: boolean = false;
    private mensaje: string = '';
    private exito: boolean = false;
    private mostrarAlertaExito: boolean = false;

    public informarError(mensaje: string) {
        this.hayError = true;
        this.mensaje = mensaje;
        this.exito = false;
    }

    public informarExito(mensaje: string) {
        this.hayError = false;
        this.mensaje = mensaje;
        this.exito = true;
        this.mostrarAlertaExito = true;
    }

    public ocultarAlertaExito() {
        this.mostrarAlertaExito = false;
    }    

    public getHayError(): boolean {
        return this.hayError;
    }

    public getMensaje(): string {
        return this.mensaje;
    }

    public getExito(): boolean {
        return this.exito;
    }

    public getMostrarAlertaExito(): boolean {
        return this.mostrarAlertaExito;
    }
}