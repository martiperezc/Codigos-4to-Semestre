package interfaz;

import negocio.GestorMantenimiento;
import modelo.activos.*;
import modelo.actores.Tecnico;
import modelo.operaciones.Analisis;
import modelo.operaciones.Mantenimiento;
import modelo.excepciones.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;



public class MainInterfaz extends JFrame {
    private static GestorMantenimiento gestor = new GestorMantenimiento();
    private JTabbedPane tabsMenuPrincipal;
    private JPanel panelLogin;

    public MainInterfaz() {
        super("Sistema Integrado de Gestión de Motores - UDLA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Precarga de Técnico y Administrador base
        try {
            // Credenciales del Admin:
            // Usuario: "Majo" | Contraseña: "majo123"
            gestor.registrarTecnico(new Tecnico("1754280269", "Ing. Martin Perez", "Automatización", "morde110"));
        } catch(Exception ignored){}

        setLayout(new CardLayout());
        inicializarComponentesLogin();
        setVisible(true);
    }

    private void inicializarComponentesLogin() {
        panelLogin = new JPanel(new GridBagLayout());
        panelLogin.setBackground(new Color(240, 244, 248));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("INICIO DE SESIÓN / REGISTRO", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panelLogin.add(title, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 1; gbc.gridx = 0; panelLogin.add(new JLabel("Usuario / Cédula:"), gbc);
        JTextField tfUser = new JTextField(15);
        gbc.gridx = 1; panelLogin.add(tfUser, gbc);

        gbc.gridy = 2; gbc.gridx = 0; panelLogin.add(new JLabel("Contraseña:"), gbc);
        JPasswordField pfPass = new JPasswordField(15); // ◄ CAMPO OCULTO DE TEXTO
        gbc.gridx = 1; panelLogin.add(pfPass, gbc);

        gbc.gridy = 3; gbc.gridx = 0; panelLogin.add(new JLabel("Rol para entrar:"), gbc);
        String[] roles = {"Técnico", "Administrador"};
        JComboBox<String> cbRol = new JComboBox<>(roles);
        gbc.gridx = 1; panelLogin.add(cbRol, gbc);

        JButton btnLogin = new JButton("Iniciar Sesión");
        gbc.gridy = 4; gbc.gridx = 0; gbc.gridwidth = 2;
        panelLogin.add(btnLogin, gbc);

        JSeparator sep = new JSeparator();
        gbc.gridy = 5; gbc.gridwidth = 2;
        panelLogin.add(sep, gbc);

        JLabel lblReg = new JLabel("REGISTRO EXCLUSIVO PARA TÉCNICOS", JLabel.CENTER);
        lblReg.setFont(new Font("Arial", Font.BOLD, 12));
        gbc.gridy = 6; panelLogin.add(lblReg, gbc);

        gbc.gridwidth = 1;
        gbc.gridy = 7; gbc.gridx = 0; panelLogin.add(new JLabel("Cédula (10 dig.):"), gbc);
        JTextField tfRegCed = new JTextField(); gbc.gridx = 1; panelLogin.add(tfRegCed, gbc);

        gbc.gridy = 8; gbc.gridx = 0; panelLogin.add(new JLabel("Nombre Completo:"), gbc);
        JTextField tfRegNom = new JTextField(); gbc.gridx = 1; panelLogin.add(tfRegNom, gbc);

        gbc.gridy = 9; gbc.gridx = 0; panelLogin.add(new JLabel("Especialidad:"), gbc);
        JTextField tfRegEsp = new JTextField(); gbc.gridx = 1; panelLogin.add(tfRegEsp, gbc);

        gbc.gridy = 10; gbc.gridx = 0; panelLogin.add(new JLabel("Definir Contraseña:"), gbc);
        JPasswordField pfRegPass = new JPasswordField(); // ◄ CLAVE PARA EL REGISTRO
        gbc.gridx = 1; panelLogin.add(pfRegPass, gbc);

        JButton btnRegister = new JButton("Registrar Técnico");
        gbc.gridy = 11; gbc.gridx = 0; gbc.gridwidth = 2;
        panelLogin.add(btnRegister, gbc);

        add(panelLogin, "LOGIN");

        // ACCIÓN DEL BOTÓN INICIAR SESIÓN (ADMIN POR DEFECTO IMPLEMENTADO)
        btnLogin.addActionListener(e -> {
            String user = tfUser.getText().trim();
            String pass = new String(pfPass.getPassword()).trim(); // Recupera la clave ingresada
            String rol = (String) cbRol.getSelectedItem();

            if ("Administrador".equals(rol)) {
                // Validación del Administrador por Defecto (Usuario: admin | Clave: admin123)
                if ("admin".equalsIgnoreCase(user) && "admin".equals(pass)) {
                    configurarMenuPrincipal(true);
                    tfUser.setText(""); pfPass.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Usuario o Contraseña de Administrador incorrectos.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                // Validación de Técnico con Cédula y Contraseña de registro
                if (gestor.iniciarSesionTecnico(user, pass)) {
                    configurarMenuPrincipal(false);
                    tfUser.setText(""); pfPass.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Cédula o Contraseña de Técnico incorrectas.", "Error de Acceso", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ACCIÓN DEL BOTÓN REGISTRAR TÉCNICO
        btnRegister.addActionListener(e -> {
            List<String> errores = new ArrayList<>();
            String ced = tfRegCed.getText().trim();
            String nom = tfRegNom.getText().trim();
            String esp = tfRegEsp.getText().trim();
            String pass = new String(pfRegPass.getPassword()).trim();

            if (!ced.matches("\\d+")) errores.add("- La cédula debe contener solo números positivos.");
            if (ced.length() != 10) errores.add("- La cédula debe tener exactamente 10 dígitos.");
            if (!nom.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) errores.add("- El nombre debe contener solo letras.");
            if (!esp.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")) errores.add("- La especialidad debe contener solo letras.");
            if (pass.isEmpty()) errores.add("- La contraseña de registro no puede estar vacía.");

            if (!errores.isEmpty()) {
                JOptionPane.showMessageDialog(this, String.join("\n", errores), "Errores de Validación", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Se envía la contraseña al constructor del modelo
                gestor.registrarTecnico(new Tecnico(ced, nom, esp, pass));
                JOptionPane.showMessageDialog(this, "Técnico registrado correctamente. Ya puede iniciar sesión.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                tfRegCed.setText(""); tfRegNom.setText(""); tfRegEsp.setText(""); pfRegPass.setText("");
            } catch (DatoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void configurarMenuPrincipal(boolean isAdmin) {
        if (tabsMenuPrincipal != null) remove(tabsMenuPrincipal);

        tabsMenuPrincipal = new JTabbedPane();

        // Creamos el panel común una sola vez
        JPanel panelEstadoComun = crearPanelEstadoMotores();

        if (isAdmin) {
            tabsMenuPrincipal.addTab("Gestión Motores", crearPanelGestionMotores());
            tabsMenuPrincipal.addTab("Técnicos Registrados", crearPanelTecnicos());
            tabsMenuPrincipal.addTab("Estado de Motores", panelEstadoComun);
            tabsMenuPrincipal.addTab("Historial Integral", crearPanelHistorial());
        } else {
            tabsMenuPrincipal.addTab("Análisis Técnico", crearPanelAnalisis());
            tabsMenuPrincipal.addTab("Mantenimiento", crearPanelMantenimiento());
            tabsMenuPrincipal.addTab("Estado de Motores", panelEstadoComun); 
        }

        JPanel panelContenedor = new JPanel(new BorderLayout());
        JPanel panelTop = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnLogout = new JButton("Cerrar Sesión");

        panelTop.add(new JLabel("Usuario Activo: " + (isAdmin ? "ADMIN" : gestor.getTecnicoLogueado().getNombre())));
        panelTop.add(btnLogout);
        panelContenedor.add(panelTop, BorderLayout.NORTH);
        panelContenedor.add(tabsMenuPrincipal, BorderLayout.CENTER);

        add(panelContenedor, "MAIN");
        CardLayout cl = (CardLayout) getContentPane().getLayout();
        cl.show(getContentPane(), "MAIN");

        btnLogout.addActionListener(e -> {
            gestor.cerrarSesion();
            cl.show(getContentPane(), "LOGIN");
        });
    }

    private JPanel crearPanelGestionMotores() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5,5,5,5); gbc.fill = GridBagConstraints.HORIZONTAL;

        JComboBox<String> cbTipo = new JComboBox<>(new String[]{"DC", "Trifásico", "AC 110V"});
        JTextField tfCod = new JTextField();
        JTextField tfPot = new JTextField();
        JTextField tfCorr = new JTextField();
        JTextField tfVolt = new JTextField();
        JTextField tfUbi = new JTextField();

        JCheckBox cbEscobillas = new JCheckBox("Tiene Escobillas");
        JComboBox<String> cbConexion = new JComboBox<>(new String[]{"Estrella", "Delta"});
        JTextField tfFrec = new JTextField();
        JTextField tfFP = new JTextField();

        cbEscobillas.setEnabled(true); cbConexion.setEnabled(false); tfFrec.setEnabled(false); tfFP.setEnabled(false);

        cbTipo.addActionListener(e -> {
            String s = (String) cbTipo.getSelectedItem();
            cbEscobillas.setEnabled("DC".equals(s));
            cbConexion.setEnabled("Trifásico".equals(s));
            tfFrec.setEnabled("AC 110V".equals(s));
            tfFP.setEnabled("AC 110V".equals(s));
        });

        int r=0;
        gbc.gridx=0; gbc.gridy=r; form.add(new JLabel("Tipo Motor:"), gbc); gbc.gridx=1; form.add(cbTipo, gbc); r++;
        gbc.gridx=0; gbc.gridy=r; form.add(new JLabel("Código (Solo +):"), gbc); gbc.gridx=1; form.add(tfCod, gbc); r++;
        gbc.gridx=0; gbc.gridy=r; form.add(new JLabel("Potencia (kW):"), gbc); gbc.gridx=1; form.add(tfPot, gbc); r++;
        gbc.gridx=0; gbc.gridy=r; form.add(new JLabel("Corriente (A):"), gbc); gbc.gridx=1; form.add(tfCorr, gbc); r++;
        gbc.gridx=0; gbc.gridy=r; form.add(new JLabel("Voltaje (V):"), gbc); gbc.gridx=1; form.add(tfVolt, gbc); r++;
        gbc.gridx=0; gbc.gridy=r; form.add(new JLabel("Ubicación:"), gbc); gbc.gridx=1; form.add(tfUbi, gbc); r++;

        JPanel pEsp = new JPanel(new GridLayout(2,2,4,4));
        pEsp.setBorder(BorderFactory.createTitledBorder("Especificaciones Técnicas"));
        pEsp.add(cbEscobillas); pEsp.add(cbConexion);
        pEsp.add(new JLabel("Frecuencia:")); pEsp.add(tfFrec);
        pEsp.add(new JLabel("FP:")); pEsp.add(tfFP);

        JButton btnReg = new JButton("Registrar Motor");
        btnReg.addActionListener(e -> {
            List<String> errores = new ArrayList<>();
            String cod = tfCod.getText().trim();
            String potS = tfPot.getText().trim();
            String corrS = tfCorr.getText().trim();
            String voltS = tfVolt.getText().trim();
            String ubi = tfUbi.getText().trim();
            String tipo = (String) cbTipo.getSelectedItem();

            if (!cod.matches("\\d+")) errores.add("El código debe contener solo números positivos.");
            if (!potS.matches("\\d+(\\.\\d+)?")) errores.add("Potencia inválida (debe ser un número positivo).");
            if (!corrS.matches("\\d+(\\.\\d+)?")) errores.add("Corriente inválida (debe ser un número positivo).");
            if (!voltS.matches("\\d+(\\.\\d+)?")) errores.add("Voltaje inválido (debe ser un número positivo).");
            if (!ubi.matches("^[a-zA-Z].*")) errores.add("La ubicación debe iniciar con una letra.");

            double p=0, c=0, v=0, f=0, fp=0;
            try{ p=Double.parseDouble(potS); }catch(Exception ignored){}
            try{ c=Double.parseDouble(corrS); }catch(Exception ignored){}
            try{ v=Double.parseDouble(voltS); }catch(Exception ignored){}

            if ("AC 110V".equals(tipo)) {
                if (!tfFrec.getText().trim().matches("\\d+(\\.\\d+)?")) errores.add("Frecuencia inválida.");
                if (!tfFP.getText().trim().matches("\\d+(\\.\\d+)?")) errores.add("FP inválido.");
                try{ f=Double.parseDouble(tfFrec.getText().trim()); }catch(Exception ignored){}
                try{ fp=Double.parseDouble(tfFP.getText().trim()); }catch(Exception ignored){}
            }

            if (!errores.isEmpty()) {
                JOptionPane.showMessageDialog(this, String.join("\n", errores), "Errores Detectados", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                Motor m;
                if ("DC".equals(tipo)) m = new MotorDC(cod, p, c, v, ubi, cbEscobillas.isSelected());
                else if ("Trifásico".equals(tipo)) m = new MotorTrifasico(cod, p, c, v, ubi, (String)cbConexion.getSelectedItem());
                else m = new MotorAC110V(cod, p, c, v, ubi, f, fp);

                gestor.registrarMotor(m);
                JOptionPane.showMessageDialog(this, "Activo registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            } catch(DatoInvalidoException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error Base de Datos", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(form, BorderLayout.NORTH);
        panel.add(pEsp, BorderLayout.CENTER);
        panel.add(btnReg, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel crearPanelTecnicos() {
        JPanel panel = new JPanel(new BorderLayout());
        String[] columnas = {"Cédula", "Nombre", "Especialidad"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0);
        JTable table = new JTable(model);

        for (Tecnico t : gestor.getCatalogoTecnicos()) {
            model.addRow(new Object[]{t.getCedula(), t.getNombre(), t.getEspecialidad()});
        }
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private JPanel crearPanelAnalisis() {
        JPanel panel = new JPanel(new BorderLayout());

        // 1. Modelo de lista lateral para mostrar los motores registrados
        DefaultListModel<String> lModel = new DefaultListModel<>();
        JList<String> listM = new JList<>(lModel);
        listM.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTextField tfCod = new JTextField();
        listM.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listM.getSelectedValue() != null) {
                String seleccion = listM.getSelectedValue();
                String codigoExtraido = seleccion.split(" ")[0];
                tfCod.setText(codigoExtraido);
            }
        });

        // 2. Formulario central de ingreso de magnitudes físicas
        JPanel form = new JPanel(new GridLayout(5, 2, 4, 4));
        JTextField tfV = new JTextField();
        JTextField tfC = new JTextField();
        JTextField tfT = new JTextField();
        JTextField tfA = new JTextField();

        form.add(new JLabel("Código Motor:")); form.add(tfCod);
        form.add(new JLabel("Voltaje Real (V):")); form.add(tfV);
        form.add(new JLabel("Corriente Real (A):")); form.add(tfC);
        form.add(new JLabel("Temperatura (°C):")); form.add(tfT);
        form.add(new JLabel("Aislamiento (MΩ):")); form.add(tfA);

        // 3. Área de reporte técnico unificada (Reemplaza los 5 cuadros individuales de la derecha)
        JTextArea taReporte = new JTextArea();
        taReporte.setEditable(false);
        taReporte.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Fuente tipo consola para alineación perfecta
        taReporte.setText("\n\n   --- ESPERANDO REGISTRO DE MEDICIONES INDUSTRIALES ---");
        JScrollPane scrollReporte = new JScrollPane(taReporte);

        // 4. Botón con validación acumulativa de errores y renderizado del reporte detallado
        JButton btnGen = new JButton("Registrar Análisis");
        btnGen.addActionListener(e -> {
            List<String> errores = new ArrayList<>();
            String cod = tfCod.getText().trim();
            if (cod.isEmpty()) errores.add("Debe ingresar o seleccionar el código del motor.");
            if (!tfV.getText().trim().matches("\\d+(\\.\\d+)?")) errores.add("Voltaje incorrecto (debe ser número positivo).");
            if (!tfC.getText().trim().matches("\\d+(\\.\\d+)?")) errores.add("Corriente incorrecta (debe ser número positivo).");
            if (!tfT.getText().trim().matches("\\d+(\\.\\d+)?")) errores.add("Temperatura incorrecta (debe ser número positivo).");
            if (!tfA.getText().trim().matches("\\d+(\\.\\d+)?")) errores.add("Aislamiento incorrecto (debe ser número positivo).");

            if (!errores.isEmpty()) {
                JOptionPane.showMessageDialog(this, String.join("\n", errores), "Validaciones de Análisis", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                // 1. Registrar el nuevo análisis en la capa de negocio
                gestor.generarAnalisisMotor(cod,
                        Double.parseDouble(tfV.getText().trim()), Double.parseDouble(tfC.getText().trim()),
                        Double.parseDouble(tfT.getText().trim()), Double.parseDouble(tfA.getText().trim()));

                // 2. CONSTRUCCIÓN GLOBAL: Recorremos TODOS los análisis históricos guardados en el sistema
                StringBuilder sbGlobal = new StringBuilder();
                sbGlobal.append("=====================================================================\n");
                sbGlobal.append("          REPORTE HISTÓRICO GLOBAL DE ANÁLISIS EN LA PLANTA         \n");
                sbGlobal.append("=====================================================================\n\n");

                List<Analisis> listaHistorica = gestor.getTodosLosAnalisis();

                // Recorremos al revés (desde el índice más alto al 0) para que el ÚLTIMO análisis aparezca arriba del todo
                for (int i = listaHistorica.size() - 1; i >= 0; i--) {
                    Analisis an = listaHistorica.get(i);

                    sbGlobal.append("---------------------------------------------------------------------\n");
                    sbGlobal.append(" ID DE DIAGNÓSTICO:    ").append(an.getIdAnalisis()).append(" (Secuencial Automático)\n");

                    // Validación de seguridad para la firma del responsable
                    String nombreResponsable = (an.getTecnico() != null) ? an.getTecnico().getNombre() : "ADMINISTRADOR CENTRAL";
                    sbGlobal.append(" PERSONAL RESPONSABLE: ").append(nombreResponsable).append("\n");
                    sbGlobal.append(" CÓDIGO DEL ACTIVO:    ").append(an.getMotor().getCodigo()).append(" [").append(an.getMotor().getTipoTexto()).append("]\n");
                    sbGlobal.append(" UBICACIÓN FÍSICA:    ").append(an.getMotor().getUbicacion()).append("\n");
                    sbGlobal.append("---------------------------------------------------------------------\n");
                    sbGlobal.append(" VALORES REGISTRADOS EN CAMPO:\n");
                    sbGlobal.append("  - Voltaje: ").append(an.getMotor().getVoltajeNominal()).append(" V\n"); // Valores base o medidos si requieres los del textfield
                    sbGlobal.append("  - Corriente: ").append(an.getMotor().getCorrienteNominal()).append(" A\n");
                    sbGlobal.append(" STATUS TÉCNICO:       [").append(an.getEstadoFinal().toUpperCase()).append("]\n");

                    if (an.getAlertas().isEmpty()) {
                        sbGlobal.append("  - Alertas: Ninguna. Operación en parámetros nominales.\n");
                    } else {
                        sbGlobal.append("  - Alertas Críticas:\n");
                        for (String al : an.getAlertas()) {
                            sbGlobal.append("    * ⚠️ ").append(al).append("\n");
                        }
                    }

                    sbGlobal.append(" RECOMENDACIONES INDUSTRIALES:\n");
                    if (an.getSugerencias().isEmpty()) {
                        sbGlobal.append("  - Continuar con el plan rutinario de inspecciones de la planta.\n");
                    } else {
                        for (String sug : an.getSugerencias()) {
                            sbGlobal.append("    * 🛠️ ").append(sug).append("\n");
                        }
                    }
                    sbGlobal.append("---------------------------------------------------------------------\n\n");
                }

                sbGlobal.append("=====================================================================");

                // 3. Imprimir el bloque histórico acumulado completo en la derecha
                taReporte.setText(sbGlobal.toString());
                taReporte.setCaretPosition(0); // Autoscroll hacia arriba para ver el más reciente de inmediato

                JOptionPane.showMessageDialog(this, "Análisis añadido. Historial técnico actualizado.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpiar campos de entrada de datos de la izquierda
                tfCod.setText(""); tfV.setText(""); tfC.setText(""); tfT.setText(""); tfA.setText("");
                listM.clearSelection();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en Análisis", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 5. ComponentListener para refrescar la lista de motores automáticamente al pulsar la pestaña
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                lModel.clear();
                for (Motor m : gestor.getCatalogoMotores()) {
                    lModel.addElement(m.getCodigo() + " - [" + m.getTipoTexto() + "]");
                }
            }
        });

        // 6. Configurar Layout y ensamblar el Panel
        JScrollPane scrollLista = new JScrollPane(listM);
        scrollLista.setPreferredSize(new Dimension(200, 0));

        JPanel panelCentro = new JPanel(new GridLayout(2, 1, 4, 4));
        panelCentro.add(form);
        panelCentro.add(scrollReporte); // El área de reporte ocupa la parte inferior/derecha de forma unificada

        panel.add(scrollLista, BorderLayout.WEST);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(btnGen, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelMantenimiento() {
        JPanel panel = new JPanel(new BorderLayout());

        // 1. Modelos de lista y componentes visuales de selección
        DefaultListModel<String> modelPrev = new DefaultListModel<>();
        DefaultListModel<String> modelCorr = new DefaultListModel<>();

        JList<String> listPrev = new JList<>(modelPrev);
        JList<String> listCorr = new JList<>(modelCorr);
        listPrev.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listCorr.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTextField tfCod = new JTextField(10);

        // Área central unificada para que el técnico vea los detalles clínicos del análisis seleccionado
        JTextArea taDetalleAnalisis = new JTextArea(8, 50);
        taDetalleAnalisis.setEditable(false);
        taDetalleAnalisis.setFont(new Font("Monospaced", Font.PLAIN, 12));
        taDetalleAnalisis.setText("\n\n   --- SELECCIONE UN MOTOR DE LAS LISTAS PARA VER EL ANÁLISIS DE ORIGEN ---");
        JScrollPane scrollDetalle = new JScrollPane(taDetalleAnalisis);

        // EVENTO LISTA PREVENTIVOS: Al hacer clic, autocompleta el código y busca su análisis
        listPrev.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listPrev.getSelectedValue() != null) {
                listCorr.clearSelection(); // Deselecciona la otra lista para no confundir
                String seleccion = listPrev.getSelectedValue();
                String codExtraido = seleccion.replace("Motor: ", "").trim();
                tfCod.setText(codExtraido);
                mostrarAnalisisEnPantalla(codExtraido, taDetalleAnalisis);
            }
        });

        // EVENTO LISTA CORRECTIVOS: Al hacer clic, autocompleta el código y busca su análisis
        listCorr.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && listCorr.getSelectedValue() != null) {
                listPrev.clearSelection(); // Deselecciona la otra lista
                String seleccion = listCorr.getSelectedValue();
                String codExtraido = seleccion.replace("Motor: ", "").trim();
                tfCod.setText(codExtraido);
                mostrarAnalisisEnPantalla(codExtraido, taDetalleAnalisis);
            }
        });

        // 2. Distribución de las listas superiores (Preventivo y Correctivo)
        JPanel pListas = new JPanel(new GridLayout(1, 2, 6, 6));
        pListas.add(new JScrollPane(listPrev));
        pListas.add(new JScrollPane(listCorr));
        pListas.setPreferredSize(new Dimension(0, 150)); // Altura fija controlada para las listas
        pListas.setBorder(BorderFactory.createTitledBorder("Motores con Análisis Pendiente (Izquierda: Preventivo | Derecha: Correctivo)"));

        // 3. Formulario de ingreso de tareas del técnico
        JPanel fInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextArea taTareas = new JTextArea(3, 35);
        fInputs.add(new JLabel("Código Motor a intervenir:")); fInputs.add(tfCod);
        fInputs.add(new JLabel("Tareas Realizadas:")); fInputs.add(new JScrollPane(taTareas));

        // 4. Botón para registrar la orden de mantenimiento cerrada
        JButton btnEjecutar = new JButton("Ejecutar Operación de Mantenimiento");
        btnEjecutar.addActionListener(e -> {
            String codigo = tfCod.getText().trim();
            String tareas = taTareas.getText().trim();

            if (codigo.isEmpty() || tareas.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Debe seleccionar un activo y detallar las tareas de mantenimiento ejecutadas.", "Campos Vacíos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                // Registrar mantenimiento en la capa de negocio
                Mantenimiento m = gestor.ejecutarMantenimiento(codigo, tareas);
                JOptionPane.showMessageDialog(this, "Mantenimiento guardado con éxito.\nID Secuencial Asignado: " + m.getIdMantenimiento(), "Éxito", JOptionPane.INFORMATION_MESSAGE);

                // Limpieza de campos y refresco inmediato
                tfCod.setText(""); taTareas.setText("");
                taDetalleAnalisis.setText("\n\n   --- SELECCIONE UN MOTOR DE LAS LISTAS PARA VER EL ANÁLISIS DE ORIGEN ---");
                actualizarListasMantenimiento(modelPrev, modelCorr);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error en Operación", JOptionPane.ERROR_MESSAGE);
            }
        });

        // 5. ComponentListener para actualizar las listas cuando el técnico entra a la pestaña
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                actualizarListasMantenimiento(modelPrev, modelCorr);
            }
        });

        // 6. Ensamblado del panel central
        JPanel panelCentro = new JPanel(new BorderLayout());
        panelCentro.add(scrollDetalle, BorderLayout.CENTER);
        panelCentro.add(fInputs, BorderLayout.SOUTH);

        panel.add(pListas, BorderLayout.NORTH);
        panel.add(panelCentro, BorderLayout.CENTER);
        panel.add(btnEjecutar, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel crearPanelEstadoMotores() {
        JPanel panel = new JPanel(new BorderLayout());

        // Estructura de la tabla: Código, Tipo, Ubicación y la Actualización de Estado en tiempo real
        String[] columnas = {"Código Motor", "Tipo de Motor", "Ubicación", "Estado Actual / Condición"};
        DefaultTableModel model = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; } // Tabla de solo lectura
        };
        JTable tablaEstados = new JTable(model);

        // Listener para refrescar la tabla automáticamente cada vez que el usuario haga clic en esta pestaña
        panel.addComponentListener(new java.awt.event.ComponentAdapter() {
            @Override
            public void componentShown(java.awt.event.ComponentEvent e) {
                model.setRowCount(0); // Vaciar tabla anterior

                for (Motor m : gestor.getCatalogoMotores()) {
                    // Consultamos el estado lógico de actualización a la capa de negocio
                    String estadoActual = gestor.obtenerEstadoActualMotor(m.getCodigo());

                    model.addRow(new Object[]{
                            m.getCodigo(),
                            m.getTipoTexto(),
                            m.getUbicacion(),
                            estadoActual
                    });
                }
            }
        });

        panel.add(new JScrollPane(tablaEstados), BorderLayout.CENTER);

        // Sub-panel informativo inferior para el usuario
        JPanel panelLeyenda = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelLeyenda.setBorder(BorderFactory.createTitledBorder("Leyenda de Operación"));
        panelLeyenda.add(new JLabel("• Buen Estado: Activo operativo o reparado con éxito. "));
        panelLeyenda.add(new JLabel("• Necesita Mantenimiento: Alertas críticas detectadas en el último diagnóstico."));
        panel.add(panelLeyenda, BorderLayout.SOUTH);

        return panel;
    }

    // MÉTODO AUXILIAR 1: Refresca el contenido de las listas preventivo/correctivo
    private void actualizarListasMantenimiento(DefaultListModel<String> modelPrev, DefaultListModel<String> modelCorr) {
        modelPrev.clear();
        modelCorr.clear();
        for (Analisis a : gestor.getTodosLosAnalisis()) {
            if ("Preventivo".equals(a.getEstadoFinal())) {
                modelPrev.addElement("Motor: " + a.getMotor().getCodigo());
            } else if ("Correctivo".equals(a.getEstadoFinal())) {
                modelCorr.addElement("Motor: " + a.getMotor().getCodigo());
            }
        }
    }

    // METODO AUXILIAR 2: Busca el último análisis de un motor y lo dibuja en pantalla con formato estructurado
    private void mostrarAnalisisEnPantalla(String codigoMotor, JTextArea areaTexto) {
        Analisis ultimoAnalisis = null;
        // Buscamos de atrás hacia adelante para obtener el análisis más reciente
        List<Analisis> historialGlobal = gestor.getTodosLosAnalisis();
        for (int i = historialGlobal.size() - 1; i >= 0; i--) {
            if (historialGlobal.get(i).getMotor().getCodigo().equals(codigoMotor)) {
                ultimoAnalisis = historialGlobal.get(i);
                break;
            }
        }

        if (ultimoAnalisis != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("=====================================================================\n");
            sb.append("             ORDEN DE TRABAJO - ANÁLISIS TÉCNICO DE REFERENCIA       \n");
            sb.append("=====================================================================\n");
            sb.append(" ID ANÁLISIS ORIGEN:   ").append(ultimoAnalisis.getIdAnalisis()).append("\n");
            sb.append(" ESTADO DETERMINADO:   [").append(ultimoAnalisis.getEstadoFinal().toUpperCase()).append("]\n");
            sb.append(" GENERADO POR:         ").append(ultimoAnalisis.getTecnico() != null ? ultimoAnalisis.getTecnico().getNombre() : "ADMINISTRADOR").append("\n");
            sb.append("---------------------------------------------------------------------\n");
            sb.append(" ALERTAS CRÍTICAS REGISTRADAS EN EL ACTIVO:\n");
            if (ultimoAnalisis.getAlertas().isEmpty()) {
                sb.append("  - Ninguna.\n");
            } else {
                for (String al : ultimoAnalisis.getAlertas()) sb.append("    * ⚠️ ").append(al).append("\n");
            }
            sb.append("---------------------------------------------------------------------\n");
            sb.append(" RECOMENDACIONES TÉCNICAS REQUERIDAS:\n");
            if (ultimoAnalisis.getSugerencias().isEmpty()) {
                sb.append("  - Procedimientos de inspección estándar.\n");
            } else {
                for (String sug : ultimoAnalisis.getSugerencias()) sb.append("    * 🛠️ ").append(sug).append("\n");
            }
            sb.append("=====================================================================");
            areaTexto.setText(sb.toString());
        } else {
            areaTexto.setText("\n\n  Error: No se encontraron registros de análisis para el motor " + codigoMotor);
        }
    }

    private JPanel crearPanelHistorial() {
        JPanel panel = new JPanel(new BorderLayout());
        DefaultListModel<String> mModel = new DefaultListModel<>();

        for(Motor m : gestor.getCatalogoMotores()){
            if(!m.getHistorial().getListaMantenimientos().isEmpty()){
                String mantTipo = m.getHistorial().getListaMantenimientos().get(0).getAnalisisOrigen().getEstadoFinal();
                mModel.addElement("Código: " + m.getCodigo() + " | Tipo: " + m.getTipoTexto() + " | Intervención: " + mantTipo);
            }
        }

        JList<String> list = new JList<>(mModel);
        JTextField tfBusq = new JTextField(10);
        JButton btnB = new JButton("Ver Detalle Clínico");
        JTextArea ta = new JTextArea(); ta.setEditable(false);

        JPanel top = new JPanel(); top.add(new JLabel("Código:")); top.add(tfBusq); top.add(btnB);

        btnB.addActionListener(e -> {
            try {
                Motor motor = gestor.buscarMotor(tfBusq.getText().trim());
                ta.setText(motor.getHistorial().obtenerHistorialCompleto());
            } catch (Exception ex) {
                ta.setText(ex.getMessage());
            }
        });

        panel.add(new JScrollPane(list), BorderLayout.WEST);
        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(ta), BorderLayout.CENTER);
        return panel;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainInterfaz::new);
    }
}