/*
 * CaseTestEditor.java
 *
 * Created on Oct 28, 2010, 3:24:27 PM
 */
package com.teg.vista;

import com.teg.dominio.Argumento;

import com.teg.dominio.ArregloInstancia;

import com.teg.dominio.AssertTest;

import com.teg.dominio.CasoPrueba;

import com.teg.dominio.ColeccionInstancia;

import com.teg.dominio.EscenarioPrueba;

import com.teg.dominio.MapaInstancia;

import com.teg.dominio.Metodo;

import com.teg.dominio.VariableInstancia;
import com.teg.logica.CodeManager;

import com.teg.logica.WidgetObjectLoading;

import com.teg.logica.XmlManager;

import com.teg.util.SwingDialog;

import com.teg.vista.ayuda.AyudaArgumentos;

import com.teg.vista.ayuda.AyudaAssert;

import com.teg.vista.ayuda.AyudaMetodos;

import com.teg.vista.ayuda.AyudaVariables;
import com.teg.vista.customlist.ClassMember;
import com.teg.vista.customlist.CustomListModel;
import com.teg.vista.customlist.MethodWrapper;

import java.awt.Color;

import java.awt.Component;

import java.awt.Dimension;

import java.awt.Toolkit;

import java.awt.event.MouseEvent;

import java.awt.event.MouseListener;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileOutputStream;

import java.io.FileReader;

import java.io.IOException;

import java.lang.reflect.Field;

import java.lang.reflect.InvocationTargetException;

import java.lang.reflect.Method;

import java.lang.reflect.Modifier;

import java.lang.reflect.ParameterizedType;

import java.lang.reflect.Type;

import java.util.ArrayList;

import java.util.Collection;

import java.util.Map;

import java.util.Vector;

import java.util.logging.Level;

import java.util.logging.Logger;

import java.util.regex.Matcher;

import java.util.regex.Pattern;

import javax.swing.BorderFactory;

import javax.swing.DefaultCellEditor;

import javax.swing.JComboBox;

import org.jdom.*;

import org.jdom.output.*;

import javax.swing.JMenuItem;

import javax.swing.JTable;

import javax.swing.JTextField;

import javax.swing.border.Border;

import javax.swing.event.PopupMenuEvent;

import javax.swing.event.PopupMenuListener;

import javax.swing.table.DefaultTableModel;

import javax.swing.table.TableCellEditor;

/**
 *
 * @author maya
 */
public class CaseTestEditor extends javax.swing.JInternalFrame {

    private java.util.List<ClassMember> metodos = new java.util.ArrayList<ClassMember>();
    private ArrayList<DefaultCellEditor> editores = new ArrayList<DefaultCellEditor>();
    private ArrayList<Metodo> metodosGuardados = new ArrayList<Metodo>();
    private ArrayList<VariableInstancia> variablesGuardadas = new ArrayList<VariableInstancia>();
    private ArrayList<ColeccionInstancia> coleccionesGuardadas = new ArrayList<ColeccionInstancia>();
    private Object[] arregloGuardado;
    private ArrayList<MapaInstancia> mapasGuardados = new ArrayList<MapaInstancia>();
    private ArrayList<ArregloInstancia> arreglosGuardados = new ArrayList<ArregloInstancia>();
    private ArrayList<File> archivosJavaDoc = new ArrayList<File>();
    private ArrayList<EscenarioPrueba> escenariosPrueba = new ArrayList<EscenarioPrueba>();
    private WidgetObjectLoading listWidget;
    private static int varId = 0;
    private static int objId = 0;
    private static int coleccionId = 0;
    private static int mapaId = 0;
    private static int arregloId = 0;
    private Class tipoVarRetorno;
    private ClassMember actualNameMethod;
    private JTable tablaArgumentos;
    private Inicio inicio;
    private Document docXml;
    private Integer contObject = 1;
    private Integer contColeccion = 1;
    private Integer contArreglo = 1;
    private Integer contMapa = 1;
    private Integer contObjectAssert = 1;
    private Integer contColeccionAssert = 1;
    private Integer contArregloAssert = 1;
    private Integer contMapaAssert = 1;
    private SwingDialog dialogo = new SwingDialog();

    /** Creates new form CaseTestEditor */
    @SuppressWarnings("LeakingThisInConstructor")
    public CaseTestEditor(java.util.List<ClassMember> metodos, Inicio inicio) {

        initComponents();

        this.metodos = metodos;

        listaMetodos.setModel(new CustomListModel<ClassMember>());

        this.inicio = inicio;

        this.inicio.getSeleccionarJar().setEnabled(false);

        this.inicio.getAnadirJarAlClasspath().setEnabled(false);

        archivosJavaDoc = inicio.getArchivosJavaDoc();

        this.myInits();

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

    public void cargarMetodos() {

        CustomListModel<ClassMember> modeloMetodos =
                ((CustomListModel<ClassMember>) listaMetodos.getModel());

        for (ClassMember clase : metodos) {

            modeloMetodos.addItem(clase);
        }

        listaMetodos.setModel(modeloMetodos.actualizar());
    }

    public void crearMetawidgetMetadata(Document docXml) throws JDOMException, IOException {

        try {

            XMLOutputter out = new XMLOutputter();

            FileOutputStream file = new FileOutputStream(inicio.getDirectorioCasoPrueba().getPath() + "/" + "metawidgetData.xml");

            out.output(docXml, file);

            file.flush();

            file.close();

            out.output(docXml, System.out);

        } catch (Exception e) {
        }
    }

    public Method getMethodSelected(ClassMember clase) {

        CustomListModel<ClassMember> modeloMetodos =
                ((CustomListModel<ClassMember>) listaMetodos.getModel());

        ClassMember metodoBuscado;

        return null;
    }

    public void cargarAssert(Class metodoRetorno) {

        assertVariables.removeAllItems();

        int countVar = varId;

        countVar++;

        assertVariables.setSelectedItem("Seleccione el campo a evaluar");

        if (metodoRetorno.isPrimitive()) {

            assertVariables.addItem("resultado" + countVar);

        } else {

            assertVariables.addItem("resultado" + countVar);

            for (Method method : metodoRetorno.getDeclaredMethods()) {
                if (Modifier.isPublic(method.getModifiers()) && !method.getReturnType().getName().equals("void")
                        && method.getParameterTypes().length == 0) {
                    assertVariables.addItem("resultado" + countVar + "."
                            + method.getName() + "()");
                }
            }
        }

        assertVariables.repaint();

    }

    public Method getMetodoGuardado(Metodo metodoBuscar) {

        Method metodoEncontrado = null;

        for (ClassMember metodo : metodos) {



            if (metodo.getClaseDeOrigen().getName().equals(metodoBuscar.getClaseOrigen())) {

                if (metodo.getMetodo().getName().equals(metodoBuscar.getNombre())) {

                    ArrayList<Argumento> listaArgs = metodoBuscar.getArgumentos();

                    MethodWrapper metodoWrap = new MethodWrapper(metodo.getMetodo());

                    ArrayList<String> argNombre = new ArrayList<String>();

                    for (Class clase : metodoWrap.getArgumentos()) {

                        argNombre.add(clase.getName());

                    }

                    if (listaArgs.size() == argNombre.size()) {

                        if (sonArgumentosIguales(metodoBuscar, argNombre)) {
                            metodoEncontrado = metodo.getMetodo();
                        }
                    }

                }
            }
        }

        return metodoEncontrado;
    }

    public Boolean sonArgumentosIguales(Metodo metodo1, ArrayList<String> metodo2) {

        Boolean iguales = Boolean.FALSE;
        ArrayList<Argumento> args1 = metodo1.getArgumentos();


        if ((metodo1.getArgumentos().isEmpty()) && (metodo2.isEmpty())) {
            iguales = Boolean.TRUE;
        } else if (args1.size() == metodo2.size()) {

            for (int i = 0; i < args1.size(); i++) {
                if (args1.get(i).getClaseOrigen().equals(metodo2.get(i))) {
                    iguales = Boolean.TRUE;
                } else {
                    iguales = Boolean.FALSE;
                    break;
                }
            }
        }
        return iguales;
    }

    public void cargarComboItemsComplex(JComboBox combo, Class argument) {

        Method method;

        for (Metodo metodo : metodosGuardados) {

            method = getMetodoGuardado(metodo);

            if (!method.getReturnType().getName().equals("void")) {
                Class retorno = method.getReturnType();

                if (retorno.getName().equals(argument.getName()) || argument.isAssignableFrom(retorno) == true) {

                    combo.addItem(metodo.getRetorno().getNombreVariable());

                } else {

                    if (retorno.getDeclaredFields().length != 0) {

                        for (Field field : retorno.getDeclaredFields()) {

                            if (field.getType().getName().equals(argument.getName())) {

                                combo.addItem(metodo.getRetorno().getNombreVariable()
                                        + "." + field.getName());
                            }
                        }
                    }
                }
            }
        }

        for (VariableInstancia variable : variablesGuardadas) {

            Class variableClase = variable.getInstancia().getClass();


            if (variableClase.getName().equals(argument.getName())
                    || variableClase.getSuperclass().getName().equals(argument.getName())) {

                combo.addItem(variable.getNombreVariable());

            } else {

                if (variableClase.getDeclaredFields().length != 0) {

                    for (Field field : variableClase.getDeclaredFields()) {

                        if (field.getType().getName().equals(argument.getName())) {

                            combo.addItem(variable.getNombreVariable()
                                    + "." + field.getName());
                        }
                    }
                }
            }
        }

        java.util.List<Class> clasesGenericas = new java.util.ArrayList<Class>();

        clasesGenericas = obtenerGenericos(argument);

        if (!coleccionesGuardadas.isEmpty()) {

            for (ColeccionInstancia coleccionInstancia : coleccionesGuardadas) {

                if (coleccionInstancia.getColeccionInstancia().getClass().getName().equals(argument.getName())
                        && coleccionInstancia.getTipoDatoColeccion().equals(clasesGenericas.get(0).getSimpleName())) {

                    combo.addItem(coleccionInstancia.getNombreColeccion());
                }

            }

        }

        if (!mapasGuardados.isEmpty()) {

            for (MapaInstancia mapaInstancia : mapasGuardados) {

                if (mapaInstancia.getMapa().getClass().getName().equals(argument.getName())
                        && mapaInstancia.getClaseKey().equals(clasesGenericas.get(0))
                        && mapaInstancia.getClaseValue().equals(clasesGenericas.get(1))) {


                    combo.addItem(mapaInstancia.getNombreMapa());
                }
            }

        }


        if (!arreglosGuardados.isEmpty()) {

            for (ArregloInstancia arregloInstancia : arreglosGuardados) {

                if (argument.isArray()
                        && argument.getComponentType().getName().equals(arregloInstancia.getClaseComponente())) {

                    combo.addItem(arregloInstancia.getNombreArreglo());
                }
            }

        }

    }

    public void deshabilitarMetodos() {

        for (Component component : panelTablaArgumentos.getComponents()) {

            component.setEnabled(false);

        }

        assertVariables.setEnabled(false);

        assertCondiciones.setEnabled(false);

        resultadoAssert.setEnabled(false);

        assertMensaje.setEnabled(false);

        guardarBt.setEnabled(false);
    }

    public void deshabilitarMetodosData() {

        for (Component component : panelTablaArgumentos.getComponents()) {

            component.setEnabled(false);
        }

        assertVariables.setEnabled(false);

        assertVariables.removeAllItems();

        assertCondiciones.setEnabled(false);

        resultadoAssert.setEnabled(false);

        assertMensaje.setEnabled(false);

        guardarBt.setEnabled(false);
    }

    public void deshabilitarAssert() {

        panelAssert.setEnabled(false);

        assertVariables.setEnabled(false);
        assertCondiciones.setEnabled(false);
        assertMensaje.setEnabled(false);
        assertMensaje.setEditable(false);
        resultadoAssert.setEnabled(false);
        resultadoAssert.setEditable(false);
        tipoDeDatoResultado.setEnabled(false);

        lbResultadoAssert.setEnabled(false);
        lbAssertCondiciones.setEnabled(false);
        lbAssertMensaje.setEnabled(false);
        lbAssertVariables.setEnabled(false);
        lbAssertVariables1.setEnabled(false);
    }

    public void habilitarAssert() {

        panelAssert.setEnabled(true);

        assertVariables.setEnabled(true);
        assertCondiciones.setEnabled(true);
        assertMensaje.setEnabled(true);
        assertMensaje.setEditable(true);
        resultadoAssert.setEnabled(true);
        resultadoAssert.setEditable(true);
        tipoDeDatoResultado.setEnabled(true);

        lbResultadoAssert.setEnabled(true);
        lbAssertCondiciones.setEnabled(true);
        lbAssertMensaje.setEnabled(true);
        lbAssertVariables.setEnabled(true);
        lbAssertVariables1.setEnabled(true);
    }

    /**
     * 
     * @param nombreMetodo
     */
    public void habilitarMetodosData(ClassMember metodoSeleccionado) {

        for (Component component : panelTablaArgumentos.getComponents()) {

            component.setEnabled(true);

        }

        Method metodo = metodoSeleccionado.getMetodo();

        if (!metodo.getReturnType().getName().equals("void")) {

            assertVariables.setEnabled(true);

            assertCondiciones.setEnabled(true);

            resultadoAssert.setEnabled(true);

            assertMensaje.setEnabled(true);

            guardarBt.setEnabled(true);

        } else {

            assertCondiciones.setEnabled(false);

            resultadoAssert.setEnabled(false);

            assertMensaje.setEnabled(false);

            guardarBt.setEnabled(true);


        }

    }

    public void cargarComboItemsPrimitive(JComboBox combo, Class parameter) {

        Method method;


        for (Metodo metodo : metodosGuardados) {

            method = getMetodoGuardado(metodo);

            Class retorno = method.getReturnType();

            if (retorno.getName().equals(parameter.getName())) {

                combo.addItem(metodo.getRetorno().getNombreVariable());

                for (Field field : retorno.getDeclaredFields()) {

                    if (field.getType().getName().equals(parameter.getName())) {

                        combo.addItem(metodo.getRetorno().getNombreVariable() + "." + field.getName());
                    }
                }

            } else {

                for (Field field : retorno.getDeclaredFields()) {

                    if (field.getType().getName().equals(parameter.getName())) {

                        combo.addItem(metodo.getRetorno().getNombreVariable() + "." + field.getName());
                    }
                }

            }
        }

        for (VariableInstancia variable : variablesGuardadas) {

            Class variableClase = variable.getInstancia().getClass();

            if (variableClase.getDeclaredFields().length != 0) {

                for (Field field : variableClase.getDeclaredFields()) {

                    if (field.getType().getName().equals(parameter.getName())) {

                        combo.addItem(variable.getNombreVariable()
                                + "." + field.getName());
                    }
                }
            }
        }
    }

    public void deepInstantiate(Object claseInstancia, Element raiz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {

        raiz.addContent("\n");

        Element entidad = getEntity(claseInstancia.getClass());

        raiz.addContent(entidad);

        Field[] campos = claseInstancia.getClass().getDeclaredFields();

        for (Field field : campos) {

            boolean flag = false;

            if (!field.getType().isPrimitive() && verificarDato(field.getType()) == false) {

                Method[] metodosClase = claseInstancia.getClass().getDeclaredMethods();

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

    public Element getEntity(Class clase) {

        Element entidad = new Element("entity");

        Attribute tipoEntidad = new Attribute("type", clase.getName());

        entidad.setAttribute(tipoEntidad);

        entidad.addContent("\n \t");

        java.util.List<Field> fields = new java.util.ArrayList<Field>();

        fields = getAllFields(fields, clase);

        for (Field field : fields) {

            Element prop = new Element("property");

            Attribute atr = new Attribute("name", field.getName());

            Attribute atrSeccion = new Attribute("section", clase.getSimpleName());

            ArrayList<Attribute> listaAtributosProperty = new ArrayList<Attribute>();

            if (!field.getType().isPrimitive() && verificarDato(field.getType()) == false) {

                Attribute atr2 = new Attribute("section", clase.getSimpleName());

                Attribute atr3 = new Attribute("type", field.getType().getName());

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

            for (Field field : clase.getDeclaredFields()) {
                if (!java.lang.reflect.Modifier.isFinal(field.getModifiers())) {

                    fields.add(field);

                }
            }
        }


        if (clase.getSuperclass() != null) {

            fields = getAllFields(fields, clase.getSuperclass());
        }

        return fields;
    }

    public java.util.ArrayList<Method> getAllMethods(java.util.ArrayList<Method> methods, Class<?> clase) {

        methods.addAll(java.util.Arrays.asList(clase.getDeclaredMethods()));

        for (Method method : clase.getMethods()) {

            if (methods.contains(method) == false) {
                methods.add(method);
            }
        }


        return methods;
    }

    public Object getInstance(Class clase) throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException, JDOMException, IOException {

        Element raiz = new Element("inspection-result");

        raiz.addContent("\n");

        Element entidad = getEntity(clase);

        raiz.addContent(entidad);

        Object claseInstancia = clase.newInstance();

        java.util.List<Field> campos = new java.util.ArrayList<Field>();

        campos = getAllFields(campos, clase);

        for (Field field : campos) {

            boolean flag = false;

            java.util.ArrayList<Method> metodosClase = new java.util.ArrayList<Method>();

            metodosClase = getAllMethods(metodosClase, clase);

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

        docXml = new Document(raiz);

        crearMetawidgetMetadata(docXml);

        return claseInstancia;

    }

    public void addInstanceVariable() {
        if (listWidget.getVariableInstancia() != null) {

            objId++;

            DefaultTableModel model = new DefaultTableModel();

            model = (DefaultTableModel) tablaVariables.getModel();

            Method method = actualNameMethod.getMetodo();


            listWidget.getVariableInstancia().setNombreVariable("objeto" + objId);



            tablaArgumentos.setValueAt(listWidget.getVariableInstancia().getInstancia().getClass().getName(),
                    tablaArgumentos.getSelectedRow(), 0);


            variablesGuardadas.add(listWidget.getVariableInstancia());

            Vector objects = new Vector();

            objects.add("objeto" + objId);


            objects.add(method.getName());

            objects.add(listWidget.getVariableInstancia().getInstancia().getClass().getName());

            model.addRow(objects);


        }

    }

    private void addInstanceArreglo() {
        if (listWidget.getArregloInstancia() != null) {

            arregloId++;

            DefaultTableModel model = new DefaultTableModel();

            model = (DefaultTableModel) tablaVariables.getModel();

            tablaArgumentos.setValueAt(listWidget.getArregloInstancia().getClaseComponente() + "[]",
                    tablaArgumentos.getSelectedRow(), 0);


            listWidget.getArregloInstancia().setNombreArreglo("arreglo" + arregloId);

            arreglosGuardados.add(listWidget.getArregloInstancia());

            Vector arreglos = new Vector();

            arreglos.add("arreglo" + arregloId);

            arreglos.add(this.actualNameMethod.getMetodo().getName());

            arreglos.add(listWidget.getArregloInstancia().getClaseComponente());

            model.addRow(arreglos);


        }


    }

    private void addInstanceMap() {

        if (listWidget.getMapaInstancia() != null) {

            mapaId++;

            DefaultTableModel model = new DefaultTableModel();

            model = (DefaultTableModel) tablaVariables.getModel();

            tablaArgumentos.setValueAt(listWidget.getMapaInstancia().getMapa().getClass().getName(), tablaArgumentos.getSelectedRow(), 0);


            listWidget.getMapaInstancia().setNombreMapa("mapa" + mapaId);

            mapasGuardados.add(listWidget.getMapaInstancia());

            Vector mapas = new Vector();

            mapas.add("mapa" + mapaId);

            mapas.add(actualNameMethod.getMetodo().getName());

            mapas.add("<" + listWidget.getMapaInstancia().getClaseKey().getName()
                    + "," + listWidget.getMapaInstancia().getClaseValue().getName() + ">");

            model.addRow(mapas);


        }
    }

    public void addInstanceCollection() {
        if (listWidget.getColeccionInstancia() != null) {

            coleccionId++;

            DefaultTableModel model = new DefaultTableModel();

            model = (DefaultTableModel) tablaVariables.getModel();

            tablaArgumentos.setValueAt(listWidget.getColeccionInstancia().getColeccionInstancia().getClass().getName(), tablaArgumentos.getSelectedRow(), 0);

            listWidget.getColeccionInstancia().setNombreColeccion("coleccion" + coleccionId);

            coleccionesGuardadas.add(listWidget.getColeccionInstancia());

            Vector colecciones = new Vector();

            colecciones.add("coleccion" + coleccionId);

            colecciones.add(this.actualNameMethod.getMetodo().getName());

            colecciones.add(listWidget.getColeccionInstancia().getTipoDatoColeccion());

            colecciones.add("Collection");

            model.addRow(colecciones);

        }
    }

    private boolean superInterface(Class clase, boolean esColeccion) {

        Class[] interfaces = clase.getInterfaces();



        for (Class class1 : interfaces) {
            if (class1.getName().equals("java.util.Map")
                    || class1.getName().equals("java.util.Set")
                    || class1.getName().equals("java.util.List")
                    || class1.getName().equals("java.util.Queue")) {

                esColeccion = true;
            } else {

                esColeccion = superInterface(class1, esColeccion);
            }


        }

        return esColeccion;

    }

    private boolean argumentoEsColeccion(Class clase) {

        boolean esColeccion = false;

        boolean flagList = java.util.List.class.isAssignableFrom(clase);

        boolean flagCollection = java.util.Collection.class.isAssignableFrom(clase);

        boolean flagSet = java.util.Set.class.isAssignableFrom(clase);

        boolean flagQueue = java.util.Queue.class.isAssignableFrom(clase);

        boolean flagMap = java.util.Map.class.isAssignableFrom(clase);

        if (flagList == true || flagSet == true || flagQueue == true || flagCollection == true || flagMap == true) {
            esColeccion = true;
        }



        return esColeccion;
    }

    private ArrayList<Class> getColeccionesSegunInterfaz(Class interfaz) {

        ArrayList<Class> clasesInterfaz = new ArrayList<Class>();

        for (Class class1 : getClasesExtendCollection()) {
            if (interfaz.isAssignableFrom(class1)) {
                clasesInterfaz.add(class1);
            }

        }

        return clasesInterfaz;

    }

    private ArrayList<Class> getMapasSegunInterfaz(Class interfaz) {

        ArrayList<Class> clasesInterfaz = new ArrayList<Class>();

        for (Class class1 : getClasesExtendMapa()) {

            if (interfaz.isAssignableFrom(class1) == true) {
                clasesInterfaz.add(class1);
            }
        }

        return clasesInterfaz;
    }

    private ArrayList<Class> getClasesExtendMapa() {

        ArrayList<Class> clases = new ArrayList<Class>();

        clases.add(java.util.HashMap.class);

        clases.add(java.util.jar.Attributes.class);

        clases.add(java.security.AuthProvider.class);

        clases.add(java.util.concurrent.ConcurrentHashMap.class);

        clases.add(java.util.concurrent.ConcurrentSkipListMap.class);

        clases.add(java.util.EnumMap.class);

        clases.add(java.util.HashMap.class);

        clases.add(java.util.Hashtable.class);

        clases.add(java.util.IdentityHashMap.class);

        clases.add(java.util.LinkedHashMap.class);

        clases.add(java.util.Properties.class);

        clases.add(java.util.TreeMap.class);

        clases.add(javax.swing.UIDefaults.class);

        clases.add(java.util.WeakHashMap.class);

        return clases;
    }

    private ArrayList<Class> getClasesExtendCollection() {

        ArrayList<Class> clases = new ArrayList<Class>();

        clases.add(java.util.ArrayDeque.class);

        clases.add(java.util.ArrayList.class);

        clases.add(javax.management.AttributeList.class);

        clases.add(java.util.concurrent.ConcurrentLinkedQueue.class);

        clases.add(java.util.concurrent.ConcurrentSkipListSet.class);

        clases.add(java.util.concurrent.CopyOnWriteArrayList.class);

        clases.add(java.util.concurrent.CopyOnWriteArraySet.class);

        clases.add(java.util.concurrent.DelayQueue.class);

        clases.add(java.util.EnumSet.class);

        clases.add(java.util.HashSet.class);

        clases.add(java.util.concurrent.LinkedBlockingDeque.class);

        clases.add(java.util.concurrent.LinkedBlockingQueue.class);

        clases.add(java.util.LinkedHashSet.class);

        clases.add(java.util.LinkedList.class);

        clases.add(java.util.concurrent.PriorityBlockingQueue.class);

        clases.add(java.util.PriorityQueue.class);

        clases.add(javax.management.relation.RoleList.class);

        clases.add(javax.management.relation.RoleUnresolvedList.class);

        clases.add(java.util.Stack.class);

        clases.add(java.util.concurrent.SynchronousQueue.class);

        clases.add(java.util.TreeSet.class);

        clases.add(java.util.Vector.class);

        return clases;
    }

    private ArrayList<Class> getClasesColeccion(Class interfaz) {

        ArrayList<Class> clasesObtener = new ArrayList<Class>();

        boolean flagMapa = java.util.Map.class.isAssignableFrom(interfaz);

        boolean flagColeccion = java.util.Collection.class.isAssignableFrom(interfaz);

        if (flagMapa == true) {
            if (interfaz.getName().equals("java.util.Map")) {

                clasesObtener = getClasesExtendMapa();


            } else {
                clasesObtener = getMapasSegunInterfaz(interfaz);
            }


        } else {
            if (flagColeccion == true) {
                if (interfaz.getName().equals("java.util.Collection")) {
                    clasesObtener = getClasesExtendCollection();
                } else {
                    clasesObtener = getColeccionesSegunInterfaz(interfaz);
                }
            }
        }




        return clasesObtener;
    }

    private boolean superInterfaceMap(Class clase, boolean esMapa) {

        Class[] interfaces = clase.getInterfaces();



        for (Class class1 : interfaces) {
            if (class1.getName().equals("java.util.Map")) {

                esMapa = true;
            } else {

                esMapa = superInterfaceMap(class1, esMapa);
            }


        }

        return esMapa;

    }

    private boolean argumentoEsMapa(Class clase) {


        boolean flagMapa = java.util.Map.class.isAssignableFrom(clase);


        return flagMapa;

    }

    public boolean argumentoEsArreglo(Class clase) {
        boolean esArreglo = false;

        if (clase.isArray()) {
            esArreglo = true;
        }
        return esArreglo;
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
                || clase.getName().equals("java.lang.Boolean")) {

            verificado = true;
        }

        return verificado;
    }

    private boolean interfazExiste(Class[] interfaces, Class interfaz) {

        boolean interfazEncontrada = false;
        for (Class class1 : interfaces) {

            if (interfaz.isAssignableFrom(class1) == true) {

                interfazEncontrada = true;
            }

        }

        return interfazEncontrada;

    }

    private ArrayList<Class> obtenerClasesJars() {

        ArrayList<Class> clasesJar = inicio.obtenerClasesJars();
        return clasesJar;
    }

    private ArrayList<Class> obtenerClasesDeClaseAbstracta(Class claseAbstracta) {

        ArrayList<Class> clasesJars = inicio.obtenerClasesJars();

        ArrayList<Class> clasesImplAbstracta = new ArrayList<Class>();

        for (Class class1 : clasesJars) {

            Class superClass = class1.getSuperclass();

            if (superClass.toString().equals(claseAbstracta.toString()) == true) {
                clasesImplAbstracta.add(class1);

            }
        }

        return clasesImplAbstracta;

    }

    private ArrayList<Class> obtenerClasesDeInterfaz(Class interfaz) {

        ArrayList<Class> clasesJar = inicio.obtenerClasesJars();

        ArrayList<Class> clasesInterfaz = new ArrayList<Class>();

        for (Class class1 : clasesJar) {



            if (interfaz.isAssignableFrom(class1) == true) {

                clasesInterfaz.add(class1);

            }
        }



        return clasesInterfaz;
    }

    private ArrayList<Class> obtenerGenericos(Method metodo, int pos) {

        ArrayList<Class> clasesInstances = new ArrayList<Class>();

        Type[] genericParameterTypes = metodo.getGenericParameterTypes();

        Type genericParameterType = genericParameterTypes[pos];

        if (genericParameterType instanceof ParameterizedType) {

            ParameterizedType aType = (ParameterizedType) genericParameterType;

            Type[] parameterArgTypes = aType.getActualTypeArguments();

            for (Type parameterArgType : parameterArgTypes) {

                Class parameterArgClass = (Class) parameterArgType;

                clasesInstances.add(parameterArgClass);

            }
        }
        return clasesInstances;
    }

    private ArrayList<Class> obtenerGenericos(Class clase) {

        ArrayList<Class> clasesInstances = new ArrayList<Class>();

        Type genericParameterType = clase;

        if (genericParameterType instanceof ParameterizedType) {

            ParameterizedType aType = (ParameterizedType) genericParameterType;

            Type[] parameterArgTypes = aType.getActualTypeArguments();

            for (Type parameterArgType : parameterArgTypes) {

                Class parameterArgClass = (Class) parameterArgType;

                clasesInstances.add(parameterArgClass);

            }

        }

        return clasesInstances;


    }

    @SuppressWarnings("empty-statement")
    public void cargarTablaArgumentos(ClassMember metodoSeleccionado) {

        for (Component component : panelTablaArgumentos.getComponents()) {

            if (!component.getClass().getName().equals("javax.swing.JLabel")) {

                panelTablaArgumentos.remove(component);

            }
        }

        editores.clear();

        Method metodo = metodoSeleccionado.getMetodo();

        this.labelObjetoRetorno.setText(metodo.getReturnType().getName());

        final Class[] parameterTypes = metodo.getParameterTypes();

        for (int i = 0; i < parameterTypes.length; i++) {

            final int pos = i;

            final Class argument = parameterTypes[i];

            if (!argument.isPrimitive() && verificarDato(argument) == false) {

                final JComboBox combo = new JComboBox();

                cargarComboItemsComplex(combo, argument);

                combo.addPopupMenuListener(new PopupMenuListener() {

                    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    }

                    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

                        String item = "";

                        JComboBox cb = new JComboBox();

                        cb = (JComboBox) e.getSource();

                        item = cb.getSelectedItem().toString();

                        String[] cadena = item.split(":");

                        if (cadena[0].equals("Crear")) {

                            boolean esColeccion = false;

                            listWidget = new WidgetObjectLoading();

                            boolean esArreglo = false;

                            boolean esMapa = false;

                            listWidget.setGuardado(false);

                            Method metodo = actualNameMethod.getMetodo();

                            ArrayList<Class> classInstances;

                            ArrayList<Object> objectInstances;

                            if (argument.isInterface()) {
                                if (argumentoEsColeccion(argument) == true && argumentoEsMapa(argument) == false) {

                                    InstanceListForm editorInstance = new InstanceListForm(inicio, true,
                                            getClasesColeccion(argument), obtenerGenericos(metodo, pos), obtenerClasesJars(),
                                            inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, coleccionId, false);

                                    editorInstance.setVisible(true);

                                    addInstanceCollection();

                                } else {

                                    if (argumentoEsMapa(argument) == true) {

                                        InstanceMapForm editorInstance = new InstanceMapForm(inicio, true, getClasesColeccion(argument),
                                                obtenerGenericos(metodo, pos), obtenerClasesJars(),
                                                inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, mapaId, false);

                                        editorInstance.setVisible(true);

                                        addInstanceMap();
                                    } else {

                                        InstanceForm editorInstance = new InstanceForm(inicio, true,
                                                obtenerClasesDeInterfaz(argument), inicio.getDirectorioCasoPrueba().getPath(),
                                                listWidget, inicio, objId, false);

                                        editorInstance.setVisible(true);

                                        addInstanceVariable();
                                    }
                                }
                            } else {
                                if (Modifier.isAbstract(argument.getModifiers()) == true) {

                                    InstanceForm editorInstance = new InstanceForm(inicio, true,
                                            obtenerClasesDeClaseAbstracta(argument),
                                            inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, objId, false);

                                    editorInstance.setVisible(true);


                                    addInstanceVariable();

                                } else {

                                    if (argumentoEsColeccion(argument) == true) {

                                        classInstances = obtenerGenericos(metodo, pos);

                                        objectInstances = new ArrayList<Object>();


                                        if (classInstances.size() == 2) {

                                            try {
                                                InstanceMapForm editorMap = new InstanceMapForm(inicio, true, classInstances, inicio.getDirectorioCasoPrueba().getPath(), listWidget, argument, mapaId, false);

                                                editorMap.setVisible(true);

                                                addInstanceMap();

                                            } catch (InstantiationException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                                            }



                                        } else {

                                            if (classInstances.size() == 1) {


                                                if (!classInstances.get(0).isPrimitive()
                                                        && verificarDato(classInstances.get(0)) == false) {
                                                    try {

                                                        Object claseInstance = getInstance(classInstances.get(0));


                                                        if (listWidget.getColeccionInstancia() != null) {


                                                            listWidget.setColeccionInstancia(null);
                                                        } else {

                                                            if (listWidget.getMapaInstancia() != null) {

                                                                listWidget.setMapaInstancia(null);
                                                            }
                                                        }

                                                        InstanceListForm editorList = new InstanceListForm(inicio, true, claseInstance, inicio.getDirectorioCasoPrueba().getPath(), listWidget, argument, inicio, coleccionId, false);

                                                        editorList.Visible();


                                                        addInstanceCollection();

                                                    } catch (InstantiationException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (IllegalAccessException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (NoSuchMethodException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (IllegalArgumentException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (InvocationTargetException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (JDOMException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (IOException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                                                    }

                                                } else {
                                                    if (classInstances.get(0).isPrimitive()
                                                            || verificarDato(classInstances.get(0)) == true) {

                                                        InstanceListForm editorList = new InstanceListForm(inicio, true, classInstances.get(0), listWidget, argument, coleccionId, false);

                                                        editorList.setVisible(true);

                                                        addInstanceCollection();


                                                    }
                                                }
                                            } else {

                                                if (classInstances.isEmpty()) {

                                                    if (java.util.Map.class.isAssignableFrom(argument) == false) {

                                                        InstanceListForm selector = new InstanceListForm(inicio, true, obtenerClasesJars(), inicio.getDirectorioCasoPrueba().getPath(), listWidget, argument, inicio, coleccionId, false);

                                                        selector.setVisible(true);

                                                        addInstanceCollection();

                                                    } else {
                                                        if (argumentoEsMapa(argument) == true) {

                                                            InstanceMapForm selector = new InstanceMapForm(inicio, true, obtenerClasesJars(), listWidget, argument, inicio, mapaId, false);

                                                            selector.setVisible(true);

                                                            addInstanceMap();

                                                        }
                                                    }




                                                }
                                            }

                                        }


                                    } else {

                                        if (argumentoEsArreglo(argument) == true) {

                                            Class arrayComponente = argument.getComponentType();

                                            if (arrayComponente.isPrimitive()
                                                    || verificarDato(arrayComponente) == true) {
                                                InstanceArrayForm editorArray = new InstanceArrayForm(inicio, true, arrayComponente, inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, arregloId, false);

                                                editorArray.setVisible(true);

                                                addInstanceArreglo();

                                            } else {
                                                if (!arrayComponente.isPrimitive()
                                                        && verificarDato(arrayComponente) == false) {

                                                    try {

                                                        Object object = getInstance(arrayComponente);

                                                        InstanceArrayForm editorArray = new InstanceArrayForm(inicio, true, object, inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, arregloId, false);

                                                        editorArray.VisibleObject();

                                                        addInstanceArreglo();


                                                    } catch (InstantiationException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (IllegalAccessException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (NoSuchMethodException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (IllegalArgumentException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (InvocationTargetException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (JDOMException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                                    } catch (IOException ex) {

                                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                                                    }
                                                }
                                            }


                                        } else {

                                            try {

                                                if (listWidget.getVariableInstancia() != null) {
                                                    listWidget.setVariableInstancia(null);
                                                }

                                                Object clase = getInstance(argument);

                                                InstanceForm editorInstance = new InstanceForm(inicio, true, clase, inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, objId, false);

                                                editorInstance.Visible();

                                                addInstanceVariable();


                                            } catch (JDOMException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                            } catch (IOException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                            } catch (InstantiationException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                            } catch (IllegalAccessException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                            } catch (NoSuchMethodException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                            } catch (IllegalArgumentException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                            } catch (InvocationTargetException ex) {

                                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                                            }
                                        }

                                    }
                                }
                            }
                        }
                    }

                    public void popupMenuCanceled(PopupMenuEvent e) {
                    }
                });

                combo.setEditable(true);

                combo.addItem("Crear: " + parameterTypes[i].getName());

                DefaultCellEditor editor = new DefaultCellEditor(combo);

                editores.add(editor);

            } else {

                if (argument.isPrimitive() || verificarDato(argument) == true) {

                    JComboBox combo = new JComboBox();

                    cargarComboItemsPrimitive(combo, argument);

                    combo.setEditable(true);

                    DefaultCellEditor editor = new DefaultCellEditor(combo);

                    editores.add(editor);
                }
            }
        }

        tipoVarRetorno = metodo.getReturnType();

        cargarAssert(tipoVarRetorno);

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Argumento", "Valor"}, parameterTypes.length);

        tablaArgumentos = new JTable(model) {

            @Override
            public TableCellEditor getCellEditor(int row, int column) {

                int modelColumn = super.convertColumnIndexToModel(column);

                if (modelColumn == 1 && row < parameterTypes.length) {

                    return editores.get(row);

                } else {

                    return super.getCellEditor(row, column);
                }

            }
        };

        panelTablaArgumentos.add(tablaArgumentos);

        Border line = BorderFactory.createLineBorder(Color.black);

        tablaArgumentos.setBorder(line);

        tablaArgumentos.setSelectionMode(0);

        tablaArgumentos.setGridColor(Color.black);

        tablaArgumentos.setSize(new Dimension(450, 120));

        tablaArgumentos.setRowHeight(25);

        tablaArgumentos.setLocation(20, 40);



        for (int i = 0; i < parameterTypes.length; i++) {

            tablaArgumentos.setValueAt(parameterTypes[i].getName(), i, 0);
        }

        panelTablaArgumentos.repaint();

    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        popMenuMetodos = new javax.swing.JPopupMenu();
        panelInicial = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaVariables = new javax.swing.JTable();
        ayudaVariable = new javax.swing.JLabel();
        panelMetodoInfo = new javax.swing.JPanel();
        panelTablaArgumentos = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        labelObjetoRetorno = new javax.swing.JLabel();
        ayudaArgumento = new javax.swing.JLabel();
        panelAssert = new javax.swing.JPanel();
        lbAssertVariables = new javax.swing.JLabel();
        assertVariables = new javax.swing.JComboBox();
        lbAssertCondiciones = new javax.swing.JLabel();
        assertCondiciones = new javax.swing.JComboBox();
        lbResultadoAssert = new javax.swing.JLabel();
        resultadoAssert = new javax.swing.JTextField();
        assertMensaje = new javax.swing.JTextField();
        lbAssertMensaje = new javax.swing.JLabel();
        ayudaAssert = new javax.swing.JLabel();
        lbAssertVariables1 = new javax.swing.JLabel();
        tipoDeDatoResultado = new javax.swing.JLabel();
        guardarBt = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaMetodos = new javax.swing.JList();
        ayudaMetodo = new javax.swing.JLabel();
        newTestEscenario = new javax.swing.JButton();
        siguiente = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaMetodosRegistrados = new javax.swing.JTable();
        nombreEscenario = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        volver = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();

        setBorder(null);
        setTitle("Case Test Editor");

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Historial de Variables", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        tablaVariables.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Variable", "Metodo", "Tipo Dato"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaVariables.setIntercellSpacing(new java.awt.Dimension(2, 2));
        tablaVariables.setShowGrid(false);
        tablaVariables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablaVariablesMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tablaVariables);

        ayudaVariable.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/question.png"))); // NOI18N
        ayudaVariable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ayudaVariable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ayudaVariableMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                    .addComponent(ayudaVariable))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ayudaVariable)
                .addContainerGap())
        );

        panelMetodoInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        panelTablaArgumentos.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Argumentos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        jLabel1.setText("Argumento");

        jLabel2.setText("Valor");

        jLabel6.setFont(new java.awt.Font("Lucida Grande", 1, 13));
        jLabel6.setText("Objeto Retorno:");

        labelObjetoRetorno.setText("Object");

        ayudaArgumento.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/question.png"))); // NOI18N
        ayudaArgumento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ayudaArgumento.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ayudaArgumentoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout panelTablaArgumentosLayout = new javax.swing.GroupLayout(panelTablaArgumentos);
        panelTablaArgumentos.setLayout(panelTablaArgumentosLayout);
        panelTablaArgumentosLayout.setHorizontalGroup(
            panelTablaArgumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelTablaArgumentosLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(jLabel1)
                .addGap(108, 108, 108)
                .addComponent(jLabel2)
                .addGap(192, 192, 192))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTablaArgumentosLayout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelObjetoRetorno, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(ayudaArgumento)
                .addContainerGap())
        );
        panelTablaArgumentosLayout.setVerticalGroup(
            panelTablaArgumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelTablaArgumentosLayout.createSequentialGroup()
                .addGroup(panelTablaArgumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(panelTablaArgumentosLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(ayudaArgumento))
                    .addGroup(panelTablaArgumentosLayout.createSequentialGroup()
                        .addGroup(panelTablaArgumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                        .addGroup(panelTablaArgumentosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(labelObjetoRetorno))))
                .addContainerGap())
        );

        panelTablaArgumentosLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ayudaArgumento, jLabel6, labelObjetoRetorno});

        panelAssert.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Assert", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        lbAssertVariables.setText("Resultado Método: ");

        assertVariables.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                assertVariablesMouseClicked(evt);
            }
        });
        assertVariables.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                assertVariablesPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        assertVariables.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                assertVariablesItemStateChanged(evt);
            }
        });
        assertVariables.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assertVariablesActionPerformed(evt);
            }
        });

        lbAssertCondiciones.setText("Condición: ");

        assertCondiciones.setFont(new java.awt.Font("Calibri", 1, 12));
        assertCondiciones.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Seleccione una opcion...", "Igual", "No Igual", "Nulo", "No Nulo", "Verdadero", "Falso", "" }));
        assertCondiciones.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
                assertCondicionesPopupMenuWillBecomeInvisible(evt);
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
            }
        });
        assertCondiciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assertCondicionesActionPerformed(evt);
            }
        });

        lbResultadoAssert.setText("Resultado: ");
        lbResultadoAssert.setEnabled(false);

        resultadoAssert.setEnabled(false);
        resultadoAssert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                resultadoAssertMouseClicked(evt);
            }
        });
        resultadoAssert.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultadoAssertActionPerformed(evt);
            }
        });

        assertMensaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assertMensajeActionPerformed(evt);
            }
        });

        lbAssertMensaje.setText("Mensaje :");

        ayudaAssert.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/question.png"))); // NOI18N
        ayudaAssert.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ayudaAssert.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ayudaAssertMouseClicked(evt);
            }
        });

        lbAssertVariables1.setText("Tipo de Dato Resultado:");

        tipoDeDatoResultado.setText("Object");

        javax.swing.GroupLayout panelAssertLayout = new javax.swing.GroupLayout(panelAssert);
        panelAssert.setLayout(panelAssertLayout);
        panelAssertLayout.setHorizontalGroup(
            panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAssertLayout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelAssertLayout.createSequentialGroup()
                        .addComponent(lbAssertMensaje)
                        .addGap(18, 18, 18)
                        .addComponent(assertMensaje, javax.swing.GroupLayout.DEFAULT_SIZE, 241, Short.MAX_VALUE))
                    .addGroup(panelAssertLayout.createSequentialGroup()
                        .addComponent(lbAssertVariables, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(assertVariables, javax.swing.GroupLayout.PREFERRED_SIZE, 239, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelAssertLayout.createSequentialGroup()
                        .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lbResultadoAssert, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbAssertCondiciones, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbAssertVariables1, javax.swing.GroupLayout.Alignment.LEADING))
                        .addGap(18, 18, 18)
                        .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resultadoAssert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(assertCondiciones, 0, 241, Short.MAX_VALUE)
                            .addGroup(panelAssertLayout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(tipoDeDatoResultado, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(79, 79, 79))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelAssertLayout.createSequentialGroup()
                .addContainerGap(476, Short.MAX_VALUE)
                .addComponent(ayudaAssert)
                .addContainerGap())
        );

        panelAssertLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {assertCondiciones, assertMensaje, assertVariables, resultadoAssert});

        panelAssertLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {lbAssertCondiciones, lbAssertMensaje, lbAssertVariables, lbAssertVariables1, lbResultadoAssert});

        panelAssertLayout.setVerticalGroup(
            panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelAssertLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAssertVariables, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assertVariables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAssertVariables1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(tipoDeDatoResultado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAssertCondiciones, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assertCondiciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbResultadoAssert, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(resultadoAssert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelAssertLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbAssertMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assertMensaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(ayudaAssert)
                .addContainerGap())
        );

        panelAssertLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {assertCondiciones, assertMensaje, assertVariables, lbAssertCondiciones, lbAssertMensaje, lbAssertVariables, lbAssertVariables1, lbResultadoAssert, resultadoAssert, tipoDeDatoResultado});

        guardarBt.setText("Nuevo Metodo");
        guardarBt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                guardarBtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelMetodoInfoLayout = new javax.swing.GroupLayout(panelMetodoInfo);
        panelMetodoInfo.setLayout(panelMetodoInfoLayout);
        panelMetodoInfoLayout.setHorizontalGroup(
            panelMetodoInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMetodoInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelMetodoInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelTablaArgumentos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelMetodoInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(guardarBt)
                        .addComponent(panelAssert, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        panelMetodoInfoLayout.setVerticalGroup(
            panelMetodoInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelMetodoInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelTablaArgumentos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(panelAssert, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(guardarBt)
                .addContainerGap())
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Metodos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        listaMetodos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaMetodosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaMetodos);

        ayudaMetodo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/teg/recursos/imagenes/question.png"))); // NOI18N
        ayudaMetodo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        ayudaMetodo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ayudaMetodoMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                    .addComponent(ayudaMetodo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ayudaMetodo)
                .addGap(118, 118, 118))
        );

        newTestEscenario.setText("Nuevo Escenario");
        newTestEscenario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newTestEscenarioActionPerformed(evt);
            }
        });

        siguiente.setText("Siguiente");
        siguiente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siguienteActionPerformed(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "Escenario de Prueba", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Calibri", 1, 12))); // NOI18N

        tablaMetodosRegistrados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Orden", "Metodo"
            }
        ));
        jScrollPane3.setViewportView(tablaMetodosRegistrados);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(8, 8, 8)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        jLabel5.setText("Nombre Escenario de Prueba :");

        volver.setText("Volver");
        volver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                volverActionPerformed(evt);
            }
        });

        jButton1.setText("Revisar Escenarios");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelInicialLayout = new javax.swing.GroupLayout(panelInicial);
        panelInicial.setLayout(panelInicialLayout);
        panelInicialLayout.setHorizontalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelInicialLayout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInicialLayout.createSequentialGroup()
                        .addComponent(volver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 472, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(newTestEscenario, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(siguiente))
                    .addGroup(panelInicialLayout.createSequentialGroup()
                        .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panelInicialLayout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nombreEscenario))
                            .addComponent(panelMetodoInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 581, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        panelInicialLayout.setVerticalGroup(
            panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelInicialLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelInicialLayout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(panelInicialLayout.createSequentialGroup()
                        .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(nombreEscenario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(panelMetodoInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelInicialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(volver)
                    .addComponent(newTestEscenario)
                    .addComponent(jButton1)
                    .addComponent(siguiente))
                .addGap(75, 75, 75))
        );

        panelInicialLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jButton1, newTestEscenario, siguiente, volver});

        panelInicialLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel5, nombreEscenario});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelInicial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void assertCondicionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assertCondicionesActionPerformed
}//GEN-LAST:event_assertCondicionesActionPerformed

    private void guardarBtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_guardarBtActionPerformed

        varId++;

        Metodo metodoActual = this.agregarMetodo(actualNameMethod, varId,
                this.getActualAssert(actualNameMethod.getMetodo()),
                this.getArgumentos(actualNameMethod.getMetodo()));

        tablaVariables.removeAll();

        tablaMetodosRegistrados.removeAll();

        DefaultTableModel modelMetodos = new DefaultTableModel();

        modelMetodos = (DefaultTableModel) tablaMetodosRegistrados.getModel();

        DefaultTableModel model = new DefaultTableModel();

        model = (DefaultTableModel) tablaVariables.getModel();

        Vector objects = new Vector();

        objects.add(metodoActual.getRetorno().getNombreVariable());

        objects.add(metodoActual.getNombre());

        objects.add(metodoActual.getRetorno().getRetorno());

        model.addRow(objects);

        Vector metodosObj = new Vector();

        metodosObj.add(varId);

        metodosObj.add(metodoActual.getNombre());

        modelMetodos.addRow(metodosObj);


    }//GEN-LAST:event_guardarBtActionPerformed

    private void assertVariablesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assertVariablesActionPerformed
    }//GEN-LAST:event_assertVariablesActionPerformed

    private void assertCondicionesPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_assertCondicionesPopupMenuWillBecomeInvisible

        if (assertCondiciones.getSelectedItem().equals("Igual")
                || assertCondiciones.getSelectedItem().equals("No Igual")) {

            lbResultadoAssert.setEnabled(true);

            resultadoAssert.setEnabled(true);

        } else {

            if (assertCondiciones.getSelectedItem().equals("Elige una opcion")) {

                lbResultadoAssert.setEnabled(false);

                resultadoAssert.setEnabled(false);

            } else {

                lbResultadoAssert.setEnabled(false);

                resultadoAssert.setEnabled(false);
            }
        }
    }//GEN-LAST:event_assertCondicionesPopupMenuWillBecomeInvisible

    private void assertMensajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assertMensajeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assertMensajeActionPerformed

    private void listaMetodosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaMetodosMouseClicked

        popMenuMetodos.setVisible(false);

        popMenuMetodos.removeAll();

        ClassMember metodoProcesar = (ClassMember) listaMetodos.getSelectedValue();

        if (evt.getButton() == MouseEvent.BUTTON3) {

            JMenuItem item = new JMenuItem();

            item.setText("Ver Javadoc");

            item.addMouseListener(new MouseListener() {

                public void mouseClicked(MouseEvent me) {

                    popMenuMetodos.setVisible(false);

                    ClassMember metodoSeleccionado = (ClassMember) listaMetodos.getSelectedValue();

                    Method metodoActual = metodoSeleccionado.getMetodo();

                    JavadocFrame javadoc = new JavadocFrame(archivosJavaDoc, metodoActual);

                    javadoc.setVisible(true);
                }

                public void mousePressed(MouseEvent me) {
                }

                public void mouseReleased(MouseEvent me) {
                }

                public void mouseEntered(MouseEvent me) {
                }

                public void mouseExited(MouseEvent me) {
                }
            });

            popMenuMetodos.add(item);

            popMenuMetodos.setLocation(evt.getLocationOnScreen());

            popMenuMetodos.setVisible(true);

        } else {

            if (evt.getButton() == MouseEvent.BUTTON1) {

                boolean isIn = false;

                Method metodoActual = metodoProcesar.getMetodo();

                if (metodoActual.getReturnType().getName().equals("void")) {
                    this.deshabilitarAssert();
                } else {
                    this.habilitarAssert();
                }

                for (int i = 0; i < metodosGuardados.size(); i++) {

                    if (metodosGuardados.get(i).getNombre().equals(metodoProcesar.getMetodo().getName())) {

                        isIn = true;

                    }
                }

                if (isIn == true) {

                    cargarTablaArgumentos(metodoProcesar);

                    //deshabilitarMetodosData();

                } else {

                    cargarTablaArgumentos(metodoProcesar);

                    //habilitarMetodosData(metodoProcesar);//ver orden

                    actualNameMethod = metodoProcesar;
                }
            }

            if (assertVariables.getSelectedItem() != null) {
                Method method = actualNameMethod.getMetodo();
                Class retorno = method.getReturnType();
                String opcionAssert[] = assertVariables.getSelectedItem().toString().split("\\.");
                Class assertion = getClaseEvaluar(retorno, opcionAssert);

                this.tipoDeDatoResultado.setText(assertion.getName());
            }

        }
    }//GEN-LAST:event_listaMetodosMouseClicked

    private void newTestEscenarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newTestEscenarioActionPerformed

        String escenarioNombre = this.nombreEscenario.getText();

        if (escenarioNombre.isEmpty()) {
            dialogo.errorDialog("El campo Nombre Escenario de Prueba es obligatorio", inicio);
        } else {

            String escenario = this.cadenaSinBlancos(escenarioNombre);

            EscenarioPrueba escenarioPrueba = new EscenarioPrueba(escenario);

            escenarioPrueba.setMetodos(metodosGuardados);

            escenariosPrueba.add(escenarioPrueba);


            //varId = 0;

            metodosGuardados = new ArrayList<Metodo>();



            nombreEscenario.setText("");

            //DefaultTableModel model = (DefaultTableModel) tablaVariables.getModel();

            //model.setNumRows(0);

            DefaultTableModel model2 = (DefaultTableModel) tablaMetodosRegistrados.getModel();

            model2.setNumRows(0);

            assertVariables.removeAllItems();

            assertMensaje.setText("");

            resultadoAssert.setText("");

            for (Component component : panelTablaArgumentos.getComponents()) {

                if (!component.getClass().getName().equals("javax.swing.JLabel")) {

                    panelTablaArgumentos.remove(component);
                }

                this.repaint();
            }
        }

    }//GEN-LAST:event_newTestEscenarioActionPerformed

    private void volverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_volverActionPerformed

        varId = 0;

        metodosGuardados = new ArrayList<Metodo>();

        variablesGuardadas.clear();

        nombreEscenario.setText("");


        ArrayList<Class> clases = this.inicio.getClasesManager();


        this.inicio.caseTestToMethods(this, clases);

        //this.dispose();
    }//GEN-LAST:event_volverActionPerformed

    private void tablaVariablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablaVariablesMouseClicked
    }//GEN-LAST:event_tablaVariablesMouseClicked

    private void siguienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siguienteActionPerformed

        ArrayList<Class> clasesCasoPrueba = this.getClasesCasoPrueba(metodos);
        ArrayList<Method> metodosSet = this.getMetodosSet(clasesCasoPrueba);

        if (this.hasMetodosSet(metodosSet)) {
            CasoPrueba casoPrueba = this.crearCasoPrueba(this.inicio.getNombreCasoPrueba(), escenariosPrueba);
            this.reiniciarContadores();
            this.inicio.caseTestToDependenciesSelection(this, metodosSet, casoPrueba, metodos);
        } else {
            XmlManager xmlManager = new XmlManager();
            xmlManager.setInicio(inicio);
            xmlManager.crearCasoPrueba(this.inicio.getNombreCasoPrueba(), escenariosPrueba);
        }

    }//GEN-LAST:event_siguienteActionPerformed

    public void reiniciarContadores() {
        varId = 0;
        objId = 0;
        coleccionId = 0;
        mapaId = 0;
        arregloId = 0;
        contObject = 1;
        contColeccion = 1;
        contArreglo = 1;
        contMapa = 1;
        contObjectAssert = 1;
        contColeccionAssert = 1;
        contArregloAssert = 1;
        variablesGuardadas.clear();
        arreglosGuardados.clear();
        mapasGuardados.clear();
        coleccionesGuardadas.clear();
        metodos.clear();
    }

    /**
     * Metodo para verificar la existencia de una clase
     * @param clase la clase a comprobar
     * @return true existe, false no existe
     */
    public Boolean existeClase(String clase, ArrayList<Class> clases) {

        Boolean flag = Boolean.FALSE;

        for (Class clazz : clases) {

            if (clase.equals(clazz.getName())) {
                flag = Boolean.TRUE;
                break;
            } else {
                flag = Boolean.FALSE;
            }
        }
        return flag;
    }

    public ArrayList<Class> getClasesCasoPrueba(java.util.List<ClassMember> metodosCasoPrueba) {

        ArrayList<Class> clases = new ArrayList<Class>();

        ArrayList<Method> metodoCaso = new ArrayList<Method>();

        for (ClassMember claseMember : metodosCasoPrueba) {
            metodoCaso.add(claseMember.getMetodo());
        }

        for (Method method : metodoCaso) {

            if (clases.isEmpty()) {
                clases.add(method.getDeclaringClass());
            } else {
                if (!existeClase(method.getDeclaringClass().getName(), clases)) {
                    clases.add(method.getDeclaringClass());
                }
            }
        }

        return clases;
    }

    public ArrayList<Method> getMetodosSet(ArrayList<Class> clasesCasoPrueba) {
        ArrayList<Method> metodosSet = new ArrayList<Method>();

        for (Class clazz : clasesCasoPrueba) {
            Method[] methods = clazz.getDeclaredMethods();

            for (Method method : methods) {
                if (method.getName().startsWith("set") == true && method.getParameterTypes().length == 1
                        && method.getReturnType().getName().equals("void")) {
                    metodosSet.add(method);
                }
            }
        }

        return metodosSet;
    }

    private boolean hasMetodosSet(ArrayList<Method> metodosSet) {

        boolean hasMetodosSet;

        if (metodosSet.isEmpty()) {
            hasMetodosSet = false;
        } else {
            hasMetodosSet = true;
        }

        return hasMetodosSet;
    }

    private Class getClaseEvaluar(Class retorno, String opcion[]) {

        Class claseEvaluar = null;

        if (opcion.length == 1) {

            claseEvaluar = retorno;
        } else {
            if (opcion.length == 2) {

                String metodoNombreArreglo = opcion[1];

                String metodoNombre = metodoNombreArreglo.replace("()", "");

                Method metodoAEvaluar = null;

                for (Method method : retorno.getDeclaredMethods()) {
                    if (method.getName().equals(metodoNombre)) {

                        metodoAEvaluar = method;

                    }
                }

                claseEvaluar = metodoAEvaluar.getReturnType();
            }
        }

        return claseEvaluar;
    }

    private void resultadoAssertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_resultadoAssertMouseClicked

        Method method = actualNameMethod.getMetodo();
        Class retorno = method.getReturnType();
        String opcionAssert[] = assertVariables.getSelectedItem().toString().split("\\.");
        Class assertion = getClaseEvaluar(retorno, opcionAssert);

        if (!assertion.isPrimitive() && verificarDato(assertion) == false) {

            ArrayList<Class> classInstances;
            ArrayList<Object> objectInstances;
            listWidget = new WidgetObjectLoading();

            if (assertion.isInterface()) {
                if (argumentoEsColeccion(assertion) == true && argumentoEsMapa(assertion) == false) {

                    InstanceListForm editorInstance = new InstanceListForm(inicio, true, getClasesColeccion(assertion), obtenerGenericos(assertion), obtenerClasesJars(), inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, coleccionId, true);
                    editorInstance.setVisible(true);

                } else {

                    if (argumentoEsMapa(assertion) == true) {

                        InstanceMapForm editorInstance = new InstanceMapForm(inicio, true, getClasesColeccion(assertion), obtenerGenericos(assertion), obtenerClasesJars(), inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, mapaId, true);
                        editorInstance.setVisible(true);

                    } else {

                        InstanceForm editorInstance = new InstanceForm(inicio, true, obtenerClasesDeInterfaz(assertion), inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, objId, true);
                        editorInstance.setVisible(true);

                    }
                }
            } else {
                if (Modifier.isAbstract(assertion.getModifiers()) == true) {

                    InstanceForm editorInstance = new InstanceForm(inicio, true, obtenerClasesDeClaseAbstracta(assertion), inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, objId, true);

                    editorInstance.setVisible(true);

                } else {

                    if (argumentoEsColeccion(assertion) == true) {

                        classInstances = obtenerGenericos(assertion);
                        objectInstances = new ArrayList<Object>();

                        if (classInstances.size() == 2) {

                            try {
                                InstanceMapForm editorMap = new InstanceMapForm(inicio, true, classInstances, inicio.getDirectorioCasoPrueba().getPath(), listWidget, assertion, mapaId, true);

                                editorMap.setVisible(true);

                            } catch (InstantiationException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                            }

                        } else {

                            if (classInstances.size() == 1) {

                                if (!classInstances.get(0).isPrimitive()
                                        && verificarDato(classInstances.get(0)) == false) {
                                    try {

                                        Object claseInstance = getInstance(classInstances.get(0));

                                        if (listWidget.getColeccionInstancia() != null) {

                                            listWidget.setColeccionInstancia(null);
                                        } else {

                                            if (listWidget.getMapaInstancia() != null) {

                                                listWidget.setMapaInstancia(null);
                                            }
                                        }

                                        InstanceListForm editorList = new InstanceListForm(inicio, true, claseInstance, inicio.getDirectorioCasoPrueba().getPath(), listWidget, assertion, inicio, coleccionId, true);

                                        editorList.Visible();

                                    } catch (InstantiationException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (IllegalAccessException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (NoSuchMethodException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (IllegalArgumentException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (InvocationTargetException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (JDOMException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (IOException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                                    }

                                } else {
                                    if (classInstances.get(0).isPrimitive()
                                            || verificarDato(classInstances.get(0)) == true) {

                                        InstanceListForm editorList = new InstanceListForm(inicio, true, classInstances.get(0), listWidget, assertion, coleccionId, true);

                                        editorList.setVisible(true);
                                    }
                                }
                            } else {

                                if (classInstances.isEmpty()) {

                                    if (java.util.Map.class.isAssignableFrom(assertion) == false) {

                                        InstanceListForm selector = new InstanceListForm(inicio, true, obtenerClasesJars(), inicio.getDirectorioCasoPrueba().getPath(), listWidget, assertion, inicio, coleccionId, true);

                                        selector.setVisible(true);

                                    } else {
                                        if (argumentoEsMapa(assertion) == true) {
                                            InstanceMapForm selector = new InstanceMapForm(inicio, true, obtenerClasesJars(), listWidget, assertion, inicio, mapaId, true);

                                            selector.setVisible(true);
                                        }
                                    }
                                }
                            }
                        }

                    } else {

                        if (argumentoEsArreglo(assertion) == true) {

                            Class arrayComponente = assertion.getComponentType();

                            if (arrayComponente.isPrimitive()
                                    || verificarDato(arrayComponente) == true) {
                                InstanceArrayForm editorArray = new InstanceArrayForm(inicio, true, arrayComponente, inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, arregloId, true);

                                editorArray.setVisible(true);

                            } else {
                                if (!arrayComponente.isPrimitive()
                                        && verificarDato(arrayComponente) == false) {
                                    try {
                                        Object object = getInstance(arrayComponente);

                                        InstanceArrayForm editorArray = new InstanceArrayForm(inicio, true, object, inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, arregloId, true);

                                    } catch (InstantiationException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (IllegalAccessException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (NoSuchMethodException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (IllegalArgumentException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (InvocationTargetException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (JDOMException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                                    } catch (IOException ex) {

                                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                                    }
                                }
                            }

                        } else {

                            try {

                                if (listWidget.getVariableInstancia() != null) {
                                    listWidget.setVariableInstancia(null);
                                }

                                Object claseInstance = getInstance(assertion);

                                InstanceForm editorInstance = new InstanceForm(inicio, true, claseInstance, inicio.getDirectorioCasoPrueba().getPath(), listWidget, inicio, objId, true);

                                editorInstance.Visible();

                            } catch (JDOMException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                            } catch (IOException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                            } catch (InstantiationException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                            } catch (IllegalAccessException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                            } catch (NoSuchMethodException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                            } catch (IllegalArgumentException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);

                            } catch (InvocationTargetException ex) {

                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }

                    }
                }
            }

        } else {
        }

        String tipoDeDato = tipoDeDatoResultado.getText();
        CodeManager codeManager = new CodeManager();
        boolean esEnvolvente = codeManager.esClaseEnvolvente(tipoDeDato);

        if (!tipoDeDato.equals("java.lang.String") && !tipoDeDato.equals("char") && !esEnvolvente) {
            resultadoAssert.setText("Objeto resultado creado");
        }

    }//GEN-LAST:event_resultadoAssertMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        EscenariosVista vistaEscenarios = new EscenariosVista(inicio, true, escenariosPrueba);
        vistaEscenarios.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ayudaArgumentoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayudaArgumentoMouseClicked

        AyudaArgumentos ayudaArgumentos = new AyudaArgumentos(inicio, true);
        ayudaArgumentos.pack();
        ayudaArgumentos.setLocationRelativeTo(null);
        ayudaArgumentos.setVisible(true);

    }//GEN-LAST:event_ayudaArgumentoMouseClicked

    private void ayudaAssertMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayudaAssertMouseClicked

        AyudaAssert ayudaAssertGui = new AyudaAssert(inicio, true);
        ayudaAssertGui.pack();
        ayudaAssertGui.setLocationRelativeTo(null);
        ayudaAssertGui.setVisible(true);

    }//GEN-LAST:event_ayudaAssertMouseClicked

    private void ayudaMetodoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayudaMetodoMouseClicked

        AyudaMetodos ayudaMetodos = new AyudaMetodos(inicio, true);
        ayudaMetodos.pack();
        ayudaMetodos.setLocationRelativeTo(null);
        ayudaMetodos.setVisible(true);

    }//GEN-LAST:event_ayudaMetodoMouseClicked

    private void ayudaVariableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ayudaVariableMouseClicked

        AyudaVariables ayudaVariables = new AyudaVariables(inicio, true);
        ayudaVariables.pack();
        ayudaVariables.setLocationRelativeTo(null);
        ayudaVariables.setVisible(true);

    }//GEN-LAST:event_ayudaVariableMouseClicked

    private void resultadoAssertActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultadoAssertActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resultadoAssertActionPerformed

    private void assertVariablesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assertVariablesMouseClicked
    }//GEN-LAST:event_assertVariablesMouseClicked

    private void assertVariablesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_assertVariablesItemStateChanged
    }//GEN-LAST:event_assertVariablesItemStateChanged

    private void assertVariablesPopupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_assertVariablesPopupMenuWillBecomeInvisible

        Method method = actualNameMethod.getMetodo();
        Class retorno = method.getReturnType();
        String opcionAssert[] = assertVariables.getSelectedItem().toString().split("\\.");
        Class assertion = getClaseEvaluar(retorno, opcionAssert);

        this.tipoDeDatoResultado.setText(assertion.getName());

    }//GEN-LAST:event_assertVariablesPopupMenuWillBecomeInvisible
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox assertCondiciones;
    private javax.swing.JTextField assertMensaje;
    private javax.swing.JComboBox assertVariables;
    private javax.swing.JLabel ayudaArgumento;
    private javax.swing.JLabel ayudaAssert;
    private javax.swing.JLabel ayudaMetodo;
    private javax.swing.JLabel ayudaVariable;
    private javax.swing.JButton guardarBt;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelObjetoRetorno;
    private javax.swing.JLabel lbAssertCondiciones;
    private javax.swing.JLabel lbAssertMensaje;
    private javax.swing.JLabel lbAssertVariables;
    private javax.swing.JLabel lbAssertVariables1;
    private javax.swing.JLabel lbResultadoAssert;
    private javax.swing.JList listaMetodos;
    private javax.swing.JButton newTestEscenario;
    private javax.swing.JTextField nombreEscenario;
    private javax.swing.JPanel panelAssert;
    private javax.swing.JPanel panelInicial;
    private javax.swing.JPanel panelMetodoInfo;
    private javax.swing.JPanel panelTablaArgumentos;
    private javax.swing.JPopupMenu popMenuMetodos;
    private javax.swing.JTextField resultadoAssert;
    private javax.swing.JButton siguiente;
    private javax.swing.JTable tablaMetodosRegistrados;
    private javax.swing.JTable tablaVariables;
    private javax.swing.JLabel tipoDeDatoResultado;
    private javax.swing.JButton volver;
    // End of variables declaration//GEN-END:variables

    public String cadenaSinBlancos(String sTexto) {
        String sCadenaSinBlancos = "";

        for (int x = 0; x < sTexto.length(); x++) {
            if (sTexto.charAt(x) != ' ') {
                sCadenaSinBlancos += sTexto.charAt(x);
            }
        }
        return sCadenaSinBlancos;
    }

    public ArrayList<File> getJarsRuta() {
        return this.inicio.getJarsRuta();
    }

    /**
     * FALTA LIMPIAR ASSERT VARIABLE EN ESTE METODO
     */
    public void disableAssert() {
        this.lbAssertVariables.setEnabled(false);
        this.assertVariables.setEnabled(false);

        this.lbAssertCondiciones.setEnabled(false);
        this.assertCondiciones.setEnabled(false);

        this.lbResultadoAssert.setEnabled(false);
        this.resultadoAssert.setEnabled(false);

        this.lbAssertMensaje.setEnabled(false);
        this.assertMensaje.setEnabled(false);

        this.limpiarFormulario();
    }

    public void limpiarFormulario() {

        assertMensaje.setText("");
        resultadoAssert.setText("");
        assertCondiciones.setSelectedItem("Seleccione una opcion...");
        this.repaint();
    }

    public String setAssertCondition(String assertCondition) {
        String condicion = "";

        if (assertCondition.equals("Igual")) {
            condicion = "assertEquals";
        }

        if (assertCondition.equals("No Igual")) {
            condicion = "assertNotSame";
        }

        if (assertCondition.equals("Nulo")) {
            condicion = "assertNull";
        }

        if (assertCondition.equals("No Nulo")) {
            condicion = "assertNotNull";
        }

        if (assertCondition.equals("Verdadero")) {
            condicion = "assertTrue";
        }

        if (assertCondition.equals("Falso")) {
            condicion = "assertFalse";
        }

        return condicion;
    }

    public AssertTest getActualAssert(Method method) {

        Integer contSimple = 1;
        AssertTest assertion = null;
        String tipoDeDatoAssert = tipoDeDatoResultado.getText();
        String valorComplejo = "";

        Class retorno = method.getReturnType();
        String opcionAssert[] = assertVariables.getSelectedItem().toString().split("\\.");
        Class claseAssert = getClaseEvaluar(retorno, opcionAssert);

        if (!method.getReturnType().getName().equals("void")) {
            if (this.assertVariables.getSelectedItem() != null) {
                assertion = new AssertTest(assertMensaje.getText(),
                        assertVariables.getSelectedItem().toString(),
                        setAssertCondition(assertCondiciones.getSelectedItem().toString()));

                if (assertCondiciones.getSelectedItem().toString().equals("Igual")
                        || assertCondiciones.getSelectedItem().equals("No Igual")) {

                    assertion.setTipoDatoAssert(tipoDeDatoAssert);

                    if (tipoDeDatoAssert.equals("java.lang.String")) {

                        assertion.setValorAssert("\"" + resultadoAssert.getText() + "\"");

                    } else if (tipoDeDatoAssert.equals("char")) {

                        assertion.setValorAssert("\'" + resultadoAssert.getText() + "\'");

                    } else if (!claseAssert.isPrimitive()) {
                        String[] arregloCampos = claseAssert.getName().split("\\.");
                        String primerCampo = arregloCampos[0];

                        if (primerCampo.equals("java")) {
                            boolean isCollection;
                            boolean isMap;
                            try {
                                Class myClass = Class.forName(claseAssert.getName());
                                isCollection = Collection.class.isAssignableFrom(myClass);
                                isMap = Map.class.isAssignableFrom(myClass);

                                if (isCollection) {
                                    assertion.setValorAssert("resultadoColeccion" + contColeccionAssert);
                                    assertion.setComplejo(true);
                                    assertion.setArreglo(false);
                                    assertion.setMapa(false);
                                    assertion.setGenerarXstream(true);

                                } else if (isMap) {
                                    assertion.setValorAssert("resultadoMapa" + contMapaAssert);
                                    assertion.setComplejo(true);
                                    assertion.setArreglo(false);
                                    assertion.setMapa(true);
                                    assertion.setGenerarXstream(true);

                                } else {
                                    assertion.setValorAssert(resultadoAssert.getText());
                                    assertion.setComplejo(false);
                                    assertion.setMapa(false);
                                    assertion.setGenerarXstream(false);
                                }
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            if (claseAssert.isArray()) {
                                valorComplejo = "resultadoArreglo" + contArregloAssert;
                                assertion.setValorAssert(valorComplejo);
                                assertion.setComplejo(true);
                                assertion.setGenerarXstream(true);

                            } else {
                                valorComplejo = "resultadoObjeto" + contObjectAssert;
                                assertion.setValorAssert(valorComplejo);
                                assertion.setComplejo(true);
                                assertion.setArreglo(false);
                                assertion.setMapa(false);
                                assertion.setGenerarXstream(true);
                            }
                        }
                    } else {
                        assertion.setValorAssert(resultadoAssert.getText());
                        assertion.setComplejo(false);
                        assertion.setGenerarXstream(false);
                    }

                    if (resultadoAssert.getText().startsWith("resultado")) {
                        assertion.setValorAssert(resultadoAssert.getText());
                        assertion.setGenerarXstream(false);
                    }

                    contSimple++;
                    contObjectAssert++;
                    contColeccionAssert++;
                    contMapaAssert++;
                    contArregloAssert++;
                }
            }
        }

        return assertion;
    }

    public Metodo agregarMetodo(ClassMember method, Integer cont, AssertTest condicionAssert, ArrayList<Argumento> argumentos) {

        Metodo metodo = null;

        XmlManager xmlManager = new XmlManager();

        metodo = xmlManager.agregarMetodoALista(metodosGuardados, method, cont, argumentos, condicionAssert);



        return metodo;


    }

    public String eliminaCaracteres(String s_cadena, String s_caracteres) {
        String nueva_cadena = "";
        Character caracter = null;


        boolean valido = true;

        /* Va recorriendo la cadena s_cadena y copia a la cadena que va a regresar,
        sólo los caracteres que no estén en la cadena s_caracteres */


        for (int i = 0; i
                < s_cadena.length(); i++) {
            valido = true;


            for (int j = 0; j
                    < s_caracteres.length(); j++) {
                caracter = s_caracteres.charAt(j);



                if (s_cadena.charAt(i) == caracter) {
                    valido = false;


                    break;


                }
            }
            if (valido) {
                nueva_cadena += s_cadena.charAt(i);


            }
        }

        return nueva_cadena;


    }

    public String classNameArray(String tipo) {
        String nuevaCadena = this.eliminaCaracteres(tipo.substring(2), ";");


        return nuevaCadena;


    }

    public ArrayList<Argumento> getArgumentos(Method method) {

        Integer contSimple = 1;
        Integer contFila = 0;

        String valorComplejo = "";
        ArrayList<Argumento> argumentos = new ArrayList<Argumento>();
        Class[] parametros = method.getParameterTypes();

        for (Class clazz : parametros) {

            Argumento argumento = new Argumento();
            argumento.setNombre("arg" + contSimple);
            argumento.setClaseOrigen(clazz.getName());

            if (!tablaArgumentos.getValueAt(contSimple - 1, 1).toString().startsWith("Crear:")) {
                String valor = tablaArgumentos.getValueAt(contSimple - 1, 1).toString();
                if (valor.startsWith("objeto")) {
                    for (VariableInstancia var : variablesGuardadas) {
                        if (var.getNombreVariable().equals(valor)) {
                            argumento.setTipo(var.getInstancia().getClass().getName());
                            argumento.setArreglo(false);
                        }
                    }
                } else if (valor.startsWith("coleccion")) {
                    for (ColeccionInstancia var : coleccionesGuardadas) {
                        if (var.getColeccionInstancia().getClass().getName().equals(valor)) {
                            argumento.setTipo(var.getColeccionInstancia().getClass().getName());
                            argumento.setArreglo(false);
                        }
                    }
                } else if (valor.startsWith("mapa")) {
                    for (MapaInstancia var : mapasGuardados) {
                        if (var.getMapa().getClass().getName().equals(valor)) {
                            argumento.setTipo(var.getMapa().getClass().getName());
                            argumento.setArreglo(false);
                        }
                    }
                } else if (valor.startsWith("arreglo")) {
                    for (ArregloInstancia var : arreglosGuardados) {
                        if (var.getArreglo().getClass().getName().equals(valor)) {
                            String clase = this.classNameArray(var.getArreglo().getClass().getName());
                            argumento.setTipo(clase + "[]");
                            argumento.setArreglo(true);
                        }
                    }
                } else {
                    String clase = tablaArgumentos.getValueAt(contFila, 0).toString();
                    argumento.setTipo(clase);
                    argumento.setArreglo(false);
                }
            } else if (clazz.isArray()) {
                String clase = this.classNameArray(tablaArgumentos.getValueAt(contFila, 0).toString());
                argumento.setTipo(clase + "[]");
                argumento.setArreglo(true);

            } else {
                String clase = tablaArgumentos.getValueAt(contFila, 0).toString();
                argumento.setTipo(clase);
                argumento.setArreglo(false);
            }

            if (clazz.getSimpleName().equals("String")) {
                argumento.setValor("\"" + tablaArgumentos.getValueAt(contSimple - 1, 1).toString() + "\"");
                argumento.setComplejo(false);



            } else if (clazz.getSimpleName().equals("char")) {
                argumento.setValor("\'" + tablaArgumentos.getValueAt(contSimple - 1, 1).toString() + "\'");
                argumento.setComplejo(false);



            } else if (!clazz.isPrimitive()) {
                String[] arregloCampos = argumento.getTipo().split("\\.");
                String primerCampo = arregloCampos[0];

                if (primerCampo.equals("java")) {

                    boolean isCollection;
                    boolean isMap;

                    try {
                        Class myClass = Class.forName(argumento.getTipo());
                        isCollection = Collection.class.isAssignableFrom(myClass);
                        isMap = Map.class.isAssignableFrom(myClass);

                        if (isCollection) {
                            argumento.setValor("coleccion" + contColeccion);
                            argumento.setComplejo(true);
                            argumento.setArreglo(false);
                            argumento.setMapa(false);
                            argumento.setGenerarXstream(true);
                            contColeccion++;

                        } else if (isMap) {
                            argumento.setValor("mapa" + contMapa);
                            argumento.setComplejo(true);
                            argumento.setArreglo(false);
                            argumento.setMapa(true);
                            argumento.setGenerarXstream(true);
                            contMapa++;

                        } else {
                            argumento.setValor(tablaArgumentos.getValueAt(contSimple - 1, 1).toString());
                            argumento.setComplejo(false);
                            argumento.setGenerarXstream(false);
                        }
                    } catch (ClassNotFoundException ex) {
                        Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }

                } else {

                    if (clazz.isArray()) {
                        valorComplejo = "arreglo" + contArreglo;
                        argumento.setValor(valorComplejo);
                        argumento.setComplejo(true);
                        argumento.setGenerarXstream(true);
                        contArreglo++;

                    } else if (tablaArgumentos.getValueAt(contSimple - 1, 1).toString().startsWith("objeto")) {
                        String valor = tablaArgumentos.getValueAt(contSimple - 1, 1).toString();
                        valorComplejo = valor;
                        argumento.setValor(valorComplejo);
                        argumento.setComplejo(false);
                        argumento.setArreglo(false);
                        argumento.setMapa(false);
                        argumento.setGenerarXstream(false);
                    } else {
                        valorComplejo = "objeto" + contObject;
                        argumento.setValor(valorComplejo);
                        argumento.setComplejo(true);
                        argumento.setArreglo(false);
                        argumento.setMapa(false);
                        argumento.setGenerarXstream(true);
                        contObject++;

                    }
                }
            } else {
                argumento.setValor(tablaArgumentos.getValueAt(contSimple - 1, 1).toString());
                argumento.setComplejo(false);
                argumento.setGenerarXstream(false);

            }

            if (tablaArgumentos.getValueAt(contSimple - 1, 1).toString().startsWith("resultado")) {
                argumento.setValor(tablaArgumentos.getValueAt(contSimple - 1, 1).toString());
                argumento.setGenerarXstream(false);
            }

//            System.out.println("ARGUMENTO ACTUAL, tipo: " + argumento.getTipo()
//                    + " valor: " + argumento.getValor()
//                    + " complejo?: " + argumento.isComplejo());
            argumentos.add(argumento);

            contSimple++;
            contFila++;

        }

        return argumentos;
    }

    public File getMethodHTML(ArrayList<File> archivos, String className) {

        File archivo = null;

        for (File file : archivos) {
            if (file.getName().equals(className + ".html")) {
                archivo = file;
                return archivo;
            }
        }
        return archivo;
    }

    public void javaDocPanel(String className, String methodName, ArrayList<File> archivos) {

        File file = this.getMethodHTML(archivos, className);

        BufferedReader reader = null;



        try {
            reader = new BufferedReader(new FileReader(file.getPath()));
            StringBuilder sb = new StringBuilder();
            String line;



            while ((line = reader.readLine()) != null) {
                sb.append(line);


            }

            Pattern pattern = Pattern.compile("<H3>" + methodName + "</H3>.*<!-- ========= END OF CLASS DATA ========= --><HR>");
            Matcher matcherExterno = pattern.matcher(sb.toString());



            while (matcherExterno.find()) {
                String javadocMetodo = matcherExterno.group().substring(0, matcherExterno.group().indexOf("<HR>"));



            }

        } catch (IOException ex) {
            Logger.getLogger(CaseTestEditor.class.getName()).log(Level.SEVERE, null, ex);
        }


    }

    public CasoPrueba crearCasoPrueba(String nombreCasoPrueba, ArrayList<EscenarioPrueba> escenarios) {

        CasoPrueba casoPrueba = new CasoPrueba(nombreCasoPrueba);
        //casoPrueba.setNombrePaquete("com.test.prueba");
        casoPrueba.setEscenariosPrueba(escenarios);

        return casoPrueba;
    }

    /**
     * @return the escenariosPrueba
     */
    public ArrayList<EscenarioPrueba> getEscenariosPrueba() {
        return escenariosPrueba;


    }

    /**
     * @param escenariosPrueba the escenariosPrueba to set
     */
    public void setEscenariosPrueba(ArrayList<EscenarioPrueba> escenariosPrueba) {
        this.escenariosPrueba = escenariosPrueba;

    }
}
