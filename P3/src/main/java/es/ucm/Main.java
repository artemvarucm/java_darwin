package es.ucm;

import es.ucm.factories.*;
import es.ucm.individuos.Individuo;
import es.ucm.individuos.IndividuoHormiga;
import es.ucm.individuos.arbol.Coord;
import es.ucm.individuos.arbol.Hormiga;
import es.ucm.initializer.AbstractInitializer;
import es.ucm.initializer.FULLInitializer;
import es.ucm.initializer.GrowInitializer;
import es.ucm.initializer.RampedHalfInitializer;
import es.ucm.mapa.AbstractFoodMap;
import es.ucm.mapa.SantaFeMap;
import es.ucm.mutation.*;
import es.ucm.selection.*;
import es.ucm.cross.*;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

/**
 * Interfaz gráfica para el simulador de algoritmos genéticos.
 */
public class Main extends JFrame {

    // Campos de texto para los parámetros del algoritmo
    private JTextField populationSizeField;
    private JTextField generationsField;
    private JTextField mutationRateField;
    private JTextField crossoverRateField;
    private JTextField elitismRateField;
    private JTextField maxTreeDepthField;
    private JTextField stepsLimitField;
    private JTextField bloatingField;

    // ComboBox para seleccionar métodos de inicializacion, selección, cruce, mutación y tipo de individuo
    private JComboBox<String> initMethodComboBox;
    private JComboBox<String> selectionMethodComboBox;
    private JComboBox<String> crossoverMethodComboBox;
    private JComboBox<String> mutationMethodComboBox;
    private JComboBox<String> individualTypeComboBox;

    // Área de texto para mostrar resultados
    private JTextArea resultsArea;
    private JTextArea treeExpressionArea;

    // Panel para graficar la evolución del algoritmo
    private Plot2DPanel plotPanel;

    // Panel para representar el mapa con gráficos
    private FoodMapPanel mapPanelGraphics;

    // Referencia al objeto FoodMap (para poder actualizar el panel cada vez que se calcule la ruta)
    private AbstractFoodMap mapa;

    /**
     * Constructor de la interfaz gráfica.
     */
    public Main() {
        setTitle("Genetic Algorithm Simulator");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    /**
     * Inicializa los componentes de la interfaz gráfica.
     */
    private void initComponents() {
        // Panel principal con BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel de control para configurar parámetros
        JPanel controlPanel = new JPanel(new GridLayout(0, 2, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Parameters"));

        populationSizeField = new JTextField("100");
        generationsField = new JTextField("300");
        mutationRateField = new JTextField("0.2");
        crossoverRateField = new JTextField("0.6");
        elitismRateField = new JTextField("0.1");
        maxTreeDepthField = new JTextField("2");
        stepsLimitField = new JTextField("400");
        bloatingField = new JTextField("0");

        initMethodComboBox = new JComboBox<>(new String[]{
                "Full",
                "Grow",
                "Ramped & Half",
        });

        selectionMethodComboBox = new JComboBox<>(new String[]{
            "Roulette", 
            "Tournament Deterministic", 
            "Tournament Probabilistic", 
            "Stochastic Universal", 
            "Truncation", 
            "Remainder + Roulette", 
            "Ranking"
        });
        crossoverMethodComboBox = new JComboBox<>(new String[]{
            "Subtrees swap"
        });
        mutationMethodComboBox = new JComboBox<>(new String[]{
            "Terminal",
            "Functional",
            "Tree",
            "Permutation",
            "Hoist",
            "Expansion",
            "Contraction"
        });
        individualTypeComboBox = new JComboBox<>(new String[]{"Santa Fe"});

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
        controlPanel.add(new JLabel("Initialization Method:"));
        controlPanel.add(initMethodComboBox);
        controlPanel.add(new JLabel("Selection Method:"));
        controlPanel.add(selectionMethodComboBox);
        controlPanel.add(new JLabel("Crossover Method:"));
        controlPanel.add(crossoverMethodComboBox);
        controlPanel.add(new JLabel("Mutation Method:"));
        controlPanel.add(mutationMethodComboBox);
        controlPanel.add(new JLabel("Problem Type:"));
        controlPanel.add(individualTypeComboBox);
        controlPanel.add(new JLabel("Max tree depth (on initialization):"));
        controlPanel.add(maxTreeDepthField);
        controlPanel.add(new JLabel("Steps limit (actions from terminal nodes):"));
        controlPanel.add(stepsLimitField);
        controlPanel.add(new JLabel("Bloating:"));
        controlPanel.add(bloatingField);

        // Botones de control
        JButton startButton = new JButton("Start");
        startButton.addActionListener(e -> startAlgorithm());
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(e -> resetFields());
        JButton exportButton = new JButton("Export Results");
        exportButton.addActionListener(e -> exportResults());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(startButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(exportButton);

        // Área de texto para mostrar resultados
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.setBorder(BorderFactory.createTitledBorder("Results"));
        resultsArea = new JTextArea(10, 2);
        resultsArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultsArea);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel para graficar la evolución
        plotPanel = new Plot2DPanel();
        plotPanel.setBorder(BorderFactory.createTitledBorder("Evolution Plot"));
        plotPanel.addLegend("SOUTH");

        // Añadir paneles al principal
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(resultsPanel, BorderLayout.SOUTH);

        // Panel con pestañas: uno para el gráfico y otro para el mapa gráfico
        JTabbedPane tabPane = new JTabbedPane();
        tabPane.addTab("GRAFICO", plotPanel);

        // Creamos el mapa y el MapPanel para visualizarla.
        mapa = new SantaFeMap();
        // Inicialmente sin ruta, se actualizará al finalizar la ejecución.
        mapPanelGraphics = new FoodMapPanel(mapa);
        mapPanelGraphics.setPreferredSize(new Dimension(500, 500));

        JPanel mapaPanel = new JPanel(new BorderLayout());
        mapaPanel.add(mapPanelGraphics, BorderLayout.CENTER);
        tabPane.addTab("MAPA", mapaPanel);

        // Panel para mostrar la expresión del árbol
        treeExpressionArea = new JTextArea();
        treeExpressionArea.setEditable(false);
        treeExpressionArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane treeScrollPane = new JScrollPane(treeExpressionArea);
        treeScrollPane.setBorder(BorderFactory.createTitledBorder("Solution Tree Expression"));

        JPanel treePanel = new JPanel(new BorderLayout());
        treePanel.add(treeScrollPane, BorderLayout.CENTER);
        tabPane.addTab("ÁRBOL", treePanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, mainPanel, tabPane);
        splitPane.setResizeWeight(0.5);
        add(splitPane);
    }

    /**
     * Ejecuta el algoritmo genético y actualiza la interfaz con la mejor ruta obtenida.
     */
    private void startAlgorithm() {
        try {
            int populationSize = Integer.parseInt(populationSizeField.getText());
            int generations = Integer.parseInt(generationsField.getText());
            double mutationRate = Double.parseDouble(mutationRateField.getText());
            double crossoverRate = Double.parseDouble(crossoverRateField.getText());
            double elitismRate = Double.parseDouble(elitismRateField.getText());
            int maxDepth = Integer.parseInt(maxTreeDepthField.getText());
            if (maxDepth < 2) {
                throw new RuntimeException("MAX DEPTH should be greater than 1 (>= 2)");
            }
            int stepsLimit = Integer.parseInt(stepsLimitField.getText());

            this.mapa = getSelectedMapa();
            mapPanelGraphics.setMansion(mapa);
            AbstractInitializer initializationMethod = getInitializationMethod(maxDepth);
            IndividuoFactory factory = new IndividuoHormigaFactory(this.mapa, stepsLimit, initializationMethod);
            AbstractSelection selectionMethod = getSelectionMethod(factory);
            AbstractCross crossoverMethod = getCrossoverMethod(factory);
            AbstractMutate mutationMethod = getMutationMethod(mutationRate);

            AlgoritmoGenetico algorithm = new AlgoritmoGenetico(factory, populationSize);
            algorithm.setMaxGeneraciones(generations);
            algorithm.setProbCruce(crossoverRate);
            algorithm.setElitismRate(elitismRate);
            algorithm.setSelectionMethod(selectionMethod);
            algorithm.setCrossoverMethod(crossoverMethod);
            algorithm.setMutationMethod(mutationMethod);
            
            algorithm.setProgressListener((generation, bestIndividual) -> {
                IndividuoHormiga bestAnt = (IndividuoHormiga) bestIndividual;
                
                SwingUtilities.invokeLater(() -> {
                    // Actualizar panel con el mejor individuo de cada generación
                    mapPanelGraphics.updateAntData(
                        bestAnt.getCurrentPosition(),
                        bestAnt.getCurrentDirection(),
                        (int) bestAnt.getOriginalFitness(),
                        bestAnt.getStepsTaken(),
                        bestAnt.getPathHistory()
                    );
                    
                    // Actualizar gráficas de progreso
                    plotAlgorithmResults(algorithm, generation + 1);
                });
            });
            Instant start = Instant.now();
            algorithm.optimize();
            Instant end = Instant.now();
            Duration timeElapsed = Duration.between(start, end);

            IndividuoHormiga bestIndividual = (IndividuoHormiga) algorithm.getMejor();
            
            // Actualizar visualización con la mejor solución
            mapPanelGraphics.updateAntData(
                bestIndividual.getCurrentPosition(),
                bestIndividual.getCurrentDirection(),
                (int) bestIndividual.getOriginalFitness(),
                bestIndividual.getStepsTaken(),
                bestIndividual.getPathHistory()
            );

            // Mostrar resultados en el área de texto
            StringBuilder sb = new StringBuilder();
            sb.append("=== MEJOR SOLUCIÓN ENCONTRADA ===\n");
            sb.append("Comida recolectada: ").append((int) bestIndividual.getOriginalFitness()).append("/").append(mapa.getAllFoodCount()).append("\n");
            sb.append("Pasos utilizados: ").append(bestIndividual.getStepsTaken()).append("/").append(stepsLimit).append("\n");
            sb.append("Fitness (incluye bloating): ").append(bestIndividual.getFitness()).append("\n");
            sb.append("Tiempo de ejecución: ").append(timeElapsed.toMillis() / 1000.0).append(" segundos\n");
            sb.append("Profundidad del árbol: ").append(bestIndividual.getTreeDepth()).append("\n");
            sb.append("Nodos del árbol: ").append(bestIndividual.getNodeCount()).append("\n");
            
            resultsArea.setText(sb.toString());

            // Actualizar panel del árbol con la expresión
            treeExpressionArea.setText(bestIndividual.getExpressionString());

            // Graficar resultados finales
            plotAlgorithmResults(algorithm, generations);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Obtiene el mapa seleccionado
     */
    private AbstractFoodMap getSelectedMapa() {
        int individualType = individualTypeComboBox.getSelectedIndex();
        switch (individualType) {
            case 0:
                return new SantaFeMap();
            default:
                throw new IllegalArgumentException("Mapa no válido");
        }
    }

    /**
     * Obtiene el método de inicializacion según la opción elegida.
     */
    private AbstractInitializer getInitializationMethod(int maxDepth) {
        int initType = initMethodComboBox.getSelectedIndex();
        switch (initType) {
            case 0:
                return new FULLInitializer(maxDepth);
            case 1:
                return new GrowInitializer(maxDepth);
            case 2:
            	return new RampedHalfInitializer(maxDepth);
            default:
                throw new IllegalArgumentException("Método de mutación no válido");
        }
    }

    /**
     * Obtiene el método de selección según la opción elegida.
     */
    private AbstractSelection getSelectionMethod(IndividuoFactory factory) {
        int selectionType = selectionMethodComboBox.getSelectedIndex();
        switch (selectionType) {
            case 0:
                return new RouletteSelection(factory);
            case 1:
                return new TorneoSelection(factory, 3, true);
            case 2:
                return new TorneoSelection(factory, 3, false);
            case 3:
                return new StochasticUniversalSelection(factory);
            case 4:
                return new TruncationSelection(factory, 0.5);
            case 5:
                return new RemainderRouletteSelection(factory);
            case 6:
                return new RankingSelection(factory);
            default:
                throw new IllegalArgumentException("Método de selección no válido");
        }
    }

    /**
     * Obtiene el método de cruce según la opción elegida.
     */
    private AbstractCross getCrossoverMethod(IndividuoFactory factory) {
        int crossoverType = crossoverMethodComboBox.getSelectedIndex();
        switch (crossoverType) {
            case 0:
                return new SubtreeSwapCross(factory);
            default:
                throw new IllegalArgumentException("Método de cruce no válido");
        }
    }

    /**
     * Obtiene el método de mutación según la opción elegida.
     */
    private AbstractMutate getMutationMethod(double mutationRate) {
        int mutationType = mutationMethodComboBox.getSelectedIndex();
        switch (mutationType) {
            case 0:
                return new TerminalMutate(mutationRate);
            case 1: 
            	return new FunctionalMutate(mutationRate);
            case 2: 
            	return new TreeMutate(mutationRate, Integer.parseInt(maxTreeDepthField.getText()));
            case 3: 
            	return new PermutationMutate(mutationRate);
            case 4: 
            	return new HoistMutate(mutationRate);
            case 5: 
            	return new ExpansionMutate(mutationRate, Integer.parseInt(maxTreeDepthField.getText()));
            case 6: 
            	return new ContractionMutate(mutationRate);
            default:
                throw new IllegalArgumentException("Método de mutación no válido");
        }
    }

    /**
     * Grafica la evolución del algoritmo para cada generación.
     */
    private void plotAlgorithmResults(AlgoritmoGenetico algorithm, int generations) {
        double[] generationNumbers = new double[generations];
        for (int i = 0; i < generations; i++) {
            generationNumbers[i] = i;
        }
        double[] bestFitness = algorithm.getBestFitnessHistory();
        double[] averageFitness = algorithm.getAverageFitnessHistory();
        double[] absoluteBest = algorithm.getAbsoluteBestHistory();

        plotPanel.removeAllPlots();
        plotPanel.setAxisLabels("Generación", "Fitness");
        plotPanel.addLinePlot("Mejor Absoluto", Color.BLUE, generationNumbers, absoluteBest);
        plotPanel.addLinePlot("Mejor Generación", Color.RED, generationNumbers, bestFitness);
        plotPanel.addLinePlot("Media Generación", Color.GREEN, generationNumbers, averageFitness);
    }

    /**
     * Resetea los campos de la interfaz.
     */
    private void resetFields() {
        populationSizeField.setText("100");
        generationsField.setText("300");
        mutationRateField.setText("0.2");
        crossoverRateField.setText("0.6");
        elitismRateField.setText("0.1");
        maxTreeDepthField.setText("2");
        stepsLimitField.setText("400");
        bloatingField.setText("0");
        initMethodComboBox.setSelectedIndex(0);
        selectionMethodComboBox.setSelectedIndex(0);
        crossoverMethodComboBox.setSelectedIndex(0);
        mutationMethodComboBox.setSelectedIndex(0);
        individualTypeComboBox.setSelectedIndex(0);
        resultsArea.setText("");
        treeExpressionArea.setText("");
        plotPanel.removeAllPlots();
        mapa = new SantaFeMap();
        mapPanelGraphics.setMansion(mapa);
        mapPanelGraphics.updateAntData(null, null, 0, 0, null);
    }

    /**
     * Exporta los resultados a un archivo.
     */
    private void exportResults() {
        JFileChooser fileChooser = new JFileChooser();
        int option = fileChooser.showSaveDialog(this);
        if (option == JFileChooser.APPROVE_OPTION) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile()))) {
                writer.write(resultsArea.getText());
                JOptionPane.showMessageDialog(this, "Los resultados se exportaron correctamente!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error al exportar los resultados", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    /**
     * Método principal.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            Main frame = new Main();
            frame.setVisible(true);
        });
    }
}