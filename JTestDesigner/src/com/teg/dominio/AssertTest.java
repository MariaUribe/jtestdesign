package com.teg.dominio;

/**
 *
 * @author maya
 */
public class AssertTest {

    private String mensaje;

    private String variable;

    private String condicion;

    private String valorAssert;

    private String tipoDatoAssert;

    private boolean complejo;

    private boolean arreglo;
    
    private boolean mapa;

    private boolean generarXstream;

    public AssertTest() {
    }
    
    /**
     *
     * @param mensaje the mensaje to set
     * @param variable the variable to set
     * @param condicion the condicion to set
     */
    public AssertTest(String mensaje, String variable, String condicion) {
        this.mensaje = mensaje;
        this.variable = variable;
        this.condicion = condicion;

    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the variable
     */
    public String getVariable() {
        return variable;
    }

    /**
     * @param variable the variable to set
     */
    public void setVariable(String variable) {
        this.variable = variable;
    }

    /**
     * @return the condicion
     */
    public String getCondicion() {
        return condicion;
    }

    /**
     * @param condicion the condicion to set
     */
    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    /**
     * @return the valorAssert
     */
    public String getValorAssert() {
        return valorAssert;
    }

    /**
     * @param valorAssert the valorAssert to set
     */
    public void setValorAssert(String valorAssert) {
        this.valorAssert = valorAssert;
    }

    /**
     * @return the complejo
     */
    public boolean isComplejo() {
        return complejo;
    }

    /**
     * @param complejo the complejo to set
     */
    public void setComplejo(boolean complejo) {
        this.complejo = complejo;
    }

    /**
     * @return the arreglo
     */
    public boolean isArreglo() {
        return arreglo;
    }

    /**
     * @param arreglo the arreglo to set
     */
    public void setArreglo(boolean arreglo) {
        this.arreglo = arreglo;
    }

    /**
     * @return the mapa
     */
    public boolean isMapa() {
        return mapa;
    }

    /**
     * @param mapa the mapa to set
     */
    public void setMapa(boolean mapa) {
        this.mapa = mapa;
    }

    /**
     * @return the generarXstream
     */
    public boolean isGenerarXstream() {
        return generarXstream;
    }

    /**
     * @param generarXstream the generarXstream to set
     */
    public void setGenerarXstream(boolean generarXstream) {
        this.generarXstream = generarXstream;
    }

    /**
     * @return the tipoDatoAssert
     */
    public String getTipoDatoAssert() {
        return tipoDatoAssert;
    }

    /**
     * @param tipoDatoAssert the tipoDatoAssert to set
     */
    public void setTipoDatoAssert(String tipoDatoAssert) {
        this.tipoDatoAssert = tipoDatoAssert;
    }

}
