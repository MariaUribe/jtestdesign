/* ClassMember.java
 *
 * @Copyrigth 24/02/2011
 */
package com.teg.vista.customlist;

import java.lang.reflect.Method;

/**
 * Class ClassMember
 *
 * Clase que encapasula el metodo y la clase en donde ha sido declarado
 * o la cual pertenece dicho metodo
 *
 * @version 1.0
 */
public class ClassMember {

    private Class claseDeOrigen;

    private Method metodo;

    /**
     * Creates a new instance of <code>ClassMember</code>
     */
    public ClassMember() {
    }

    public ClassMember(Class claseDeOrigen, Method metodo) {
        this.claseDeOrigen = claseDeOrigen;
        this.metodo = metodo;
    }

    /**
     * @return the claseDeOrigen
     */
    public Class getClaseDeOrigen() {
        return claseDeOrigen;
    }

    /**
     * @param claseDeOrigen the claseDeOrigen to set
     */
    public void setClaseDeOrigen(Class claseDeOrigen) {
        this.claseDeOrigen = claseDeOrigen;
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
     * Representacion en String bajo el formato ClassName.methodName(args)
     */
    @Override
    public String toString() {

        StringBuilder builder = new StringBuilder();

        builder.append(claseDeOrigen.getSimpleName()).append(".");

        StringBuilder parameters = new StringBuilder();

        for (Class tipoParam: metodo.getParameterTypes()) {
            parameters.append(tipoParam.getSimpleName()).append(",");
        }

        builder.append(metodo.getName()).append("(");

        if (parameters.length() > 0) {
            parameters.deleteCharAt(parameters.lastIndexOf(","));
            builder.append(parameters.toString());
        }

        builder.append(")");

        return builder.toString();
    }
}
