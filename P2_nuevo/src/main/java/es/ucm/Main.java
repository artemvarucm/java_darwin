package es.ucm;

import es.ucm.factories.*;
import es.ucm.individuos.Individuo;
import es.ucm.mansion.MansionMap;
import es.ucm.mansion.objects.Room;
import es.ucm.mutation.*;
import es.ucm.selection.*;
import es.ucm.cross.*;
import org.math.plot.Plot2DPanel;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
    private JTextField turnPenaltyField;
    private JTextField obstaclePenaltyField;

    // ComboBox para seleccionar métodos de selección, cruce, mutación y tipo de individuo
    private JComboBox<String> selectionMethodComboBox;
    private JComboBox<String> crossoverMethodComboBox;
    private JComboBox<String> mutationMethodComboBox;
    private JComboBox<String> individualTypeComboBox;

    // Área de texto para mostrar resultados
    private JTextArea resultsArea;

    // Panel para graficar la evolución del algoritmo
    private Plot2DPanel plotPanel;

    // Panel para representar el mapa con gráficos
    private MansionMapPanel mapPanelGraphics;

    // Referencia al objeto MansionMap (para poder actualizar el panel cada vez que se calcule la ruta)
    private MansionMap mansion;

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
        generationsField = new JTextField("100");
        mutationRateField = new JTextField("0.05");
        crossoverRateField = new JTextField("0.6");
        elitismRateField = new JTextField("0.1");
        turnPenaltyField = new JTextField("0");
        obstaclePenaltyField = new JTextField("0");

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
            "OX (Orden)", 
            "PMX (Emparejamiento parcial)", 
            "OXPP (Orden pos. prioritario)", 
            "CX (Ciclos)", 
            "CO (Cod. ordinal)", 
            "ERX (Recomb. ruletas)", 
            "Custom Cross"
        });
        mutationMethodComboBox = new JComboBox<>(new String[]{
            "Swap", 
            "Insertion", 
            "Inversion", 
            "Heuristic (n = 3)",
            "Custom Mutation"
        });
        individualTypeComboBox = new JComboBox<>(new String[]{"Problema 1"});

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
        controlPanel.add(new JLabel("Selection Method:"));
        controlPanel.add(selectionMethodComboBox);
        controlPanel.add(new JLabel("Crossover Method:"));
        controlPanel.add(crossoverMethodComboBox);
        controlPanel.add(new JLabel("Mutation Method:"));
        controlPanel.add(mutationMethodComboBox);
        controlPanel.add(new JLabel("Problem Type:"));
        controlPanel.add(individualTypeComboBox);
        controlPanel.add(new JLabel("FITNESS ADJUSTMENT"));
        controlPanel.add(new JLabel(""));
        controlPanel.add(new JLabel("Turn penalty:"));
        controlPanel.add(turnPenaltyField);
        controlPanel.add(new JLabel("Obstacle penalty:"));
        controlPanel.add(obstaclePenaltyField);
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

        // Creamos la mansión y el MapPanel para visualizarla.
        mansion = new MansionMap();
        // Inicialmente sin ruta, se actualizará al finalizar la ejecución.
        mapPanelGraphics = new MansionMapPanel(mansion, null);
        mapPanelGraphics.setPreferredSize(new Dimension(500, 500));

        JPanel mapaPanel = new JPanel(new BorderLayout());
        mapaPanel.add(mapPanelGraphics, BorderLayout.CENTER);
        tabPane.addTab("MAPA", mapaPanel);

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
            double turnPenalty = Double.parseDouble(turnPenaltyField.getText());
            double obstaclePenalty = Double.parseDouble(obstaclePenaltyField.getText());
            this.mansion.setTurnPenalty(turnPenalty);
            this.mansion.setObstaclePenalty(obstaclePenalty);
            int individualType = individualTypeComboBox.getSelectedIndex();

            IndividuoFactory factory = getIndividuoFactory(individualType);
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
            algorithm.optimize();

            Individuo bestIndividual = algorithm.getMejor();
            StringBuilder sb = new StringBuilder();
            sb.append("Mejor ruta encontrada:\n");
            sb.append("Fitness = ").append(bestIndividual.getFitness()).append("\n");
            sb.append("Fitness (sin penalizaciones) = ").append(mansion.calculateFitness(bestIndividual.getFenotipos())).append("\n");
            sb.append("Ruta: ").append(bestIndividual.getSolutionString()).append("\n");
            //sb.append("------------------------------------------------------------------\n");
            resultsArea.setText(sb.toString());

            // Actualizamos el mapa gráfico con la ruta calculada
            List<Number> roomOrder = bestIndividual.getFenotipos();
            mapPanelGraphics.setRouteCells(mansion.calculatePath(roomOrder));

            // Graficar evolución del algoritmo
            plotAlgorithmResults(algorithm, generations);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }

    /**
     * Busca una habitación en el mapa según su id.
     */
    private Room findRoomById(MansionMap mansion, int roomId) {
        for (Room r : mansion.getRooms()) {
            if (r.getId() == roomId)
                return r;
        }
        return null;
    }

    /**
     * Obtiene la fábrica de individuos según el tipo seleccionado.
     */
    private IndividuoFactory getIndividuoFactory(int individualType) {
        switch (individualType) {
            case 0:
                return new IndividuoAspiradoraFactory(this.mansion);
            default:
                throw new IllegalArgumentException("Tipo de individuo no válido");
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
                return new OXCross(factory);
            case 1:
                return new PMXCross(factory);
            case 2:
                return new OXPPCross(factory);
            case 3:
                return new CXCross(factory);
            case 4:
                return new COCross(factory);
            case 5:
                return new ERXCross(factory);
            case 6:
                return new CustomCross(factory);
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
                return new SwapMutate(mutationRate);
            case 1:
                return new InsertionMutate(mutationRate);
            case 2:
                return new InversionMutate(mutationRate);
            case 3:
                return new HeuristicMutate(mutationRate);
            case 4:
                return new ScrambleMutate(mutationRate);
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
        generationsField.setText("100");
        mutationRateField.setText("0.05");
        crossoverRateField.setText("0.6");
        elitismRateField.setText("0.1");
        turnPenaltyField.setText("0");
        obstaclePenaltyField.setText("0");
        selectionMethodComboBox.setSelectedIndex(0);
        crossoverMethodComboBox.setSelectedIndex(0);
        mutationMethodComboBox.setSelectedIndex(0);
        individualTypeComboBox.setSelectedIndex(0);
        resultsArea.setText("");
        mapPanelGraphics.setRouteCells(null);
        plotPanel.removeAllPlots();
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