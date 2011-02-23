/*
 * InstanceForm.java
 *
 * Created on Dec 6, 2010, 11:59:28 PM
 */
package com.teg.vista;

import com.teg.dominio.VariableInstancia;
import com.teg.logica.ClassLoading;

import com.teg.logica.WidgetObjectLoading;
import com.teg.util.SwingDialog;

import com.thoughtworks.xstream.XStream;

import com.thoughtworks.xstream.io.xml.DomDriver;

import java.awt.BorderLayout;
import java.awt.Dimension;


import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.KeyEvent;


import java.io.File;

import java.io.FileInputStream;

import java.io.FileNotFoundException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;



import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.logging.Level;

import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JTabbedPane;

import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;
import org.jdesktop.beansbinding.Converter;
import org.jdom.JDOMException;

import org.metawidget.inspector.composite.CompositeInspector;

import org.metawidget.inspector.composite.CompositeInspectorConfig;

import org.metawidget.inspector.iface.Inspector;

import org.metawidget.inspector.propertytype.PropertyTypeInspector;

import org.metawidget.inspector.xml.XmlInspector;

import org.metawidget.inspector.xml.XmlInspectorConfig;

import org.metawidget.swing.SwingMetawidget;

import org.metawidget.swing.layout.GridBagLayoutConfig;

import org.metawidget.swing.layout.TabbedPaneLayoutDecorator;

import org.metawidget.swing.layout.TabbedPaneLayoutDecoratorConfig;

import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessor;

import org.metawidget.swing.widgetprocessor.binding.beansbinding.BeansBindingProcessorConfig;

/**
 *
 * @author danielbello
 */
public class InstanceForm extends javax.swing.JDialog {

    private Object instanceInspect;
    private String path;
    private WidgetObjectLoading listWidget = new WidgetObjectLoading();
    private SwingMetawidget metawidget = new SwingMetawidget();
    private javax.swing.JButton buttonCancelar;
    private org.jdom.Document docXml;
    private javax.swing.JButton buttonGuardar;
    private javax.swing.JPanel buttonPanel;
    private javax.swing.JPanel objectContainer;
    private javax.swing.JTabbedPane tabPanel;
    private javax.swing.JPanel panelSeleccion;
    private Inicio inicio;
    private String casoPrueba;
    private Class clase;
    private ArrayList<Class> claseJars;
    private ClassLoading classLoader = new ClassLoading();
    private int objId;
    private JList listaSeleccionAbstracta;
    private JButton aceptarSeleccion;
    private JButton cancelarSeleccion;
    private SwingDialog dialogoColeccion;
    VariableInstancia variableInstancia;
    boolean vieneDelAssert;

    /** Creates new form InstanceForm */
    public InstanceForm(java.awt.Frame parent, boolean modal, Object instance, String dataPath, WidgetObjectLoading listObject, Inicio inicio, int objId, boolean vieneDelAssert) {

        super(parent, modal);


        listWidget = listObject;

        instanceInspect = instance;

        path = dataPath;


        this.inicio = inicio;

        this.casoPrueba = inicio.getNombreCasoPrueba();

        this.objId = objId;

        this.vieneDelAssert = vieneDelAssert;

        initComponents2();

    }

    public InstanceForm(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }

    public InstanceForm(java.awt.Frame parent, boolean modal, ArrayList<Class> clasesJars, String path, WidgetObjectLoading listObject, Inicio inicio, int objId, boolean vieneDelAssert) {

        super(parent, modal);


        this.path = path;

        this.listWidget = listObject;

        this.inicio = inicio;

        this.objId = objId;

        this.claseJars = clasesJars;

        this.casoPrueba = inicio.getNombreCasoPrueba();

        this.vieneDelAssert = vieneDelAssert;

        initComponentsAbstract();


    }

    public void InspectObject(Object instance) {
        // asociamor al metawidget la instancia que va a manejar el "binding" de propiedades
       metawidget.addWidgetProcessor(new BeansBindingProcessor(
               new BeansBindingProcessorConfig().setUpdateStrategy(UpdateStrategy.READ_WRITE).
               setConverter(java.util.Date.class, String.class, new Converter<java.util.Date, String>() {

           private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

           @Override
           public String convertForward(java.util.Date value) {
               return formatter.format(value);
           }

           @Override
           public java.util.Date convertReverse(String value) {

               try {
                   return formatter.parse(value);
               } catch (ParseException ex) {
                   return null;
               }
           }
       })));

        CompositeInspectorConfig inspectorConfig = null;

        try {
            XmlInspectorConfig xmlConfig = new XmlInspectorConfig();

            File file = new File(path + "/" + "metawidgetData.xml");
            // System.out.println(file.exists());

            xmlConfig.setInputStream(new FileInputStream(new File(path + "/" + "metawidgetData.xml")));
            PropertyTypeInspector inspector = new PropertyTypeInspector();

            inspectorConfig = new CompositeInspectorConfig().setInspectors(
                    new Inspector[]{new PropertyTypeInspector(),
                        new XmlInspector(xmlConfig)});

        } catch (FileNotFoundException ex) {
        }

        GridBagLayoutConfig nestedLayoutConfig = new GridBagLayoutConfig().setNumberOfColumns(2);

        nestedLayoutConfig.setRequiredAlignment(2);

        TabbedPaneLayoutDecoratorConfig layoutConfig = new TabbedPaneLayoutDecoratorConfig().setLayout(
                new org.metawidget.swing.layout.GridBagLayout(nestedLayoutConfig));

        metawidget.setMetawidgetLayout(new TabbedPaneLayoutDecorator(layoutConfig));

        metawidget.setInspector(new CompositeInspector(inspectorConfig));
        metawidget.setPreferredSize(new java.awt.Dimension(450, 450));

        metawidget.setToInspect(instance);

        objectContainer.add(metawidget);
    }

    private void initComponentsAbstract() {

        tabPanel = new javax.swing.JTabbedPane();

        panelSeleccion = new javax.swing.JPanel(false);

        panelSeleccion.setLayout(new GridBagLayout());

        panelSeleccion.setAutoscrolls(true);

        GridBagConstraints c = new GridBagConstraints();

        listaSeleccionAbstracta = new javax.swing.JList();

        listaSeleccionAbstracta.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        listaSeleccionAbstracta.setSize(new Dimension(100, 100));

        listaSeleccionAbstracta.setMaximumSize(new Dimension(1000, 1000));

        listaSeleccionAbstracta.setLayoutOrientation(JList.HORIZONTAL_WRAP);

        listaSeleccionAbstracta.setVisibleRowCount(-1);

        javax.swing.JScrollPane listScroller = new javax.swing.JScrollPane(listaSeleccionAbstracta);

        listScroller.setPreferredSize(new Dimension(250, 300));

        llenarListaJarSeleccion(listaSeleccionAbstracta);

        c.gridx = 0;

        c.gridy = 0;

        c.insets = new Insets(10, 20, 0, 5);

        c.anchor = GridBagConstraints.CENTER;

        panelSeleccion.add(listScroller, c);


        aceptarSeleccion = new javax.swing.JButton();

        aceptarSeleccion.setText("Aceptar Seleccion..");

        aceptarSeleccion.setFont(new Font("Calibri", Font.BOLD, 12));

        aceptarSeleccion.setSize(new Dimension(20, 20));

        aceptarSeleccion.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {

                aceptarSeleccionActionPerformed(evt);

            }
        });

        c.gridx = 0;

        c.gridy = 1;

        c.anchor = GridBagConstraints.WEST;

        panelSeleccion.add(aceptarSeleccion, c);

        cancelarSeleccion = new javax.swing.JButton();

        cancelarSeleccion.setText("Cancelar Seleccion..");

        cancelarSeleccion.setEnabled(false);

        cancelarSeleccion.setSize(new Dimension(20, 20));

        cancelarSeleccion.setFont(new Font("Calibri", Font.BOLD, 12));

        cancelarSeleccion.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelarSeleccionActionPerformed(evt);
            }
        });

        c.gridx = 2;

        c.gridy = 1;

        c.anchor = GridBagConstraints.EAST;

        panelSeleccion.add(cancelarSeleccion, c);

        objectContainer = new javax.swing.JPanel(false);

        objectContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

        objectContainer.setAutoscrolls(true);

        buttonPanel = new javax.swing.JPanel();

        buttonCancelar = new javax.swing.JButton();

        buttonGuardar = new javax.swing.JButton();

        buttonGuardar.setEnabled(false);



        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        buttonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonCancelar.setText("Cancelar");

        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonGuardar.setText("Guardar Objeto");

        buttonGuardar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuardarActionPerformed(evt);
            }
        });



        setLayout(new BorderLayout());

        tabPanel.addTab("Seleccion", panelSeleccion);

        tabPanel.setMnemonicAt(0, KeyEvent.VK_1);

        tabPanel.addTab("Objeto", objectContainer);

        tabPanel.setMnemonicAt(1, KeyEvent.VK_2);

        tabPanel.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(buttonGuardar);

        buttonPanel.add(buttonCancelar);



        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        getContentPane().add(tabPanel, BorderLayout.CENTER);

        setTitle("Editor de Objetos");

        setSize(500, 500);
    }

    private void initComponents2() {

        objectContainer = new javax.swing.JPanel();

        buttonPanel = new javax.swing.JPanel();

        buttonCancelar = new javax.swing.JButton();

        buttonGuardar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.HIDE_ON_CLOSE);

        buttonPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        objectContainer.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        buttonCancelar.setText("Cancelar");

        buttonCancelar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCancelarActionPerformed(evt);
            }
        });

        buttonGuardar.setText("Guardar Objeto");

        buttonGuardar.addActionListener(new java.awt.event.ActionListener() {

            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonGuardarActionPerformed(evt);
            }
        });


        setLayout(new BorderLayout());

        objectContainer.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        buttonPanel.add(buttonGuardar);

        buttonPanel.add(buttonCancelar);

        getContentPane().add(objectContainer);

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Editor de Objetos");

        setSize(500, 500);
    }

    private void llenarListaJarSeleccion(javax.swing.JList lista) {

        lista.setListData(claseJars.toArray());
    }

    private void aceptarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {
        try {

            buttonGuardar.setEnabled(true);


            Class claseAbstracta = (Class) listaSeleccionAbstracta.getSelectedValue();


            Object instance = getInstance(claseAbstracta);

            InspectObject(instance);

        } catch (InstantiationException ex) {
            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JDOMException ex) {
            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        }


        aceptarSeleccion.setEnabled(false);
        cancelarSeleccion.setEnabled(true);


    }

    private void cancelarSeleccionActionPerformed(java.awt.event.ActionEvent evt) {

        dialogoColeccion = new com.teg.util.SwingDialog();

        int opcion = dialogoColeccion.advertenciaDialog("Se perdera el objeto generado, Desea Continuar?", this);

        if (opcion == 0) {

            aceptarSeleccion.setEnabled(true);

            buttonGuardar.setEnabled(false);

            cancelarSeleccion.setEnabled(false);

            objectContainer.removeAll();


        }

    }

    private void buttonCrearOtroActionPerformed(java.awt.event.ActionEvent evt) {
        //InspectObject(instanceInspect);
    }

    private void buttonGuardarActionPerformed(java.awt.event.ActionEvent evt) {

        listWidget.setGuardado(true);

        Object instance = metawidget.getToInspect();

        System.out.println(instance.getClass().getName());

        variableInstancia = new VariableInstancia();

        variableInstancia.setInstancia(instance);


        listWidget.setVariableInstancia(variableInstancia);

        Class claseJar = this.loadClass(instance.getClass());

        this.crearXML(claseJar, instance, casoPrueba);

        this.dispose();
    }

    public void crearXML(Class claseJar, Object instance, String casoPrueba) {

        objId++;

        try {

            File casoPruebaFile = new File(System.getProperty("user.home")
                    + System.getProperty("file.separator") + casoPrueba
                    + System.getProperty("file.separator"));

            File metadata = new File(casoPruebaFile.getPath()
                    + System.getProperty("file.separator") + "metadata"
                    + System.getProperty("file.separator"));

            FileOutputStream fos = null;

            if (vieneDelAssert) {

                fos = new FileOutputStream(metadata.getPath()
                        + System.getProperty("file.separator") + "resultadoObject" + objId + ".xml");
            } else {

                fos = new FileOutputStream(metadata.getPath()
                        + System.getProperty("file.separator") + "object" + objId + ".xml");
            }

            XStream xstream = new XStream(new DomDriver());

            // xstream.alias("" + claseJar.getName() + "", instance.getClass());
            xstream.toXML(instance, fos);

        } catch (FileNotFoundException ex) {

            Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean verificarDato(Class clase) {

        boolean verificado = false;
        if (clase.getName().equals("java.lang.Integer")
                || clase.getName().equals("java.lang.Float")
                || clase.getName().equals("java.lang.Double")
                || clase.getName().equals("java.lang.Long")
                || clase.getName().equals("java.lang.Short")
                || clase.getName().equals("java.lang.Byte")
                || clase.getName().equals("java.lang.Character")
                || clase.getName().equals("java.lang.String")
                || clase.getName().equals("java.lang.Boolean")
                || clase.isPrimitive() == true) {

            verificado = true;
        }

        return verificado;
    }

    public void deepInstantiate(Object claseInstancia, org.jdom.Element raiz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        raiz.addContent("\n");

        org.jdom.Element entidad = getEntity(claseInstancia.getClass());

        raiz.addContent(entidad);

        java.util.List<Field> campos = new java.util.ArrayList<Field>();

        campos = Arrays.asList(claseInstancia.getClass().getDeclaredFields());

        for (Field field : campos) {

            boolean flag = false;

            java.util.List<Method> metodosClase = new java.util.ArrayList<Method>();

            metodosClase = Arrays.asList(claseInstancia.getClass().getDeclaredMethods());

            if (!field.getType().isPrimitive() && verificarDato(field.getType()) == false) {


                for (Method method : metodosClase) {

                    if (method.getParameterTypes().length > 0) {

                        if (method.getParameterTypes()[0].getName().equals(field.getType().getName())
                                && (method.getReturnType().getName() == null ? "void" == null : method.getReturnType().getName().equals("void"))
                                && flag == false) {

                            Object campoInstance = field.getType().newInstance();

                            claseInstancia.getClass().getMethod(method.getName(), field.getType()).invoke(claseInstancia, campoInstance);

                            flag = true;

                            deepInstantiate(campoInstance, raiz);

                        }
                    }
                }
            }
        }
    }

    public org.jdom.Element getEntity(Class clase) {

        org.jdom.Element entidad = new org.jdom.Element("entity");

        org.jdom.Attribute tipoEntidad = new org.jdom.Attribute("type", clase.getName());

        entidad.setAttribute(tipoEntidad);

        entidad.addContent("\n \t");


        java.util.List<Field> fields = new java.util.ArrayList<Field>();

        fields = getAllFields(fields, clase);


        //Field[] fields = clase.getDeclaredFields();

        for (Field field : fields) {

            org.jdom.Element prop = new org.jdom.Element("property");

            org.jdom.Attribute atr = new org.jdom.Attribute("name", field.getName());

            org.jdom.Attribute atrSeccion = new org.jdom.Attribute("section", clase.getSimpleName());

            ArrayList<org.jdom.Attribute> listaAtributosProperty = new ArrayList<org.jdom.Attribute>();

            if (!field.getType().isPrimitive() && verificarDato(field.getType()) == false) {

                org.jdom.Attribute atr2 = new org.jdom.Attribute("section", clase.getSimpleName());

                org.jdom.Attribute atr3 = new org.jdom.Attribute("type", field.getType().getName());

                listaAtributosProperty.add(atr);

                listaAtributosProperty.add(atr2);

                listaAtributosProperty.add(atr3);

                prop.setAttributes(listaAtributosProperty);

                entidad.addContent("\n \t");

            } else {

                listaAtributosProperty.add(atr);

                listaAtributosProperty.add(atrSeccion);

                prop.setAttributes(listaAtributosProperty);

                entidad.addContent("\n \t");

            }

            entidad.addContent(prop);

            entidad.addContent("\n");
        }

        return entidad;
    }

    public java.util.List<Field> getAllFields(java.util.List<Field> fields, Class<?> clase) {


        if (clase.getDeclaredFields() != null) {
            fields.addAll(Arrays.asList(clase.getDeclaredFields()));
        }


        if (clase.getSuperclass() != null) {

            fields = getAllFields(fields, clase.getSuperclass());
        }

        return fields;
    }

    public java.util.ArrayList<Method> getAllMethods(java.util.ArrayList<Method> methods, Class<?> clase) {

        methods.addAll(Arrays.asList(clase.getDeclaredMethods()));

        for (Method method : clase.getMethods()) {

            if (methods.contains(method) == false) {
                methods.add(method);
            }
        }


        return methods;
    }

    public Object getInstance(Class clase) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, JDOMException, IOException {

        org.jdom.Element raiz = new org.jdom.Element("inspection-result");

        raiz.addContent("\n");

        org.jdom.Element entidad = getEntity(clase);

        raiz.addContent(entidad);

        Object claseInstancia = clase.newInstance();

        java.util.List<Field> campos = new java.util.ArrayList<Field>();

        campos = getAllFields(campos, clase);

        for (Field field : campos) {

            boolean flag = false;

            java.util.ArrayList<Method> metodosClase = new java.util.ArrayList<Method>();

            metodosClase = getAllMethods(metodosClase, clase);

            //Method[] metodosHeredadados = clase.getMethods();

            if (!field.getType().isPrimitive() && verificarDato(field.getType()) == false) {

                for (Method method : metodosClase) {

                    if (method.getParameterTypes().length == 1
                            && method.getParameterTypes()[0].getName().equals(field.getType().getName())
                            && (method.getReturnType().getName() == null ? "void" == null : method.getReturnType().getName().equals("void"))
                            && flag == false) {

                        Object campoInstance = field.getType().newInstance();

                        clase.getMethod(method.getName(), field.getType()).invoke(claseInstancia, campoInstance);

                        flag = true;

                        deepInstantiate(campoInstance, raiz);
                    }
                }
            }
        }





        docXml = new org.jdom.Document(raiz);

        crearMetawidgetMetadata(docXml);

        return claseInstancia;

    }

    public void crearMetawidgetMetadata(org.jdom.Document docXml) throws JDOMException, IOException {

        try {

            org.jdom.output.XMLOutputter out = new org.jdom.output.XMLOutputter();

            FileOutputStream file = new FileOutputStream(inicio.getDirectorioCasoPrueba().getPath() + "/" + "metawidgetData.xml");

            out.output(docXml, file);

            file.flush();

            file.close();

            out.output(docXml, System.out);

        } catch (Exception e) {
        }
    }

    public Class loadClass(Class miClase) {

        Class claseJar = null;

        for (File jar : inicio.getJarsRuta()) {

            try {

                claseJar = classLoader.getClassDetail(jar.getPath(), miClase.getName());

            } catch (MalformedURLException ex) {

                Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);

            } catch (ClassNotFoundException ex) {

                Logger.getLogger(InstanceForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return claseJar;
    }

    private void buttonCancelarActionPerformed(java.awt.event.ActionEvent evt) {

        this.dispose();
    }

    public void Visible() {


        InspectObject(instanceInspect);

        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Instance Editor");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 588, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 385, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    /**
     * @param args the command line arguments
     */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
