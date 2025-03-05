   package es.ucm.individuos;

   import es.ucm.mansion.MansionMap;
   import es.ucm.mansion.PathFinder;
   import es.ucm.mansion.Room;
   import java.util.ArrayList;
   import java.util.Collections;
   import java.util.List;

   public class IndividuoRuta extends Individuo {
       // El cromosoma es una lista con la permutación de números de 1 a 20
       private List<Integer> cromosoma;
       // Bandera para indicar que se busca minimizar la distancia (fitness menor es mejor)
       public boolean maximizar = false;
       
       // Para el cálculo de fitness se utilizan la mansión y el A*
       private static MansionMap mansion = new MansionMap();
       private static PathFinder pathFinder = new PathFinder(mansion);
       // Valor de penalización para segmentos sin camino
       private static final double PENALIZACION = 10000;

       public IndividuoRuta() {
           // Llamada al constructor de la clase base
           super(1.0, false);
           // Se genera una permutación aleatoria de números de 1 a 20
           cromosoma = new ArrayList<>();
           for (int i = 1; i <= 20; i++) {
               cromosoma.add(i);
           }
           Collections.shuffle(cromosoma);
       }

       // Constructor copia interna
       public IndividuoRuta(List<Integer> cromosoma) {
           // Llamada al constructor de la clase base
           super(1.0, false);
           this.cromosoma = new ArrayList<>(cromosoma);
       }

       public List<Integer> getCromosoma() {
           return cromosoma;
       }

       @Override
       public double getFitness() {
           double total = 0;
           // Coordenadas base
           int baseRow = mansion.getBaseRow();
           int baseCol = mansion.getBaseCol();
           // Obtención de la lista de habitaciones (con id y coordenadas)
           List<Room> rooms = mansion.getRooms();

           // Primer segmento: base a la primera habitación
           Room nextRoom = rooms.get(cromosoma.get(0) - 1);
           double seg = segmentoDistance(baseRow, baseCol, nextRoom.getRow(), nextRoom.getCol());
           total += seg;

           // Segmentos intermedios
           for (int i = 0; i < cromosoma.size() - 1; i++) {
               Room r1 = rooms.get(cromosoma.get(i) - 1);
               Room r2 = rooms.get(cromosoma.get(i + 1) - 1);
               seg = segmentoDistance(r1.getRow(), r1.getCol(), r2.getRow(), r2.getCol());
               total += seg;
           }
           // Último segmento: última habitación -> base
           Room lastRoom = rooms.get(cromosoma.get(cromosoma.size() - 1) - 1);
           seg = segmentoDistance(lastRoom.getRow(), lastRoom.getCol(), baseRow, baseCol);
           total += seg;

           return total;
       }

       private double segmentoDistance(int startRow, int startCol, int goalRow, int goalCol) {
           List<int[]> path = pathFinder.findPath(startRow, startCol, goalRow, goalCol);
           if (path == null) {
               return PENALIZACION;
           } else {
               // La distancia es (número de pasos - 1)
               return path.size() - 1;
           }
       }

       @Override
       public Individuo copy() {
           return new IndividuoRuta(cromosoma);
       }

       public void printCromosoma() {
           for (Integer val : cromosoma) {
               System.out.print(val + " ");
           }
           System.out.println();
       }
   }