export class InformeCambio {
    private cambio: boolean = false;

    public getCambio(): boolean {
        return this.cambio;
    }

    public setCambio(cambio: boolean): void {
        this.cambio = cambio;
    }
}