/* CustomListModel.java
 *
 * @Copyrigth 24/02/2011
 */
package com.teg.vista.customlist;

import java.util.ArrayList;
import java.util.List;
import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

/**
 * Class CustomListModel
 *
 * @version 1.0
 */
public class CustomListModel<T> implements ListModel {

    private ArrayList<T> classMembers = new ArrayList<T>();

    /**
     * Creates a new instance of <code>CustomListModel</code>
     */
    public CustomListModel() {
    }

    public boolean addItem(T nuevoElemento) {
        return classMembers.add(nuevoElemento);
    }

    public CustomListModel<T> actualizar() {
        CustomListModel<T> salida = new CustomListModel<T>();
        salida.classMembers = classMembers;

        return salida;
    }

    public boolean contains(T elemento){

        boolean encontrado = false;

        for (Object object : classMembers) {

            if (object.toString().equals(elemento.toString())){
            encontrado = true;
            }
        }

        return encontrado;
    }
    

    public List<T> getItems() {
        return classMembers;
    }

    public CustomListModel<T> clear(){

        
        this.classMembers.clear();

        CustomListModel<T> nuevaSalida = new CustomListModel<T>();

        nuevaSalida.classMembers = this.classMembers;

        return nuevaSalida;
    }

  public boolean removeItem(T elemento){

      ArrayList<T> nuevaLista = new ArrayList<T>();
        
      for (Object object : classMembers) {

          if (!object.toString().equals(elemento.toString())){

              nuevaLista.add((T) object);
          }

      }

      this.classMembers = nuevaLista;

        return true;
  }

  public void removeItems(List<T> elementos){

      ArrayList<T> nuevaLista = new ArrayList<T>();

      for (T object : this.classMembers) {

          boolean siEsta = false;
          
          for (T object2 : elementos){

              if (!object2.toString().equals(object.toString())){
                  siEsta = false;
              }else{
                  siEsta = true;
                  break;
              }
          }
          if (siEsta == false){
              nuevaLista.add(object);
          }

      }

      this.classMembers = nuevaLista;
  }

    //--------------------------------------------------------------
    // Metodos implementados de la intefaz ListModel
    //--------------------------------------------------------------

    public int getSize() {
        return classMembers.size();
    }

    public Object getElementAt(int index) {
        return classMembers.get(index);
    }

    

    public void addListDataListener(ListDataListener l) {
    }

    public void removeListDataListener(ListDataListener l) {
    }
}
