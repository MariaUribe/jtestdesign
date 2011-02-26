/* ClassWrapper.java
 *
 * @Copyrigth 24/02/2011
 */
package com.teg.vista.customlist;

/**
 * Class ClassWrapper
 *
 * @version 1.0
 */
public class ClassWrapper {

    private Class claseDeOrigen;

    /**
     * Creates a new instance of <code>ClassWrapper</code>
     */
    public ClassWrapper() {
    }

    public ClassWrapper(Class claseDeOrigen) {
        this.claseDeOrigen = claseDeOrigen;
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

    @Override
    public String toString() {
        return claseDeOrigen.getName();
    }


}
