/* MethodWrapper.java
 *
 * @Copyrigth 24/02/2011
 */
package com.teg.vista.customlist;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Class MethodWrapper
 *
 * @author Carlos D. Barroeta M.
 * @version 1.0
 */
public class MethodWrapper {

    private Method metodo;

    private java.util.List<Class> argumentos;

    /**
     * Creates a new instance of <code>MethodWrapper</code>
     */
    public MethodWrapper() {
    }

    public MethodWrapper(Method metodo) {
        this.metodo = metodo;

        java.util.List<Class> listaArgs = new java.util.ArrayList<Class>();
        listaArgs.addAll(Arrays.asList(metodo.getParameterTypes()));

        this.argumentos = listaArgs;
    }


    /**
     * @return the metodo
     */
    public Method getMetodo() {
        return metodo;
    }

    /**
     * @param metodo the metodo to set
     */
    public void setMetodo(Method metodo) {
        this.metodo = metodo;
    }

    /**
     * Metodo que retorna la informacion del metodo encapsulado de acuerdo
     * a un formato establecido
     * 
     * @return
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder(metodo.getReturnType().getSimpleName());

        builder.append(" ").append(metodo.getName()).append("(");

        StringBuilder parameters = new StringBuilder();

        for (Class tipoParam: metodo.getParameterTypes()) {
            parameters.append(tipoParam.getSimpleName()).append(",");
        }

        if (parameters.length() > 0) {
            parameters.deleteCharAt(parameters.lastIndexOf(","));
            builder.append(parameters.toString());
        }

        builder.append(")");

        return builder.toString();
    }

    /**
     * @return the argumentos
     */
    public java.util.List<Class> getArgumentos() {
        return argumentos;
    }

    /**
     * @param argumentos the argumentos to set
     */
    public void setArgumentos(java.util.List<Class> argumentos) {
        this.argumentos = argumentos;
    }


}
