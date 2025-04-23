package es.ucm.individuos.grammar;

import es.ucm.individuos.tree.*;

import java.util.*;

public class Grammar {
    private final List<String> grammarBNFClauses = List.of(
        "<start> ::= <code>",
        "<code> ::= <line> | <code> <line>",
        "<line> ::= <if_food> | <action>",
        "<if_food> ::= if_food <line> <line>",
        "<action> ::= left | right | forward"
    );

    private final Map<String, List<List<String>>> grammarBNFMap;
    private int wrapsUsed = 0;

    public Grammar() {
        grammarBNFMap = new HashMap<>();
        for (String clause : grammarBNFClauses) {
            String[] equalSep = clause.split(" ::= ");
            List<String> orSep = List.of(equalSep[1].split(" \\| "));
            grammarBNFMap.put(equalSep[0], orSep.stream()
                    .map(el -> List.of(el.trim().split(" ")))
                    .toList());
        }
    }

    public AbstractNode decode(List<Integer> codons, int nWraps) {
        wrapsUsed = 0;
        IndexWrapper index = new IndexWrapper(0);
        return decodeRecursive(codons, "<start>", index, nWraps);
    }

    private AbstractNode decodeRecursive(List<Integer> codons, String symbol, IndexWrapper index, int nWraps) {
        if (symbol.equals("left")) return new LeftNode();
        if (symbol.equals("right")) return new RightNode();
        if (symbol.equals("forward")) return new ForwardNode();

        // No terminal
        List<List<String>> productions = grammarBNFMap.get(symbol);
        if (productions == null || productions.isEmpty()) return null;

        if (index.get() >= codons.size()) {
            if (wrapsUsed < nWraps) {
                wrapsUsed++;
                index.set(0);
            } else {
                return null;
            }
        }

        int choice = codons.get(index.get()) % productions.size();
        List<String> chosenProd = productions.get(choice);
        index.increment();

        // Crear nodo funcional o lista de nodos
        if (symbol.equals("<if_food>")) {
            // Esperamos: [if_food, <line>, <line>]
            AbstractNode cond1 = decodeRecursive(codons, chosenProd.get(1), index, nWraps);
            AbstractNode cond2 = decodeRecursive(codons, chosenProd.get(2), index, nWraps);
            return new IfFoodNode(cond1, cond2);
        } else if (symbol.equals("<code>") || symbol.equals("<start>") || symbol.equals("<line>")) {
            AbstractNode codeNode = new CompositeNode(symbol);
            for (String sym : chosenProd) {
                AbstractNode child = decodeRecursive(codons, sym, index, nWraps);
                if (child != null) {
                    codeNode.getChildNodes().add(child);
                    child.setParentNode(codeNode);
                }
            }
            return codeNode;
        }

        return null;
    }

    // Wrapper para índice mutable
    private static class IndexWrapper {
        private int i;
        public IndexWrapper(int i) { this.i = i; }
        public int get() { return i; }
        public void set(int value) { i = value; }
        public void increment() { i++; }
    }

    // Nodo para varios hijos si no es funcional específico
    private static class CompositeNode extends AbstractNode {
        private final String name;
        public CompositeNode(String name) {
            this.name = name;
        }

        @Override
        public List<Coord> walkAndReturnCoords(Hormiga hormiga) {
            List<Coord> coords = new LinkedList<>();
            for (AbstractNode child : childNodes) {
                coords.addAll(child.walkAndReturnCoords(hormiga));
            }
            return coords;
        }

        @Override
        public AbstractNode clone() {
            CompositeNode clone = new CompositeNode(name);
            this.copyChildrenToClone(clone);
            return clone;
        }

        @Override
        public String getNodeName() {
            return name.toUpperCase();
        }
        @Override
        public Integer getChildrenSize() {
            return 0;
        }
    }
}
