//package es.ucm.mansion;
//
//import org.w3c.dom.Node;
//
//import java.util.*;
//
//public class PathFinder {
//    private MansionMap map;
//
//    public PathFinder(MansionMap map) {
//        this.map = map;
//    }
//
//    // Nodo interno para A*
//    private class Node {
//        int row, col;
//        double g; // costo desde el inicio
//        double f; // g + h
//        Node parent;
//
//        Node(int row, int col, double g, double f, Node parent) {
//            this.row = row;
//            this.col = col;
//            this.g = g;
//            this.f = f;
//            this.parent = parent;
//        }
//
//        public int hashCode() {
//            return Objects.hash(row, col);
//        }
//
//        public boolean equals(Object o) {
//            if (!(o instanceof Node)) return false;
//            Node other = (Node) o;
//            return this.row == other.row && this.col == other.col;
//        }
//    }
//
//    /**
//     * Calcula el camino más corto usando A* desde (startRow,startCol) a (goalRow,goalCol).
//     * Si no existe camino, retorna null.
//     */
//    public List<int[]> findPath(int startRow, int startCol, int goalRow, int goalCol) {
//        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
//        Set<Node> closed = new HashSet<>();
//
//        Node start = new Node(startRow, startCol, 0, heuristic(startRow, startCol, goalRow, goalCol), null);
//        open.add(start);
//
//        while (!open.isEmpty()) {
//            Node current = open.poll();
//            if (current.row == goalRow && current.col == goalCol) {
//                return reconstructPath(current);
//            }
//            closed.add(current);
//
//            for (int[] dir : directions()) {
//                int neighborRow = current.row + dir[0];
//                int neighborCol = current.col + dir[1];
//                if (!isValid(neighborRow, neighborCol)) continue;
//                if (isObstacle(neighborRow, neighborCol)) continue;
//
//                double tentativeG = current.g + 1; // costo uniforme
//                Node neighbor = new Node(neighborRow, neighborCol, tentativeG,
//                        tentativeG + heuristic(neighborRow, neighborCol, goalRow, goalCol), current);
//
//                // Si ya fue evaluado con un menor costo, se ignora
//                boolean skip = false;
//                for (Node closedNode : closed) {
//                    if (closedNode.equals(neighbor) && tentativeG >= closedNode.g) {
//                        skip = true;
//                        break;
//                    }
//                }
//                if (skip) continue;
//                open.add(neighbor);
//            }
//        }
//        // No se encontró camino
//        return null;
//    }
//
//    // Reconstruye el camino (lista de coordenadas [fila, columna]) desde el nodo final
//    private List<int[]> reconstructPath(Node node) {
//        List<int[]> path = new ArrayList<>();
//        while (node != null) {
//            path.add(new int[]{node.row, node.col});
//            node = node.parent;
//        }
//        Collections.reverse(path);
//        return path;
//    }
//
//    // Direcciones: arriba, abajo, izquierda, derecha
//    private List<int[]> directions() {
//        List<int[]> dirs = new ArrayList<>();
//        dirs.add(new int[]{-1, 0});
//        dirs.add(new int[]{1, 0});
//        dirs.add(new int[]{0, -1});
//        dirs.add(new int[]{0, 1});
//        return dirs;
//    }
//
//    private boolean isValid(int row, int col) {
//        return row >= 0 && row < MansionMap.ROWS && col >= 0 && col < MansionMap.COLS;
//    }
//
//    private boolean isObstacle(int row, int col) {
//        // Se considera obstáculo si la celda contiene el OBSTACLE_CHAR
//        char[][] grid = map.getGridCopy();
//        return grid[row][col] == MansionMap.OBSTACLE_CHAR;
//    }
//
//    // Heurística: distancia Manhattan
//    private double heuristic(int row, int col, int goalRow, int goalCol) {
//        return Math.abs(row - goalRow) + Math.abs(col - goalCol);
//    }
//}