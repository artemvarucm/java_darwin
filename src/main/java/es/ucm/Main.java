package es.ucm;

import es.ucm.factories.*;
import es.ucm.individuos.Individuo;
import es.ucm.selection.*;
import es.ucm.mutation.*;
import es.ucm.cross.*;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Interfaz gráfica para el simulador de algoritmos genéticos.
 * Permite configurar y ejecutar un algoritmo genético, visualizar resultados y exportarlos.
 */
public class Main extends JFrame {

    // Campos de texto para los parámetros del algoritmo
    private JTextField populationSizeField;
    private JTextField generationsField;
    private JTextField mutationRateField;
    private JTextField crossoverRateField;
    private JTextField elitismRateField;
    private JTextField precisionField;
    private JTextField dimensionsField;

    // ComboBox para seleccionar métodos de selección, cruce, mutación y tipo de individuo
    private JComboBox<String> selectionMethodComboBox;
    private JComboBox<String> crossoverMethodComboBox;
    private JComboBox<String> mutationMethodComboBox;
    private JComboBox<String> individualTypeComboBox;

    // Área de texto para mostrar resultados
    private JTextArea resultsArea;

    // Panel para graficar la evolución del algoritmo
    private Plot2DPanel plotPanel;

    /**
     * Constructor de la interfaz gráfica.
     * Configura la ventana principal y inicializa los componentes.
     */
    public Main() {
        setTitle("Genetic Algorithm Simulator"); // Título de la ventana
        setSize(1200, 800); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar la aplicación al cerrar la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        initComponents(); // Inicializar los componentes de la interfaz
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void initComponents() {
        // Panel principal con un diseño BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margen interno

        // Panel de control para los parámetros del algoritmo
        JPanel controlPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // Diseño de cuadrícula
        controlPanel.setBorder(BorderFactory.createTitledBorder("Parameters")); // Borde con título

        // Campos de texto para los parámetros
        populationSizeField = new JTextField("100"); // Tamaño de la población
        generationsField = new JTextField("100"); // Número de generaciones
        mutationRateField = new JTextField("0.05"); // Tasa de mutación
        crossoverRateField = new JTextField("0.6"); // Tasa de cruce
        elitismRateField = new JTextField("0.1"); // Tasa de elitismo
        precisionField = new JTextField("0.001"); // Precisión
        dimensionsField = new JTextField("2"); // Dimensiones

        // ComboBox para seleccionar métodos
        selectionMethodComboBox = new JComboBox<>(new String[]{"Roulette", "Tournament Deterministic", "Tournament Probabilistic", "Stochastic Universal", "Truncation", "Remainder + Roulette", "Ranking"});
        crossoverMethodComboBox = new JComboBox<>(new String[]{"Single Point", "Uniform", "Arithmetic", "SBX", "BLX"});
        mutationMethodComboBox = new JComboBox<>(new String[]{"Uniform"});
        individualTypeComboBox = new JComboBox<>(new String[]{"Problema 1", "Problema 2", "Problema 3", "Problema 4", "Problema 5"});

        // Añadir etiquetas y campos al panel de control
        controlPanel.add(new JLabel("Population Size:"));
        controlPanel.add(populationSizeField);
        controlPanel.add(new JLabel("Number of Generations:"));
        controlPanel.add(generationsField);
        controlPanel.add(new JLabel("Mutation Rate:"));
        controlPanel.add(mutationRateField);
        controlPanel.add(new JLabel("Crossover Rate:"));
        controlPanel.add(crossoverRateField);
        controlPanel.add(new JLabel("Elitism Rate:"));
        controlPanel.add(elitismRateField);
        controlPanel.add(new JLabel("Precision:"));
        controlPanel.add(precisionField);
        controlPanel.add(new JLabel("Dimensions:"));
        controlPanel.add(dimensionsField);
        controlPanel.add(new JLabel("Selection Method:"));
        controlPanel.add(selectionMethodComboBox);
        controlPanel.add(new JLabel("Crossover Method:"));
        controlPanel.add(crossoverMethodComboBox);
        controlPanel.add(new JLabel("Mutation Method:"));
        controlPanel.add(mutationMethodComboBox);
        controlPanel.add(new JLabel("Problem Type:"));
        controlPanel.add(individualTypeComboBox);

        // Botón para iniciar el algoritmo
        JButton startButton = new JButton("Start");
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startAlgorithm(); // Ejecutar el algoritmo al hacer clic
            }
        });

        // Botón para resetear los campos
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resetFields(); // Resetear los campos al hacer clic
            }
        });

        // Botón para exportar los resultados
        JButton exportButton = new JButton("Export Results");
        exportButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportResults(); // Exportar resultados al hacer clic
            }
        });

        // Panel para los botones
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exportButton);

        // Panel para mostrar los resultados
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));

        resultsArea = new JTextArea(5, 2);
        resultsArea.setEditable(false); // El área de texto no es editable
        JScrollPane scrollPane = new JScrollPane(resultsArea); // Añadir scroll al área de texto
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel para graficar la evolución del algoritmo
        plotPanel = new Plot2DPanel();
        plotPanel.setBorder(BorderFactory.createTitledBorder("Evolution Plot"));
        plotPanel.addLegend("SOUTH"); // Leyenda en la parte inferior

        // Añadir paneles al panel principal
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);

        // Añadir el panel de gráficos a la derecha
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, plotPanel);
        splitPane.setResizeWeight(0.5); // Dividir el espacio equitativamente
        add(splitPane); // Añadir el split pane a la ventana
    }

    /**
     * Inicia el algoritmo genético con los parámetros configurados.
     */
    private void startAlgorithm() {
        try {
            // Obtener los valores de los campos de texto
            int populationSize = Integer.parseInt(populationSizeField.getText());
            int generations = Integer.parseInt(generationsField.getText());
            double mutationRate = Double.parseDouble(mutationRateField.getText());
            double crossoverRate = Double.parseDouble(crossoverRateField.getText());
            double elitismRate = Double.parseDouble(elitismRateField.getText());
            double precision = Double.parseDouble(precisionField.getText());
            int dimensions = Integer.parseInt(dimensionsField.getText());
            int individualType = individualTypeComboBox.getSelectedIndex();

            // Obtener la fábrica de individuos según el tipo seleccionado
            IndividuoFactory factory = getIndividuoFactory(individualType, precision, dimensions);

            // Obtener los métodos de selección, cruce y mutación
            AbstractSelection selectionMethod = getSelectionMethod(factory);
            AbstractCross crossoverMethod = getCrossoverMethod(factory);
            AbstractMutate mutationMethod = getMutationMethod(mutationRate);

            // Crear y ejecutar el algoritmo genético
            AlgoritmoGenetico algorithm = new AlgoritmoGenetico(factory, populationSize);
            algorithm.setMaxGeneraciones(generations);
            algorithm.setProbCruce(crossoverRate);
            algorithm.setElitismRate(elitismRate);
            algorithm.setSelectionMethod(selectionMethod);
            algorithm.setCrossoverMethod(crossoverMethod);
            algorithm.setMutationMethod(mutationMethod);

            algorithm.optimize();

            // Obtener el mejor individuo y mostrar los resultados
            Individuo bestIndividual = algorithm.getMejor();
            resultsArea.setText("Best Fitness: " + bestIndividual.getFitness() + "\n");
            resultsArea.append("Best Solution: " + bestIndividual.getFenotipos().toString() + "\n");

            // Graficar los resultados
            plotAlgorithmResults(algorithm, generations);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene la fábrica de individuos según el tipo seleccionado.
     *
     * @param individualType El índice del tipo de individuo seleccionado.
     * @return La fábrica de individuos correspondiente.
     */
    private IndividuoFactory getIndividuoFactory(int individualType, double precision, int dimension) {
        switch (individualType) {
            case 0:
                return new Individuo1Factory(precision);
            case 1:
                return new Individuo2Factory(precision);
            case 2:
                return new Individuo3Factory(precision);
            case 3:
                return new Individuo4Factory(precision, dimension);
            case 4:
                return new Individuo5Factory(dimension);
            default:
                throw new IllegalArgumentException("Invalid individual type");
        }
    }

    /**
     * Obtiene el método de selección según la opción seleccionada.
     *
     * @param factory La fábrica de individuos.
     * @return El método de selección correspondiente.
     */
    private AbstractSelection getSelectionMethod(IndividuoFactory factory) {
        int selectionType = selectionMethodComboBox.getSelectedIndex();
        switch (selectionType) {
            case 0:
            	return new RouletteSelection(factory);
            case 1:
                return new TorneoSelection(factory, 3, true); // Toreno Determinista
            case 2:
                return new TorneoSelection(factory, 3, false); // Torneo Probabilistico
            case 3:
            	return new StochasticUniversalSelection(factory);
            case 4:
            	return new TruncationSelection(factory, 0.5); // Umbral de truncamiento
            case 5:
            	return new RemainderRouletteSelection(factory); // Por restos + ruleta
            case 6:
                return new RankingSelection(factory);
            default:
                throw new IllegalArgumentException("Invalid selection method");
        }
    }

    /**
     * Obtiene el método de cruce según la opción seleccionada.
     *
     * @param factory La fábrica de individuos.
     * @return El método de cruce correspondiente.
     */
    private AbstractCross getCrossoverMethod(IndividuoFactory factory) {
        int crossoverType = crossoverMethodComboBox.getSelectedIndex();
        switch (crossoverType) {
            case 0:
                return new SinglePointCross(factory);
            case 1:
                return new UniformCross(factory, 0.5);
            case 2:
                return new ArithmeticCross(factory, 0.5);
            case 3:
                return new SBXCross(factory, 2.0); // Índice de distribución para SBX
            case 4:
                return new BLXCross(factory, 0.5); // Alpha para BLX
            default:
                throw new IllegalArgumentException("Invalid crossover method");
        }
    }

    /**
     * Obtiene el método de mutación según la opción seleccionada.
     *
     * @param mutationRate La tasa de mutación.
     * @return El método de mutación correspondiente.
     */
    private AbstractMutate getMutationMethod(double mutationRate) {
        int mutationType = mutationMethodComboBox.getSelectedIndex();
        switch (mutationType) {
            case 0:
                return new UniformMutate(mutationRate);
            default:
                throw new IllegalArgumentException("Invalid mutation method");
        }
    }

    /**
     * Grafica los resultados del algoritmo genético.
     *
     * @param algorithm    El algoritmo genético.
     * @param generations  El número de generaciones.
     */
    private void plotAlgorithmResults(AlgoritmoGenetico algorithm, int generations) {
        double[] generationNumbers = new double[generations];
        for (int i = 0; i < generations; i++) {
            generationNumbers[i] = i;
        }

        double[] bestFitness = algorithm.getBestFitnessHistory();
        double[] averageFitness = algorithm.getAverageFitnessHistory();
        double[] absoluteBest = algorithm.getAbsoluteBestHistory();
        double[] presionSelectiva = algorithm.getPresionSelectiva();

        plotPanel.removeAllPlots();
        plotPanel.setAxisLabels("N. GENERACIÓN", "VALOR FUNCIÓN");
        plotPanel.addLinePlot("Mejor Absoluto", Color.BLUE, generationNumbers, absoluteBest);
        plotPanel.addLinePlot("Mejor Generación", Color.RED, generationNumbers, bestFitness);
        plotPanel.addLinePlot("Media Generación", Color.GREEN, generationNumbers, averageFitness);
        plotPanel.addLinePlot("Presión selectiva", Color.BLACK, generationNumbers, presionSelectiva);
    }

    /**
     * Resetea los campos de la interfaz gráfica.
     */
    private void resetFields() {
        populationSizeField.setText("100");
        generationsField.setText("100");
        mutationRateField.setText("0.05");
        crossoverRateField.setText("0.6");
        elitismRateField.setText("0.1");
        precisionField.setText("0.001");
        dimensionsField.setText("2");
        selectionMethodComboBox.setSelectedIndex(0);
        crossoverMethodComboBox.setSelectedIndex(0);
        mutationMethodComboBox.setSelectedIndex(0);
        individualTypeComboBox.setSelectedIndex(0);
        resultsArea.setText("");
        plotPanel.removeAllPlots();
    }

    /**
     * Exporta los resultados a un archivo de texto.
     */
    private void exportResults() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                writer.write(resultsArea.getText());
                JOptionPane.showMessageDialog(this, "Results exported successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error exporting results", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * Método principal para ejecutar la interfaz gráfica.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan).
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}