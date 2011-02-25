/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DependenciesEditor.java
 *
 * Created on Jan 25, 2011, 9:16:10 PM
 */
package com.teg.vista;

import com.teg.dominio.Argumento;
import com.teg.dominio.CasoPrueba;
import com.teg.dominio.ClaseTest;
import com.teg.dominio.EscenarioPrueba;
import com.teg.dominio.Metodo;
import com.teg.dominio.MockObject;
import com.teg.dominio.Retorno;
import com.teg.logica.XmlManager;
import com.teg.util.EscenarioPersonalizado;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.lang.reflect.Method;
import java.util.ArrayList;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.JEditorPane;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.text.EditorKit;
import javax.swing.text.StyledEditorKit;

import net.miginfocom.swing.MigLayout;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author maya
 */
public class DependenciesEditor extends javax.swing.JInternalFrame {

    private ArrayList<Method> metodosSet = new ArrayList<Method>();
    private ArrayList<Method> metodosSetSeleccionados = new ArrayList<Method>();
    private ArrayList<MockObject> mockObjects = new ArrayList<MockObject>();
    private CasoPrueba casoPrueba = new CasoPrueba();
    private Inicio inicio;
    private JPanel scrollPaneContent;
    private JScrollPane scrollPane;

    /** Creates new form DependenciesEditor */
    public DependenciesEditor(ArrayList<Method> metodosSet, ArrayList<Method> metodosSetSeleccionados, Inicio inicio, CasoPrueba casoPrueba) {
        initComponents();
        this.inicio = inicio;
        this.metodosSet = metodosSet;
        this.metodosSetSeleccionados = metodosSetSeleccionados;
        this.casoPrueba = casoPrueba;
        this.myInits();
        this.paintFrame();
    }

    public final void myInits() {

        javax.swing.plaf.InternalFrameUI ifu = this.getUI();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) ifu).setNorthPane(null);

        int w2 = this.getSize().width;
        int h2 = this.getSize().height;

        this.inicio.setSize(new Dimension(w2, h2));

        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

        int w = this.inicio.getSize().width;
        int h = this.inicio.getSize().height;
        int x = (dim.width - w) / 2;
        int y = (dim.height - h) / 2;

        this.inicio.setLocation(x, y);
    }

    public final void paintFrame() {

        contentPane.setLayout(new MigLayout("fill, flowy"));
        this.setContentPane(contentPane);

        scrollPaneContent = new JPanel(new MigLayout("fill, flowy"));
        scrollPane = new JScrollPane(scrollPaneContent);

        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(FlowLayout.RIGHT);

        JButton atras = new JButton("Atras");
        atras.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                atrasButtonActionPerformed();
            }
        });

        JButton finalizar = new JButton("Finalizar");
        finalizar.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                finalizarButtonActionPerformed();
            }
        });

        FlowLayout flowLayoutTitulo = new FlowLayout();
        flowLayoutTitulo.setAlignment(FlowLayout.LEFT);

        JLabel dependencias = new JLabel();
        dependencias.setText("DEPENDENCIAS");
        dependencias.setFont(new java.awt.Font("Lucida Grande", 1, 14));

        JPanel titulo = new JPanel();
        titulo.setLayout(new FlowLayout());
        titulo.add(dependencias);

        JPanel botonPanel = new JPanel();
        botonPanel.setPreferredSize(new Dimension(700, 50));
        botonPanel.setLayout(flowLayout);
        botonPanel.add(atras);
        botonPanel.add(finalizar);

        int idEscenario = 1;
        int idMock = 1;

        for (Method metodoSetSelected : metodosSetSeleccionados) {
            ButtonGroup grupoRadios = new ButtonGroup();

            Class[] paramArreglo = metodoSetSelected.getParameterTypes();
            Class parametro = paramArreglo[0];

            //String claseSimpleName = metodoSetSelected.getDeclaringClass().getName();
            //String nombreVariable = claseSimpleName + idMock;
            //String nombreVar = StringUtils.uncapitalize(nombreVariable);

            String claseNombre = metodoSetSelected.getDeclaringClass().getName();
            String metodo = metodoSetSelected.getName() + "(" + parametro.getName() + " " + StringUtils.uncapitalize(parametro.getSimpleName()) + ")";

            String objetoMock = "<HTML><BR><strong>Objeto Mock: </strong>jmockContext</HTML>";
            String clase = "<HTML><strong>Clase: </strong>" + claseNombre + "</HTML>";
            String metodoObjeto = "<HTML><strong>Método: </strong>" + metodo + "</HTML>";
            String objetosCreados = "<HTML>Lista de objetos creados en el caso de prueba:<BR></HTML>";

            String dependencia = "<HTML><BR>Seleccione el manejo de dependencias:<BR></HTML>";

            String escenarios = "<HTML><BR>Seleccione el escenario donde se introducirá el código:<BR></HTML>";

            String codigo = "<HTML><BR>Código:<BR><BR></HTML>";


            MyComboBox myComboBox = new MyComboBox(idEscenario);
            ArrayList<String> objetosCreadosList = this.getObjetosCreados(myComboBox.getSelectedItem().toString());
            MyComboBoxObjetos myComboBoxObjetos = new MyComboBoxObjetos(objetosCreadosList);

            DefaultTableModel modelo = new DefaultTableModel();
            MyTable myTable = new MyTable(modelo);

            modelo.addColumn("etiqueta columna 1");
            modelo.addColumn("etiqueta columna 2");
            modelo.setNumRows(objetosCreadosList.size());
            modelo.setValueAt("nuevo valor1", 0, 0); // Cambia el valor de la fila 1, columna 1.
            modelo.setValueAt("nuevo valor2", 0, 1); // Cambia el valor de la fila 1, columna 2.

            JTableHeader tableHeader = new JTableHeader();
            TableColumnModel tcm = new DefaultTableColumnModel();

            TableColumn columnaTipo = new TableColumn();
            columnaTipo.setHeaderValue("TIPO");
            tcm.addColumn(columnaTipo);

            TableColumn columnaValor = new TableColumn();
            columnaValor.setHeaderValue("VALOR");
            tcm.addColumn(columnaValor);

            tableHeader.setColumnModel(tcm);
            myTable.setTableHeader(tableHeader);

//            for (int i = 0; i < objetosCreadosList.size(); i++) {
//                //if (i == 0) {
////                    myTable.setValueAt("Tipo", i, 0);
////                    myTable.setValueAt("Valor", i, 1);
//
//                //} else {
//                String[] lineaArg = objetosCreadosList.get(i).split(" ");
//
//                String argTipo = lineaArg[0].toString();
//                String argValor = lineaArg[1].toString();
//
//                myTable.setValueAt(argTipo, i, 0);
//                myTable.setValueAt(argValor, i, 1);
//                // }
//            }
            JTableHeader header = myTable.getTableHeader();
            header.setBackground(Color.yellow);

            MyRadioButton radioMock = new MyRadioButton("Inyección con JMock", idEscenario);
            radioMock.setSelected(true);
            MyRadioButton radioOtro = new MyRadioButton("Código Personalizado", idEscenario);
            grupoRadios.add(radioMock);
            grupoRadios.add(radioOtro);

            MyEditorPane myEditorPane = new MyEditorPane();

            scrollPaneContent.add(new MyLabel(objetoMock, false));
            scrollPaneContent.add(new MyLabel(clase, false));
            scrollPaneContent.add(new MyLabel(metodoObjeto, false));
            scrollPaneContent.add(new MyLabel(objetosCreados, true));
            scrollPaneContent.add(myComboBoxObjetos);
            scrollPaneContent.add(myTable);

            scrollPaneContent.add(new MyLabel(dependencia, true));
            scrollPaneContent.add(radioMock);
            scrollPaneContent.add(radioOtro);

            scrollPaneContent.add(new MyLabel(escenarios, true));
            scrollPaneContent.add(myComboBox);

            scrollPaneContent.add(new MyLabel(codigo, true));
            scrollPaneContent.add(new ScrollEditorPane(myEditorPane));
            scrollPaneContent.add(new MySpaceLabel());

            idEscenario++;
            idMock++;
        }

        contentPane.add(titulo);
        this.setPreferredSize(new Dimension(650, 700));
        add(scrollPane, "grow");
        contentPane.add(botonPanel);

        pack();
    }

    private void atrasButtonActionPerformed() {
        // volver a DependenciesSelection
        this.inicio.dependenciesEditorToSelection(this, metodosSet, casoPrueba);
    }

    private void finalizarButtonActionPerformed() {

        ArrayList<EscenarioPersonalizado> escenarios = new ArrayList<EscenarioPersonalizado>();
        ArrayList<String> codigos = new ArrayList<String>();

        Component[] componentes = scrollPaneContent.getComponents();

        for (Component componente : componentes) {

            if (componente.getName().startsWith("escenarios")) {
                JComboBox comboBox = (JComboBox) componente;

                if (componente.isEnabled()) {
                    escenarios.add(new EscenarioPersonalizado(comboBox.getSelectedItem().toString(), Boolean.TRUE));
                } else {
                    escenarios.add(new EscenarioPersonalizado(comboBox.getSelectedItem().toString(), Boolean.FALSE));
                }
            }

            if (componente.getName().equals("scroll")) {
                JScrollPane scrollPanel = (JScrollPane) componente;
                JViewport view = scrollPanel.getViewport();

                Component[] viewComponents = view.getComponents();

                for (Component component : viewComponents) {
                    if (component.getName().equals("codigo")) {
                        JEditorPane editor = (JEditorPane) component;
                        codigos.add(editor.getText());
                    }
                }
            }
        }

        int cont = 0;
        int idVariable = 1;

        for (Method metodoSetSelected : metodosSetSeleccionados) {

            //Class[] paramArreglo = metodoSetSelected.getParameterTypes();

            String claseName = metodoSetSelected.getDeclaringClass().getName();
            String claseSimpleName = metodoSetSelected.getDeclaringClass().getSimpleName();

            ClaseTest clase = new ClaseTest(claseName, claseSimpleName);

            String nombreVar = claseSimpleName;

            Metodo metodoSet = new Metodo(metodoSetSelected.getName(), clase);
            ArrayList<Argumento> argumentos = new ArrayList<Argumento>();
            Class[] parameters = metodoSetSelected.getParameterTypes();

            for (Class param : parameters) {
                argumentos.add(new Argumento(param.getSimpleName(), param.getName(), null, false, false, false));
            }
            metodoSet.setArgumentos(argumentos);
            metodoSet.setNombre(metodoSetSelected.getName());
            metodoSet.setRetorno(new Retorno(metodoSetSelected.getReturnType().getName(), metodoSetSelected.getReturnType().getSimpleName(), nombreVar));

            MockObject mockObject = new MockObject(metodoSet, nombreVar, escenarios.get(cont).getEscenario(), codigos.get(cont), escenarios.get(cont).getEscenarioHabilitado());
            mockObjects.add(mockObject);

            cont++;
            idVariable++;
        }

        Boolean flag = false;
        for (EscenarioPersonalizado escenario : escenarios) {

            if (escenario.getEscenarioHabilitado()) {
                flag = true;
                break;
            }
        }

        Boolean hasMock = flag;

        casoPrueba.setMock(hasMock);
        casoPrueba.setMockObjects(mockObjects);

        XmlManager xmlManager = new XmlManager();
        xmlManager.setInicio(inicio);
        xmlManager.crearCasoPrueba(this.inicio.getNombreCasoPrueba(), casoPrueba.getEscenariosPrueba(), mockObjects, hasMock);
    }

    public ArrayList<String> getObjetosCreados(String escenarioDePrueba) {

        ArrayList<EscenarioPrueba> escenarios = casoPrueba.getEscenariosPrueba();
        ArrayList<String> objetosCreados = new ArrayList<String>();

        for (EscenarioPrueba escenario : escenarios) {

            if (escenario.getNombre().equals(escenarioDePrueba)) {
                ArrayList<Metodo> metodos = escenario.getMetodos();

                for (Metodo metodo : metodos) {
                    ArrayList<Argumento> args = metodo.getArgumentos();

                    if (args.size() > 0) {
                        for (Argumento arg : args) {
                            objetosCreados.add(arg.getTipo() + " " + arg.getValor());
                        }
                    }
                }
            }
        }
        return objetosCreados;
    }

    private class MyLabel extends JLabel {

        public MyLabel(String texto, boolean negritas) {

            setName("etiqueta");
            setText(texto);
            setVisible(true);

            if (negritas) {
                setFont(new java.awt.Font("Lucida Grande", 1, 13));
            }

            pack();
        }
    }

    private class MyComboBox extends JComboBox {

        public MyComboBox(int idEscenario) {

            ArrayList<EscenarioPrueba> escenarios = casoPrueba.getEscenariosPrueba();

            setName("escenarios" + idEscenario);
            for (EscenarioPrueba escenario : escenarios) {
                addItem(escenario.getNombre());
            }
            setPreferredSize(new Dimension(400, 20));
            setVisible(true);

            pack();

            addPopupMenuListener(new javax.swing.event.PopupMenuListener() {

                public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
                }

                public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                    myComboBoxPopupMenuWillBecomeInvisible(evt);
                }

                public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                }
            });
        }

        public void myComboBoxPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {

            ArrayList<String> objetosCreados = new ArrayList<String>();
            Component[] componentes = scrollPaneContent.getComponents();

            for (Component componente : componentes) {
                if (componente.getName().startsWith("escenarios")) {
                    objetosCreados = this.getObjetosCreados(getSelectedItem().toString());
                }
                if (componente.getName().equals("objetosCreados")) {
                    if (!objetosCreados.isEmpty()) {
                        MyComboBoxObjetos cb = (MyComboBoxObjetos) componente;
                        cb.removeAllItems();
                        for (String string : objetosCreados) {
                            cb.addItem(string);
                        }
                    }
                }
            }
        }

        public ArrayList<String> getObjetosCreados(String escenarioDePrueba) {

            ArrayList<EscenarioPrueba> escenarios = casoPrueba.getEscenariosPrueba();
            ArrayList<String> objetosCreados = new ArrayList<String>();

            for (EscenarioPrueba escenario : escenarios) {

                if (escenario.getNombre().equals(escenarioDePrueba)) {
                    ArrayList<Metodo> metodos = escenario.getMetodos();

                    for (Metodo metodo : metodos) {
                        ArrayList<Argumento> args = metodo.getArgumentos();

                        if (args.size() > 0) {
                            for (Argumento arg : args) {
                                objetosCreados.add(arg.getTipo() + " " + arg.getValor());
                            }
                        }
                    }
                }
            }
            return objetosCreados;
        }
    }

    private class MyComboBoxObjetos extends JComboBox {

        public MyComboBoxObjetos(ArrayList<String> objetosCreados) {

            setName("objetosCreados");
            for (String objeto : objetosCreados) {
                addItem(objeto);
            }
            setPreferredSize(new Dimension(400, 20));
            setVisible(true);

            pack();
        }
    }

    private class MySpaceLabel extends JLabel {

        public MySpaceLabel() {

            setName("espacio");
            setText("<HTML><BR><BR></HTML>");
            setVisible(true);

            pack();
        }
    }

    private class MyRadioButton extends JRadioButton {

        private int idEscenario;

        public MyRadioButton(String texto, int idEscenario) {
            setName("radio");
            setText(texto);
            this.idEscenario = idEscenario;

            addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    radioButtonActionPerformed();
                }
            });
        }

        private void radioButtonActionPerformed() {
            Component[] componentes = scrollPaneContent.getComponents();

            if (getText().equals("Código Personalizado")) {

                for (Component componente : componentes) {
                    if (componente.getName().equals("escenarios" + idEscenario)) {
                        componente.setEnabled(false);
                    }
                }

            } else {

                for (Component componente : componentes) {
                    if (componente.getName().equals("escenarios" + idEscenario)) {
                        componente.setEnabled(true);
                    }
                }
            }
        }
    }

    private class ScrollEditorPane extends JScrollPane {

        public ScrollEditorPane(MyEditorPane editorPane) {

            setName("scroll");
            setViewportView(editorPane);

            editorPane.setEditable(true);
            editorPane.setContentType("text/x-java");
            EditorKit kit = JEditorPane.createEditorKitForContentType("text/x-java");
            if (kit == null) {
                kit = new StyledEditorKit();
            }
            editorPane.setEditorKit(kit);
            editorPane.setEditorKitForContentType("text/x-java", kit);
            kit.install(editorPane);

            editorPane.setText("// TODO add your handling code here:");
            pack();
        }
    }

    private class MyEditorPane extends JEditorPane {

        public MyEditorPane() {

            setName("codigo");
            setPreferredSize(new Dimension(480, 120));
            setContentType("text/x-java");
            setVisible(true);

            pack();
        }
    }

    private class MyTable extends JTable {

        public MyTable(DefaultTableModel modelo) {
            setName("tabla");
            setPreferredSize(new Dimension(480, 100));
            setVisible(true);

            Border line = BorderFactory.createLineBorder(Color.black);
            setBorder(line);
            setSelectionMode(0);
            setGridColor(Color.black);
            setRowHeight(50);
            setLocation(20, 40);
            setModel(modelo);

            pack();
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        contentPane = new javax.swing.JPanel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));

        org.jdesktop.layout.GroupLayout contentPaneLayout = new org.jdesktop.layout.GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 651, Short.MAX_VALUE)
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 697, Short.MAX_VALUE)
        );

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(contentPane, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel contentPane;
    // End of variables declaration//GEN-END:variables
}
