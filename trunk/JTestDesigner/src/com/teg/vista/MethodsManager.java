/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * MethodsManager.java
 *
 * Created on Oct 26, 2010, 1:33:34 PM
 */
package com.teg.vista;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.DefaultListModel;

/**
 *
 * @author maya
 */
public class MethodsManager extends javax.swing.JInternalFrame {

    private Inicio inicio;
    private ArrayList<Class> clases = new ArrayList<Class>();
    public static DefaultListModel modelo = new DefaultListModel();
    public static ArrayList listaTDev = new ArrayList();

    /** Creates new form MethodsManager */
    public MethodsManager(Inicio inicio, ArrayList<Class> clases) {
        initComponents();
        this.inicio = inicio;
        this.clases = clases;
        continuar.setEnabled(false);
        this.inicio.getSeleccionarJar().setEnabled(false);
        ArrayList<String> nameClass = new ArrayList<String>();

        for (Class clazz : clases) {
            nameClass.add(clazz.getName());
        }
        classList.setListData(nameClass.toArray());

        javax.swing.plaf.InternalFrameUI ifu = this.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) ifu).setNorthPane(null);

        int w2 = this.getSize().width;
        int h2 = this.getSize().height;
        this.inicio.setSize(new Dimension(w2, h2));

        // Get the size of the screen
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        // Determine the new location of the window
        int w = this.inicio.getSize().width;
        int h = this.inicio.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        // Move the window
        this.inicio.setLocation(x, y);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        metodosLista = new javax.swing.JList();
        jScrollPane3 = new javax.swing.JScrollPane();
        selectedMethodsList = new javax.swing.JList();
        jPanel1 = new javax.swing.JPanel();
        selectAllOption = new javax.swing.JLabel();
        unselectAllOption = new javax.swing.JLabel();
        unselectOption = new javax.swing.JLabel();
        selectOption = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        classList = new javax.swing.JList();
        jLabel1 = new javax.swing.JLabel();
        continuar = new javax.swing.JButton();
        volver = new javax.swing.JButton();

        setBorder(null);

        jPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Seleccion de Metodos"));

        jScrollPane1.setViewportView(metodosLista);

        jScrollPane3.setViewportView(selectedMethodsList);

        selectAllOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/rightAll.png"))); // NOI18N
        selectAllOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectAllOption.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectAllOptionMouseClicked(evt);
            }
        });

        unselectAllOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/leftAll.png"))); // NOI18N
        unselectAllOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        unselectAllOption.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unselectAllOptionMouseClicked(evt);
            }
        });

        unselectOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/left.png"))); // NOI18N
        unselectOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        unselectOption.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                unselectOptionMouseClicked(evt);
            }
        });

        selectOption.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/right.png"))); // NOI18N
        selectOption.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        selectOption.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectOptionMouseClicked(evt);
            }
        });
        selectOption.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                selectOptionMouseMoved(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selectOption, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(selectAllOption))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(unselectAllOption)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(unselectOption)
                .addContainerGap())
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {selectAllOption, selectOption, unselectAllOption, unselectOption});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(selectOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectAllOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unselectAllOption)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(unselectOption)
                .addContainerGap(11, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {selectAllOption, selectOption, unselectAllOption, unselectOption});

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(34, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE))
                .addContainerGap(29, Short.MAX_VALUE))
        );

        classList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        classList.setDragEnabled(true);
        classList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                classListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(classList);

        jLabel1.setText("Clases escogidas:");

        continuar.setText("Continuar");
        continuar.setEnabled(false);
        continuar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                continuarActionPerformed(evt);
            }
        });

        volver.setText("Volver");
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelLayout = new javax.swing.GroupLayout(jPanel);
        jPanel.setLayout(jPanelLayout);
        jPanelLayout.setHorizontalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanelLayout.createSequentialGroup()
                            .addComponent(volver)
                            .addGap(656, 656, 656)
                            .addComponent(continuar))
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        jPanelLayout.setVerticalGroup(
            jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelLayout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(continuar)
                    .addComponent(volver))
                .addContainerGap(59, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void selectAllOptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectAllOptionMouseClicked
        ArrayList<String> metodosIzquierda = new ArrayList<String>();
        ArrayList<String> metodosDerecha = new ArrayList<String>();

        metodosIzquierda = this.getMetodosIzquierda();
        metodosDerecha = this.getMetodosDerecha();

        metodosDerecha.addAll(metodosIzquierda);
        metodosIzquierda.removeAll(metodosIzquierda);

        metodosLista.setListData(metodosIzquierda.toArray());
        selectedMethodsList.setListData(metodosDerecha.toArray());

        this.setVisibilidadContinuar();
}//GEN-LAST:event_selectAllOptionMouseClicked

    private void unselectAllOptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unselectAllOptionMouseClicked
        ArrayList<String> metodosIzquierda = new ArrayList<String>();
        ArrayList<String> metodosDerecha = new ArrayList<String>();

        metodosIzquierda = this.getMetodosIzquierda();
        metodosDerecha = this.getMetodosDerecha();

        metodosIzquierda.addAll(metodosDerecha);
        System.out.println(metodosIzquierda.size());
        metodosDerecha.removeAll(metodosDerecha);

        metodosLista.setListData(metodosIzquierda.toArray());
        selectedMethodsList.setListData(metodosDerecha.toArray());

        this.setVisibilidadContinuar();
}//GEN-LAST:event_unselectAllOptionMouseClicked

    private void unselectOptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_unselectOptionMouseClicked
        ArrayList<String> metodosIzquierda = new ArrayList<String>();
        ArrayList<String> metodosDerecha = new ArrayList<String>();

        Object[] clasesSeleccionadas = selectedMethodsList.getSelectedValues();

        metodosIzquierda = this.getMetodosIzquierda();
        metodosDerecha = this.getMetodosDerecha();

        for (Object object : clasesSeleccionadas) {
            String clase = object.toString();
            metodosIzquierda.add(clase);
            metodosDerecha.remove(clase);
        }

        metodosLista.setListData(metodosIzquierda.toArray());
        selectedMethodsList.setListData(metodosDerecha.toArray());

        this.setVisibilidadContinuar();
}//GEN-LAST:event_unselectOptionMouseClicked

    private void selectOptionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectOptionMouseClicked
        ArrayList<String> metodosIzquierda = new ArrayList<String>();
        ArrayList<String> metodosDerecha = new ArrayList<String>();

        Object[] metodosSeleccionados = metodosLista.getSelectedValues();

        metodosIzquierda = this.getMetodosIzquierda();
        metodosDerecha = this.getMetodosDerecha();

        for (Object object : metodosSeleccionados) {
            String clase = object.toString();
            metodosIzquierda.remove(clase);
            metodosDerecha.add(clase);
        }

        metodosLista.setListData(metodosIzquierda.toArray());
        selectedMethodsList.setListData(metodosDerecha.toArray());

        this.setVisibilidadContinuar();
    }//GEN-LAST:event_selectOptionMouseClicked

    private void selectOptionMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectOptionMouseMoved
}//GEN-LAST:event_selectOptionMouseMoved

    private void classListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_classListMouseClicked
        ArrayList<String> metodosDerecha = new ArrayList<String>();

        if (MouseEvent.BUTTON1 == 1) {

            Method[] methods = null;
            Method[] methodsHeritance = null;


            String nameClass = classList.getSelectedValue().toString();

            for (Class clazz : clases) {
                if (clazz.getName().equals(nameClass)) {

                    methods = clazz.getDeclaredMethods();
                   
                    methodsHeritance = clazz.getMethods();
                   
                    
                }
            }
            
          
            String nombre;
              

            ArrayList<String> nameMethods = new ArrayList<String>();

            try{
                
            for (Method method : methods) {

                if(method.toString().contains(" native ")){
                    System.out.println(method.getName());
                }

                if (!method.toString().contains(" native ")){

                nombre = getNombreDefinido(method);

                nameMethods.add(nombre);
                }

                //nameMethods.add(method.getDeclaringClass().getSimpleName() + "." + method.getName());
            }
            }catch (java.lang.ArrayIndexOutOfBoundsException e){
                System.out.println(e);
            }

            for (Method method : methodsHeritance) {


                if (!method.toString().contains(" native ")) {
                    nombre = getNombreDefinido(method);


                    if (nameMethods.contains(nombre) == false) {

                        nameMethods.add(nombre);
                    }

                }
            }


            if (selectedMethodsList.getModel().getSize() > 0) {
                metodosDerecha = this.getMetodosDerecha();
                nameMethods.removeAll(metodosDerecha);
            }

            metodosLista.setListData(nameMethods.toArray());

        }
}//GEN-LAST:event_classListMouseClicked

    private void continuarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_continuarActionPerformed

        ArrayList<Method> metodos = new ArrayList<Method>();
        metodos = this.getMetodos();

        this.inicio.methodsToCaseTest(this, metodos);

    }//GEN-LAST:event_continuarActionPerformed

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed
        this.inicio.methodsToClass(this);
    }//GEN-LAST:event_volverActionPerformed

    public void setVisibilidadContinuar(){
        int cantidadDeElementos = selectedMethodsList.getModel().getSize();

        if(cantidadDeElementos <= 0){
            continuar.setEnabled(false);
        } else {
            continuar.setEnabled(true);
        }
    }

    /**
     * Metodo para obtener los metodos seleccionados
     * @return Lista con los metodos seleccionados
     */
    public ArrayList<Method> getMetodos() {

        ArrayList<Method> metodos = new ArrayList<Method>();

        ArrayList<String> metodosDerecha = new ArrayList<String>();


        ArrayList<Method> metodosTodos = new ArrayList<Method>();




       

        metodosDerecha = this.getMetodosDerecha();

        String cadena[];


        for (Class clazz : clases) {
            
            metodosTodos.addAll(Arrays.asList(clazz.getDeclaredMethods()));

            for (Method method : clazz.getMethods()){

                if (metodosTodos.contains(method) == false){
                    metodosTodos.add(method);
                }
            }

           
        }

        
        for (String str : metodosDerecha){
            
           for (Method method : metodosTodos){
               String[] nombre = str.split("\\(");

               String claseDeclaracion = method.getDeclaringClass().getName();
               
               if (nombre[0].equals(method.getName()) == true && classList.getSelectedValue().toString().equals(claseDeclaracion)){
                   metodos.add(method);
               }
               
           }
        }






        return metodos;
    }

    private String getNombreDefinido(Method method){

        String metodo = method.getName();

        String campos = "(";

        if (method.getParameterTypes().length != 0){

        for (int i = 0; i < method.getParameterTypes().length; i++) {

           

           if (i == method.getParameterTypes().length - 1){

                if (method.getParameterTypes()[i].isPrimitive()){

                    campos = campos + method.getParameterTypes()[i].getName() + ")";


               }else{

                   campos = campos + method.getParameterTypes()[i].getSimpleName() + ")";
               }


           }else{

               if (method.getParameterTypes()[i].isPrimitive()){

                    campos = campos + method.getParameterTypes()[i].getName();


               }else{

                   campos = campos + method.getParameterTypes()[i].getSimpleName();
               }

                

                campos = campos + ",";
           }

        }
        }else{
            campos = campos + ")";
        }

        metodo = metodo + campos;

        return metodo;
    }

    /**
     * Metodo para agregar en un arreglo las clases de la izquierda
     * @return Lista de Metodos del panel izquierdo
     */
    public ArrayList<String> getMetodosIzquierda() {
        ArrayList<String> metodosIzquierda = new ArrayList<String>();

        for (int i = 0; i < metodosLista.getModel().getSize(); i++) {
            metodosIzquierda.add(metodosLista.getModel().getElementAt(i).toString());
        }
        return metodosIzquierda;
    }

    /**
     * Metodo para agregar en un arreglo las clases de la derecha
     * @return Lista de Metodos del panel derecho
     */
    public ArrayList<String> getMetodosDerecha() {
        ArrayList<String> metodosDerecha = new ArrayList<String>();

        for (int i = 0; i < selectedMethodsList.getModel().getSize(); i++) {
            metodosDerecha.add(selectedMethodsList.getModel().getElementAt(i).toString());
        }
        return metodosDerecha;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList classList;
    private javax.swing.JButton continuar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JList metodosLista;
    private javax.swing.JLabel selectAllOption;
    private javax.swing.JLabel selectOption;
    private javax.swing.JList selectedMethodsList;
    private javax.swing.JLabel unselectAllOption;
    private javax.swing.JLabel unselectOption;
    private javax.swing.JButton volver;
    // End of variables declaration//GEN-END:variables
}
